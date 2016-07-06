package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.algaworks.brewer.model.Usuario;

@Controller
@RequestMapping("/usuarios/")
public class UsuarioController {

	private static final String CADASTRO_USUARIO_VIEW = "/usuario/CadastroUsuario";

	@RequestMapping("/novo") 
	public String novo(Model model) {
		model.addAttribute(new Usuario());
		return CADASTRO_USUARIO_VIEW;
	}
}
