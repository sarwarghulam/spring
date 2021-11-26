package com.example.demo;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.demo.services.CoronaVirusDataService;

@SpringBootApplication
@EnableScheduling
public class CoronaVirusTrackerApplication {
	@Autowired
	private CoronaVirusDataService coronaVirusDataService ;
	

	public static void main(String[] args) {
		SpringApplication.run(CoronaVirusTrackerApplication.class, args);
	}
	
	@PostConstruct
	public void getAllDataForCovidCases() throws IOException, InterruptedException {
		coronaVirusDataService.fetchCoronaData();
	}

}
