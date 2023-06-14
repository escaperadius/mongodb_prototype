package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;


public interface TrackRepository extends MongoRepository<Track, String>{
    Optional<Track> findByName(String name);
}
