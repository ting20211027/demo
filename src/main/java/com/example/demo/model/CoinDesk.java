package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity @Table(name = "幣別")
@Component
public class CoinDesk {
	@Id @Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "SYMBOL")
	private String symbol;
	
	@Column(name = "RATE")
	private String rate;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "RATEFLOAT")
	private Float rateFloat;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Float getRateFloat() {
		return rateFloat;
	}
	public void setRateFloat(Float rateFloat) {
		this.rateFloat = rateFloat;
	}
	@Override
	public String toString() {
		return "CoinDesk [id=" + id + ", code=" + code + ", symbol=" + symbol + ", rate=" + rate + ", description="
				+ description + ", rateFloat=" + rateFloat + "]";
	}
	
	

}
