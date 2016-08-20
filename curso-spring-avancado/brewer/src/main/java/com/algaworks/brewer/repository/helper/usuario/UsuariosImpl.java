package com.algaworks.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.algaworks.brewer.model.Usuario;

public class UsuariosImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Optional<Usuario> porEmailAtivo(String email) {
		return manager
				.createQuery("from Usuario u where lower(u.email) = lower(:email) and u.ativo = true",Usuario.class)
				.setParameter("email",email)
				.getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager
				.createQuery("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

}
