package com.github.slamdev.spring.cloud.stream.binder.nats;

import io.nats.client.Options;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties("spring.cloud.nats")
public class NatsProperties {
    private String[] servers;
    private boolean noRandomize;
    private String connectionName;
    private boolean verbose;
    private boolean pedantic;
    private int maxReconnect = Options.DEFAULT_MAX_RECONNECT;
    private int maxControlLine = Options.DEFAULT_MAX_CONTROL_LINE;
    private Duration reconnectWait = Options.DEFAULT_RECONNECT_WAIT;
    private Duration connectionTimeout = Options.DEFAULT_CONNECTION_TIMEOUT;
    private Duration pingInterval = Options.DEFAULT_PING_INTERVAL;
    private Duration requestCleanupInterval = Options.DEFAULT_REQUEST_CLEANUP_INTERVAL;
    private int maxPingsOut = Options.DEFAULT_MAX_PINGS_OUT;
    private long reconnectBufferSize = Options.DEFAULT_RECONNECT_BUF_SIZE;
    private String username;
    private String password;
    private String token;
    private String inboxPrefix = Options.DEFAULT_INBOX_PREFIX;
    private boolean useOldRequestStyle;
    private int bufferSize = Options.DEFAULT_BUFFER_SIZE;
    private boolean noEcho;
    private boolean utf8Support;
    private int executorThreads = 4;
}
