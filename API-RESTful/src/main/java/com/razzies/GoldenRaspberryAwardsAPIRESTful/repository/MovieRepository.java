package com.razzies.GoldenRaspberryAwardsAPIRESTful.repository;

import com.razzies.GoldenRaspberryAwardsAPIRESTful.model.MovieModel;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<MovieModel,Integer> {
}
