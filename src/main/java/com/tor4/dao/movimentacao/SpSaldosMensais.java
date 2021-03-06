package com.tor4.dao.movimentacao;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import com.tor4.model.movimentacao.SaldosMensais;
import com.tor4.util.JPAUtil;

public class SpSaldosMensais {

	  public static  List<SaldosMensais> spSaldosMensais(String codigo, String ano, String cnpj) {
		  EntityManager entityManager = JPAUtil.getEntityManager();
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
		  
		  entityManager.close();
		  
		  return lista;
		 
	  }
	  
	  
}
