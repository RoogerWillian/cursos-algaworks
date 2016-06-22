var fox = {cor : 'Prata', modelo : 'Fox', marca : 'VW'};

console.log(typeof fox);

fox.ligar = function(){
	console.log('Ligando o carro');
}

console.log('Fox: ',fox);
fox.ligar();

var celta = {
	cor : 'Branco',
	modelo : 'Celta',
	fabricante : 'GM',
	ligar : function(){
		console.log('Ligando o celta');
	}
};

console.log('celta', celta);