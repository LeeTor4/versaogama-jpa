package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;

import com.tor4.dao.DAO;
import com.tor4.model.movimentacao.LoteImportacaoSpedFiscal;
import com.tor4.util.JPAUtil;

public class LoteImportacaoEfdIcmsDao {
	
	private EntityManager em = JPAUtil.getEntityManager();

	private DAO<LoteImportacaoSpedFiscal> dao;
	
	
	public LoteImportacaoEfdIcmsDao() {
		this.dao = new DAO<LoteImportacaoSpedFiscal>(em, LoteImportacaoSpedFiscal.class);
	}
	
	public void adiciona(LoteImportacaoSpedFiscal t) {
		dao.adiciona(t);
	}

	public void remove(LoteImportacaoSpedFiscal t) {
		dao.remove(t);
	}

	public void atualiza(LoteImportacaoSpedFiscal t) {
		dao.atualiza(t);
	}

	public List<LoteImportacaoSpedFiscal> listaTodos() {
		return dao.listaTodos();
	}

	public LoteImportacaoSpedFiscal buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		
		return dao.contaTodos();
	}

}
