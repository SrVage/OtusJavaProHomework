package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionsDto {
    @JsonProperty("chat_sessions")
    private List<ChatSessionDto> chatSessions;
}
