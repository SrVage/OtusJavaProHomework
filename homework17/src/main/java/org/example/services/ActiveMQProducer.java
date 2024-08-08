package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.ActiveMQConfig;
import org.example.dto.StudentDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.jms.core.JmsTemplate;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class ActiveMQProducer {
    private final Logger logger = Logger.getLogger(ActiveMQProducer.class.getName());

    private static final int TEXT = 0;
    private static final int SERIALIZABLE = 1;

    private final JmsTemplate jmsTemplate;
    private final Random random = new Random();
    private final ObjectMapper objectMapper;

    public ActiveMQProducer(@Qualifier(ActiveMQConfig.JMS_TEMPLATE) JmsTemplate jmsTemplate,
                            ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 5000)
    public void convertAndSend() {
        switch (random.nextInt(3)) {
            case TEXT:
                jmsTemplate.convertAndSend(ActiveMQConfig.DESTINATION_NAME, createTextMessage());
                break;
            case SERIALIZABLE:
                jmsTemplate.convertAndSend(ActiveMQConfig.DESTINATION_NAME, createSerializableMessage());
                break;
            default:
                logger.info("ignore random");
        }
    }

    private String createTextMessage() {
        int id = random.nextInt(10);
        return "text-message:" + id;
    }

    private Serializable createSerializableMessage() {
        int id = random.nextInt(10);
        String firstName = "oleg_" + id;
        String lastName = "pavlov_" + id;

        return StudentDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .educationCode(random.nextInt(10))
                .dateOfBirth(LocalDate.now())
                .averageMark(random.nextDouble(5))
                .build();
    }
}
