package crud;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;

import com.leetor4.handler.ParseDocXML;
import com.tor4.dao.cadastro.EmpresaDao;
import com.tor4.dao.cadastro.EstabelecimentoDao;
import com.tor4.dao.cadastro.ProdutoDao;
import com.tor4.dao.movimentacao.EquipamentoCFeDao;
import com.tor4.dao.movimentacao.HistoricoItensDao;
import com.tor4.dao.movimentacao.LoteImportacaoEfdIcmsDao;
import com.tor4.dao.movimentacao.NotaFiscalDao;
import com.tor4.dao.movimentacao.ProdutoNotaDao;
import com.tor4.dao.movimentacao.ReducaoZDao;
import com.tor4.handler.ImportaEfdIcms;
import com.tor4.model.cadastro.Empresa;
import com.tor4.model.cadastro.EquipamentoECF;
import com.tor4.model.cadastro.Estabelecimento;
import com.tor4.model.cadastro.Produto;
import com.tor4.model.movimentacao.EquipamentoCFe;
import com.tor4.model.movimentacao.HistoricoItens;
import com.tor4.model.movimentacao.LoteImportacaoSpedFiscal;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ProdutoNotaFiscal;
import com.tor4.model.movimentacao.ReducaoZ;
import com.tor4.util.JPAUtil;

import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.manager.LeitorEfdIcms;

public class Importacoes {

	public static void main(String[] args) {
	
		EntityManager em = JPAUtil.getEntityManager();
		EmpresaDao         empDao = new EmpresaDao();
		EstabelecimentoDao estDao = new EstabelecimentoDao();
		LoteImportacaoEfdIcmsDao importDao = new LoteImportacaoEfdIcmsDao();
		ProdutoDao daoProd = new ProdutoDao();
		NotaFiscalDao nfDao = new NotaFiscalDao();
		ReducaoZDao rdzDao = new ReducaoZDao();
		EquipamentoCFeDao equipCfeDao = new EquipamentoCFeDao();
		HistoricoItensDao histDao = new HistoricoItensDao();
		
		String ano = "2019";
		String emp = "SELLENE";
		String estab = "MEGADIET";
		
		String anomes1 = ano.concat("01").concat(".txt");
		String anomes2 = ano.concat("02").concat(".txt");
		String anomes3 = ano.concat("03").concat(".txt");
		String anomes4 = ano.concat("04").concat(".txt");
		String anomes5 = ano.concat("05").concat(".txt");
		String anomes6 = ano.concat("06").concat(".txt");
		String anomes7 = ano.concat("07").concat(".txt");
		String anomes8 = ano.concat("08").concat(".txt");
		String anomes9 = ano.concat("09").concat(".txt");
		String anomes10 = ano.concat("10").concat(".txt");
		String anomes11 = ano.concat("11").concat(".txt");
		String anomes12 = ano.concat("12").concat(".txt");
		
        Path x1 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jan"));
	    Path p1 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
		
	    Path x2 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\fev"));
	    Path p2 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
		
	    Path x3 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\mar"));
	    Path p3 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes3));
	    
	    Path x8 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\ago"));
	    Path p8 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes8));
	    
	    Path x9 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\set"));
	    Path p9 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes9));

	    
	    Path p = p1;
		Path x = x1;
		
	    ImportaEfdIcms lerEfd = new ImportaEfdIcms();

		LeitorEfdIcms leitor = new LeitorEfdIcms();		
		leitor.leitorSpedFiscal(p, null,1L,0L);
	
		
		
        Empresa empresa = empDao.buscaPorId(1L);
		Estabelecimento mega = estDao.buscaPorId(2L);
		
		LoteImportacaoSpedFiscal loteImportacao = lerEfd.getLoteImportacao(leitor,empresa.getId(),mega.getId());
		List<Produto> prods = lerEfd.getProduto(leitor, empresa.getId(),mega.getId());
		List<NotaFiscal> docs = lerEfd.getNotasFiscais(em,leitor, x.toString(), empresa.getId(),mega.getId());
		List<ReducaoZ> reducoes = lerEfd.getReducoes(em,leitor, empresa.getId(),mega.getId());
		
		List<EquipamentoCFe> equipCFe = lerEfd.getEquipamentosCFe(em,leitor, x.toString(), empresa.getId(),mega.getId());

		lerEfd.getHistoricoItensNotasSped(em,leitor, x.toString(), empresa.getId(),mega.getId());
		lerEfd.getHistoricoItensNotasXml(em,leitor, x.toString(), empresa.getId(),mega.getId());
		List<HistoricoItens> historicoItensNotas = lerEfd.getListHistItem();
		
		
		
		importDao.adiciona(loteImportacao);
		
		for(Produto prod : prods){
			daoProd.adiciona(prod);
		}
		for(NotaFiscal nota :  docs){				
			nfDao.adiciona(nota);
		}
        
		for (ReducaoZ rdz : reducoes) {
			rdzDao.adiciona(rdz); 			
		}
		
		for(EquipamentoCFe equip :  equipCFe){
			equipCfeDao.adiciona(equip);
		}
		
	    for(HistoricoItens hist : historicoItensNotas){
	    	//System.out.println(hist.getOperacao() + "|" + hist.getCodItem() + "|" + hist.getChaveDoc());
	    	histDao.adiciona(hist);
	    }

		
	}

}
