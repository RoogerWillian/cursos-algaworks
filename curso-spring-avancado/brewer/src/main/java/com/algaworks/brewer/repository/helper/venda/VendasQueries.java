package com.algaworks.brewer.repository.helper.venda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.dto.VendaMes;
import com.algaworks.brewer.dto.VendaPorOrigem;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.venda.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable);

	public BigDecimal valorTotalNoAno();

	public BigDecimal valorTotalNoMes();

	public BigDecimal valorTicketMedioNoAno();

	public List<VendaMes> totalPorMes();
	
	public List<VendaPorOrigem> totalPorOrigem();
}