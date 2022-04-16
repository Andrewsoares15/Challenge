package com.alura.challenge.domain.entity;

import com.alura.challenge.domain.DTOs.EmailDto;
import com.alura.challenge.domain.DTOs.StatusEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Email")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long emailId;
    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendDateEmail;
    @Enumerated(EnumType.STRING)
    private StatusEmail statusEmail;

    public Email(EmailDto emailDto) {
        this.ownerRef = emailDto.getOwnerRef();
        this.emailFrom = emailDto.getEmailFrom();
        this.emailTo = emailDto.getEmailTo();
        this.subject = emailDto.getSubject();
        this.text = emailDto.getText();
    }

    public void setStatus(StatusEmail sent) {
        this.statusEmail = sent;
    }
}