package com.tor4.dao.movimentacao;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import com.tor4.util.JPAUtil;

public class SpSaldosAnuais {

	  public  SpInicialAnual spSaldosAnuais(String codigo, String ano, String cnpj) {
		  EntityManager entityManager = JPAUtil.getEntityManager();
		  StoredProcedureQuery storedProcedureQuery = entityManager
	                .createStoredProcedureQuery("saldo_inicial", SpInicialAnual.class);
		  
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
		  
		  SpInicialAnual retorno =  (SpInicialAnual) storedProcedureQuery.getSingleResult();
		  
          entityManager.close();
		  
		  return retorno;
	  }
}
