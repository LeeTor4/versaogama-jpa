package com.tor4.model.exportacoes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tor4.dao.cadastro.ProdutoDao;
import com.tor4.dao.movimentacao.SaldoItensTotalizadoPorLoteDao;
import com.tor4.dao.movimentacao.SpSaldosMensais;
import com.tor4.model.movimentacao.SaldoItensTotalizadoPorLote;
import com.tor4.model.movimentacao.SaldosMensais;

public class ExportaQuantitativosMensaisDeEstoque2 {

	
	public void exportaControleQuantitativosMensais(String file, String ano, String cnpj) {
	
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
		
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
			
			SpSaldosMensais spSaldos = new SpSaldosMensais();
			SaldoItensTotalizadoPorLoteDao dao = new SaldoItensTotalizadoPorLoteDao();
			ProdutoDao prodDao = new ProdutoDao();
			ModeloTotalizadoresMensais totaisMensais = new ModeloTotalizadoresMensais(); 
			Set<String> listaProdutos = new LinkedHashSet<String>();
			  
		    
		    List<SaldoItensTotalizadoPorLote> lista = dao.listaTodos().stream()
				  .filter(c -> c.getAno().equals(ano))
				  .collect(Collectors.toList());

		    for(SaldoItensTotalizadoPorLote item : lista){
			  listaProdutos.add(item.getCodItem());
		    }
			
		    for(String codigo : listaProdutos){ 	
		    	
		    	 List<SaldosMensais> jan = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("1"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());		    	 
		    	 List<SaldosMensais> fev = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("2"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());		    	 
		    	 List<SaldosMensais> mar = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("3"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());			    	 
		    	 List<SaldosMensais> abr = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("4"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 List<SaldosMensais> mai = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("5"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 List<SaldosMensais> jun = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("6"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 List<SaldosMensais> jul = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("7"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 List<SaldosMensais> ago = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("8"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 List<SaldosMensais> set = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("9"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 List<SaldosMensais> out = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("10"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 List<SaldosMensais> nov = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("11"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 List<SaldosMensais> dez = spSaldos.spSaldosMensais(codigo, "2022", cnpj).stream()
		    			 .filter(c -> c.getMes().equals("12"))
						 .filter(c -> c.getCodItem().equals(codigo))
					     .collect(Collectors.toList());	
		    	 
		    	 
		    	 for(SaldosMensais item : jan){	
		    		 
		    		System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
			    	totaisMensais.setQtdeEntJan(item.getTotQtdeEnt());
					totaisMensais.setVrEntJan(item.getTotVlEnt().doubleValue());
				    totaisMensais.setQtdeSaiJan(item.getTotQteSai());
				    totaisMensais.setVrSaiJan(item.getTotVlSai());	
				    totaisMensais.setSaldoJan(item.getSaldo());
		    	 }
		    	 
		    	 for(SaldosMensais item : fev){
		    		System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
			    	totaisMensais.setQtdeEntFev(item.getTotQtdeEnt());
					totaisMensais.setVrEntFev(item.getTotVlEnt().doubleValue());
				    totaisMensais.setQtdeSaiFev(item.getTotQteSai());
				    totaisMensais.setVrSaiFev(item.getTotVlSai());	
				    totaisMensais.setSaldoFev(item.getSaldo());
					    
		    	 }

		    	 for(SaldosMensais item : mar){
		    		System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
			    	totaisMensais.setQtdeEntMar(item.getTotQtdeEnt());
					totaisMensais.setVrEntMar(item.getTotVlEnt().doubleValue());
				    totaisMensais.setQtdeSaiMar(item.getTotQteSai());
				    totaisMensais.setVrSaiMar(item.getTotVlSai());	
				    totaisMensais.setSaldoMar(item.getSaldo());
		    		 
		    	 }
				  
		    	 for(SaldosMensais item : abr){
		    		System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
			    	totaisMensais.setQtdeEntAbr(item.getTotQtdeEnt());
					totaisMensais.setVrEntAbr(item.getTotVlEnt().doubleValue());
				    totaisMensais.setQtdeSaiAbr(item.getTotQteSai());
				    totaisMensais.setVrSaiAbr(item.getTotVlSai());	
				    totaisMensais.setSaldoAbr(item.getSaldo());
		    		 
		    	 }
 		    	 
                 for(SaldosMensais item : mai){
                	System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
 			    	totaisMensais.setQtdeEntMai(item.getTotQtdeEnt());
 					totaisMensais.setVrEntMai(item.getTotVlEnt().doubleValue());
 				    totaisMensais.setQtdeSaiMai(item.getTotQteSai());
 				    totaisMensais.setVrSaiMai(item.getTotVlSai());	
 				    totaisMensais.setSaldoMai(item.getSaldo());
		    		 
		    	 }
                 
                 for(SaldosMensais item : jun){
                	System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
 			    	totaisMensais.setQtdeEntJun(item.getTotQtdeEnt());
 					totaisMensais.setVrEntJun(item.getTotVlEnt().doubleValue());
 				    totaisMensais.setQtdeSaiJun(item.getTotQteSai());
 				    totaisMensais.setVrSaiJun(item.getTotVlSai());	
 				    totaisMensais.setSaldoJun(item.getSaldo());
		    		 
		    	 }
                 for(SaldosMensais item : jul){
                	System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
 			    	totaisMensais.setQtdeEntJul(item.getTotQtdeEnt());
 					totaisMensais.setVrEntJul(item.getTotVlEnt().doubleValue());
 				    totaisMensais.setQtdeSaiJul(item.getTotQteSai());
 				    totaisMensais.setVrSaiJul(item.getTotVlSai());	
 				    totaisMensais.setSaldoJul(item.getSaldo());
		    		 
		    	 }
                 for(SaldosMensais item : ago){
                	System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
 			    	totaisMensais.setQtdeEntAgo(item.getTotQtdeEnt());
 					totaisMensais.setVrEntAgo(item.getTotVlEnt().doubleValue());
 				    totaisMensais.setQtdeSaiAgo(item.getTotQteSai());
 				    totaisMensais.setVrSaiAgo(item.getTotVlSai());	
 				    totaisMensais.setSaldoAgo(item.getSaldo());
		    		 
		    	 }
                 for(SaldosMensais item : set){
                	System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
 			    	totaisMensais.setQtdeEntSet(item.getTotQtdeEnt());
 					totaisMensais.setVrEntSet(item.getTotVlEnt().doubleValue());
 				    totaisMensais.setQtdeSaiSet(item.getTotQteSai());
 				    totaisMensais.setVrSaiSet(item.getTotVlSai());	
 				    totaisMensais.setSaldoSet(item.getSaldo());
		    		 
		    	 }
                 for(SaldosMensais item : out){
                	System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
 			    	totaisMensais.setQtdeEntOut(item.getTotQtdeEnt());
 					totaisMensais.setVrEntOut(item.getTotVlEnt().doubleValue());
 				    totaisMensais.setQtdeSaiOut(item.getTotQteSai());
 				    totaisMensais.setVrSaiOut(item.getTotVlSai());	
 				    totaisMensais.setSaldoOut(item.getSaldo());
		    		 
		    	 }
                 for(SaldosMensais item : nov){
                	System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
 			    	totaisMensais.setQtdeEntNov(item.getTotQtdeEnt());
 					totaisMensais.setVrEntNov(item.getTotVlEnt().doubleValue());
 				    totaisMensais.setQtdeSaiNov(item.getTotQteSai());
 				    totaisMensais.setVrSaiNov(item.getTotVlSai());	
 				    totaisMensais.setSaldoNov(item.getSaldo());
		    		 
		    	 }
                 for(SaldosMensais item : dez){
                    System.out.println(item.getAno()+"|" + item.getMes()+"|" + item.getDescricao() +"|" + item.getCodItem() + "|" + item.getTotQtdeEnt() + "|" +item.getTotQteSai() + "|" + item.getSaldo());				    
 			    	totaisMensais.setQtdeEntDez(item.getTotQtdeEnt());
 					totaisMensais.setVrEntDez(item.getTotVlEnt().doubleValue());
 				    totaisMensais.setQtdeSaiDez(item.getTotQteSai());
 				    totaisMensais.setVrSaiDez(item.getTotVlSai());	
 				    totaisMensais.setSaldoDez(item.getSaldo());
		    		 
		    	 }
		    	
					
					
			    if(totaisMensais != null) {
    	        	 
    	        	 totaisMensais.setCodItem(codigo);
    	        	 totaisMensais.setCodAntItem("");
    	        	 totaisMensais.setDescricao(prodDao.buscaPorCodigo(codigo).getDescricao());
    	        	 totaisMensais.setUnidMedida("");
    	        	 
    	        	// System.out.println("Saldo Inicial "   +p.getCogigo()+"|"+p.getCodAntItem() + "|"+saldoInicial(p.getCogigo(), p.getCodAntItem(), cnpj, String.valueOf(Integer.valueOf(ano)-1)));
                    // System.out.println("Saldo Declarado "+String.valueOf(Integer.valueOf(ano)-1)+"| " +p.getCogigo()+"|"+p.getCodAntItem() + "|"+invDeclarado(p.getCogigo(), p.getCodAntItem(), cnpj, String.valueOf(Integer.valueOf(ano)-1)));
                      
    	        	 
    	        	 if(!formatacaoPlanilha(totaisMensais).contains(";0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00;0,00")) {

    	        			 System.out.println("linha " + formatacaoPlanilha(totaisMensais));
    	        			 linha = formatacaoPlanilha(totaisMensais);
    				         writer.write(linha);
    				         writer.newLine(); 
    	        	 }
    	        	
    	         }
				 		
		    
		    
		    }

		    writer.close();	
			
	        System.out.println("Exportado com Sucesso!!!");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
       
	}
	
	private String formatacaoPlanilha(ModeloTotalizadoresMensais totaisMensais) {
		String linha = "";
		
		//System.out.println(totaisMensais.getCodItem()+"|"+totaisMensais.getCodAntItem()+"|"+totaisMensais.getMes()+"|"+totaisMensais.getTotQtde());
          
        Double saldoJan=0.0;
		Double saldoFev=0.0;
		Double saldoMar=0.0;
		Double saldoAbr=0.0;
		Double saldoMai=0.0;
		Double saldoJun=0.0;
		Double saldoJul=0.0;
		Double saldoAgo=0.0;
		Double saldoSet=0.0;
		Double saldoOut=0.0;		
		Double saldoNov=0.0;
		Double saldoDez=0.0;
		
		
		saldoJan = totaisMensais.getQteIniInv() + totaisMensais.getQtdeEntJan() - totaisMensais.getQtdeSaiJan();
		
		saldoFev = saldoJan + totaisMensais.getQtdeEntFev() - totaisMensais.getQtdeSaiFev();
		saldoMar = saldoFev + totaisMensais.getQtdeEntMar() - totaisMensais.getQtdeSaiMar();
		saldoAbr = saldoMar + totaisMensais.getQtdeEntAbr() - totaisMensais.getQtdeSaiAbr();
		saldoMai = saldoAbr + totaisMensais.getQtdeEntMai() - totaisMensais.getQtdeSaiMai();
		saldoJun = saldoMai + totaisMensais.getQtdeEntJun() - totaisMensais.getQtdeSaiJun();
		saldoJul = saldoJun + totaisMensais.getQtdeEntJul() - totaisMensais.getQtdeSaiJul();
		saldoAgo = saldoJul + totaisMensais.getQtdeEntAgo() - totaisMensais.getQtdeSaiAgo();
		saldoSet = saldoAgo + totaisMensais.getQtdeEntSet() - totaisMensais.getQtdeSaiSet();
		saldoOut = saldoSet + totaisMensais.getQtdeEntOut() - totaisMensais.getQtdeSaiOut();
		
		saldoNov = saldoOut + totaisMensais.getQtdeEntNov() - totaisMensais.getQtdeSaiNov();
		saldoDez = saldoNov + totaisMensais.getQtdeEntDez() - totaisMensais.getQtdeSaiDez();

		
		
		 String invIni = String.format("%.2f", totaisMensais.getQteIniInv()); // Implementar depois		 
	 	 String invDec = String.format("%.2f", totaisMensais.getQteInvDec());	
		
		
		String qteEntJan = String.format("%.2f", totaisMensais.getQtdeEntJan());
		String vlEntJan = String.format("%.2f", totaisMensais.getVrEntJan());
		String qteSaiJan = String.format("%.2f", totaisMensais.getQtdeSaiJan());
		String vlSaiJan = String.format("%.2f", totaisMensais.getVrSaiJan());
		String saldJan = String.format("%.2f", totaisMensais.getSaldoJan());
		
		String EntQtdeFev = String.format("%.2f", totaisMensais.getQtdeEntFev());
		String EntVlFev = String.format("%.2f", totaisMensais.getVrEntFev());
		String SaiQtdeFev = String.format("%.2f", totaisMensais.getQtdeSaiFev());
		String SaiVlFev = String.format("%.2f", totaisMensais.getVrSaiFev());
		String saldFev = String.format("%.2f", totaisMensais.getSaldoFev());

		String EntQtdeMar = String.format("%.2f", totaisMensais.getQtdeEntMar());
		String EntVlMar = String.format("%.2f", totaisMensais.getVrEntMar());
		String SaiQtdeMar = String.format("%.2f", totaisMensais.getQtdeSaiMar());
		String SaiVlMar = String.format("%.2f",  totaisMensais.getVrSaiMar());
		String saldMar = String.format("%.2f", totaisMensais.getSaldoMar());

		String EntQtdeAbr = String.format("%.2f", totaisMensais.getQtdeEntAbr());
		String EntVlAbr = String.format("%.2f", totaisMensais.getVrEntAbr());
		String SaiQtdeAbr = String.format("%.2f", totaisMensais.getQtdeSaiAbr());
		String SaiVLAbr = String.format("%.2f", totaisMensais.getVrSaiAbr());
		String saldAbr = String.format("%.2f", totaisMensais.getSaldoAbr());

		String EntQtdeMai = String.format("%.2f", totaisMensais.getQtdeEntMai());
		String EntVlMai = String.format("%.2f", totaisMensais.getVrEntMai());
		String SaiQtdeMai = String.format("%.2f", totaisMensais.getQtdeSaiMai());
		String SaiVlMai = String.format("%.2f", totaisMensais.getVrSaiMai());
		String saldMai = String.format("%.2f", totaisMensais.getSaldoMai());

		String EntQtdeJun = String.format("%.2f", totaisMensais.getQtdeEntJun());
		String EntVlJun = String.format("%.2f", totaisMensais.getVrEntJun());
		String SaiQtdeJun = String.format("%.2f", totaisMensais.getQtdeSaiJun());
		String SaiVlJun = String.format("%.2f", totaisMensais.getVrSaiJun());
		String saldJun = String.format("%.2f", totaisMensais.getSaldoJun());

		String EntQtdeJul = String.format("%.2f",  totaisMensais.getQtdeEntJul());
		String EntVlJul = String.format("%.2f", totaisMensais.getVrEntJul());
		String SaiQtdeJul = String.format("%.2f",  totaisMensais.getQtdeSaiJul());
		String SaiVlJul = String.format("%.2f", totaisMensais.getVrSaiJul());
		String saldJul = String.format("%.2f", totaisMensais.getSaldoJul());

		String EntQtdeAgo = String.format("%.2f",  totaisMensais.getQtdeEntAgo());
		String EntVlAgo = String.format("%.2f",  totaisMensais.getVrEntAgo());
		String SaiQtdeAgo = String.format("%.2f", totaisMensais.getQtdeSaiAgo());
		String SaiVlAgo = String.format("%.2f",  totaisMensais.getVrSaiAgo());
		String saldAgo = String.format("%.2f", totaisMensais.getSaldoAgo());

		String EntQtdeSet = String.format("%.2f", totaisMensais.getQtdeEntSet());
		String EntVlSet = String.format("%.2f",  totaisMensais.getVrEntSet());
		String SaiQtdeSet = String.format("%.2f", totaisMensais.getQtdeSaiSet());
		String SaiVlSet = String.format("%.2f",  totaisMensais.getVrSaiSet());
		String saldSet = String.format("%.2f", totaisMensais.getSaldoSet());

		String EntQtdeOut = String.format("%.2f", totaisMensais.getQtdeEntOut());
		String EntVlOut = String.format("%.2f", totaisMensais.getVrEntOut());
		String SaiQtdeOut = String.format("%.2f", totaisMensais.getQtdeSaiOut());
		String SaiVlOut = String.format("%.2f", totaisMensais.getVrSaiOut());
		String saldOut = String.format("%.2f", totaisMensais.getSaldoOut());

		String EntQtdeNov = String.format("%.2f", totaisMensais.getQtdeEntNov());
		String EntVlNov = String.format("%.2f", totaisMensais.getVrEntNov());
		String SaiQtdeNov = String.format("%.2f", totaisMensais.getQtdeSaiNov());
		String SaiVlNov = String.format("%.2f", totaisMensais.getVrSaiNov());
		String saldNov = String.format("%.2f", totaisMensais.getSaldoNov());

		String EntQtdeDez = String.format("%.2f", totaisMensais.getQtdeEntDez());
		String EntVlDez = String.format("%.2f", totaisMensais.getVrEntDez());
		String SaiQtdeDez = String.format("%.2f",  totaisMensais.getQtdeSaiDez());
		String SaiVlDez = String.format("%.2f", totaisMensais.getVrSaiDez());
		String saldDez = String.format("%.2f", totaisMensais.getSaldoDez());

		linha  = totaisMensais.getCodItem();
		linha += ";";
		linha += totaisMensais.getCodAntItem();
		linha += ";";
		linha += totaisMensais.getDescricao();
		linha += ";";
		linha += invIni.replace(".", ",");
		linha += ";";
		linha += invDec.replace(".", ",");
		linha += ";";
		
		
		linha += qteEntJan.replace(".", ",");
		linha += ";";
		linha += vlEntJan.replace(".", ",");
		linha += ";";
		linha += qteSaiJan.replace(".", ",");
		linha += ";";
		linha += vlSaiJan.replace(".", ",");
		linha += ";";
		linha += saldJan.replace(".", ",");
		
		
		linha += ";";
		linha += EntQtdeFev.replace(".", ",");
		linha += ";";
		linha += EntVlFev.replace(".", ",");
		linha += ";";
		linha += SaiQtdeFev.replace(".", ",");
		linha += ";";
		linha += SaiVlFev.replace(".", ",");
		linha += ";";
		linha += saldFev.replace(".", ",");

		linha += ";";
		linha += EntQtdeMar.replace(".", ",");
		linha += ";";
		linha += EntVlMar.replace(".", ",");
		linha += ";";
		linha += SaiQtdeMar.replace(".", ",");
		linha += ";";
		linha += SaiVlMar.replace(".", ",");
		linha += ";";
		linha += saldMar.replace(".", ",");

		linha += ";";
		linha += EntQtdeAbr.replace(".", ",");
		linha += ";";
		linha += EntVlAbr.replace(".", ",");
		linha += ";";
		linha += SaiQtdeAbr.replace(".", ",");
		linha += ";";
		linha += SaiVLAbr.replace(".", ",");
		linha += ";";
		linha += saldAbr.replace(".", ",");

		linha += ";";
		linha += EntQtdeMai.replace(".", ",");
		linha += ";";
		linha += EntVlMai.replace(".", ",");
		linha += ";";
		linha += SaiQtdeMai.replace(".", ",");
		linha += ";";
		linha += SaiVlMai.replace(".", ",");
		linha += ";";
		linha += saldMai.replace(".", ",");

		linha += ";";
		linha += EntQtdeJun.replace(".", ",");
		linha += ";";
		linha += EntVlJun.replace(".", ",");
		linha += ";";
		linha += SaiQtdeJun.replace(".", ",");
		linha += ";";
		linha += SaiVlJun.replace(".", ",");
		linha += ";";
		linha += saldJun.replace(".", ",");

		linha += ";";
		linha += EntQtdeJul.replace(".", ",");
		linha += ";";
		linha += EntVlJul.replace(".", ",");
		linha += ";";
		linha += SaiQtdeJul.replace(".", ",");
		linha += ";";
		linha += SaiVlJul.replace(".", ",");
		linha += ";";
		linha += saldJul.replace(".", ",");

		linha += ";";
		linha += EntQtdeAgo.replace(".", ",");
		linha += ";";
		linha += EntVlAgo.replace(".", ",");
		linha += ";";
		linha += SaiQtdeAgo.replace(".", ",");
		linha += ";";
		linha += SaiVlAgo.replace(".", ",");
		linha += ";";
		linha += saldAgo.replace(".", ",");

		linha += ";";
		linha += EntQtdeSet.replace(".", ",");
		linha += ";";
		linha += EntVlSet.replace(".", ",");
		linha += ";";
		linha += SaiQtdeSet.replace(".", ",");
		linha += ";";
		linha += SaiVlSet.replace(".", ",");
		linha += ";";
		linha += saldSet.replace(".", ",");

		linha += ";";
		linha += EntQtdeOut.replace(".", ",");
		linha += ";";
		linha += EntVlOut.replace(".", ",");
		linha += ";";
		linha += SaiQtdeOut.replace(".", ",");
		linha += ";";
		linha += SaiVlOut.replace(".", ",");
		linha += ";";
		linha += saldOut.replace(".", ",");

		linha += ";";
        linha += EntQtdeNov.replace(".", ",");
		linha += ";";
		linha += EntVlNov.replace(".", ",");
		linha += ";";
		linha += SaiQtdeNov.replace(".", ",");
		linha += ";";
		linha += SaiVlNov.replace(".", ",");
		linha += ";";
		linha += saldNov.replace(".", ",");

		linha += ";";
		linha += EntQtdeDez.replace(".", ",");
		linha += ";";
		linha += EntVlDez.replace(".", ",");
		linha += ";";
		linha += SaiQtdeDez.replace(".", ",");
		linha += ";";
		linha += SaiVlDez.replace(".", ",");
		linha += ";";
		linha += saldDez.replace(".", ",");
		
		 return linha;
	}

	private String cabecalho() {
		String linha;
		linha  = "C?DIGO ITEM";
		linha += ";";
		linha += "C?DIGO ANTERIOR ITEM";
		linha += ";";
		linha += "DESCRI??O";
		linha += ";";
		linha += "QTDE INV INICIAL/APURADO";
		linha += ";";
		linha += "QTDE INV DECLARADO";
		linha += ";";

		linha += "QTDE_ENTRADA_JAN";
		linha += ";";
		linha += "VALOR_ENTRADA_JAN";
		linha += ";";
		linha += "QTDE_SAIDA_JAN";
		linha += ";";
		linha += "VALOR_SAIDA_JAN";
		linha += ";";
		linha += "SALDO_QTDE_JAN";

		linha += ";";
		linha += "QTDE_ENTRADA_FEV";
		linha += ";";
		linha += "VALOR_ENTRADA_FEV";
		linha += ";";
		linha += "QTDE_SAIDA_FEV";
		linha += ";";
		linha += "VALOR_SAIDA_FEV";
		linha += ";";
		linha += "SALDO_QTDE_FEV";

		linha += ";";
		linha += "QTDE_ENTRADA_MAR";
		linha += ";";
		linha += "VALOR_ENTRADA_MAR";
		linha += ";";
		linha += "QTDE_SAIDA_MAR";
		linha += ";";
		linha += "VALOR_SAIDA_MAR";
		linha += ";";
		linha += "SALDO_QTDE_MAR";

		linha += ";";
		linha += "QTDE_ENTRADA_ABR";
		linha += ";";
		linha += "VALOR_ENTRADA_ABR";
		linha += ";";
		linha += "QTDE_SAIDA_ABR";
		linha += ";";
		linha += "VALOR_SAIDA_ABR";
		linha += ";";
		linha += "SALDO_QTDE_ABR";

		linha += ";";
		linha += "QTDE_ENTRADA_MAI";
		linha += ";";
		linha += "VALOR_ENTRADA_MAI";
		linha += ";";
		linha += "QTDE_SAIDA_MAI";
		linha += ";";
		linha += "VALOR_SAIDA_MAI";
		linha += ";";
		linha += "SALDO_QTDE_MAI";

		linha += ";";
		linha += "QTDE_ENTRADA_JUN";
		linha += ";";
		linha += "VALOR_ENTRADA_JUN";
		linha += ";";
		linha += "QTDE_SAIDA_JUN";
		linha += ";";
		linha += "VALOR_SAIDA_JUN";
		linha += ";";
		linha += "SALDO_QTDE_JUN";

		linha += ";";
		linha += "QTDE_ENTRADA_JUL";
		linha += ";";
		linha += "VALOR_ENTRADA_JUL";
		linha += ";";
		linha += "QTDE_SAIDA_JUL";
		linha += ";";
		linha += "VALOR_SAIDA_JUL";
		linha += ";";
		linha += "SALDO_QTDE_JUL";

		linha += ";";
		linha += "QTDE_ENTRADA_AGO";
		linha += ";";
		linha += "VALOR_ENTRADA_AGO";
		linha += ";";
		linha += "QTDE_SAIDA_AGO";
		linha += ";";
		linha += "VALOR_SAIDA_AGO";
		linha += ";";
		linha += "SALDO_QTDE_AGO";

		linha += ";";
		linha += "QTDE_ENTRADA_SET";
		linha += ";";
		linha += "VALOR_ENTRADA_SET";
		linha += ";";
		linha += "QTDE_SAIDA_SET";
		linha += ";";
		linha += "VALOR_SAIDA_SET";
		linha += ";";
		linha += "SALDO_QTDE_SET";

		linha += ";";
		linha += "QTDE_ENTRADA_OUT";
		linha += ";";
		linha += "VALOR_ENTRADA_OUT";
		linha += ";";
		linha += "QTDE_SAIDA_OUT";
		linha += ";";
		linha += "VALOR_SAIDA_OUT";
		linha += ";";
		linha += "SALDO_QTDE_OUT";

		linha += ";";
		linha += "QTDE_ENTRADA_NOV";
		linha += ";";
		linha += "VALOR_ENTRADA_NOV";
		linha += ";";
		linha += "QTDE_SAIDA_NOV";
		linha += ";";
		linha += "VALOR_SAIDA_NOV";
		linha += ";";
		linha += "SALDO_QTDE_NOV";

		linha += ";";
		linha += "QTDE_ENTRADA_DEZ";
		linha += ";";
		linha += "VALOR_ENTRADA_DEZ";
		linha += ";";
		linha += "QTDE_SAIDA_DEZ";
		linha += ";";
		linha += "VALOR_SAIDA_DEZ";
		linha += ";";
		linha += "SALDO_QTDE_DEZ";
		return linha;
	}
}
