package org.example.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatSession {
    private String chatIdentifier;
    private String memberLast;
    private String messageBelongNumber;
    private LocalDateTime messageSendDate;
    private String messageText;
}
