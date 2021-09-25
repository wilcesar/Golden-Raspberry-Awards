package com.razzies.GoldenRaspberryAwardsAPIRESTful;

import com.razzies.GoldenRaspberryAwardsAPIRESTful.controller.MovieController;
import com.razzies.GoldenRaspberryAwardsAPIRESTful.controller.readCSV;
import com.razzies.GoldenRaspberryAwardsAPIRESTful.model.MovieModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
public class GoldenRaspberryAwardsApiResTfulApplication {

	@GetMapping("/Salvar")
	public String salvarDados(){

		return "Salvo";
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(GoldenRaspberryAwardsApiResTfulApplication.class, args);
		readCSV read = new readCSV();
		MovieController movieController = new MovieController();

		ArrayList<MovieModel> movies = read.readFile();
		try {
			movies.forEach(mov ->{
				movieController.salvar(mov);
			});
		}catch (Exception ex){
			System.out.println("PROBLEMA AO GRAVAR OS DADOS NO BANCO DE DADOS!\n " + ex);
		}

	}

}
