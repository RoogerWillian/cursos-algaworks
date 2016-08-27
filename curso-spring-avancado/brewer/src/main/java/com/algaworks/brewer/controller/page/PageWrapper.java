package com.algaworks.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
		this.page = page;
		String httpUrl = httpServletRequest.getRequestURL().append(
				httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
				.toString().replaceAll("\\+", "%20");
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);	
	}

	public List<T> getConteudo() {
		return page.getContent();
	}

	public int getTotal() {
		return page.getTotalPages();
	}

	public boolean isUltima() {
		return page.isLast();
	}

	public boolean isPrimeira() {
		return page.isFirst();
	}

	public boolean isVazia() {
		return page.getContent().isEmpty();
	}

	public int getAtual() {
		return page.getNumber();
	}

	public String urlParaPagina(int pagina) {
		return this.uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}

	public String urlOrdenacao(String propiedade) {
		UriComponentsBuilder uriBuilderOrdenacao = UriComponentsBuilder
				.fromUriString(this.uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s,%s", propiedade, inverterOrdenacao(propiedade));
		
		return uriBuilderOrdenacao.replaceQueryParam("sort", valorSort).build(true).encode().toString();
	}

	public String inverterOrdenacao(String propiedade) {
		String direction = "asc";

		Order order = page.getSort() != null ? page.getSort().getOrderFor(propiedade) : null;

		if (order != null) {
			direction = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}

		return direction;
	}
	
	public boolean descendente(String propiedade){
		return inverterOrdenacao(propiedade).equals("asc");
	}
	
	public boolean ordenado(String propriedade){
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null; 
		
		if(order == null)
			return false;
		
		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}
}
