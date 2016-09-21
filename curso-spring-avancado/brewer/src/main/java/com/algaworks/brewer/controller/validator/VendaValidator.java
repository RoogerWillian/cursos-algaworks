package com.algaworks.brewer.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.algaworks.brewer.model.Venda;

@Component
public class VendaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Venda.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "cliente.codigo", "", "Selecione um cliente na pesquisa rápida");
		Venda venda = (Venda) target;

		validarHorarioVenda(errors, venda);
		validarQuantidadeItens(errors, venda);
		validarValorTotal(errors, venda);
	}

	private void validarValorTotal(Errors errors, Venda venda) {
		if(venda.getValorTotal().compareTo(BigDecimal.ZERO) < 0){
			errors.rejectValue("", "", "Valor total não pode ser negativo");
		}
	}

	private void validarQuantidadeItens(Errors errors, Venda venda) {
		if (venda.getItens().isEmpty()) {
			errors.rejectValue("", "", "Adicione pelo menos uma cerveja na venda");
		}
	}

	private void validarHorarioVenda(Errors errors, Venda venda) {
		if (venda.getHorarioEntrega() != null && venda.getDataEntrega() == null) {
			errors.rejectValue("dataEntrega", "", "Informe uma data de entrega para um horario");
		}
	}

}
