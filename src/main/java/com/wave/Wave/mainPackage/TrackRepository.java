package com.wave.Wave.mainPackage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Component
public interface TrackRepository extends MongoRepository<Track, String> {

    @Query("{name:'?0'}")
    Track findTrackByName(String name);


    @Query("{artist:'?0'}")
    List<Track> findTracksByArtist(String artist);


}
