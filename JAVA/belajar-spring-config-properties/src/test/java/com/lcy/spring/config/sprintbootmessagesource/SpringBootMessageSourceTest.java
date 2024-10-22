package com.lcy.spring.config.sprintbootmessagesource;

import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

@SpringBootTest(classes = SpringBootMessageSourceTest.TestApplication.class)

public class SpringBootMessageSourceTest {
    @Autowired
    private TestApplication.SampleSource sampleSource;

    @Test
    void testHelloLCY() {
        Assertions.assertEquals("Hello LCY", sampleSource.helloLCY(Locale.ENGLISH));
        Assertions.assertEquals("Hallo LCY", sampleSource.helloLCY(new Locale("in_ID")));
    }

    @SpringBootApplication
    public static class TestApplication {

        @Setter
        @Component
        public static class SampleSource implements MessageSourceAware {
            private MessageSource messageSource;

            public String helloLCY(Locale locale) {
                return messageSource.getMessage("hello", new Object[]{"LCY"}, locale);
            }
        }
    }
}
