package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.CoindeskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api")
public class CoindeskController {

	@Autowired
	private CoindeskService coindeskService;
	
	@GetMapping("/coindesk")
	public ResponseEntity<String> getCoindeskApi() throws JsonMappingException, JsonProcessingException {
		String coindeskApi = coindeskService.getCoindeskApi();
		System.out.println("coindeskApi:"+coindeskApi);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode readTree = objectMapper.readTree(coindeskApi);
		JsonNode time = readTree.get("time");
		JsonNode updated = readTree.get("time").get("updated");
		JsonNode updatedISO = readTree.get("time").get("updatedISO");
		JsonNode updateduk = readTree.get("time").get("updateduk");
		JsonNode disclaimer = readTree.get("disclaimer");
		JsonNode chartName = readTree.get("chartName");
		JsonNode bpi = readTree.get("bpi");
		JsonNode usd = readTree.get("bpi").get("USD");
		JsonNode gbp = readTree.get("bpi").get("GBP");

		System.out.println("============");
		System.out.println("time:"+time);
		System.out.println("updated:"+updated);
		System.out.println("updatedISO:"+updatedISO);
		System.out.println("updateduk:"+updateduk);
		System.out.println("disclaimer:"+disclaimer);
		System.out.println("chartName:"+chartName);
		System.out.println("bpi:"+bpi);
		System.out.println("usd:"+usd);
		System.out.println("gbp:"+gbp);
		return new ResponseEntity<String>(coindeskApi,HttpStatus.OK);
		
	}
}
