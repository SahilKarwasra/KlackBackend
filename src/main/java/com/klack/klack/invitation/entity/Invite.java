package com.klack.klack.invitation.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Invite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invite {
    @Id
    private String id;

    private String companyId;
    private String invitedUserId;
    private boolean accepted;
}
