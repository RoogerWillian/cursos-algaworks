package com.algaworks.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	
	
	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(request);
	}
	
	public List<T> getConteudo(){
		return page.getContent();
	}
	
	public int getTotal() {
		return page.getTotalPages();
	}
	
	public boolean isUltima(){
		return page.isLast();
	}
	
	public boolean isPrimeira(){
		return page.isFirst();
	}
	
	public boolean isVazia(){
		return page.getContent().isEmpty();
	}
	
	public int getAtual(){
		return page.getNumber();
	}
	
	public String urlParaPagina(int pagina){
		return this.uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toString();
	}
}
