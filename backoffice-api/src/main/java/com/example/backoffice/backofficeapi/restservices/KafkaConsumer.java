package com.example.backoffice.backofficeapi.restservices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "devnation", groupId = "devnation-spring")
    public void listener(@Payload(required = false) String value) {
        log.info("Received message:"
                + "\n\t Value: " + value);
    }
}
