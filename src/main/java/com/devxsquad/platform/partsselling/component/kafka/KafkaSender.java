package com.devxsquad.platform.partsselling.component.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnMissingBean(name = "kafkaConfig")
public class KafkaSender {
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    public void send(ProducerRecord<Integer, String> message) {
        ListenableFuture<SendResult<Integer, String>> sendResult = kafkaTemplate.send(message);
        sendResult.addCallback(resultResolverCallback);
    }

    ListenableFutureCallback resultResolverCallback = new ListenableFutureCallback<SendResult<Integer, String>>() {

        @Override
        public void onSuccess(SendResult<Integer, String> integerStringSendResult) {
            log.trace("Message {} was send successfully", integerStringSendResult.getProducerRecord().toString());
        }

        @Override
        public void onFailure(Throwable throwable) {
            throw new KafkaException("Fail to send kafka message", throwable.getCause());
        }
    };
}

