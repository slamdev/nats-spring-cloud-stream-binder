package com.github.slamdev.spring.cloud.stream.binder.nats.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.stream.binder.AbstractExtendedBindingProperties;
import org.springframework.cloud.stream.binder.BinderSpecificPropertiesProvider;

@ConfigurationProperties("spring.cloud.stream.nats")
public class NatsExtendedBindingProperties extends
        AbstractExtendedBindingProperties<NatsConsumerProperties, NatsProducerProperties, NatsBindingProperties> {

    private static final String DEFAULTS_PREFIX = "spring.cloud.stream.nats.default";

    @Override
    public String getDefaultsPrefix() {
        return DEFAULTS_PREFIX;
    }

    @Override
    public Class<? extends BinderSpecificPropertiesProvider> getExtendedPropertiesEntryClass() {
        return NatsBindingProperties.class;
    }
}
