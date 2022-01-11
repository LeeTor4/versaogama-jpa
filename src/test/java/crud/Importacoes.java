package crud;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import com.leetor4.handler.ParseDocXML;
import com.tor4.dao.cadastro.EmpresaDao;
import com.tor4.dao.cadastro.EstabelecimentoDao;
import com.tor4.dao.cadastro.ProdutoDao;
import com.tor4.dao.metadados.BancoDados;
import com.tor4.dao.movimentacao.EquipamentoCFeDao;
import com.tor4.dao.movimentacao.HistoricoItensDao;
import com.tor4.dao.movimentacao.LoteImportacaoEfdIcmsDao;
import com.tor4.dao.movimentacao.NotaFiscalDao;
import com.tor4.dao.movimentacao.ProdutoNotaDao;
import com.tor4.dao.movimentacao.ReducaoZDao;
import com.tor4.dao.movimentacao.SaldoItensTotalizadoPorLoteDao;
import com.tor4.handler.ImportaEfdIcms;
import com.tor4.model.cadastro.Empresa;
import com.tor4.model.cadastro.EquipamentoECF;
import com.tor4.model.cadastro.Estabelecimento;
import com.tor4.model.cadastro.Produto;
import com.tor4.model.exportacoes.ExportaQuantitativosMensaisDeEstoque;
import com.tor4.model.exportacoes.ExportaQuantitativosMensaisDeEstoque2;
import com.tor4.model.movimentacao.EquipamentoCFe;
import com.tor4.model.movimentacao.HistoricoItens;
import com.tor4.model.movimentacao.LoteImportacaoSpedFiscal;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ProdutoNotaFiscal;
import com.tor4.model.movimentacao.ReducaoZ;
import com.tor4.model.movimentacao.SaldoItensTotalizadoPorLote;
import com.tor4.util.JPAUtil;

import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.manager.LeitorEfdIcms;

public class Importacoes {
	

	public static String getMes(int mes) {
	       
        if(mes == 1) {
        	mes = 12;
        }else {
        	mes = mes - 1;
        }
        
        return String.valueOf(mes);
	}
	
	
	public static String getAno(int mes, int ano) {
		if(mes == 1) {
			ano = ano - 1;
		}
		return String.valueOf(ano);
	}
	
	public static Double getSaldoIncial(int mes, int ano) {
	   SaldoItensTotalizadoPorLoteDao saldoDao = new SaldoItensTotalizadoPorLoteDao();
		Double map = saldoDao.listaTodos().stream()
			    .filter(c -> c.getCnpj().equals("05329222000419"))
			    .filter(c -> c.getMes().equals(getMes(mes)))
			    .filter(c -> c.getAno().equals(getAno(mes, ano)))
			    .filter(c -> c.getCodItem().equals("33"))		   
			    .map(SaldoItensTotalizadoPorLote :: getSaldoFin).mapToDouble(BigDecimal::doubleValue).sum();
		
		return map;
	}
	
	public static void importaRegistrosEfdIcms(EntityManager em,Path x,LeitorEfdIcms leitor, ImportaEfdIcms lerEfd,Empresa empresa,Estabelecimento mega,
			Long id0000) {
		ProdutoDao daoProd = new ProdutoDao();
		NotaFiscalDao nfDao = new NotaFiscalDao();
		ReducaoZDao rdzDao = new ReducaoZDao();
		EquipamentoCFeDao equipCfeDao = new EquipamentoCFeDao();
		HistoricoItensDao histDao = new HistoricoItensDao();
		SaldoItensTotalizadoPorLoteDao saldoDao = new SaldoItensTotalizadoPorLoteDao();
		List<Produto> prods = lerEfd.getProduto(x.toString(),leitor, empresa.getId(),mega.getId());
		List<NotaFiscal> docs = lerEfd.getNotasFiscais(em,leitor, x.toString(), empresa.getId(),mega.getId(),leitor.incLoteImportacao(id0000));
		List<ReducaoZ> reducoes = lerEfd.getReducoes(em,leitor, empresa.getId(),mega.getId(),leitor.incLoteImportacao(id0000));		
		List<EquipamentoCFe> equipCFe = lerEfd.getEquipamentosCFe(em,leitor, x.toString(), empresa.getId(),mega.getId());		
		List<HistoricoItens> historicos = lerEfd.getHistoricoItens(em,leitor, x.toString(), empresa.getId(),mega.getId(),leitor.incLoteImportacao(id0000));
		
		List<SaldoItensTotalizadoPorLote> saldoItensPorLote = lerEfd.getSaldoItensPorLote(leitor, lerEfd.getListaProdutos(),historicos,leitor.incLoteImportacao(id0000));

		for(Produto prod : prods){
		     daoProd.adiciona(prod);
	    }
		
		for(NotaFiscal nota :  docs){				
		     nfDao.adiciona(nota);
	    }
		
		
		for (ReducaoZ rdz : reducoes) {
			rdzDao.adiciona(rdz);
		}

		for (EquipamentoCFe equip : equipCFe) {
			equipCfeDao.adiciona(equip);
		}

		for (HistoricoItens hist : historicos) {
			if (hist != null) {
				histDao.adiciona(hist);
			}
		}

		for (SaldoItensTotalizadoPorLote saldo : saldoItensPorLote) {
			if (saldo != null) {
				saldoDao.adiciona(saldo);
			}
		}
	}
	
