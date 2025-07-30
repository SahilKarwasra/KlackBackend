package com.klack.klack.company.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("company")
public class Company {
    @Id
    private String id;

    private String name;
    private String ownerId;
    private List<String> membersIds;
    private LocalDateTime createdDate;
}
