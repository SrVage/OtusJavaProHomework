package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dto.ChatSessionsDto;
import org.example.entities.ChatSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ConvertService {
    private final Logger logger = Logger.getLogger(ConvertService.class.getName());
    private final ObjectMapper jsonMapper;
    private final static String FILE_NAME = "chats.json";

    public ConvertService(){
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ResponseEntity convert(String contentType, ChatSessionsDto chatSessionsDto) {
        List<ChatSession> chatList = convertToListChatSession(chatSessionsDto);
        chatList = sortingByDate(chatList);

        writeListInFile(chatList);

        List<ChatSession> newChatList = readFromFile();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);
        if (contentType.equals("application/json")){
            try {
                return new ResponseEntity<>(jsonMapper.writeValueAsString(newChatList), headers, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else if (contentType.equals("application/xml")){
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            try {
                return new ResponseEntity<>(xmlMapper.writeValueAsString(newChatList), headers, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ResponseEntity<>("Unsupported media type", headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private static List<ChatSession> sortingByDate(List<ChatSession> chatList) {
        chatList = chatList.stream()
                .sorted(Comparator.comparing(ChatSession::getMessageSendDate))
                .collect(Collectors.toList());
        return chatList;
    }

    private void writeListInFile(List<ChatSession> chatList) {
        try {
            jsonMapper.writeValue(new File(FILE_NAME), chatList);
        } catch (IOException e) {
            logger.warning(e.getStackTrace().toString());
        }
    }

    private List<ChatSession> readFromFile(){
        try {
            return jsonMapper.readValue(new File(FILE_NAME),
                    jsonMapper.getTypeFactory().constructCollectionType(List.class, ChatSession.class));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + FILE_NAME, e);
        }
    }

    private static List<ChatSession> convertToListChatSession(ChatSessionsDto chatSessionsDto) {
        List<ChatSession> chatList = new ArrayList<>();
        for (var chatSession : chatSessionsDto.getChatSessions()) {
            for (var member : chatSession.getMembers()) {
                for (var message : chatSession.getMessages()) {
                    if (Objects.equals(message.getHandleId(), member.getHandleId())) {
                        ChatSession newChatSession = new ChatSession(chatSession.getChatIdentifier(),
                                member.getLast(),
                                message.getBelongNumber(),
                                message.getSendDate(),
                                message.getText());
                        chatList.add(newChatSession);
                    }
                }
            }
        }
        return chatList;
    }
}
