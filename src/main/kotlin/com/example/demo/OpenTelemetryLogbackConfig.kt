package com.example.demo

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Configuration

@Configuration
class OpenTelemetryLogbackConfig(
    private val openTelemetryProvider: ObjectProvider<OpenTelemetry>
) {
    private val log = LoggerFactory.getLogger(OpenTelemetryLogbackConfig::class.java)

    @PostConstruct
    fun installLogAppender() {
        openTelemetryProvider.ifAvailable { openTelemetry ->
            OpenTelemetryAppender.install(openTelemetry)
            log.info("[OpenTelemetryLogbackConfig] Successfully installed OpenTelemetryAppender with OpenTelemetry SDK")
        }
    }
}
