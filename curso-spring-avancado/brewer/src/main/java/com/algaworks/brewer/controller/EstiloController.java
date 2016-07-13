package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.service.CadastroEstiloService;

@Controller
@RequestMapping("/estilos")
public class EstiloController {

	private static final String CADASTRO_ESTILO_VIEW = "/estilo/CadastroEstilo";

	@Autowired
	private CadastroEstiloService estiloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		ModelAndView mv = new ModelAndView(CADASTRO_ESTILO_VIEW);
		mv.addObject(estilo);
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return novo(estilo);
		}
		this.estiloService.cadastrar(estilo);
		attr.addFlashAttribute("mensagem", "Estilo salvo com sucesso");
		return new ModelAndView("redirect:/estilos/novo");
	}
}
