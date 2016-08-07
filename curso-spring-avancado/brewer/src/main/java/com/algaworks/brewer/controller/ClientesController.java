package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.cliente.ClienteFilter;
import com.algaworks.brewer.service.CadastroClienteService;
import com.algaworks.brewer.service.exception.ClienteCpfCnpjJaCadastradoException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

	private static final String CADASTRO_CLIENTE_VIEW = "/cliente/CadastroCliente";
	private static final String PESQUISA_CLIENTE_VIEW = "/cliente/PesquisaCliente";
	
	@Autowired
	private Estados estados;

	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@Autowired
	private Clientes clientes;
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter filter, BindingResult result,
			@PageableDefault(size = 2) Pageable pageable, HttpServletRequest request){
		ModelAndView mv = new ModelAndView(PESQUISA_CLIENTE_VIEW);
		PageWrapper<Cliente> pagina = new PageWrapper<>(clientes.filtrar(filter, pageable), request);
		mv.addObject("pagina", pagina);
		return mv;
	}
	
	@GetMapping("/novo")
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView(CADASTRO_CLIENTE_VIEW);
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estados.findAll());
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cliente);
		}
		try {
			this.cadastroClienteService.salvar(cliente);
		} catch (ClienteCpfCnpjJaCadastradoException e) {
			result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso");
		return new ModelAndView("redirect:/clientes/novo");
	}
}
