package com.example.BloggingPlatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloggingPlatform.service.DataPopulationSerivce;

@RestController
public class DataPopulationController {

	@Autowired
	private DataPopulationSerivce dataPopulationSerivce;
	
	@PostMapping("/populateData")
	public String populateData() {
		return dataPopulationSerivce.populateData();
	}
}
