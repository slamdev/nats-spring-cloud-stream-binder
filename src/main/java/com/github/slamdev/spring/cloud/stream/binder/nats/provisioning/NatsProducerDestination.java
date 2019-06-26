package com.github.slamdev.spring.cloud.stream.binder.nats.provisioning;

import lombok.Value;
import org.springframework.cloud.stream.provisioning.ProducerDestination;

@Value
public class NatsProducerDestination implements ProducerDestination {

    private String name;

    @Override
    public String getNameForPartition(int partition) {
        return name + "-" + partition;
    }
}
