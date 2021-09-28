package com.razzies.apirestful.controller;

import com.razzies.apirestful.model.Movie;
import com.razzies.apirestful.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRrepository;

    private static final String CSV_PATH = "D:\\movielist.csv";
    private Integer minDif = 0;
    private String  minProducers;
    private String  minMovie1;
    private String  minMovie2;
    private Integer maxFif = 0;
    private String  maxProducers;
    private String  maxMovie1;
    private String  maxMovie2;

    @PostMapping(path = "/movie/salvar")
    public Movie salvar(@RequestBody Movie movie){
        return movieRrepository.save(movie);
    }

    @GetMapping(path = "/movie/producers")
    public String producers(){
        ArrayList producersMovies =movieRrepository.producersMovies();
        producersMovies.forEach(prod->{
            /*ArrayList<Movie> moviesProducers = movieRrepository.moviesByProducers(prod.toString());
            if(moviesProducers.size() > 1){
                moviesProducers.forEach(movie->{
                    ArrayList<Movie> nextMovie = movieRrepository.nextMovies(movie.getProducers(), movie.getYear());
                    if (Math.abs(movie.getYear() * nextMovie.get(0).getYear())>maxFif){
                        maxFif = Math.abs(movie.getYear() * nextMovie.get(0).getYear());
                        maxProducers = movie.getProducers();
                        maxMovie1 = movie.getTitle();
                        maxMovie2 = nextMovie.get(0).getTitle();
                    };

                    if(Math.abs(movie.getYear() * nextMovie.get(0).getYear())<minDif){
                        minDif = Math.abs(movie.getYear() * nextMovie.get(0).getYear());
                        minProducers = movie.getProducers();
                        minMovie1 = movie.getTitle();
                        minMovie2 = nextMovie.get(0).getTitle();
                    };

                });
            }*/
        });

        return "foi";
    }
    @GetMapping(path = "/movie/carregar")
    public Boolean carregar(){
        ArrayList<Movie> movies = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String linha;
            Integer idCount = 1;
            boolean eCabecalho = true;
            while ((linha = br.readLine()) != null) {
                if (eCabecalho || linha.trim().isEmpty()) {
                    eCabecalho = false;
                    continue;
                }
                String[] colunas = linha.split(";", 5); //Presumo que seu CSV tenha 5 colunas
                if (colunas.length != 5) {
                    System.out.println("O CSV tem mais ou menos de 5 colunas!");
                    continue;
                }
                try {
                    Integer year = Integer.parseInt(colunas[0].trim());
                    String title = colunas[1].trim();
                    String studio = colunas[2].trim();
                    String producer = colunas[3].trim();
                    String winner = colunas[4].trim();

                    Movie movie = new Movie();
                    movie.setYear(year);
                    movie.setTitle(title);
                    movie.setStudios(studio);
                    movie.setProducers(producer);
                    movie.setWinner(winner);
                    movies.add(movie);

                    movieRrepository.save(movie);
                    System.out.println("SALVANDO FILME: " + title);
                } catch (Exception ex) {
                    System.out.println("PROBLEMA AO LER O ARQUIVO CSV!\n " + ex);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
