package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.movimentacao.SaldoItensTotalizadoPorLote;
import com.tor4.util.JPAUtil;

public class SaldoItensTotalizadoPorLoteDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<SaldoItensTotalizadoPorLote> dao;
	
	public SaldoItensTotalizadoPorLoteDao() {
		this.dao = new DAO<SaldoItensTotalizadoPorLote>(em, SaldoItensTotalizadoPorLote.class);
	}
	
	
	public void adiciona(SaldoItensTotalizadoPorLote t) {
		dao.adiciona(t);
	}

	public void remove(SaldoItensTotalizadoPorLote t) {
		dao.remove(t);
	}

	public void atualiza(SaldoItensTotalizadoPorLote t) {
		dao.atualiza(t);
	}

	public List<SaldoItensTotalizadoPorLote> listaTodos() {
		return dao.listaTodos();
	}

	public SaldoItensTotalizadoPorLote buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
