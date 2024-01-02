package com.example.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CoinDesk;

public interface CoinDeskRepository extends JpaRepository<CoinDesk, Long>{
	
	Optional<CoinDesk> findByCodeIgnoreCase(String code);
}
