package com.algaworks.brewer.repository.filter.venda;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.algaworks.brewer.model.StatusVenda;

public class VendaFilter {

	private Long codigo;
	
	private StatusVenda statusVenda;

	private LocalDate desde;
	
	private LocalDate ate;
	
	private BigDecimal valorMinimo;
	
	private BigDecimal valorMaximo;
	
	private String nomeCliente;
	
	private String cpfOuCpnjCliente;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public StatusVenda getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(StatusVenda statusVenda) {
		this.statusVenda = statusVenda;
	}

	public LocalDate getDesde() {
		return desde;
	}

	public void setDesde(LocalDate desde) {
		this.desde = desde;
	}

	public LocalDate getAte() {
		return ate;
	}

	public void setAte(LocalDate ate) {
		this.ate = ate;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCpfOuCpnjCliente() {
		return cpfOuCpnjCliente;
	}

	public void setCpfOuCpnjCliente(String cpfOuCpnjCliente) {
		this.cpfOuCpnjCliente = cpfOuCpnjCliente;
	}

}
