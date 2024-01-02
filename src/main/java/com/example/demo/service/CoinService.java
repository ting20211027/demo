package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.CoinDeskRepository;
import com.example.demo.model.CoinDesk;
@Service
@Transactional
public class CoinService {
	
	@Autowired
	private CoinDeskRepository coinDeskRepository;
	
	public List<CoinDesk> findAll(){
		return coinDeskRepository.findAll();
	}
	
	public void saveCoin(CoinDesk coinDesk) {
		coinDeskRepository.save(coinDesk);
	}
	
	public void deleteById(Long id) {
		coinDeskRepository.deleteById(id);
	}
	
	public CoinDesk update(CoinDesk coinDesk) {
		return coinDeskRepository.save(coinDesk);
	}
	
	public Optional<CoinDesk> findByCode(String code) {
		return coinDeskRepository.findByCodeIgnoreCase(code);
	}

}
