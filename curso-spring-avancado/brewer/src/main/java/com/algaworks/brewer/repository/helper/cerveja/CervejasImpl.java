package com.algaworks.brewer.repository.helper.cerveja;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.cerveja.CervejaFilter;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Cerveja> filtrar(CervejaFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);
		if (filter != null) {
			if (!isEmpty(filter.getSku()))
				criteria.add(Restrictions.eq("sku", filter.getSku()));

			if (!isEmpty(filter.getNome()))
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));

			if (isEstiloPresente(filter))
				criteria.add(Restrictions.eq("estilo", filter.getEstilo()));
			
			if(filter.getSabor() != null)
				criteria.add(Restrictions.eq("sabor", filter.getSabor()));
			
			if(filter.getOrigem() != null)
				criteria.add(Restrictions.eq("origem", filter.getOrigem()));
			
			if(filter.getValorDe() != null)
				criteria.add(Restrictions.ge("valor", filter.getValorDe()));
			
			if(filter.getValorAte() != null)
				criteria.add(Restrictions.eq("valor", filter.getValorAte()));
			
		}
		return criteria.list();
	}

	private boolean isEstiloPresente(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getCodigo() != null;
	}

}
