package com.klack.klack.repository;

import com.klack.klack.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepo extends MongoRepository<Company, String> {
}
