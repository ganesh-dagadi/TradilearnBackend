package org.virtuConf.demo.auth.utils.communication;

import java.io.IOException;

public interface MailCommunication {
    public void sendMailForEmailVerification(String email , String otp) throws IOException;
}
