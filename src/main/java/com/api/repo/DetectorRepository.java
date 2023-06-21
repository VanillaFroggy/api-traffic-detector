package com.api.repo;

import com.api.entity.Detector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectorRepository extends MongoRepository<Detector, String> {
}
