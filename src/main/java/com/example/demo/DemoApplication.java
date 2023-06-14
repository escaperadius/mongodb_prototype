package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(TrackRepository repository, MongoTemplate mongoTemplate) {
		return args -> {
			String name = "phaser";
			Track track = new Track(name, 1111);
			
		//	usingMongoTemplateAndQuery(repository, mongoTemplate, name, track);
			repository.findByName(name).ifPresentOrElse(t -> {
			   System.out.println("Track already exists");
			}, ()->{
				System.out.println("Inserting track " + track);
			    repository.insert(track);
			});
		};
	}

	private void usingMongoTemplateAndQuery(TrackRepository repository, MongoTemplate mongoTemplate, String name, Track track) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(name));

		List<Track> tracks = mongoTemplate.find(query, Track.class);

		if(tracks.size() > 1 ) {
			throw new IllegalStateException("Found tracks with multiple names: " + name);
		}

		if(tracks.isEmpty()) {
			System.out.println("Inserting track " + track);
			repository.insert(track);
		} else {
			System.out.println("Track already exists");
		}
	}


}