	public static void main(String[] args) {
	
		EntityManager em = JPAUtil.getEntityManager();
		BancoDados banco = new BancoDados();
		EmpresaDao         empDao = new EmpresaDao();
		EstabelecimentoDao estDao = new EstabelecimentoDao();
		LoteImportacaoEfdIcmsDao importDao = new LoteImportacaoEfdIcmsDao();
		ProdutoDao daoProd = new ProdutoDao();
		NotaFiscalDao nfDao = new NotaFiscalDao();
		ReducaoZDao rdzDao = new ReducaoZDao();
		EquipamentoCFeDao equipCfeDao = new EquipamentoCFeDao();
		HistoricoItensDao histDao = new HistoricoItensDao();
		SaldoItensTotalizadoPorLoteDao saldoDao = new SaldoItensTotalizadoPorLoteDao();
		
		ExportaQuantitativosMensaisDeEstoque exportacao = new ExportaQuantitativosMensaisDeEstoque();
		
		String ano = "2018";
		String emp = "SELLENE";
		String estab = "MEGADIET";
		String cnpj  = "05329222000419";
		
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
		
        Path x1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jan"));
	    Path p1 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
		
	    Path x2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\fev"));
	    Path p2 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes2));
		
	    Path x3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\mar"));
	    Path p3 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes3));
	    
	    Path x4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\abr"));
	    Path p4 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes4));
	    
	    Path x5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\mai"));
	    Path p5 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes5));
	    
	    Path x6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jun"));
	    Path p6 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes6));
	    
	    Path x7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jul"));
	    Path p7 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes7));
	    
	    Path x8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\ago"));
	    Path p8 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes8));
	    
	    Path x9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\set"));
	    Path p9 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes9));
	    
	    Path x10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\out"));
	    Path p10 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes10));
	    
	    Path x11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\nov"));
	    Path p11 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes11));
	    
	    Path x12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\dez"));
	    Path p12 = Paths.get("E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes12));
	    

	    Path p = p3;
		Path x = x3;
		
		
		Long id0000 = 0L;
		Long id0200 = 0L;
		Long idC100 = 0L;
		Long idC405 = 0L;
		Long idC420 = 0L;
		Long idC860 = 0L;
		Long idH005 = 0L;
		
		
		id0000 = banco.getIncremento("tb_importspedfiscal");
		id0200 = banco.getIncremento( "tb_produto");
		idC100 = banco.getIncremento("tb_notafiscal");
		idC405 = banco.getIncremento("tb_reducaoz");
		idC420 = banco.getIncremento("tb_totaisparcrdz");
		idC860 = banco.getIncremento("tb_equipamentocfe");
		//idH005 = banco.getIncremento(em, "tb_reducaoz");

	    ImportaEfdIcms lerEfd = new ImportaEfdIcms();
		LeitorEfdIcms leitor = new LeitorEfdIcms();		
		
		
//		System.out.println(leitor.incLoteImportacao(id0000) +"|" +
//		                   leitor.incRDZ(idC405) +"|" +
//		                   leitor.incProd(id0200) +"|" +
//		                   leitor.incNFe(idC100)+"|" +
//		                   leitor.incRDZ(idC405)+"|" +
//		                   leitor.incTotParcRdz(idC420));
		
	     //id0000,id0200,idC100,leitor.incRDZ(idC405),idC420,idC860, 0L 
		
		
		leitor.leitorSpedFiscal(p,leitor.incLoteImportacao(id0000),
				leitor.incProd(id0200),leitor.incNFe(idC100),leitor.incRDZ(idC405),leitor.incTotParcRdz(idC420),
				leitor.incTotEquipCFe(idC860), 0L );
		
        Empresa empresa = empDao.buscaPorId(1L);
		Estabelecimento mega = estDao.buscaPorId(2L);
		
		LoteImportacaoSpedFiscal loteImportacao = lerEfd.getLoteImportacao(leitor,empresa.getId(),mega.getId());
	
		//System.out.println(loteImportacao.getCnpj()+"|"+loteImportacao.getDtFin().getYear()+"|"+ loteImportacao.getDtFin().getMonthValue());
		
		if(!importDao.listaTodos().contains(loteImportacao)) {
			importDao.adiciona(loteImportacao);
			importaRegistrosEfdIcms(em, x, leitor, lerEfd, empresa, mega, id0000);
		}else {
			System.out.println("Lote já importado!!!");
		}

//		String dirPlanilha  = "E:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\CONTROLE_ESTOQUE_".concat(cnpj).concat("_").concat(ano).concat(".csv"));
//		exportacao.exportaControleQuantitativosMensais(dirPlanilha,ano,cnpj);
	}

}
