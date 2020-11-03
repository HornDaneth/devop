package com.example.backoffice.backofficeapi.restservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaProducer {

    private String kafkaTopic = "devnation";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String text) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(kafkaTopic, text);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + text +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + text + "] due to : " + ex.getMessage());
            }
        });
    }
}
