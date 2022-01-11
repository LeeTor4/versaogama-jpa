package crud;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.tor4.dao.metadados.BancoDados;
import com.tor4.dao.movimentacao.HistoricoItensDao;
import com.tor4.model.movimentacao.HistoricoItens;
import com.tor4.dao.movimentacao.NotaFiscalDao;
import com.tor4.dao.movimentacao.ProdutoNotaDao;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ProdutoNotaFiscal;
import com.tor4.util.JPAUtil;

public class Consultas {

    
	public static void main(String[] args) {

		EntityManager em = JPAUtil.getEntityManager();
		
		BancoDados bd = new BancoDados();
		

		
		System.out.println(bd.getIncremento("tb_reducaoz"));
		
	    Set<String> listaProdEntradas = new LinkedHashSet<String>();
	    Set<String> listaProdSaidas = new LinkedHashSet<String>();
	    Set<String> listaProdutos = new LinkedHashSet<String>();
	    ProdutoNotaDao  pNFDao = new ProdutoNotaDao();		
	    HistoricoItensDao histDao = new HistoricoItensDao();
//		for (int x = 0; x < pNFDao.listaTodos().size(); x++) {
//
//			listaProdutos.add(pNFDao.listaTodos().get(x).getCodProduto());
//			if(pNFDao.listaTodos().get(x).getCfop().startsWith("1")
//					|| pNFDao.listaTodos().get(x).getCfop().startsWith("2")) {
//				listaProdEntradas.add(pNFDao.listaTodos().get(x).getCodProduto());
//			}else if(pNFDao.listaTodos().get(x).getCfop().startsWith("5")
//					|| pNFDao.listaTodos().get(x).getCfop().startsWith("6")) {
//				listaProdSaidas.add(pNFDao.listaTodos().get(x).getCodProduto());
//			}
//
//		}
		
		Double qtdeEntSum = 0.0;
		Double vlEntTotalSum = 0.0;
		Double qtdeSaiSum = 0.0;
		Double vlSaiTotalSum = 0.0;
		DecimalFormat df = new DecimalFormat("#,###.00");
//		for (String prod : listaProdutos) {
//			//if (prod.equals("28276")) {}
//				qtdeEntSum = pNFDao.listaTodos().stream().filter(c -> c.getCodProduto().equals(prod))
//						.filter(c -> (c.getCfop().startsWith("1") || c.getCfop().startsWith("2")))
//						.mapToDouble(ProdutoNotaFiscal::getQtde).sum();
//				vlEntTotalSum = pNFDao.listaTodos().stream().filter(c -> c.getCodProduto().equals(prod))
//						.filter(c -> (c.getCfop().startsWith("1") || c.getCfop().startsWith("2")))
//						.mapToDouble(ProdutoNotaFiscal::getVlBruto).sum();
//
//				
//				qtdeSaiSum = pNFDao.listaTodos().stream().filter(c -> c.getCodProduto().equals(prod))
//						.filter(c -> (c.getCfop().startsWith("5") || c.getCfop().startsWith("6")))
//						.mapToDouble(ProdutoNotaFiscal::getQtde).sum();
//				vlSaiTotalSum = pNFDao.listaTodos().stream().filter(c -> c.getCodProduto().equals(prod))
//						.filter(c -> (c.getCfop().startsWith("5") || c.getCfop().startsWith("6")))
//						.mapToDouble(ProdutoNotaFiscal::getVlBruto).sum();
//			
//				System.out.println("Item: " + prod + "|" + qtdeEntSum +"|"+ df.format(vlEntTotalSum)+ "|" + qtdeSaiSum +"|"+ df.format(vlSaiTotalSum)+"|" + (qtdeEntSum-qtdeSaiSum));
//		}		

		
	
		    
		
		
    String prod = "5257";

	qtdeEntSum = histDao.listaTodos().stream().filter(c -> c.getCodItem().equals(prod))
			.filter(c -> (c.getOperacao().equals("E") && c.getIdPaiLote().equals(1L)))
			.map(HistoricoItens:: getQtde).mapToDouble(BigDecimal::doubleValue).sum();
	
	vlEntTotalSum = histDao.listaTodos().stream().filter(c -> c.getCodItem().equals(prod))
			.filter(c -> (c.getOperacao().equals("E")&& c.getIdPaiLote().equals(1L)))
			.map(HistoricoItens:: getVlLiq).mapToDouble(BigDecimal::doubleValue).sum();

	
	qtdeSaiSum = histDao.listaTodos().stream().filter(c -> c.getCodItem().equals(prod))
			.filter(c -> (c.getOperacao().equals("S")&& c.getIdPaiLote().equals(1L)))
			.map(HistoricoItens:: getQtde).mapToDouble(BigDecimal::doubleValue).sum();
	vlSaiTotalSum = histDao.listaTodos().stream().filter(c -> c.getCodItem().equals(prod))
			.filter(c -> (c.getOperacao().equals("S")&& c.getIdPaiLote().equals(1L)))
			.map(HistoricoItens:: getVlLiq).mapToDouble(BigDecimal::doubleValue).sum();

	System.out.println("Item: " + prod + "|" + qtdeEntSum +"|"+ df.format(vlEntTotalSum)+ "|" + qtdeSaiSum +"|"+ df.format(vlSaiTotalSum)+"|" + (qtdeEntSum-qtdeSaiSum));


		
		
	
		
		
		
		
		
		
		
		
		
		
//		 DecimalFormat df = new DecimalFormat("#,###.00");
//		 Double qtdeSum = dao.listaTodos().stream()
//             .filter(c -> c.getCodProduto().equals("28276"))
//             .mapToDouble( ProdutoNotaFiscal :: getQtde)
//             .sum();
//		 
//		 Double vlTotalSum = dao.listaTodos().stream()
//	             .filter(c -> c.getCodProduto().equals("28276"))
//	             .mapToDouble( ProdutoNotaFiscal :: getVlBruto)
//	             .sum();
//
//		 System.out.println("Item 12777 => " + qtdeSum +"|"+ df.format(vlTotalSum));
	   
	   
	}

}
