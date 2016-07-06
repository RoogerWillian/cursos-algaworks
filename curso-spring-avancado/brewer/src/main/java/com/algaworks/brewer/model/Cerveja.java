package com.algaworks.brewer.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Cerveja {

	@NotBlank(message = "SKU é obrigatório")
	private String sku;

	@NotBlank(message = "NOME é obrigatório")
	private String nome;

	@NotBlank(message = "DESCRIÇÃO é obrigatória")
	@Size(min = 1, max = 50, message = "O tamanho de DESCRIÇÃO deve estar entre 1 e 50")
	private String descricao;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Cerveja [sku=" + sku + ", nome=" + nome + ", descricao=" + descricao + "]";
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
