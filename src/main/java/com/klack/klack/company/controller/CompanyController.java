package com.klack.klack.company.controller;


import com.klack.klack.auth.entity.Users;
import com.klack.klack.company.dto.CreateCompanyRequest;
import com.klack.klack.company.service.CompanyService;
import com.klack.klack.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company/")
public class CompanyController {
    private final CompanyService companyService;
    private final JwtUtils jwtUtils;


    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CreateCompanyRequest company, @RequestHeader("Authorization")  String authHeader) {

        String token = authHeader.substring(7);
        String id = companyService.createCompany(company, token);
        return ResponseEntity.ok("Company created with id: " + id);

    }

    @GetMapping("{companyID}/users")
    public ResponseEntity<List<Users>> findAllUsersInCompany(
            @PathVariable String companyID ,
            @RequestHeader("Authorization") String authHeader
    ) {
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        authHeader = authHeader.substring(7);

        try {
            String currentUserId = jwtUtils.getUserIdFromToken(authHeader);
            System.out.println("Company Controller: User ID is: " + currentUserId);
            if(!companyService.isPartOfCompany(currentUserId, companyID)) {
                System.out.println("JwtUtils: Part of Company not found");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            System.out.println("CompanyController: User ID is part of our company: " + currentUserId);

            List<Users> allMembersOfCompany = companyService.findUsersInTheCompany(companyID);

            System.out.println("CompanyController: All members of Company: " + allMembersOfCompany);
            return ResponseEntity.ok(allMembersOfCompany);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
