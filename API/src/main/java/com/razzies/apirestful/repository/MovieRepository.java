package com.razzies.apirestful.repository;

import com.razzies.apirestful.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

//SELECT  PRODUCERS, COUNT(*) AS QTDE  FROM MOVIES M GROUP BY PRODUCERS HAVING QTDE >1;
//SELECT * FROM MOVIES  WHERE PRODUCERS = 'Jerry Weintraub' ORDER BY RELEASEYEAR;
@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

    @Query(value = "SELECT array_agg(m.producers) as chields FROM Movies m where m.winner = 'yes'", nativeQuery = true)
    String producersMovies();

    @Query("SELECT m FROM Movie m where m.winner = 'yes' and m.producers like '%:producer%'  order by m.year")
    ArrayList<Movie> moviesByProducer(@Param("producer") String producer);

    @Query("SELECT m FROM Movie m where m.year = :year")
    ArrayList<Movie> nomineesByYear(@Param("year") Integer year);

    @Query("SELECT m FROM Movie m where m.winner = 'yes' and m.producers = :producers  and m.year > :year order by m.year")
    ArrayList<Movie> nextMovies(@Param("producers") String producers, @Param("year") Integer year );

    @Query("SELECT m FROM Movie m where m.winner = 'yes' and m.year = :year")
    Movie winnerFronYear(@Param("year") Integer year);
}
