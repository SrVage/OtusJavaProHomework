package org.example.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.config.ActiveMQConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.TextMessage;
import java.io.Serializable;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ActiveMQListener {
    private final Logger logger = Logger.getLogger(ActiveMQListener.class.getName());
    private final ObjectMapper objectMapper;

    @JmsListener(destination = ActiveMQConfig.DESTINATION_NAME,
            containerFactory = ActiveMQConfig.JMS_LISTENER_CONTAINER_FACTORY)
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                onTextMessage((TextMessage) message);
            } else if (message instanceof ObjectMessage) {
                onObjectMessage((ObjectMessage) message);
            } else {
                throw new IllegalArgumentException("Unsupported message type");
            }
        } catch (IllegalArgumentException e) {
            logger.warning("Message Error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void onTextMessage(TextMessage message) throws JMSException {
        String msg = message.getText();
        logger.info("[active-mq: text] : " + msg);
    }

    private void onObjectMessage(ObjectMessage message)
            throws JMSException, ClassNotFoundException, JsonProcessingException {
        String className = message.getStringProperty(ActiveMQConfig.CLASS_NAME);

        if (className == null) {
            onSerializableObjectMessage(message);
        } else {
            onCustomObjectMessage(Class.forName(className), message);
        }
    }

    private void onSerializableObjectMessage(ObjectMessage message) throws JMSException {
        Serializable obj = (Serializable) message.getObject();
        logger.info("[active-mq: serializable] : " + obj);
    }

    private void onCustomObjectMessage(Class<?> cls, ObjectMessage message)
            throws JMSException, JsonProcessingException {
        String json = String.valueOf(message.getObject());
        Object obj = objectMapper.readValue(json, cls);
        logger.info(String.format("[active-mq: %s] : %s%n", cls.getSimpleName(), obj));
    }
}
