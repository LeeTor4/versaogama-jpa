package com.tor4.dao.movimentacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import com.tor4.model.movimentacao.SaldoItensTotalizadoPorLote;
import com.tor4.model.movimentacao.SaldosMensais;
import com.tor4.util.JPAUtil;

public class SpSaldosMensais {

	  public  static List<SaldosMensais> spSaldosMensais(EntityManager entityManager, String codigo, String ano, String cnpj) {
		  
		  StoredProcedureQuery storedProcedureQuery = entityManager
	                .createStoredProcedureQuery("saldos_mensais", SaldosMensais.class);
		  
		  storedProcedureQuery.registerStoredProcedureParameter(
	                "vCodItem", String.class, ParameterMode.IN);
		  
		  storedProcedureQuery.registerStoredProcedureParameter(
	                "vAno", String.class, ParameterMode.IN);
		  
		  storedProcedureQuery.registerStoredProcedureParameter(
	                "vCnpj", String.class, ParameterMode.IN);
		  
		  storedProcedureQuery.setParameter("vCodItem", codigo);
		  storedProcedureQuery.setParameter("vAno", ano);
		  storedProcedureQuery.setParameter("vCnpj", cnpj);
		  
		  storedProcedureQuery.execute();
		  
		  List<SaldosMensais> lista = storedProcedureQuery.getResultList();
		  
		  return lista;
		 
	  }
	  
	  public static void main(String[] args) {
		  EntityManager em = JPAUtil.getEntityManager();
		 
		  spSaldosMensais(em, "33", "2019", "05329222000419")
		     .forEach(item->System.out.println(item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo()));
	  }
}
