package com.klack.klack.company.repository;

import com.klack.klack.auth.entity.Users;
import com.klack.klack.company.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CompanyRepo extends MongoRepository<Company, String> {
}
