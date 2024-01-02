package com.example.demo.controller;


import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CoinDeskRepository;
import com.example.demo.model.CoinDesk;
import com.example.demo.service.CoinService;
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
	@Autowired
	private CoinService coinService;
	@Autowired
	private CoinDeskRepository coinDeskRepository;
	
	@GetMapping("/coindeskApi")
	public ResponseEntity<JsonNode> getCoindeskApi() throws JsonMappingException, JsonProcessingException {
		String coindeskApi = coindeskService.getCoindeskApi();
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode readTree = objectMapper.readTree(coindeskApi);
		
		return new ResponseEntity<JsonNode>(readTree,HttpStatus.OK);
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addCoinDesk() throws JsonMappingException, JsonProcessingException{
		String coindeskApi = coindeskService.getCoindeskApi();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode readTree = objectMapper.readTree(coindeskApi);
		JsonNode bpi = readTree.get("bpi");
		
		for (JsonNode jsonNode : bpi) {
			JsonNode code = jsonNode.get("code");
			JsonNode symbol = jsonNode.get("symbol");
			JsonNode rate = jsonNode.get("rate");
			JsonNode description = jsonNode.get("description");
			JsonNode rateFloat = jsonNode.get("rate_float");

			CoinDesk coinDesk = new CoinDesk();
			coinDesk.setCode(code.asText());
			coinDesk.setSymbol(symbol.asText());
			coinDesk.setRate(rate.asText());
			coinDesk.setDescription(description.asText());
			coinDesk.setRateFloat(Float.valueOf(rateFloat.asText()));
			coinService.saveCoin(coinDesk);
		}
		
		return new ResponseEntity<String>(HttpStatus.OK);

	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCoinDesk(Long id) {
		try {
			coinService.deleteById(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<CoinDesk> updateCoinDesk(@PathVariable Long id, @RequestBody CoinDesk coinDesk){
		
		CoinDesk existCoinDesk = coinDeskRepository.findById(id).orElse(null);
		
		 if (existCoinDesk == null) {
	         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	     }
		 
		 coinDesk.setId(existCoinDesk.getId());
		 
		 if(coinDesk.getCode() == null) {
			 coinDesk.setCode(existCoinDesk.getCode());
		 }
		 
		 if(coinDesk.getDescription() == null) {
			 coinDesk.setDescription(existCoinDesk.getDescription());
		 }
		 
		 if(coinDesk.getRate() == null) {
			 coinDesk.setRate(existCoinDesk.getRate());
		 }
		 
		 if(coinDesk.getRateFloat() == null) {
			 coinDesk.setRateFloat(existCoinDesk.getRateFloat());
		 }
		 
		 if(coinDesk.getSymbol() == null) {
			 coinDesk.setSymbol(existCoinDesk.getSymbol());
		 }
		
		CoinDesk update = coinService.update(coinDesk);
		return new ResponseEntity<CoinDesk>(update,HttpStatus.OK);
	}
	
	@GetMapping("/findall")
	public ResponseEntity<List<CoinDesk>> getDbData(){
		try {
			List<CoinDesk> findAll = coinService.findAll();
			return new ResponseEntity<List<CoinDesk>>(findAll,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<CoinDesk>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/findbycode")
	public ResponseEntity<Optional<CoinDesk>> getDbDataByCode(String code){
		try {
			Optional<CoinDesk> findByCode = coinService.findByCode(code);
			return new ResponseEntity<Optional<CoinDesk>>(findByCode,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Optional<CoinDesk>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/newCoindeskApi")
	public ResponseEntity<Map<String, Object>> getNewCoinDeskApi() throws JsonMappingException, JsonProcessingException, ParseException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z");
		DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, String> coinMap = new HashMap<>();
		coinMap.put("USD", "美金");
		coinMap.put("GBP", "英鎊");
		coinMap.put("EUR", "歐元");
		
		String coindeskApi = coindeskService.getCoindeskApi();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode readTree = objectMapper.readTree(coindeskApi);
		JsonNode updated = readTree.get("time").get("updated");
		JsonNode bpi = readTree.get("bpi");
		
		LocalDateTime parse = LocalDateTime.parse(updated.asText(), formatter);
		String format = parse.format(targetFormatter);
		
		map.put("updateTime", format);
		
		for (JsonNode jsonNode : bpi) {
			Map<String, Object> bpiMap = new HashMap<>();
			String code = jsonNode.get("code").asText();
			String rate = jsonNode.get("rate").asText();
			String codeName = coinMap.getOrDefault(code, "其他");
			
			bpiMap.put("codeName", codeName);
			bpiMap.put("code", code);
			bpiMap.put("rate", rate);
			
			list.add(bpiMap);
		}
			
		map.put("bpi",list);
		
		return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
		
		
		
	}
}
