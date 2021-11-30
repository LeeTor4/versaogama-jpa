package com.tor4.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tor4.model.cadastro.Produto;
import com.tor4.model.movimentacao.NotaFiscal;

import modulos.efdicms.entidades.Reg0200;
import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.manager.LeitorEfdIcms;

public class ImportaEfdIcms {
	
	public List<Produto>   getProduto(LeitorEfdIcms leitor){
		 List<Produto> retorno = new ArrayList<Produto>();
		 
		 for(Reg0200 prod :  leitor.getRegs0200()){
			 Produto p = new Produto();
			 
			 p.setIdPai(prod.getIdPai());
			 p.setCodUtilizEstab(prod.getCodItem());
			 p.setDescricao(prod.getDescrItem());
			 p.setNcm(prod.getCodNcm());
			 p.setCodigodeBarras(prod.getCodBarra());
			 
			 retorno.add(p);
		 }
		 
		 return retorno;
	}
	public List<NotaFiscal> getNotasFiscais(LeitorEfdIcms leitor){
		
		List<NotaFiscal> retorno = new ArrayList<NotaFiscal>();
		
		for(RegC100 nota : leitor.getRegsC100()){
			NotaFiscal nf = new NotaFiscal();
			
			nf.setIdPai(nota.getIdPai());
			
			if(nota.getIndOper().equals("0")){
				nf.setTipoOperacao("E");
			}else {
				nf.setTipoOperacao("S");
			}
			
			if(nota.getIndEmit().equals("0")) {
				nf.setIndDocProprio("S");
			}else {
				nf.setIndDocProprio("N");
			}
			
			nf.setCodRemetenteDestinatario(nota.getCodPart());
			nf.setEspecie(nota.getCodMod());
			nf.setSerie(nota.getSer());		
			nf.setChaveEletronica(nota.getChvNfe());
			nf.setNumDoc(nota.getNumDoc());
			nf.setDataEmissao(nota.getDtDoc());
			nf.setDataEntradaSaida(nota.getDtEntSai());
			nf.setValorTotalProdutos(BigDecimal.valueOf(nota.getVlMerc()));
			
			retorno.add(nf);
		}
		
		return retorno;
		
	}

}
