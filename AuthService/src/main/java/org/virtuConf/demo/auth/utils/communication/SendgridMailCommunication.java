package org.virtuConf.demo.auth.utils.communication;

import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendgridMailCommunication implements MailCommunication{
    @Value("${sendgrid.apikey}")
    private String API_KEY;
    @Override
    public void sendMailForEmailVerification(String mail , String otp) throws IOException {
        Email from = new Email("ganeshdagadi3@gmail.com");
        String subject = "Verify Email for registration";
        Email to = new Email(mail);
        Content content = new Content("text/plain", "OTP : " + otp);
        Mail sendgridMail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(sendgridMail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            Integer statusCode = response.getStatusCode();
            if(statusCode != 200 && statusCode != 202) throw new IOException("Unable to send mail. Check mail");
        } catch (IOException ex) {
            throw new IOException("unable to send mail");
        }
    }
}
