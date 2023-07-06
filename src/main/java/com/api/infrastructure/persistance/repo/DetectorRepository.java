package com.api.infrastructure.persistance.repo;

import com.api.infrastructure.persistance.entity.Detector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectorRepository extends MongoRepository<Detector, String> {
}
