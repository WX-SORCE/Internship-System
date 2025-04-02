package com.alxy.portfolioservice;

import com.alxy.portfolioservice.repository.PortfolioItemsRepository;
import com.alxy.portfolioservice.repository.PortfoliosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PortfolioServiceApplicationTests {

    @Autowired
    private PortfoliosRepository portfoliosRepository;

    @Autowired
    private PortfolioItemsRepository portfolioItemsRepository;

}
