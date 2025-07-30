package com.klack.klack.invitation.repository;

import com.klack.klack.invitation.entity.Invite;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InvitationRepository extends MongoRepository<Invite, String> {
    List<Invite> findByInvitedUserIdAndAcceptedFalse(String userId);
}
