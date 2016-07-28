package com.algaworks.brewer.repository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {

	public void preparar(Criteria criteria, Pageable pageable) {
		int paginalAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiraPagina = paginalAtual * totalRegistrosPorPagina;

		criteria.setFirstResult(primeiraPagina);
		criteria.setMaxResults(totalRegistrosPorPagina);

		Sort sort = pageable.getSort();
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String field = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(field) : Order.desc(field));
		}
	}

}
