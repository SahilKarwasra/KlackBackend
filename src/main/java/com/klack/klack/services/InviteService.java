package com.klack.klack.services;

import com.klack.klack.config.JwtUtils;
import com.klack.klack.entity.Company;
import com.klack.klack.entity.Invite;
import com.klack.klack.entity.Users;
import com.klack.klack.repository.CompanyRepo;
import com.klack.klack.repository.InvitationRepository;
import com.klack.klack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InviteService {
    private final InvitationRepository invitationRepository;
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

        Invite invite = Invite.builder()
                .companyId(owner.getCompanyId())
                .invitedUserId(invitedUser.getId())
                .accepted(false)
                .build();

        invitationRepository.save(invite);
    }

    public void acceptInvite(String token) {
        String userId = jwtUtils.getUserIdFromToken(token);

        List<Invite> companyInvites = invitationRepository.findByInvitedUserIdAndAcceptedFalse(userId);
        if (companyInvites.isEmpty()) {
            throw new RuntimeException("No Invites...");
        }

        Invite invite = companyInvites.getFirst();

        Company company = companyRepo.findById(invite.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Users user = userRepository.findById(userId).get();
        user.setCompanyId(company.getId());
        userRepository.save(user);

        company.getMembersIds().add(userId);
        companyRepo.save(company);

        invite.setAccepted(true);
        invitationRepository.save(invite);

    }
}







