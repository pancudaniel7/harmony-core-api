package com.devxsquad.platform.partsselling.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${spring.kafka.topic.partition-number:3}")
    private int partitionNumber;

    @Value("${spring.kafka.topic.partition-replica-number:1}")
    private short partitionReplicaNumber;

    @Value("#{'${spring.kafka.platforms.supported}'.split(',')}")
    private List<String> supportedPlatforms;

    @Autowired
    GenericApplicationContext context;

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProps());
    }

    @Bean
    public Map<String, Object> producerProps() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
        props.put(KEY_SERIALIZER_CLASS_CONFIG, Integer.class);
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, String.class);
        props.put(RETRIES_CONFIG, 3);
        props.put(REQUEST_TIMEOUT_MS_CONFIG, 10_000);
        props.put(RETRY_BACKOFF_MS_CONFIG, 500);
        return props;
    }

    public Optional<List<String>> getSupportedPlatforms() {
        return Optional.ofNullable(supportedPlatforms);
    }

    @PostConstruct
    public void createTopics() {
        this.getSupportedPlatforms().ifPresent(this::initializeBeans);
    }

    private void initializeBeans(List<String> supportedPlatforms) {
        supportedPlatforms.forEach(topicName ->
                context.registerBean(topicName, NewTopic.class, () -> new NewTopic(topicName, partitionNumber, partitionReplicaNumber)));
    }
}
