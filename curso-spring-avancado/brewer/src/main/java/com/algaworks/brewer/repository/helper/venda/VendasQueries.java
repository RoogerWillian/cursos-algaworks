package com.algaworks.brewer.repository.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.venda.VendaFilter;

public interface VendasQueries {
	
	public Page<Venda> filtrar(VendaFilter vendaFilter,Pageable pageable);
	
}
