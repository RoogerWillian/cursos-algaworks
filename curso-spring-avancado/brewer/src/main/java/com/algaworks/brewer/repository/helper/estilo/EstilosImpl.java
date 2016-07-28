package com.algaworks.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.estilo.EstiloFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class EstilosImpl implements EstilosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacao;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable) {
		Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Estilo.class);
		
		paginacao.preparar(criteria, pageable);
		
		adicionarFiltro(filter, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}

	private Long total(EstiloFilter filter) {
		Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Estilo.class);
		this.adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(EstiloFilter filter, Criteria criteria) {
		if (filter != null) {
			if (filter.getNome() != null) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

}
