package com.shecancode.designpatterns;

import java.util.ArrayList;
import java.util.List;

public class NotificationMessage {

    private final String recipient;
    private final String subject;
    private final String body;
    private final Priority priority;
    private final List<String> attachments;

    private NotificationMessage(Builder builder) {
        this.recipient = builder.recipient;
        this.subject = builder.subject;
        this.body = builder.body;
        this.priority = builder.priority;
        this.attachments = builder.attachments;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Priority getPriority() {
        return priority;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public static class Builder {

        private String recipient;
        private String subject;
        private String body;
        private Priority priority = Priority.LOW;
        private List<String> attachments = new ArrayList<>();

        public Builder to(String recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public Builder attach(String attachment) {
            this.attachments.add(attachment);
            return this;
        }

        public NotificationMessage build() {

            if (recipient == null || recipient.isBlank()) {
                throw new IllegalStateException("Recipient is required");
            }

            if (body == null || body.isBlank()) {
                throw new IllegalStateException("Body is required");
            }

            return new NotificationMessage(this);
        }
    }
}