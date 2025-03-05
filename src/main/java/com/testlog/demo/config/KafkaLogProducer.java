package com.testlog.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaLogProducer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(KafkaLogProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaLogProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Application started, sending logs to Kafka...");
        kafkaTemplate.send("logs", "Log message from Spring Boot with Log4j2");
    }
}
