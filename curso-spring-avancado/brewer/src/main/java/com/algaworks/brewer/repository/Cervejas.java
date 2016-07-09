package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.brewer.model.Cerveja;

public interface Cervejas extends JpaRepository<Cerveja, Long>{
	
	public Optional<Cerveja> findBySku(String sku);
	
}
