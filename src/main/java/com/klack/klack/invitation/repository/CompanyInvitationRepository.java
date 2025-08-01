package com.klack.klack.invitation.repository;

import com.klack.klack.invitation.entity.CompanyInvite;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyInvitationRepository extends MongoRepository<CompanyInvite, String> {
    List<CompanyInvite> findByInvitedUserIdAndAcceptedFalse(String userId);
}
