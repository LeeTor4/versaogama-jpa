package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.movimentacao.EquipamentoCFe;
import com.tor4.util.JPAUtil;

public class EquipamentoCFeDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<EquipamentoCFe> dao;
	
	
	public EquipamentoCFeDao() {
		this.dao = new DAO<EquipamentoCFe>(em, EquipamentoCFe.class);
	}
	
	public void adiciona(EquipamentoCFe t) {
		dao.adiciona(t);
	}

	public void remove(EquipamentoCFe t) {
		dao.remove(t);
	}

	public void atualiza(EquipamentoCFe t) {
		dao.atualiza(t);
	}

	public List<EquipamentoCFe> listaTodos() {
		return dao.listaTodos();
	}

	public EquipamentoCFe buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
