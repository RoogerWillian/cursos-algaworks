package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.Vendas;

@Controller
public class DashboardController {
	
	@Autowired
	private Vendas vendas;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Cervejas cervejas;
	
	@RequestMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");

		mv.addObject("vendasAno", vendas.valorTotalNoAno());
		mv.addObject("vendasMes", vendas.valorTotalNoMes());
		mv.addObject("ticketMedio", vendas.valorTicketMedioNoAno());
		mv.addObject("totalClientes", clientes.totalClientes());
		mv.addObject("itensEmEstoque",cervejas.totalCervejasEmEstoque());
		
		mv.addObject("valorItensEstoque", cervejas.valorItensEstoque());
		
		return mv;
	}

}
