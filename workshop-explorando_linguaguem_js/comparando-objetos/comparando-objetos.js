var joao = {
	nome : 'João',
	iddade : 25,
	email : 'teste@email.com',
	igualA : function(obj){
		return this.email === obj.email;
	}
}

var maria = {
	nome : 'Maria',
	iddade : 27,
	email : 'teste@email.com'
}

console.log('O E-mail de João é igual ao de Maria:', joao.igualA(maria));