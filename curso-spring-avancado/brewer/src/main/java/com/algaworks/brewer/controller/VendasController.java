package com.algaworks.brewer.controller;

import static java.util.UUID.randomUUID;
import static org.springframework.util.StringUtils.isEmpty;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.controller.validator.VendaValidator;
import com.algaworks.brewer.dto.VendaMes;
import com.algaworks.brewer.dto.VendaPorOrigem;
import com.algaworks.brewer.mail.Mailer;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Vendas;
import com.algaworks.brewer.repository.filter.venda.VendaFilter;
import com.algaworks.brewer.security.UsuarioLogado;
import com.algaworks.brewer.service.CadastroVendaService;
import com.algaworks.brewer.session.TabelasItensSession;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	private static final String CADASTRO_NOVA_VENDA = "/venda/CadastroVenda";
	private static final String PESQUISA_VENDA = "/venda/PesquisaVenda";
	private static final String VENDA_ITENS = "/venda/TabelaItemVenda";

	@Autowired
	private Cervejas cervejas;

	@Autowired
	private TabelasItensSession tabelaItens;

	@Autowired
	private CadastroVendaService cadastroVendaService;

	@Autowired
	private VendaValidator vendaValidator;

	@Autowired
	private Vendas vendas;
	
	@Autowired
	private Mailer mailer;
	
	@InitBinder("venda")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(vendaValidator);
	}

	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter,@PageableDefault(size=7)Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(PESQUISA_VENDA);
		
		PageWrapper<Venda> pagina = new PageWrapper<>(vendas.filtrar(vendaFilter, pageable), request);
		mv.addObject("pagina", pagina);
		mv.addObject("todosStatus", StatusVenda.values());
		return mv;
	}

	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView(CADASTRO_NOVA_VENDA);

		if (isEmpty(venda.getUuid())) {
			venda.setUuid(randomUUID().toString());
		}

		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItens.getValorTotal(venda.getUuid()));

		return mv;
	}

	@PostMapping(value = "/nova", params = "salvar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioLogado usuarioLogado) {
		
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return nova(venda);
		}

		venda.setUsuario(usuarioLogado.getUsuario());

		cadastroVendaService.salvar(venda);
		
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
		return new ModelAndView("redirect:/vendas/nova");
	}

	@PostMapping(value = "/nova", params = "emitir")
	public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioLogado usuarioLogado) {
		validarVenda(venda, result);

		if (result.hasErrors()) {
			return nova(venda);
		}

		venda.setUsuario(usuarioLogado.getUsuario());

		cadastroVendaService.emitir(venda);
		attributes.addFlashAttribute("mensagem", "Venda emitida com sucesso");
		return new ModelAndView("redirect:/vendas/nova");
	}

	private void validarVenda(Venda venda, BindingResult result) {
		venda.adicionarItens(tabelaItens.getItens(venda.getUuid()));
		venda.calcularValorTotal();

		vendaValidator.validate(venda, result);
	}

	@PostMapping(value = "/nova", params = "enviarEmail")
	public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes attributes,
			@AuthenticationPrincipal UsuarioLogado usuarioLogado) {
		
		validarVenda(venda, result);
		if (result.hasErrors()) {
			return nova(venda);
		}

		venda.setUsuario(usuarioLogado.getUsuario());

		cadastroVendaService.salvar(venda);
		mailer.enviar(venda);
		
		attributes.addFlashAttribute("mensagem", "Venda salva e e-mail enviado.");
		return new ModelAndView("redirect:/vendas/nova");
	}

	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidade(@PathVariable("codigoCerveja") Cerveja cerveja, Integer quantidade,
			String uuid) {
		tabelaItens.alterarQuantidadeItens(uuid, cerveja, quantidade);
		return mvTabelaItens(uuid);
	}

	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
		Cerveja cerveja = cervejas.findOne(codigoCerveja);
		tabelaItens.adicionarItem(uuid, cerveja, 1);
		return mvTabelaItens(uuid);
	}

	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable String uuid) {
		tabelaItens.excluirItem(uuid, cerveja);
		return mvTabelaItens(uuid);
	}
	
	@GetMapping("/totalPorMes")
	public @ResponseBody List<VendaMes> listarTotalVendaPorMes(){
		return vendas.totalPorMes();
	}
	
	@GetMapping("/totalPorOrigem")
	public @ResponseBody List<VendaPorOrigem> listarTotalVendaPorOrigem(){
		return vendas.totalPorOrigem();
	}
	
	
	
	private ModelAndView mvTabelaItens(String uuid) {
		ModelAndView mv = new ModelAndView(VENDA_ITENS);
		mv.addObject("itens", tabelaItens.getItens(uuid));
		mv.addObject("valorTotal", tabelaItens.getValorTotal(uuid));
		return mv;
	}
}
