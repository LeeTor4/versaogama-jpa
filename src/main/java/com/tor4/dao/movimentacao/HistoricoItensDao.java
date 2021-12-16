package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.movimentacao.HistoricoItens;
import com.tor4.util.JPAUtil;

public class HistoricoItensDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<HistoricoItens> dao;
	
	
	public HistoricoItensDao() {
		this.dao = new DAO<HistoricoItens>(em, HistoricoItens.class);
	}
	
	public void adiciona(HistoricoItens t) {
		dao.adiciona(t);
	}

	public void remove(HistoricoItens t) {
		dao.remove(t);
	}

	public void atualiza(HistoricoItens t) {
		dao.atualiza(t);
	}

	public List<HistoricoItens> listaTodos() {
		return dao.listaTodos();
	}

	public HistoricoItens buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
