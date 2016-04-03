package com.viacheslavinozemtsev.cechallenge;

public class ConsoleEMailSender implements EMailSender {
    public void sendEMail(EMail eMail) {
        if (eMail == null) {
            throw new IllegalArgumentException("eMail can't be null!");
        }
        System.out.println("Sending: " + eMail.toString());
    }
}
