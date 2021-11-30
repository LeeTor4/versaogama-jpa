package com.tor4.dao.cadastro;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.cadastro.Empresa;
import com.tor4.util.JPAUtil;

public class EmpresaDao {

	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<Empresa> dao;

	public EmpresaDao() {
		this.dao = new DAO<Empresa>(em, Empresa.class);
	}

	public void adiciona(Empresa t) {
		dao.adiciona(t);
	}

	public void remove(Empresa t) {
		dao.remove(t);
	}

	public void atualiza(Empresa t) {
		dao.atualiza(t);
	}

	public List<Empresa> listaTodos() {
		return dao.listaTodos();
	}

	public Empresa buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
}
