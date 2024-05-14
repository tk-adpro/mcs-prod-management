package id.ac.ui.cs.advprog.eshop.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableAsync
public class McsProdManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(McsProdManagementApplication.class, args);
    }

}
