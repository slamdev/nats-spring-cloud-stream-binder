package com.github.slamdev.spring.cloud.stream.binder.nats;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@ConditionalOnProperty(value = "spring.cloud.nats.enabled", matchIfMissing = true)
@ConditionalOnClass(Connection.class)
@EnableConfigurationProperties(NatsProperties.class)
@RequiredArgsConstructor
public class NatsAutoConfiguration {

    private final NatsProperties natsProperties;

    @Bean
    @ConditionalOnMissingBean(name = "natsThreadPool")
    public ThreadPoolTaskScheduler natsThreadPool() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(natsProperties.getExecutorThreads());
        scheduler.setThreadNamePrefix("nats-publisher");
        return scheduler;
    }

    @Bean(destroyMethod = "close")
    @SneakyThrows
    @ConditionalOnMissingBean
    public Connection natsConnection(@Qualifier("natsThreadPool") ThreadPoolTaskScheduler executor) {
        Options.Builder optionsBuilder = new Options.Builder()
                .executor(executor.getScheduledThreadPoolExecutor())
                .servers(natsProperties.getServers())
                .connectionName(natsProperties.getConnectionName())
                .maxReconnects(natsProperties.getMaxReconnect())
                .maxControlLine(natsProperties.getMaxControlLine())
                .reconnectWait(natsProperties.getReconnectWait())
                .connectionTimeout(natsProperties.getConnectionTimeout())
                .pingInterval(natsProperties.getPingInterval())
                .requestCleanupInterval(natsProperties.getRequestCleanupInterval())
                .maxPingsOut(natsProperties.getMaxPingsOut())
                .reconnectBufferSize(natsProperties.getReconnectBufferSize())
                .userInfo(natsProperties.getUsername(), natsProperties.getPassword())
                .token(natsProperties.getToken())
                .inboxPrefix(natsProperties.getInboxPrefix())
                .bufferSize(natsProperties.getBufferSize());
        if (natsProperties.isNoRandomize()) {
            optionsBuilder.noRandomize();
        }
        if (natsProperties.isVerbose()) {
            optionsBuilder.verbose();
        }
        if (natsProperties.isPedantic()) {
            optionsBuilder.pedantic();
        }
        if (natsProperties.isUseOldRequestStyle()) {
            optionsBuilder.oldRequestStyle();
        }
        if (natsProperties.isNoEcho()) {
            optionsBuilder.noEcho();
        }
        if (natsProperties.isUtf8Support()) {
            optionsBuilder.supportUTF8Subjects();
        }
        return Nats.connect(optionsBuilder.build());
    }
}
