package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.movimentacao.ReducaoZ;
import com.tor4.util.JPAUtil;

public class ReducaoZDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<ReducaoZ> dao;
	
	
	public ReducaoZDao() {
		this.dao = new DAO<ReducaoZ>(em, ReducaoZ.class);
	}
	
	public void adiciona(ReducaoZ t) {
		dao.adiciona(t);
	}

	public void remove(ReducaoZ t) {
		dao.remove(t);
	}

	public void atualiza(ReducaoZ t) {
		dao.atualiza(t);
	}

	public List<ReducaoZ> listaTodos() {
		return dao.listaTodos();
	}

	public ReducaoZ buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
	
}
