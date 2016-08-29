package com.algaworks.brewer.dto;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Origem;

public class CervejaDTO {

	private Long codigo;
	private String nome;
	private String sku;
	private String origem;
	private BigDecimal valor;
	private String foto;

	public CervejaDTO(Long codigo, String nome, String sku, Origem origem, BigDecimal valor, String foto) {
		this.codigo = codigo;
		this.nome = nome;
		this.sku = sku;
		this.origem = origem.getDescricao();
		this.valor = valor;
		this.foto = StringUtils.isEmpty(foto) ? "cerveja-mock.png" : foto;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}
