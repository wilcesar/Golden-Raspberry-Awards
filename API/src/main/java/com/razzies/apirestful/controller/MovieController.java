package com.razzies.apirestful.controller;

import com.razzies.apirestful.model.Movie;
import com.razzies.apirestful.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class MovieController {
    @Autowired
    private MovieRepository repository;
    private static final String CSV_PATH = "D:\\movielist.csv";

    @PostMapping(path = "/movie/salvar")
    public Movie salvar(@RequestBody Movie movie){
        return repository.save(movie);
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

                    repository.save(movie);
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
