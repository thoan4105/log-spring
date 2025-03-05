package com.testlog.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemLog {
    private UUID transactionId;
    private String ipPortCurrentNode;
    private long startTime;
    private long endTime;
    private long duration;
}
