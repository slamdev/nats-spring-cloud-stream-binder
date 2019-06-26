package com.github.slamdev.spring.cloud.stream.binder.nats;

import io.nats.client.Connection;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;

@RequiredArgsConstructor
public class NatsMessageHandler extends AbstractMessageHandler {

    private final Connection connection;
    private final String subject;

    @Override
    protected void handleMessageInternal(Message<?> message) {
        connection.publish(subject, (byte[]) message.getPayload());
    }
}
