package com.github.slamdev.spring.cloud.stream.binder.nats;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.integration.endpoint.MessageProducerSupport;

@RequiredArgsConstructor
public class NatsInboundChannelAdapter extends MessageProducerSupport {

    private final String subject;
    private final String queue;
    private final Connection connection;
    private Dispatcher dispatcher;

    @Override
    protected void doStart() {
        super.doStart();
        dispatcher = connection.createDispatcher(this::consumeMessage);
        if (queue == null) {
            dispatcher.subscribe(subject);
        } else {
            dispatcher.subscribe(subject, queue);
        }
    }

    @Override
    protected void doStop() {
        if (dispatcher != null) {
            dispatcher.unsubscribe(subject);
            connection.closeDispatcher(dispatcher);
        }
        super.doStop();
    }

    @SneakyThrows
    private void consumeMessage(Message message) {
        sendMessage(getMessageBuilderFactory()
                .withPayload(message.getData())
                .build());
    }
}
