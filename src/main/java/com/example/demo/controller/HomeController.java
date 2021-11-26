package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.services.CoronaVirusDataService;

@Controller
public class HomeController {
	@Autowired
	private CoronaVirusDataService coronaVirusDataService;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("allRecord", coronaVirusDataService.allStats);
		model.addAttribute("totalCases", coronaVirusDataService.totalCases);
		model.addAttribute("newCases", coronaVirusDataService.newCases);
		return "home";
	}

}
