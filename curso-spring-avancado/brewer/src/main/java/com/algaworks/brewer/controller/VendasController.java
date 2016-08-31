package com.algaworks.brewer.controller;

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
import com.algaworks.brewer.session.TabelaItensVenda;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	private static final String CADASTRO_NOVA_VENDA = "/venda/CadastroVenda";
	private static final String VENDA_ITENS = "/venda/TabelaItemVenda";

	@Autowired
	private Cervejas cervejas;

	@Autowired
	private TabelaItensVenda tabelaItensVenda;

	@GetMapping("/nova")
	public String nova() {
		return CADASTRO_NOVA_VENDA;
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja) {
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		return mvTabelaItens();
	}

	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidade(@PathVariable("codigoCerveja") Cerveja cerveja,
			Integer quantidade) {
		tabelaItensVenda.alterarQuantidadeItemCerveja(cerveja, quantidade);
		return mvTabelaItens();
	}

	
	@DeleteMapping("/item/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja){
		tabelaItensVenda.excluirItem(cerveja);
		return mvTabelaItens();
	}
	
	private ModelAndView mvTabelaItens() {
		ModelAndView mv = new ModelAndView(VENDA_ITENS);
		mv.addObject("itens", tabelaItensVenda.getItens());
		return mv;
	}
}
