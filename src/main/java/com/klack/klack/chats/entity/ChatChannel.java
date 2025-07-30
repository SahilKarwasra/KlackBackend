package com.klack.klack.chats.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("collection")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatChannel {
    @Id
    private String id;
    private String companyId;
    private String name;
    private List<String> membersIds;
}
