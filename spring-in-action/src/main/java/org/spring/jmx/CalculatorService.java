package org.spring.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class CalculatorService extends NotificationBroadcasterSupport
        implements CalculatorServiceMXBean {

    private int maxSupport = Integer.MAX_VALUE;
    private Long sequenceNumber = 0L;

    @Override
    public int getMaxSupport() {
        return maxSupport;
    }

    @Override
    public void setMaxSupport(int newMaxSupport) {
        Notification n =
                new AttributeChangeNotification(this,
                        sequenceNumber++,
                        System.currentTimeMillis(),
                        "MaxSupport changed",
                        "Maximum Support Integer",
                        "int",
                        this.maxSupport,
                        newMaxSupport);
        sendNotification(n);
        this.maxSupport = newMaxSupport;
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] {
                AttributeChangeNotification.ATTRIBUTE_CHANGE
        };
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info =
                new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] {info};
    }




}
