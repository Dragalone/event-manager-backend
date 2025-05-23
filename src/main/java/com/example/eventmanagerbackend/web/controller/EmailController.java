package com.example.eventmanagerbackend.web.controller;


import com.example.eventmanagerbackend.service.mail.EmailService;
import com.lowagie.text.DocumentException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/email")
@CrossOrigin()
@RequiredArgsConstructor
public class EmailController {

    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);
    private final EmailService emailService;

    @GetMapping(value = "/{memberId}")
    public @ResponseBody ResponseEntity sendEmail(@PathVariable UUID memberId) throws IOException {

        try {
            emailService.sendMailWithPdf(memberId);
        } catch (MailException | MessagingException mailException) {
            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            LOG.error("Error while sending out email..{}", mailException.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (DocumentException e) {
            LOG.error("Error while creating document..{}", e.getStackTrace());
            LOG.error("Error while creating document..{}", e.fillInStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }

    @GetMapping(value = "/greetings/{memberId}")
    public @ResponseBody ResponseEntity<String> sendGreetingsEmail(@PathVariable UUID memberId) {
        try {
            emailService.sendMail(memberId);
            return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
        } catch (MessagingException e) {
            LOG.error("Error while sending out email..", e);
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}