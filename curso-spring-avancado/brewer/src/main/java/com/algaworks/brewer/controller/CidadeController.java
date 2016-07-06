package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.algaworks.brewer.model.Cidade;

@Controller
@RequestMapping("/cidades")
public class CidadeController {
	
	private static final String CADASTRO_CIDADE_VIEW ="/cidade/CadastroCidade";
	
	@RequestMapping("/nova")
	public String nova(Model model){
		model.addAttribute(new Cidade());
		return CADASTRO_CIDADE_VIEW;
	}
}
