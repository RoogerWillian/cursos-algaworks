package com.algaworks.brewer.repository.filter.cidade;

import com.algaworks.brewer.model.Estado;

public class CidadesFilter {
	
	private String nome;
	private Estado estado;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}
