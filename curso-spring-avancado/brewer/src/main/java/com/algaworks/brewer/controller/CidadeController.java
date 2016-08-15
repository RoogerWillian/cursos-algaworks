package com.algaworks.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.cidade.CidadesFilter;
import com.algaworks.brewer.service.CadastroCidadeService;

@Controller
@RequestMapping("/cidades")
public class CidadeController {

	private static final String CADASTRO_CIDADE_VIEW = "/cidade/CadastroCidade";

	private static final String PESQUISA_CIDADE_VIEW = "/cidade/PesquisaCidade";

	@Autowired
	private Cidades cidades;

	@Autowired
	private Estados estados;

	@Autowired
	private CadastroCidadeService cidadeService;

	@GetMapping
	public ModelAndView pesquisar(CidadesFilter cidadeFilter, BindingResult result,
			@PageableDefault(size = 6) Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(PESQUISA_CIDADE_VIEW);
		mv.addObject("estados", estados.findAll());
		Page<Cidade> page = cidades.filtrar(cidadeFilter, pageable);
		PageWrapper<Cidade> pagina = new PageWrapper<>(page, request);
		mv.addObject("pagina", pagina);
		return mv;
	}

	@GetMapping("/nova")
	public ModelAndView nova(Cidade cidade) {
		ModelAndView mv = new ModelAndView(CADASTRO_CIDADE_VIEW);
		mv.addObject("estados", estados.findAll());
		return mv;
	}

	@PostMapping("/nova")
	@CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return nova(cidade);
		}

		this.cidadeService.salvar(cidade);
		attributes.addFlashAttribute("mensagem", "Cidade salva com sucesso");
		return new ModelAndView("redirect:/cidades/nova");
	}

	@Cacheable(value = "cidades", key = "#codigoEstado")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		return cidades.findByEstadoCodigo(codigoEstado);
	}
}
