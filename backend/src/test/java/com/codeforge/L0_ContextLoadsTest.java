package com.codeforge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {"codeforge.mongo.enabled=false"})
@ActiveProfiles("test")
class L0_ContextLoadsTest {
    @Test
    void contextLoads() {}
}