package com.algaworks.brewer.repository.helper.venda;

import static org.springframework.util.StringUtils.isEmpty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;

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

import com.algaworks.brewer.dto.VendaMes;
import com.algaworks.brewer.dto.VendaPorOrigem;
import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.venda.VendaFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class VendasImpl implements VendasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(vendaFilter, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(vendaFilter));
	}

	@Override
	public BigDecimal valorTotalNoAno() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager
				.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano", BigDecimal.class)
				.setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalNoMes() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager
				.createQuery("select sum(valorTotal) from Venda where month(dataCriacao) = :mes and status = :status",
						BigDecimal.class)
				.setParameter("mes", MonthDay.now().getMonthValue()).setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTicketMedioNoAno() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager
				.createQuery(
						"select sum(valorTotal)/count(*) from Venda where year(dataCriacao) = :ano and status = :status",
						BigDecimal.class)
				.setParameter("ano", Year.now().getValue()).setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}

	private Long total(VendaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VendaMes> totalPorMes() {
		List<VendaMes> vendasMes = manager.createNamedQuery("Vendas.totalPorMes").getResultList();

		LocalDate hoje = LocalDate.now();
		for (int i = 1; i < 6; i++) {
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonth().getValue());

			boolean possuiOMes = vendasMes.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();

			if (!possuiOMes) {
				vendasMes.add(i - 1, new VendaMes(mesIdeal, 0));
			}

			hoje = hoje.minusMonths(1);
		}

		return vendasMes;
	}

	@Override
	public List<VendaPorOrigem> totalPorOrigem() {
		List<VendaPorOrigem> vendasOrigem = manager.createNamedQuery("Vendas.porOrigem", VendaPorOrigem.class).getResultList();

		LocalDate hoje = LocalDate.now();
		for (int i = 1; i < 6; i++) {
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonth().getValue());

			boolean possuiOMes = vendasOrigem.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
			if (!possuiOMes) {
				vendasOrigem.add(i - 1, new VendaPorOrigem(mesIdeal, 0, 0));
			}

			hoje = hoje.minusMonths(1);
		}

		return vendasOrigem;
	}

	private void adicionarFiltro(VendaFilter vendaFilter, Criteria criteria) {
		criteria.createAlias("cliente", "c");

		if (vendaFilter != null) {

			if (!isEmpty(vendaFilter.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", vendaFilter.getCodigo()));
			}

			if (vendaFilter.getStatusVenda() != null) {
				criteria.add(Restrictions.eq("status", vendaFilter.getStatusVenda()));
			}

			if (vendaFilter.getDesde() != null) {
				LocalDateTime desde = LocalDateTime.of(vendaFilter.getDesde(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataCriacao", desde));
			}

			if (vendaFilter.getAte() != null) {
				LocalDateTime ate = LocalDateTime.of(vendaFilter.getAte(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("dataCriacao", ate));
			}

			if (vendaFilter.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", vendaFilter.getValorMinimo()));
			}

			if (vendaFilter.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", vendaFilter.getValorMaximo()));
			}

			if (!isEmpty(vendaFilter.getNomeCliente())) {
				criteria.add(Restrictions.ilike("c.nome", vendaFilter.getNomeCliente(), MatchMode.ANYWHERE));
			}

			if (!isEmpty(vendaFilter.getCpfOuCpnjCliente())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", vendaFilter.getCpfOuCpnjCliente()));
			}

		}
	}

}
