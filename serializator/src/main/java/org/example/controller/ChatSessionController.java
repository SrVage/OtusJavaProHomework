package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ChatSessionsDto;
import org.example.service.ConvertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatSessionController {
    private final ConvertService convertService;

    @PostMapping("/chat")
    public ResponseEntity<?> parseChatSession(@RequestBody ChatSessionsDto body,
                                              @RequestHeader(value = "Content-Type-Answer", defaultValue = "application/json") String contentType){
        if (!contentType.equals("application/json") && !contentType.equals("application/xml")) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Unsupported media type");
        }
        return convertService.convert(contentType, body);
    }
}
