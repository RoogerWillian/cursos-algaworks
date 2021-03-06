package com.algaworks.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.filter.usuario.UsuarioFilter;

public interface UsuariosQueries {
	
	public Optional<Usuario> porEmailAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
	
	public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);
}
