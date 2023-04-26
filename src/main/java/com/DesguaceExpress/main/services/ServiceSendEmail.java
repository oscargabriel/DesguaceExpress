package com.DesguaceExpress.main.services;

import com.DesguaceExpress.main.dto.EmailBodySend;

import java.util.HashMap;

public interface ServiceSendEmail {

        public HashMap<String, String> SendEmail(EmailBodySend emailBodySend);
}
