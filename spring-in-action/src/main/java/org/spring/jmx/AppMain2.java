package org.spring.jmx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@SpringBootApplication
@EnableMBeanExport
public class AppMain2 {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppMain2.class, args);
    }
}
