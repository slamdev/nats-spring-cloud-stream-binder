package com.github.slamdev.spring.cloud.stream.binder.nats.config;

import com.github.slamdev.spring.cloud.stream.binder.nats.NatsMessageChannelBinder;
import com.github.slamdev.spring.cloud.stream.binder.nats.properties.NatsExtendedBindingProperties;
import com.github.slamdev.spring.cloud.stream.binder.nats.provisioning.NatsChannelProvisioner;
import io.nats.client.Connection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.config.BindingHandlerAdvise.MappingsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@ConditionalOnMissingBean(Binder.class)
@ConditionalOnClass(Connection.class)
@EnableConfigurationProperties(NatsExtendedBindingProperties.class)
public class NatsBinderConfiguration {

    @Bean
    public NatsChannelProvisioner natsChannelProvisioner() {
        return new NatsChannelProvisioner();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.nats.enabled", matchIfMissing = true)
    public NatsMessageChannelBinder natsBinder(NatsChannelProvisioner natsChannelProvisioner, Connection connection,
                                               NatsExtendedBindingProperties natsExtendedBindingProperties) {
        return new NatsMessageChannelBinder(natsChannelProvisioner, connection, natsExtendedBindingProperties);
    }

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.nats.enabled", havingValue = "false")
    public NatsMessageChannelBinder mockNatsBinder(NatsExtendedBindingProperties natsExtendedBindingProperties) {
        return new NatsMessageChannelBinder(null, null, natsExtendedBindingProperties);
    }

    @Bean
    public MappingsProvider natsExtendedPropertiesDefaultMappingsProvider() {
        return () -> Collections.singletonMap(
                ConfigurationPropertyName.of("spring.cloud.stream.nats.bindings"),
                ConfigurationPropertyName.of("spring.cloud.stream.nats.default"));
    }
}
