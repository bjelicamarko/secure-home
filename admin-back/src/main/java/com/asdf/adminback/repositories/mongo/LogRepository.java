package com.asdf.adminback.repositories.mongo;

import com.asdf.adminback.models.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, Long> {
}
