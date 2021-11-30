package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.util.JPAUtil;

public class NotaFiscalDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<NotaFiscal> dao;
	
	
	public NotaFiscalDao() {
		this.dao = new DAO<NotaFiscal>(em, NotaFiscal.class);
	}
	
	public void adiciona(NotaFiscal t) {
		dao.adiciona(t);
	}

	public void remove(NotaFiscal t) {
		dao.remove(t);
	}

	public void atualiza(NotaFiscal t) {
		dao.atualiza(t);
	}

	public List<NotaFiscal> listaTodos() {
		return dao.listaTodos();
	}

	public NotaFiscal buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
