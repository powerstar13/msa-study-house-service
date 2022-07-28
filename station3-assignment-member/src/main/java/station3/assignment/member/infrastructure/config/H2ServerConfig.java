package station3.assignment.member.infrastructure.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import java.sql.SQLException;

@Slf4j
@Configuration
@EnableR2dbcAuditing
@RequiredArgsConstructor
public class H2ServerConfig {

    private Server webServer;

    @Value("${spring.r2dbc.port}")
    private String h2ConsolePort;

    @EventListener(ContextStartedEvent.class)
    public void start() throws SQLException {
        log.info("starting h2 console at port {}", h2ConsolePort);
        this.webServer = Server.createWebServer("-webPort", h2ConsolePort);
        this.webServer.start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        log.info("stopping h2 console at port {}", h2ConsolePort);
        this.webServer.stop();
    }
}