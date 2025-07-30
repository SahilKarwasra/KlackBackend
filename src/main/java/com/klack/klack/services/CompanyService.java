package com.klack.klack.services;

import com.klack.klack.config.JwtUtils;
import com.klack.klack.dto.CreateCompanyRequest;
import com.klack.klack.entity.Company;
import com.klack.klack.entity.Users;
import com.klack.klack.repository.CompanyRepo;
import com.klack.klack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final UserRepository userRepository;
    private final CompanyRepo companyRepo;
    private final JwtUtils jwtUtils;

    public String createCompany(
            CreateCompanyRequest request, String token
    ) {
        String userId = jwtUtils.getUserIdFromToken(token);
        Users owner = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        if (owner.getCompanyId() != null) {
            throw new RuntimeException("You Already Belong to a Company");
        }

        Company company = Company.builder()
                .name(request.getCompanyName())
                .ownerId(userId)
                .membersIds(List.of(userId))
                .build();

        Company savedCompany = companyRepo.save(company);
        owner.setCompanyId(savedCompany.getId());
        userRepository.save(owner);

        return savedCompany.getId();

    }

}



















