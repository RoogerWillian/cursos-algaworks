this.Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	function UploadFoto() {
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.containerFoto = $('.js-container-foto');
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);
		this.uploadDrop = $('#upload-drop');
	}

	UploadFoto.prototype.iniciar = function() {
		var settings = {
			type : 'json',
			filelimit : 1,
			allow : '*.(jpg|jpeg|png)',
			action : this.containerFoto.data('url-foto'),
			complete : onUploadComplete.bind(this)
		}
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadDrop, settings);

		if (this.inputNomeFoto.val()) {
			onUploadComplete.call(this, {
				nome : this.inputNomeFoto.val(),
				contentType : this.inputContentType.val()
			});
		}
	}

	function onUploadComplete(resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);

		this.uploadDrop.addClass('hidden');
		var htmlFotoCerveja = this.template({
			nomeFoto : resposta.nome
		});
		this.containerFoto.append(htmlFotoCerveja);
		$('.js-remove-foto').on('click', onRemoverClick.bind(this));
	}

	function onRemoverClick() {
		$('.js-foto-cerveja').remove();
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
		this.uploadDrop.removeClass('hidden');
	}

	return UploadFoto;

}());

$(function() {
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
});