package com.algaworks.brewer.controller.exception;

public class CervejaSkuExistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CervejaSkuExistenteException(String msg) {
		super(msg);
	}
}
