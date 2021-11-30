package com.tor4.dao.cadastro;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.cadastro.Estabelecimento;
import com.tor4.util.JPAUtil;

public class EstabelecimentoDao {

	private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<Estabelecimento> dao;
	
	public EstabelecimentoDao() {
		this.dao = new DAO<Estabelecimento>(em, Estabelecimento.class);
	}
	
	public void adiciona(Estabelecimento t) {
		dao.adiciona(t);
	}

	public void remove(Estabelecimento t) {
		dao.remove(t);
	}

	public void atualiza(Estabelecimento t) {
		dao.atualiza(t);
	}

	public List<Estabelecimento> listaTodos() {
		return dao.listaTodos();
	}

	public Estabelecimento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
