package com.shecancode.designpatterns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationMessageTest {

    @Test
    void shouldBuildValidNotification() {

        NotificationMessage message =
                new NotificationMessage.Builder()
                        .to("noella@example.com")
                        .subject("Welcome")
                        .body("Hello Noella")
                        .priority(Priority.HIGH)
                        .attach("file.pdf")
                        .build();

        assertEquals(
                "noella@example.com",
                message.getRecipient()
        );

        assertEquals(
                "Hello Noella",
                message.getBody()
        );

        assertEquals(
                Priority.HIGH,
                message.getPriority()
        );
    }

    @Test
    void shouldThrowExceptionWhenRecipientMissing() {

        assertThrows(
                IllegalStateException.class,
                () -> new NotificationMessage.Builder()
                        .body("Hello")
                        .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenBodyMissing() {

        assertThrows(
                IllegalStateException.class,
                () -> new NotificationMessage.Builder()
                        .to("noella@example.com")
                        .build()
        );
    }
}
