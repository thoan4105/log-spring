package com.testlog.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testlog.demo.dto.SystemLog;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${server.port}")
    private int port;

    @GetMapping()
    public String get() {
        System.out.println(log.getClass().getName());
        try {
            String jsonLog = objectMapper.writeValueAsString(createSystemLog());
            log.info("System log: {}", jsonLog);
        } catch (Exception e) {
            log.error("Error converting log to JSON", e);
        }
        return "Hello world";
    }

    private SystemLog createSystemLog() {
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        UUID transactionId = UUID.randomUUID();
        String ipPortCurrentNode = "localhost:" + port;
        return new SystemLog(transactionId, ipPortCurrentNode, startTime, endTime, duration);
    }
}
