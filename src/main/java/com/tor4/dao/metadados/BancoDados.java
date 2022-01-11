package com.tor4.dao.metadados;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import com.tor4.util.JPAUtil;

@Entity
@Table(name="tb_bancodados")
public class BancoDados implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1419560752586742758L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long         id;
	private Long incremento;

	public Long getIncremento() {
		return incremento;
	}

	public void setIncremento(Long incremento) {
		this.incremento = incremento;
	}

	public  Long getIncremento(String tabela) {
		EntityManager em = JPAUtil.getEntityManager();
		Long id = 0L;
		String sql = "SELECT '1' as id ,AUTO_INCREMENT incremento FROM information_schema.tables WHERE table_schema = 'versaodeltadb' and table_name = " + "'"+ tabela + "'";
		
		Query query =  em.createNativeQuery(sql, BancoDados.class);
		
		BancoDados singleResult = (BancoDados) query.getSingleResult();
		
		id = singleResult.getIncremento();
		
		return id;
	}
	
	public  Long getIncLoteImp() {
		EntityManager em = JPAUtil.getEntityManager();
		Long id = 0L;
		String sql = "SELECT '1' as id ,AUTO_INCREMENT incremento FROM information_schema.tables WHERE table_schema = 'versaodeltadb' and table_name = 'tb_importspedfiscal'";
		
		Query query =  em.createNativeQuery(sql, BancoDados.class);
		
		BancoDados singleResult = (BancoDados) query.getSingleResult();
		
		id = singleResult.getIncremento();
		
		return id;
	}
	
	public  Long getIncRdz() {
		EntityManager em = JPAUtil.getEntityManager();
		Long id = 0L;
		String sql = "SELECT '1' as id ,AUTO_INCREMENT incremento FROM information_schema.tables WHERE table_schema = 'versaodeltadb' and table_name = 'tb_reducaoz'";
		
		Query query =  em.createNativeQuery(sql, BancoDados.class);
		
		BancoDados singleResult = (BancoDados) query.getSingleResult();
		
		id = singleResult.getIncremento();
		
		return id;
	}
	

	
}
