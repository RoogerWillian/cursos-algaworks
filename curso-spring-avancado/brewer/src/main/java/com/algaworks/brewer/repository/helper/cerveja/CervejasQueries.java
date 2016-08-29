package com.algaworks.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.cerveja.CervejaFilter;

public interface CervejasQueries {
	
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);
	
	public List<CervejaDTO> porNomeOuSku(String nomeOuSku);
}
