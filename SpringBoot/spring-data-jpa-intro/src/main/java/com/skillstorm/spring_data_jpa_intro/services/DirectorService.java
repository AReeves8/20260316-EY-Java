package com.skillstorm.spring_data_jpa_intro.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.skillstorm.spring_data_jpa_intro.models.Director;
import com.skillstorm.spring_data_jpa_intro.repositories.DirectorRepository;

@Service
public class DirectorService {

    private final DirectorRepository repository;

    DirectorService(DirectorRepository repository) {
        this.repository = repository;
    }

    public List<Director> getAllDirectors(int page, int size, String sortBy) {

        // pageable uses the page number and the size to determine which records to send back
        // OFFSET page*size
        // LIMIT size
        // ORDER BY sortBy property
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Director> directorsPage = repository.findAll(pageable);
        List<Director> directorsList = directorsPage.getContent();
        return directorsList;
    }

    public Director findDirectorById(Integer id) {

        Optional<Director> director = repository.findById(id);
        if(director.isPresent()) {
            return director.get();
        }
        return null;
    }

    public Director saveDirector(Director director) {
        return repository.save(director);
    }
}
