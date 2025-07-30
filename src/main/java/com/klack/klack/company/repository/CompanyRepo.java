package com.klack.klack.company.repository;

import com.klack.klack.company.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepo extends MongoRepository<Company, String> {
}
