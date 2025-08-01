package com.klack.klack.invitation.controller;

import com.klack.klack.invitation.dto.CompanyInviteUserRequest;
import com.klack.klack.company.service.CompanyService;
import com.klack.klack.invitation.service.CompanyInviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/company/")
public class CompanyInvitationController {
    private final CompanyInviteService companyInviteService;
    private final CompanyService companyService;

    @PostMapping("invite")
    public ResponseEntity<?> invite(
            @RequestBody CompanyInviteUserRequest companyInviteUserRequest,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        companyInviteService.inviteUser(companyInviteUserRequest.getEmail(), token);
        return ResponseEntity.ok("Invitation Sent");
    }

    @PostMapping("accept-invite")
    public ResponseEntity<?> acceptInvite(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        companyInviteService.acceptInvite(token);
        return ResponseEntity.ok("Congrats You Have Joined the Company");
    }

}
