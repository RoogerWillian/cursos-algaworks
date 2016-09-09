package com.algaworks.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.session.TabelasItensSession;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	private static final String CADASTRO_NOVA_VENDA = "/venda/CadastroVenda";
	private static final String VENDA_ITENS = "/venda/TabelaItemVenda";

	@Autowired
	private Cervejas cervejas;

	@Autowired
	private TabelasItensSession tabelaItens;

	@GetMapping("/nova")
	public ModelAndView nova() {
		ModelAndView mv = new ModelAndView(CADASTRO_NOVA_VENDA);
		mv.addObject("uuid", UUID.randomUUID().toString());
		return mv;
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja,String uuid) {
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItens.adicionarItem(uuid, cerveja, 1);
		return mvTabelaItens(uuid);
	}

	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidade(@PathVariable("codigoCerveja") Cerveja cerveja,
			Integer quantidade,String uuid) {
		tabelaItens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		return mvTabelaItens(uuid);
	}

	
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable String uuid){
		tabelaItens.excluirItem(uuid, cerveja);
		return mvTabelaItens(uuid);
	}
	
	private ModelAndView mvTabelaItens(String uuid) {
		ModelAndView mv = new ModelAndView(VENDA_ITENS);
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}
}
