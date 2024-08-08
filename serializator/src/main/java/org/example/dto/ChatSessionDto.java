package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatSessionDto {
    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("chat_identifier")
    private String chatIdentifier;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    private List<MemberDto> members;

    private List<MessageDto> messages;
}
