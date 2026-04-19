package com.n1solution.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderStatusUpdate(String toEmail, String orderType, String newStatus) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Update on your N1Solution Order: " + orderType);
            
            String content = "<html>" +
                "<body style='font-family: Arial, sans-serif; color: #333; line-height: 1.6;'>" +
                "<div style='max-width: 600px; margin: 0 auto; border: 1px solid #e2e8f0; border-radius: 8px; overflow: hidden;'>" +
                "<div style='background: linear-gradient(to right, #2563eb, #7c3aed); padding: 20px; text-align: center; color: white;'>" +
                "<h1 style='margin: 0;'>Order Status Update</h1>" +
                "</div>" +
                "<div style='padding: 30px;'>" +
                "<p>Hello,</p>" +
                "<p>Your order for <strong>" + orderType + "</strong> has been updated.</p>" +
                "<div style='background-color: #f8fafc; padding: 20px; border-radius: 8px; margin: 20px 0; border: 1px solid #eef2f6;'>" +
                "<p style='margin: 0; font-size: 14px; color: #64748b;'>New Status:</p>" +
                "<p style='margin: 5px 0 0; font-size: 24px; font-weight: bold; color: #1e293b;'>" + newStatus + "</p>" +
                "</div>" +
                "<p>You can track the progress of your order in your <a href='https://n1-frontend.vercel.app/my-orders' style='color: #2563eb; text-decoration: none; font-weight: bold;'>Customer Dashboard</a>.</p>" +
                "<p>If you have any questions, feel free to reply to this email or call us at +91 93992 85780.</p>" +
                "</div>" +
                "<div style='background-color: #f1f5f9; padding: 20px; text-align: center; font-size: 12px; color: #64748b;'>" +
                "&copy; 2024 N1Solution. All rights reserved." +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";

            helper.setText(content, true);
            mailSender.send(message);
            System.out.println("Status update email sent successfully to: " + toEmail);
            
        } catch (MessagingException e) {
            System.err.println("Failed to send status update email to: " + toEmail);
            e.printStackTrace();
        }
    }
}
