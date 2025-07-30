package com.klack.klack.invitation.controller;

import com.klack.klack.invitation.dto.InviteUserRequest;
import com.klack.klack.company.service.CompanyService;
import com.klack.klack.invitation.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/company/")
public class InvitationController {
    private final InviteService inviteService;
    private final CompanyService companyService;

    @PostMapping("invite")
    public ResponseEntity<?> invite(
            @RequestBody InviteUserRequest inviteUserRequest,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        inviteService.inviteUser(inviteUserRequest.getEmail(), token);
        return ResponseEntity.ok("Invitation Sent");
    }

    @PostMapping("accept-invite")
    public ResponseEntity<?> acceptInvite(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        inviteService.acceptInvite(token);
        return ResponseEntity.ok("Congrats You Have Joined the Company");
    }

}
