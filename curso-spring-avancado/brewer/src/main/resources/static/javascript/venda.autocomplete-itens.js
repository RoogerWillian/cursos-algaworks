Brewer = Brewer || {};

Brewer.Autocomplete = (function(){
	
	function Autocomplete(){
		this.skuOuNomeInput = $('.js-sku-nome-cerveja-input');
		var htmlTemplateAutocomplete = $('#template-autocomplete-cerveja').html();
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	Autocomplete.prototype.iniciar = function (){
		var options = {
			url: function(skuOuNome) {
				return this.skuOuNomeInput.data('url') + '?skuOuNome=' + skuOuNome;
			}.bind(this),
			getValue : 'nome',
			minCharNumber : 3,
			requestDelay : 200,
			ajaxSettings : {
				contentType : 'application/json'
			},
			template : {
				type : 'custom',
				method : template.bind(this)
			},
			list : {
				onChooseEvent : onItemSelecionado.bind(this),
				showAnimation: {
					type: "fade", //normal|slide|fade
					time: 400,
					callback: function() {}
				},

				hideAnimation: {
					type: "fade", //normal|slide|fade
					time: 400,
					callback: function() {}
				}
			}
		};
		
		this.skuOuNomeInput.easyAutocomplete(options);
	}
	
	function template(nome,cerveja){
		cerveja.valorFormatado = Brewer.formatarMoeda(cerveja.valor);
		return this.template(cerveja);
	}
	
	function onItemSelecionado(){
		this.emitter.trigger('item-selecionado',this.skuOuNomeInput.getSelectedItemData());
		this.skuOuNomeInput.val('');
		this.skuOuNomeInput.focus();
	}
	
	
	return Autocomplete;
	
}());