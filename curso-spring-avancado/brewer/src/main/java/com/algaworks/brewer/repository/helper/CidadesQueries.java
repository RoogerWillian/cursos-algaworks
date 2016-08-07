package com.algaworks.brewer.repository.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.cidade.CidadesFilter;

public interface CidadesQueries {
	
	public Page<Cidade> filtrar(CidadesFilter filter, Pageable pageable);
	
}
