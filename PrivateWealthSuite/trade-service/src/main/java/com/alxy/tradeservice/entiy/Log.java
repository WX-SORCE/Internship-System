package com.alxy.tradeservice.entiy;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "logs")
public class Log {
    @Id
    @Column(name = "log_id")
    private String logId;


    @Column(name = "trade_id")
    private String tradeId;

    @Lob
    @Column(name = "log_message")
    private String logMessage;
    @Column(name = "log_time")
    private Date logTime;

    @PrePersist
    public void onCreate() {
        if (logId == null) {
            logId = UUID.randomUUID().toString();
        }

    }
}