package com.lcy.spring.config.value;

import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest(classes = ValuePropertiesTest.TestApplication.class)

public class ValuePropertiesTest {




    @Autowired
    private TestApplication.ApplicationProperties properties;
    @Autowired
    private TestApplication.SystemProperties systemProperties;

    @Test
    void testApplicationProperties() {
        Assertions.assertEquals("belajar-spring-config-properties", properties.getName());
        Assertions.assertEquals(1, properties.getVersion());
        Assertions.assertFalse(properties.isProductionMode());
    }

    @Test
    void testSystemProperties(){
        Assertions.assertEquals("C:\\Program Files\\Java\\jdk-23.0.1", systemProperties.javaHome);
    }



    @SpringBootApplication
    public static class TestApplication {
        @Component
        @Getter
        public static class SystemProperties {
            @Value("${JAVA_HOME}")
            private String javaHome;

        }

        @Component
        @Getter
        public static class ApplicationProperties {

            @Value("${spring.application.name}")
            private String name;

            @Value("${sprint.application.version}")
            private Integer version;

            @Value("${spring.application.production-mode}")
            private boolean productionMode;
        }
    }
}
