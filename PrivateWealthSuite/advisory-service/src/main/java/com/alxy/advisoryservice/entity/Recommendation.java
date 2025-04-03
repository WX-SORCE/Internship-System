package com.alxy.advisoryservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "recommendations")
public class Recommendation {
    @Id
    @Column(name = "recommendation_id")
    private String recommendationId;
    
    @Column(name = "recommendation_name")
    private String recommendationName;
    
    @Column(name = "risk_level")
    private Integer riskLevel;
    
    @Column(name = "yield_rate")
    private Integer yieldRate;
    
    @Column(name = "client_id")
    private String clientId;
    
    @Column(name = "advisor_id")
    private String advisorId;
    
    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "accepted")
    private Boolean accepted = false;
    
    @Column(name = "feedback")
    private String feedback;
}