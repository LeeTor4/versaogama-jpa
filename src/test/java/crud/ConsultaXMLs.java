package crud;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;

import com.mysql.cj.x.protobuf.MysqlxExpect.Open.Condition.Key;
import com.tor4.dao.movimentacao.EquipamentoCFeDao;
import com.tor4.handler.ImportaEfdIcms;
import com.tor4.model.movimentacao.EquipamentoCFe;
import com.tor4.util.JPAUtil;

import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.manager.LeitorEfdIcms;

public class ConsultaXMLs {

	public static void main(String[] args) throws IOException, JAXBException {
		
		  ImportaEfdIcms lerEfd = new ImportaEfdIcms();
        
		 Map<Long, RegC860> mpC860 = new HashMap<Long, RegC860>();
		 LeitorEfdIcms leitor = new LeitorEfdIcms();
		 for(RegC860 regC860 : leitor.getRegsC860()){			 
			 mpC860.put(regC860.getId(), regC860);
		 }

		 for(Long key : mpC860.keySet()){
			 System.out.println(key + "|" + mpC860.get(key).getDocInicial()+ "|" + mpC860.get(key).getDocFinal());
		 }
		 
		 
		 
		 
		 
		 
	}

}
