package com.klack.klack.company.controller;


import com.klack.klack.company.dto.CreateCompanyRequest;
import com.klack.klack.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company/")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CreateCompanyRequest company, @RequestHeader("Authorization")  String authHeader) {

        String token = authHeader.substring(7);
        String id = companyService.createCompany(company, token);
        return ResponseEntity.ok("Company created with id: " + id);

    }

}
