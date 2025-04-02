package com.alxy.clientservice;

import com.alxy.clientservice.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientServiceApplicationTests {


    @Autowired
    ClientRepository clientRepository;

    @Test
    void contextLoads() {

    }

}
