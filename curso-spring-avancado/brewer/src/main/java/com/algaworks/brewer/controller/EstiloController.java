package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estilos")
public class EstiloController {

	private static final String CADASTRO_ESTILO_VIEW = "/estilo/CadastroEstilo";

	@RequestMapping("/novo")
	public String novo(){
		return CADASTRO_ESTILO_VIEW;
	}
}
