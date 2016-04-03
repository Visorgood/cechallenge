package com.viacheslavinozemtsev.cechallenge;

public class EMail {
    private final String sender;
    private final String recipients;
    private final String subject;
    private final String body;
    private final boolean active;

    public EMail(String sender, String recipients, String subject, String body, boolean active) {
        this.sender = sender;
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
        this.active = active;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "EMail{" +
                "sender='" + sender + '\'' +
                ", recipients='" + recipients + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", active=" + active +
                '}';
    }
}
