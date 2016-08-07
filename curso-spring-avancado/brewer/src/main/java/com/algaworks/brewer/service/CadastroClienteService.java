package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.service.exception.ClienteCpfCnpjJaCadastradoException;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;

	@Transactional
	public void salvar(Cliente cliente) {

		Optional<Cliente> clienteOptional = clientes.findByCpfOuCnpj(cliente.getCpfOuCnpjSemPontuacao());
		if (clienteOptional.isPresent()) {
			throw new ClienteCpfCnpjJaCadastradoException("CPF/CNPJ já cadastrado");
		}
		this.clientes.save(cliente);
	}

}
