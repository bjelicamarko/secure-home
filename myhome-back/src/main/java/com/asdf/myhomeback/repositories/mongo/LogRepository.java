package com.asdf.myhomeback.repositories.mongo;

import com.asdf.myhomeback.models.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, Long> {

    Page<Log> findLogsByDateTimeBetweenAndLogLevelInAndLogMessageContainingOrDateTimeBetweenAndLogLevelInAndLoggerNameContaining(
            long startDate1, long endDate1, String[] logLevels1, String searchValue1,
            long startDate2, long endDate2, String[] logLevels2, String searchValue2,
            Pageable pageable);
}
