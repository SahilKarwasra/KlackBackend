package com.klack.klack.invitation.service;

import com.klack.klack.config.JwtUtils;
import com.klack.klack.company.entity.Company;
import com.klack.klack.invitation.entity.CompanyInvite;
import com.klack.klack.auth.entity.Users;
import com.klack.klack.company.repository.CompanyRepo;
import com.klack.klack.invitation.repository.CompanyInvitationRepository;
import com.klack.klack.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyInviteService {
    private final CompanyInvitationRepository companyInvitationRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final CompanyRepo companyRepo;

    public void inviteUser(String email, String token) {
        String ownerId = jwtUtils.getUserIdFromToken(token);
        Users owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (owner.getCompanyId() == null) {
            throw new RuntimeException("Your Must Own a Company To Invite Other");
        }

        Users invitedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (invitedUser == null) {
            throw new RuntimeException("User not found");
        }

        if (invitedUser.getCompanyId() != null) {
            throw new RuntimeException("Users already belong to a company");
        }

        CompanyInvite companyInvite = CompanyInvite.builder()
                .companyId(owner.getCompanyId())
                .invitedUserId(invitedUser.getId())
                .accepted(false)
                .build();

        companyInvitationRepository.save(companyInvite);
    }

    public void acceptInvite(String token) {
        String userId = jwtUtils.getUserIdFromToken(token);

        List<CompanyInvite> companyCompanyInvites = companyInvitationRepository.findByInvitedUserIdAndAcceptedFalse(userId);
        if (companyCompanyInvites.isEmpty()) {
            throw new RuntimeException("No Invites...");
        }

        CompanyInvite companyInvite = companyCompanyInvites.getFirst();

        Company company = companyRepo.findById(companyInvite.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Users user = userRepository.findById(userId).get();
        user.setCompanyId(company.getId());
        userRepository.save(user);

        company.getMembersIds().add(userId);
        companyRepo.save(company);

        companyInvite.setAccepted(true);
        companyInvitationRepository.save(companyInvite);

    }
}







