package net.javaguides.springboot.security;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.model.Verification;
import net.javaguides.springboot.repository.VerificationRepository;

@Component
public class userVerification {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VerificationRepository verificationRepository;

    String verificationCode;

    public void sendVerificationMail(User user) {

        verificationCode = String.format("%06d", (int)(Math.random() * 1000000));

        Verification verification = new Verification("",false,user.getEmail());
        verification.setVerificationCode(verificationCode);
        verificationRepository.save(verification);

        String subject = "Your verification code for EmployeeLogs";
        String senderName = "EmployeeLogs";
        String mailContent = "<p>Dear " + user.getEmpId() + ",</p>";
               mailContent += "<p>Your verification code is:</p>";
               mailContent += "<h2 style='color:blue;'>" + verificationCode + "</h2>";
               mailContent += "<p>Please enter this code in the app to verify your email address.</p>";
               mailContent += "<br><p>Thank you,<br>The EmployeeLogs Team</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            helper.setFrom("thathyaniweerasinghe@gmail.com",senderName);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent,true);
            mailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
}
