package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.movimentacao.ItensMovDiario;
import com.tor4.util.JPAUtil;

public class ItensMovDiarioDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<ItensMovDiario> dao;
	
	
	public ItensMovDiarioDao() {
		this.dao = new DAO<ItensMovDiario>(em, ItensMovDiario.class);
	}
	
	public void adiciona(ItensMovDiario t) {
		dao.adiciona(t);
	}

	public void remove(ItensMovDiario t) {
		dao.remove(t);
	}

	public void atualiza(ItensMovDiario t) {
		dao.atualiza(t);
	}

	public List<ItensMovDiario> listaTodos() {
		return dao.listaTodos();
	}

	public ItensMovDiario buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
