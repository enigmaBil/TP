package com.enigma.tp_api_rest.infrastructure.notification;

import com.enigma.tp_api_rest.infrastructure.common.exceptions.InternalErrorException;
import com.enigma.tp_api_rest.infrastructure.database.entities.User;
import com.enigma.tp_api_rest.infrastructure.database.exceptions.BusinessMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${app.mail.from}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    @Async
    public void sendEmailVerification(String toEmail, String verificationToken, User user) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Verify your email address");

            // Construction du contexte pour le template Thymeleaf
            Context context = new Context();
            context.setVariable("userName", user.getEmail());
            context.setVariable("verificationUrl", baseUrl + "/api/v1/auth/verify-email?token=" + verificationToken);

            //traitement du template
            String htmlContent = templateEngine.process("verification-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Verification email sent successfully to: {}",toEmail);
        } catch (MessagingException e) {
            log.error("Failed to send verification email to {}", toEmail, e);
            throw new InternalErrorException(BusinessMessage.INTERNAL_ERROR, e.getMessage());
        }
    }
}
