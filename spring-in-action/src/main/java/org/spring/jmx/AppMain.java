package org.spring.jmx;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class AppMain {
    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName calculatorServiceMBeanName = new ObjectName("spring.jmx:type=CalculatorService");
        CalculatorService calculatorServiceMbean = new CalculatorService();
        mbs.registerMBean(calculatorServiceMbean, calculatorServiceMBeanName);
        System.out.println("Waiting for incoming requests...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
