package com.github.slamdev.spring.cloud.stream.binder.nats.provisioning;

import com.github.slamdev.spring.cloud.stream.binder.nats.properties.NatsConsumerProperties;
import com.github.slamdev.spring.cloud.stream.binder.nats.properties.NatsProducerProperties;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

public class NatsChannelProvisioner implements ProvisioningProvider<ExtendedConsumerProperties<NatsConsumerProperties>,
        ExtendedProducerProperties<NatsProducerProperties>> {

    @Override
    public ProducerDestination provisionProducerDestination(String subject,
                                                            ExtendedProducerProperties<NatsProducerProperties> properties)
            throws ProvisioningException {
        return new NatsProducerDestination(subject);
    }

    @Override
    public ConsumerDestination provisionConsumerDestination(String subjectName, String group,
                                                            ExtendedConsumerProperties<NatsConsumerProperties> properties)
            throws ProvisioningException {
        return new NatsConsumerDestination(subjectName);
    }
}
