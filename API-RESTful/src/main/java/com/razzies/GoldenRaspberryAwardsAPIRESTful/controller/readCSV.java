package com.razzies.GoldenRaspberryAwardsAPIRESTful.controller;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.razzies.GoldenRaspberryAwardsAPIRESTful.model.MovieModel;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class readCSV {
    private static final String CSV_PATH = "D:\\movielist.csv";
    public static ArrayList<MovieModel> readFile() throws IOException {
        ArrayList<MovieModel> movies = new ArrayList<>();

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

                    Integer id = idCount;
                    Integer year = Integer.parseInt(colunas[0].trim());
                    String title = colunas[1].trim();
                    String studio = colunas[2].trim();
                    String producer = colunas[3].trim();
                    String winner = colunas[4].trim();

                    MovieModel movie = new MovieModel();
                    movie.setId(id);
                    movie.setYear(year);
                    movie.setTitle(title.replaceAll("\'",""));
                    movie.setStudios(studio.replaceAll("\'",""));
                    movie.setProducers(producer.replaceAll("\'",""));
                    movie.setWinner(winner);
                    movies.add(movie);

                    idCount = idCount+1;

                } catch (Exception ex) {
                    System.out.println("PROBLEMA AO LER O ARQUIVO CSV!\n " + ex);
                }


            }
        }
        return movies;
    }

}
