package com.api.infrustructure.persistance.repo;

import com.api.infrustructure.persistance.entity.Detector;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectorRepository extends MongoRepository<Detector, String> {
}
