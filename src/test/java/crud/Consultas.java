package crud;

import javax.persistence.EntityManager;

import com.tor4.dao.metadados.BancoDados;
import com.tor4.util.JPAUtil;

public class Consultas {


	public static void main(String[] args) {

		EntityManager em = JPAUtil.getEntityManager();
		
		BancoDados bd = new BancoDados();
		
		System.out.println(bd.getIncremento(em, "tb_equipamentocfe"));
		 
		 
	}

}
