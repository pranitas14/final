package com.example.Event.Management.Service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email with a PDF attachment.
     *
     * @param to                 the recipient's email address
     * @param subject            the subject of the email
     * @param text               the content of the email (HTML enabled)
     * @param attachmentData     the byte array representing the PDF file
     * @param attachmentFilename the name of the attached file
     * @throws MessagingException if there is an error while sending the email
     */
    public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachmentData, String attachmentFilename) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // true to enable HTML content

            // Attach the PDF file
            ByteArrayDataSource dataSource = new ByteArrayDataSource(attachmentData, "application/pdf");
            helper.addAttachment(attachmentFilename, dataSource);

            mailSender.send(message);
            logger.info("Email sent successfully to {}", to);

        } catch (MessagingException e) {
            logger.error("Failed to send email to {}. Error: {}", to, e.getMessage());
            throw e;
        }
    }
}
