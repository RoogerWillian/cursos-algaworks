var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function() {

	function MascaraCpfCnpj() {
		this.radioCpfCnpj = $('.js-tipo-pessoa');
		this.labelCpfCnpj = $('[for=cpfOuCnpj]');
		this.inputDocumento = $('#cpfOuCnpj');
	}

	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioCpfCnpj.on('change', onTipoPessoaAlterado.bind(this));
		var tipoPessoaSelecionado = this.radioCpfCnpj.filter(':checked')[0];
		if (tipoPessoaSelecionado) {
			aplicarMascara.call(this, $(tipoPessoaSelecionado));
		}
	}

	function onTipoPessoaAlterado(event) {
		var tipoPessoaSelecionado = $(event.currentTarget);
		aplicarMascara.call(this, tipoPessoaSelecionado);
		this.inputDocumento.val('');
	}

	function aplicarMascara(tipoPessoaSelecionado) {
		this.labelCpfCnpj.text(tipoPessoaSelecionado.data('documento'));
		this.inputDocumento.removeAttr('disabled');
		this.inputDocumento.mask(tipoPessoaSelecionado.data('mascara'));
		this.inputDocumento.focus();

	}

	return MascaraCpfCnpj;

}());

$(function() {
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
});