package com.tor4.dao.cadastro;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.cadastro.Produto;
import com.tor4.util.JPAUtil;

public class ProdutoDao {

private EntityManager em = JPAUtil.getEntityManager();
	
	private DAO<Produto> dao;
	
	public ProdutoDao() {
		this.dao = new DAO<Produto>(em, Produto.class);
	}
	
	public void adiciona(Produto t) {
		dao.adiciona(t);
	}

	public void remove(Produto t) {
		dao.remove(t);
	}

	public void atualiza(Produto t) {
		dao.atualiza(t);
	}

	public List<Produto> listaTodos() {
		return dao.listaTodos();
	}

	public Produto buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
