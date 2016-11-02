package com.algaworks.brewer.repository.helper.cerveja;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.List;

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

import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.dto.ValorItensEstoque;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.cerveja.CervejaFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class CervejasImpl implements CervejasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacao;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cerveja.class);

		paginacao.preparar(criteria, pageable);

		adicionarFiltro(filter, criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}
	
	@Override
	public ValorItensEstoque valorItensEstoque() {
		String query = "select new com.algaworks.brewer.dto.ValorItensEstoque(sum(valor * quantidadeEstoque), sum(quantidadeEstoque)) from Cerveja";
		return manager.createQuery(query, ValorItensEstoque.class).getSingleResult();
	}
	
	@Override
	public List<CervejaDTO> porNomeOuSku(String skuOuNome) {

		StringBuilder jpql = new StringBuilder();
		jpql.append("select new com.algaworks.brewer.dto.CervejaDTO(codigo,nome,sku,origem,valor,foto) ");
		jpql.append("from Cerveja where lower(sku) like lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)");
		List<CervejaDTO> cervejasFiltradas = manager.createQuery(jpql.toString(), CervejaDTO.class)
				.setParameter("skuOuNome", skuOuNome + "%").getResultList();

		return cervejasFiltradas;
	}

	@Override
	public Long totalCervejasEmEstoque() {
		return manager.createQuery("select sum(c.quantidadeEstoque) from Cerveja c", Long.class).getSingleResult();
	}

	private Long total(CervejaFilter filter) {
		Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cerveja.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(CervejaFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!isEmpty(filter.getSku()))
				criteria.add(Restrictions.eq("sku", filter.getSku()));

			if (!isEmpty(filter.getNome()))
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));

			if (isEstiloPresente(filter))
				criteria.add(Restrictions.eq("estilo", filter.getEstilo()));

			if (filter.getSabor() != null)
				criteria.add(Restrictions.eq("sabor", filter.getSabor()));

			if (filter.getOrigem() != null)
				criteria.add(Restrictions.eq("origem", filter.getOrigem()));

			if (filter.getValorDe() != null)
				criteria.add(Restrictions.ge("valor", filter.getValorDe()));

			if (filter.getValorAte() != null)
				criteria.add(Restrictions.eq("valor", filter.getValorAte()));

		}
	}

	private boolean isEstiloPresente(CervejaFilter filter) {
		return filter.getEstilo() != null && filter.getEstilo().getCodigo() != null;
	}

}
