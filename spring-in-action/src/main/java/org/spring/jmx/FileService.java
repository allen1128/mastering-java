package org.spring.jmx;

import javafx.util.Pair;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@ManagedResource(objectName = "spring.jmx:type=FileService", description = "File name.")
public class FileService implements NotificationPublisherAware, IFileService {
    private List<Pair> files = new ArrayList();
    private NotificationPublisher notificationPublisher;
    private AtomicLong notificationSequence = new AtomicLong();

    @ManagedOperation
    public List<Pair> getFiles() {
        return files;
    }

    @ManagedOperation
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "fileName", description = "The first number"),
            @ManagedOperationParameter(name = "filePath", description = "The second number")})
    public void addFile(String fileName, String filePath) {
        Pair newFile = new Pair(fileName, filePath);
        List oldFiles = files.stream().collect(Collectors.toList());
        files.add(newFile);

        if (notificationPublisher != null) {
            Notification notification =
                    new AttributeChangeNotification(this,
                            notificationSequence.getAndIncrement(),
                            System.currentTimeMillis(),
                            "file added",
                            "cached file paths",
                            "List",
                            oldFiles,
                            this.files);
            notificationPublisher.sendNotification(notification);
        } else {
            System.out.println("null notificationPublisher");
        }

    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }
}
