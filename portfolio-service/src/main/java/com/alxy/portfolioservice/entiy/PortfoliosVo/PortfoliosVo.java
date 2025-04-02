package com.alxy.portfolioservice.entiy.PortfoliosVo;

import com.alxy.portfolioservice.entiy.PortfolioItems;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@Builder
public class PortfoliosVo {


    private String productName;


    private BigDecimal unitValue;

    private BigDecimal amount;
    private String type;


    private Date createdAt;

}