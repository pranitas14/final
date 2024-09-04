package com.example.Event.Management.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        try {
            mailSender.setHost("smtp.example.com");
            mailSender.setPort(587);
            mailSender.setUsername("pranitayshinde@gmail.com");
            mailSender.setPassword("pranitashinde1234");

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            logger.info("JavaMailSender bean configured successfully");
        } catch (Exception e) {
            logger.warn("Failed to configure JavaMailSender: {}", e.getMessage());
        }

        return mailSender;
    }
}
