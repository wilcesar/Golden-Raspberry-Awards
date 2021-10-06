package com.razzies.apirestful.controller;

import com.razzies.apirestful.model.Movie;
import com.razzies.apirestful.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/movie/producers/{producer}")
    ArrayList<Movie> producerMovies(@PathVariable("producer") String producer){
        return movieRrepository.moviesByProducer(producer);
    }
    @GetMapping(path = "/nominees/{year}")
    ArrayList<Movie> nomineesMovies(@PathVariable("year") Integer year){
        return movieRrepository.nomineesByYear(year);
    }
    @GetMapping(path = "/winners")
    ArrayList<Movie> winnersMovies(){
        return movieRrepository.moviesWinner();
    }
    @GetMapping(path = "/movie/winner/{year}")
    public Movie winnerFronYear(@PathVariable("year") Integer year){
        return movieRrepository.winnerFronYear(year);
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
