package com.alura.challenge.service;

import com.alura.challenge.domain.DTOs.EmailDto;
import com.alura.challenge.domain.DTOs.StatusEmail;
import com.alura.challenge.domain.entity.Email;
import com.alura.challenge.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public Email sendEmail(Email email) {
        email.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            javaMailSender.send(message);

            email.setStatus(StatusEmail.SENT);
        } catch (Exception e) {
            e.printStackTrace();
            email.setStatus(StatusEmail.ERROR);
        }finally {
            return emailRepository.save(email);
        }
    }
}
