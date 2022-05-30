package com.asdf.myhomeback.repositories;

import com.asdf.myhomeback.models.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, Long> {
}
