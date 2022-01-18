package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.movimentacao.SaldosMensais;
import com.tor4.util.JPAUtil;

public class SaldosMensaisDao {
	
	
	private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<SaldosMensais> dao;
	
	
	public SaldosMensaisDao() {
		this.dao = new DAO<SaldosMensais>(em, SaldosMensais.class);
	}
	
	
	public void adiciona(SaldosMensais t) {
		dao.adiciona(t);
	}

	public void remove(SaldosMensais t) {
		dao.remove(t);
	}

	public void atualiza(SaldosMensais t) {
		dao.atualiza(t);
	}

	public List<SaldosMensais> listaTodos() {
		return dao.listaTodos();
	}

	public SaldosMensais buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

}
