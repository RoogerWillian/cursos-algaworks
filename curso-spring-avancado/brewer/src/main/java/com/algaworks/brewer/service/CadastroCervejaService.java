package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.controller.exception.CervejaSkuExistenteException;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {

	@Autowired
	private Cervejas cervejas;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void salvar(Cerveja cerveja) {

		Optional<Cerveja> cervejaOptional = cervejas.findBySku(cerveja.getSku());

		if (cervejaOptional.isPresent()) {
			throw new CervejaSkuExistenteException(
					"Já existe uma cerveja cadastrada com o SKU " + cerveja.getSku() + " , tente novamente");
		}
		this.cervejas.save(cerveja);

		publisher.publishEvent(new CervejaSalvaEvent(cerveja));
	}
}
