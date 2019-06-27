package com.github.slamdev.spring.cloud.stream.binder.nats;

import com.github.slamdev.spring.cloud.stream.binder.nats.properties.NatsConsumerProperties;
import com.github.slamdev.spring.cloud.stream.binder.nats.properties.NatsExtendedBindingProperties;
import com.github.slamdev.spring.cloud.stream.binder.nats.properties.NatsProducerProperties;
import com.github.slamdev.spring.cloud.stream.binder.nats.provisioning.NatsChannelProvisioner;
import io.nats.client.Connection;
import org.springframework.cloud.stream.binder.*;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

public class NatsMessageChannelBinder extends AbstractMessageChannelBinder<ExtendedConsumerProperties<NatsConsumerProperties>,
        ExtendedProducerProperties<NatsProducerProperties>,
        NatsChannelProvisioner> implements ExtendedPropertiesBinder<MessageChannel, NatsConsumerProperties,
        NatsProducerProperties> {

    private final Connection connection;
    private final NatsExtendedBindingProperties natsExtendedBindingProperties;

    public NatsMessageChannelBinder(NatsChannelProvisioner provisioningProvider, Connection connection,
                                    NatsExtendedBindingProperties natsExtendedBindingProperties) {

        super(null, provisioningProvider);
        this.connection = connection;
        this.natsExtendedBindingProperties = natsExtendedBindingProperties;
    }

    @Override
    protected MessageHandler createProducerMessageHandler(ProducerDestination destination,
                                                          ExtendedProducerProperties<NatsProducerProperties> producerProperties,
                                                          MessageChannel errorChannel) {
        NatsMessageHandler messageHandler = new NatsMessageHandler(connection, destination.getName());
        messageHandler.setBeanFactory(getBeanFactory());
        return messageHandler;
    }

    @Override
    protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group,
                                                     ExtendedConsumerProperties<NatsConsumerProperties> properties) {
        return new NatsInboundChannelAdapter(destination.getName(), group, connection);
    }

    @Override
    public NatsConsumerProperties getExtendedConsumerProperties(String channelName) {
        return natsExtendedBindingProperties.getExtendedConsumerProperties(channelName);
    }

    @Override
    public NatsProducerProperties getExtendedProducerProperties(String channelName) {
        return natsExtendedBindingProperties.getExtendedProducerProperties(channelName);
    }

    @Override
    public String getDefaultsPrefix() {
        return natsExtendedBindingProperties.getDefaultsPrefix();
    }

    @Override
    public Class<? extends BinderSpecificPropertiesProvider> getExtendedPropertiesEntryClass() {
        return natsExtendedBindingProperties.getExtendedPropertiesEntryClass();
    }
}
