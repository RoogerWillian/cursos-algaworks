var incrementar = function(){
	var valor = 0;

	return  {
		teste : function (){
			return ++valor;
		}
	}
}();

console.log(incrementar.teste());
console.log(incrementar.teste());
console.log(incrementar.teste());