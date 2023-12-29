package com.example.demo.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.service.CoindeskService;

@Service
public class CoindeskServiceImpl implements CoindeskService{

	@Override
	public String getCoindeskApi() {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
		
		ResponseEntity<String> response = restTemplate.exchange("https://api.coindesk.com/v1/bpi/currentprice.json", HttpMethod.GET, httpEntity, String.class);
		
		String body = response.getBody();
		
		return body;
	}
}
