package com.klack.klack.chats.entity;


import com.klack.klack.chats.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("Messages")
public class ChatMessage {
    @Id
    private String id;
    private String senderId;
    private String senderName;
    private String companyId;
    private String channelId; // Null for the Private chats or the Company wide chats
    private String receiverId; // Only for the Private chats 1 on 1
    private String content;
    private LocalDateTime timestamp;
    private MessageType messageType;
}
