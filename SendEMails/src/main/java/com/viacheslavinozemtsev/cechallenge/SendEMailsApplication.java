package com.viacheslavinozemtsev.cechallenge;

import java.util.Iterator;

public class SendEMailsApplication {
    public static void main(String[] args) {
        final String filepath = SendEMailsApplication.class.getClassLoader().getResource("localstoragefile.csv").getFile();
        final boolean header = true;
        final String delimiter = ",";

        final Storage storage = new CSVFileStorage(filepath, header, delimiter);
        final EMailSender eMailSender = new ConsoleEMailSender();

        System.out.println("Sending emails...");
        final Iterator<EMail> iterator = storage.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            final EMail eMail = iterator.next();
            if (eMail.isActive()) {
                eMailSender.sendEMail(eMail);
                ++count;
            }
        }
        System.out.println(count + " emails were successfully sent.");
    }
}
