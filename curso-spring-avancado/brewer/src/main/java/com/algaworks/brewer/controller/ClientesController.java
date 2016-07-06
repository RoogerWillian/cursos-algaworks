package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.algaworks.brewer.model.Cliente;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	private static final String CADASTRO_CLIENTE_VIEW = "/cliente/CadastroCliente";
	
	@RequestMapping("/novo")
	public String novo(Model model){
		model.addAttribute(new Cliente());
		return CADASTRO_CLIENTE_VIEW;
	}
}
