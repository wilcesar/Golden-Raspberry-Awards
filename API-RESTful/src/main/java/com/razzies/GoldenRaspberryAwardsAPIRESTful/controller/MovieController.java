package com.razzies.GoldenRaspberryAwardsAPIRESTful.controller;

import com.razzies.GoldenRaspberryAwardsAPIRESTful.model.MovieModel;
import com.razzies.GoldenRaspberryAwardsAPIRESTful.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository repository ;
    public void salvar(MovieModel movie){
        repository.save(movie);
    }
}
