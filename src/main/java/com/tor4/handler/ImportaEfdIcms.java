package com.tor4.handler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;
import com.tor4.model.cadastro.Produto;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ProdutoNotaFiscal;

import modulos.efdicms.entidades.Reg0200;
import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.entidades.RegC170;
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
	public List<NotaFiscal> getNotasFiscais(LeitorEfdIcms leitor,String file){
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
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
			
			if(nota.getVlMerc() != null) {
				nf.setValorTotalProdutos(BigDecimal.valueOf(nota.getVlMerc()));
			}else {
				nf.setValorTotalProdutos(BigDecimal.valueOf(0.0));
			}
			
			
			retorno.add(nf);
			ProdutoNotaFiscal prod = new ProdutoNotaFiscal();
			for(RegC170 pNF : nota.getProdutosNota()){
				
				prod.setCodProduto(pNF.getCodItem());
				prod.setCfop(pNF.getCfop());
				prod.setCstA(pNF.getCstIcms().substring(0,1));
				prod.setCstB(pNF.getCstIcms().substring(1,3));
				prod.setQuantidade(BigDecimal.valueOf(pNF.getQtd()));
				prod.setUnidadeMedida(pNF.getUnid());
				prod.setValorBruto(BigDecimal.valueOf(pNF.getVlItem()));
				
				nf.adicionaProdutoNota(prod);
			}
			
			try {
				
				for(DocumentoFiscalEltronico doc :  parseDocXML.validaTipoDeParseNFE(f)){
					
					for(Produtos p : doc.getProds()){
						
						if(doc.getIdent().getModeloDoc().equals("55")) {
							
                            if(nf.getChaveEletronica().equals(doc.getIdent().getChaveeletronica())) {
								
								prod.setCodProduto(p.getCodItem());
								prod.setCfop(p.getCfop());
								prod.setCstA(p.getOrig());
								prod.setCstB(p.getCst());
								prod.setQuantidade(new BigDecimal(p.getQtdComercial()));
								prod.setUnidadeMedida(p.getUndComercial());
								
								prod.setValorBruto(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));
								
								nf.adicionaProdutoNota(prod);
								
							}
						}
							
						
					}
					
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (JAXBException e) {
				
				e.printStackTrace();
			}
			
		}
		
		return retorno;
		
	}

}
