package com.github.slamdev.spring.cloud.stream.binder.nats.provisioning;

import lombok.Value;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;

@Value
public class NatsConsumerDestination implements ConsumerDestination {

    private String name;
}
