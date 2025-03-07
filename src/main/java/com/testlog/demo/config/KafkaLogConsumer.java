package com.testlog.demo.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testlog.demo.entity.LogEntry;
import com.testlog.demo.repository.LogEntryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class KafkaLogConsumer {
    private final LogEntryRepository logEntryRepository;
    private final ObjectMapper objectMapper;

    public KafkaLogConsumer(LogEntryRepository logEntryRepository, ObjectMapper objectMapper) {
        this.logEntryRepository = logEntryRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "logs", groupId = "log-consumer-group")
    public void listen(List<String> messages) {
        List<LogEntry> logEntries = new ArrayList<>();

        for (String message : messages) {
            try {
                JsonNode jsonNode = objectMapper.readTree(message);
                JsonNode systemLog = jsonNode.get("message") != null ? objectMapper.readTree(jsonNode.get("message").asText()) : jsonNode;
                LogEntry logEntry = new LogEntry();
                logEntry.setMessage(message);
                logEntry.setTransactionId(systemLog.get("transactionId") != null ? systemLog.get("transactionId").asText() : null);
                logEntry.setIpPortCurrentNode(systemLog.get("ipPortCurrentNode") != null ? systemLog.get("ipPortCurrentNode").asText() : null);
                logEntry.setStartTime(systemLog.get("startTime") != null ? systemLog.get("startTime").asLong() : 0);
                logEntry.setEndTime(systemLog.get("endTime") != null ? systemLog.get("endTime").asLong() : 0);
                logEntry.setDuration(systemLog.get("duration") != null ? systemLog.get("duration").asLong() : 0);

                logEntries.add(logEntry);
            } catch (Exception ignored) {
            }
        }

        if (!logEntries.isEmpty()) {
            logEntryRepository.saveAll(logEntries);
        }
    }
}
