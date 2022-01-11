package com.tor4.model.exportacoes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.tor4.dao.movimentacao.SaldoItensTotalizadoPorLoteDao;
import com.tor4.model.movimentacao.SaldoItensTotalizadoPorLote;

public class ExportaQuantitativosMensaisDeEstoque2 {

	
	public void exportaControleQuantitativosMensais(String file, String ano, String cnpj) {
	
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
		
			String linha = " ";

			linha = cabecalho();

			writer.write(linha);
			writer.newLine();
			
			Set<String> listaProdutos = new LinkedHashSet<String>();
			Map<String,SaldoItensTotalizadoPorLote > mpSaldosMensais = new HashMap<String,SaldoItensTotalizadoPorLote>();
			
			SaldoItensTotalizadoPorLoteDao dao = new SaldoItensTotalizadoPorLoteDao();
			
			List<SaldoItensTotalizadoPorLote> movItensAnual = dao.listaTodos().stream()
					.filter(c -> c.getAno().equals(ano))
					.filter(c -> c.getCnpj().equals(cnpj))
					.collect(Collectors.toList());

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
		String saldJan = String.format("%.2f", saldoJan);
		
		String EntQtdeFev = String.format("%.2f", totaisMensais.getQtdeEntFev());
		String EntVlFev = String.format("%.2f", totaisMensais.getVrEntFev());
		String SaiQtdeFev = String.format("%.2f", totaisMensais.getQtdeSaiFev());
		String SaiVlFev = String.format("%.2f", totaisMensais.getVrSaiFev());
		String saldFev = String.format("%.2f", saldoFev);

		String EntQtdeMar = String.format("%.2f", totaisMensais.getQtdeEntMar());
		String EntVlMar = String.format("%.2f", totaisMensais.getVrEntMar());
		String SaiQtdeMar = String.format("%.2f", totaisMensais.getQtdeSaiMar());
		String SaiVlMar = String.format("%.2f",  totaisMensais.getVrSaiMar());
		String saldMar = String.format("%.2f", saldoMar);

		String EntQtdeAbr = String.format("%.2f", totaisMensais.getQtdeEntAbr());
		String EntVlAbr = String.format("%.2f", totaisMensais.getVrEntAbr());
		String SaiQtdeAbr = String.format("%.2f", totaisMensais.getQtdeSaiAbr());
		String SaiVLAbr = String.format("%.2f", totaisMensais.getVrSaiAbr());
		String saldAbr = String.format("%.2f", saldoAbr);

		String EntQtdeMai = String.format("%.2f", totaisMensais.getQtdeEntMai());
		String EntVlMai = String.format("%.2f", totaisMensais.getVrEntMai());
		String SaiQtdeMai = String.format("%.2f", totaisMensais.getQtdeSaiMai());
		String SaiVlMai = String.format("%.2f", totaisMensais.getVrSaiMai());
		String saldMai = String.format("%.2f", saldoMai);

		String EntQtdeJun = String.format("%.2f", totaisMensais.getQtdeEntJun());
		String EntVlJun = String.format("%.2f", totaisMensais.getVrEntJun());
		String SaiQtdeJun = String.format("%.2f", totaisMensais.getQtdeSaiJun());
		String SaiVlJun = String.format("%.2f", totaisMensais.getVrSaiJun());
		String saldJun = String.format("%.2f", saldoJun);

		String EntQtdeJul = String.format("%.2f",  totaisMensais.getQtdeEntJul());
		String EntVlJul = String.format("%.2f", totaisMensais.getVrEntJul());
		String SaiQtdeJul = String.format("%.2f",  totaisMensais.getQtdeSaiJul());
		String SaiVlJul = String.format("%.2f", totaisMensais.getVrSaiJul());
		String saldJul = String.format("%.2f", saldoJul);

		String EntQtdeAgo = String.format("%.2f",  totaisMensais.getQtdeEntAgo());
		String EntVlAgo = String.format("%.2f",  totaisMensais.getVrEntAgo());
		String SaiQtdeAgo = String.format("%.2f", totaisMensais.getQtdeSaiAgo());
		String SaiVlAgo = String.format("%.2f",  totaisMensais.getVrSaiAgo());
		String saldAgo = String.format("%.2f", saldoAgo);

		String EntQtdeSet = String.format("%.2f", totaisMensais.getQtdeEntSet());
		String EntVlSet = String.format("%.2f",  totaisMensais.getVrEntSet());
		String SaiQtdeSet = String.format("%.2f", totaisMensais.getQtdeSaiSet());
		String SaiVlSet = String.format("%.2f",  totaisMensais.getVrSaiSet());
		String saldSet = String.format("%.2f", saldoSet);

		String EntQtdeOut = String.format("%.2f", totaisMensais.getQtdeEntOut());
		String EntVlOut = String.format("%.2f", totaisMensais.getVrEntOut());
		String SaiQtdeOut = String.format("%.2f", totaisMensais.getQtdeSaiOut());
		String SaiVlOut = String.format("%.2f", totaisMensais.getVrSaiOut());
		String saldOut = String.format("%.2f", saldoOut);

		String EntQtdeNov = String.format("%.2f", totaisMensais.getQtdeEntNov());
		String EntVlNov = String.format("%.2f", totaisMensais.getVrEntNov());
		String SaiQtdeNov = String.format("%.2f", totaisMensais.getQtdeSaiNov());
		String SaiVlNov = String.format("%.2f", totaisMensais.getVrSaiNov());
		String saldNov = String.format("%.2f", saldoNov);

		String EntQtdeDez = String.format("%.2f", totaisMensais.getQtdeEntDez());
		String EntVlDez = String.format("%.2f", totaisMensais.getVrEntDez());
		String SaiQtdeDez = String.format("%.2f",  totaisMensais.getQtdeSaiDez());
		String SaiVlDez = String.format("%.2f", totaisMensais.getVrSaiDez());
		String saldDez = String.format("%.2f", saldoDez);

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
		linha  = "CÓDIGO ITEM";
		linha += ";";
		linha += "CÓDIGO ANTERIOR ITEM";
		linha += ";";
		linha += "DESCRIÇÃO";
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
