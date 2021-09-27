package com.razzies.apirestful.repository;

import com.razzies.apirestful.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie,Integer> {
}
