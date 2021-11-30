package com.tor4.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import com.tor4.util.JPAUtil;

public class DAO<T> {

	private final Class<T> classe;
	
	private EntityManager em = JPAUtil.getEntityManager();
	
	public DAO(EntityManager em, Class<T> classe) {
        this.em = em;
        this.classe = classe;
    }

	public void adiciona(T t) {
		// persiste o objeto
		em.getTransaction().begin();
		em.persist(t);		
		em.getTransaction().commit();
	}

	public void remove(T t) {
		em.getTransaction().begin();
		em.remove(em.merge(t));	
		em.getTransaction().commit();
	}

	public void atualiza(T t) {	
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
	}

	public List<T> listaTodos() {
		
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

	
		return lista;
	}

	public T buscaPorId(Long id) {
		
		T instancia = em.find(classe, id);
		
		return instancia;
	}

	public int contaTodos() {
		
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();
		em.close();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
	
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

	
		return lista;
	}

}
