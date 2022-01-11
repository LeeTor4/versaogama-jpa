package crud;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;

import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;
import com.mysql.cj.x.protobuf.MysqlxExpect.Open.Condition.Key;
import com.tor4.dao.metadados.BancoDados;
import com.tor4.dao.movimentacao.EquipamentoCFeDao;
import com.tor4.handler.ImportaEfdIcms;
import com.tor4.model.cadastro.Empresa;
import com.tor4.model.cadastro.Estabelecimento;
import com.tor4.model.cadastro.Produto;
import com.tor4.model.movimentacao.EquipamentoCFe;
import com.tor4.model.movimentacao.HistoricoItens;
import com.tor4.model.movimentacao.LoteImportacaoSpedFiscal;
import com.tor4.util.JPAUtil;

import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.manager.LeitorEfdIcms;

public class ConsultaXMLs {

	public static void main(String[] args) throws IOException, JAXBException {
		
		  ImportaEfdIcms lerEfd = new ImportaEfdIcms();
        
		 Map<Long, RegC860> mpC860 = new HashMap<Long, RegC860>();
		 LeitorEfdIcms leitor = new LeitorEfdIcms();
		 
//		 for(RegC860 regC860 : leitor.getRegsC860()){			 
//			 mpC860.put(regC860.getId(), regC860);
//		 }
//
//		 for(Long key : mpC860.keySet()){
//			 System.out.println(key + "|" + mpC860.get(key).getDocInicial()+ "|" + mpC860.get(key).getDocFinal());
//		 }
		  
		  String ano = "2019";
		  String emp = "SELLENE";
	      String estab = "MEGADIET";
		  String anomes1 = ano.concat("01").concat(".txt");
		 
//		  Path x1 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jan"));
//		  Path p1 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
//			
//		  Path p = p1;
//		  Path x = x1;
			

		  
//		  File f = new File(x.toString());
//		  ParseDocXML parseDocXML = new ParseDocXML();
//		  for(DocumentoFiscalEltronico doc :   parseDocXML.validaTipoDeParseNFE(f)){
//			  for(Produtos prod : doc.getProds()){
//				  System.out.println(prod.getCodItem());
//			  }
//		  }
		  
 
//		  for(int i = 2; i < 5; i++){
//			  
//			  String p="p";
//			  String x="x";
//			  System.out.println(p+i);
//			  System.out.println(x+i);
//		  }
	}

}
