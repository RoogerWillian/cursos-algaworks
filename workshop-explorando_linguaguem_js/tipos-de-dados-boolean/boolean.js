var nome = '';

if(!nome) {
	console.log('Informe seu nome'); /* Cairá nesse if, pq a variavel nome É vazia */
} else {
	console.log('Obrigado');
}

nome = 'Roger';

if(!nome) {
	console.log('Informe seu nome'); 
} else {
	console.log('Obrigado!'); /* Cairá nesse if, pq a variavel nome NÃO É vazia */ 
}