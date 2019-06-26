package com.github.slamdev.spring.cloud.stream.binder.nats.properties;

import lombok.Data;
import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider;

@Data
public class NatsBindingProperties implements BinderSpecificPropertiesProvider {

    private NatsConsumerProperties consumer = new NatsConsumerProperties();
    private NatsProducerProperties producer = new NatsProducerProperties();
}
