package org.spring.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.InstanceNotFoundException;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Client {
    public static void main(String[] args) throws IOException, InstanceNotFoundException {
        echo("\nCreate an RMI connector client and " +
                "connect it to the RMI connector server");
        JMXServiceURL url =
                new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

        echo("\nGet an MBeanServerConnection");
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        echo("\nDomains:");
        String domains[] = mbsc.getDomains();
        Arrays.sort(domains);
        for (String domain : domains) {
            echo("\tDomain = " + domain);
        }

        echo("\nQuery MBeanServer MBeans:");
        Set<ObjectName> names =
                new TreeSet<ObjectName>(mbsc.queryNames(null, null));
        for (ObjectName name : names) {
            echo("\tObjectName = " + name);
        }

        ClientListener listener = new ClientListener();

        echo("\n>>> Perform operations on CalculatorServiceMBean <<<");

        ObjectName calculateMBean = null;
        try {
            calculateMBean = new ObjectName("spring.jmx:type=CalculatorService");
            mbsc.addNotificationListener(calculateMBean, listener, null, null);
            CalculatorServiceMXBean calculateMBeanProxy =
                    JMX.newMBeanProxy(mbsc, calculateMBean, CalculatorServiceMXBean.class, true);
            calculateMBeanProxy.setMaxSupport(10000);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        echo("\n>>> Perform operations on FileServiceMBean <<<");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName fileMBean = null;
        try {
            fileMBean = new ObjectName("spring.jmx:type=FileService");
            mbsc.addNotificationListener(fileMBean, listener, null, null);
            IFileService fileMBeanProxy =
                    JMX.newMBeanProxy(mbsc, fileMBean, IFileService.class, true);
            fileMBeanProxy.addFile("notification-filename", "notification-filepath");
            waitForEnterPressed();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }

    }

    public static class ClientListener implements NotificationListener {
        public void handleNotification(Notification notification,
                                       Object handback) {
            echo("\nReceived notification:");
            echo("\tClassName: " + notification.getClass()
                    .getName());
            echo("\tSource: " + notification.getSource());
            echo("\tType: " + notification.getType());
            echo("\tMessage: " + notification.getMessage());
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn =
                        (AttributeChangeNotification) notification;
                echo("\tAttributeName: " + acn.getAttributeName());
                echo("\tAttributeType: " + acn.getAttributeType());
                echo("\tNewValue: " + acn.getNewValue());
                echo("\tOldValue: " + acn.getOldValue());
            }
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void waitForEnterPressed() {
        try {
            echo("\nPress <Enter> to exit...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
