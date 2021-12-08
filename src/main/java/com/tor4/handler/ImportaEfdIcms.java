package com.tor4.handler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;

import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;
import com.tor4.dao.cadastro.EquipamentoEcfDao;
import com.tor4.dao.metadados.BancoDados;
import com.tor4.model.cadastro.EquipamentoECF;
import com.tor4.model.cadastro.Produto;
import com.tor4.model.movimentacao.EquipamentoCFe;
import com.tor4.model.movimentacao.ItensMovDiario;
import com.tor4.model.movimentacao.ItensMovDiarioCFe;
import com.tor4.model.movimentacao.LoteImportacaoSpedFiscal;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ProdutoNotaFiscal;
import com.tor4.model.movimentacao.ReducaoZ;
import com.tor4.model.movimentacao.TotParciaisRDZ;

import modulos.efdicms.entidades.Reg0000;
import modulos.efdicms.entidades.Reg0200;
import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.entidades.RegC170;
import modulos.efdicms.entidades.RegC400;
import modulos.efdicms.entidades.RegC405;
import modulos.efdicms.entidades.RegC420;
import modulos.efdicms.entidades.RegC425;
import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.manager.LeitorEfdIcms;

public class ImportaEfdIcms {
	
	private BancoDados banco = new BancoDados();
	public LoteImportacaoSpedFiscal getLoteImportacao(LeitorEfdIcms leitor, Long idEmp, Long idEst) {
		LoteImportacaoSpedFiscal importacao = new LoteImportacaoSpedFiscal();
		
		for(Reg0000 lote : leitor.getRegs0000()){
			importacao.setIdEmp(idEmp);
			importacao.setIdEst(idEst);
			importacao.setCodVersao(lote.getCodVer());
			importacao.setCodFinalidade(lote.getCodFin());
			importacao.setDtIni(lote.getDtIni());
			importacao.setDtFin(lote.getDtFin());
			importacao.setNome(lote.getNome());
			importacao.setCnpj(lote.getCnpj());
			importacao.setCpf(lote.getCpf());
			importacao.setUf(lote.getUf());
			importacao.setIe(lote.getIe());
			importacao.setCodMun(lote.getCodMun());
			importacao.setIM(lote.getIm());
			importacao.setSuframa(lote.getSuframa());
			importacao.setIndPerfil(lote.getIndPerfil());
			importacao.setIndAtiv(lote.getIndAtiv());
		}

		return importacao;
	}
	
	
	public List<Produto> getProduto(LeitorEfdIcms leitor, Long idEmp, Long idEst){
		 List<Produto> retorno = new ArrayList<Produto>();
		 
		 for(Reg0200 prod :  leitor.getRegs0200()){
			 Produto p = new Produto();
			 
             p.setIdEmp(idEmp);
             p.setIdEst(idEst);
			 p.setCodUtilizEstab(prod.getCodItem());
			 p.setDescricao(prod.getDescrItem());
			 p.setNcm(prod.getCodNcm());
			 p.setCodigodeBarras(prod.getCodBarra());
			 
			 retorno.add(p);
		 }
		 
		 return retorno;
	}
	public List<NotaFiscal> getNotasFiscais(EntityManager em ,LeitorEfdIcms leitor,String file,Long idEmp, Long idEst){
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		List<NotaFiscal> retorno = new ArrayList<NotaFiscal>();
		  
		for(RegC100 nota : leitor.getRegsC100()){
			NotaFiscal nf = new NotaFiscal();
		
			nf.setIdPai(banco.getIncremento(em , "tb_importspedfiscal"));
			nf.setIdEmp(idEmp);
			nf.setIdEst(idEst);
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
			nf.setSituacaoDocumento(nota.getCodSit());
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
			
			if(nota.getVlFrt() != null) {
				nf.setValorFrete(BigDecimal.valueOf(nota.getVlFrt()));
			}else {
				nf.setValorFrete(BigDecimal.valueOf(0.0));
			}
			
			
			retorno.add(nf);
			ProdutoNotaFiscal prod = new ProdutoNotaFiscal();
			for(RegC170 pNF : nota.getProdutosNota()){
				
				prod.setNumItem(pNF.getNumItem());
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
								
                            	prod.setNumItem(p.getNumItem());
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
    
	public List<ReducaoZ> getReducoes(EntityManager em ,LeitorEfdIcms leitor,Long idEmp, Long idEst){
		EquipamentoEcfDao dao = new EquipamentoEcfDao();
		List<ReducaoZ> retorno = new ArrayList<ReducaoZ>();
		
		Long cont=0L;
		if(banco.getIncremento(em, "tb_equipamentocfe") == 1) {
			cont = banco.getIncremento(em, "tb_reducaoz")-1;
			leitor.incRDZ(cont);
		}else {
			cont = banco.getIncremento(em, "tb_reducaoz");
			leitor.incRDZ(cont);
		}
		for(RegC400 c400 : leitor.getRegsC400()){
			EquipamentoECF equip = new EquipamentoECF();
			
			equip.setNumSerieFabECF(c400.getNumSerieFabECF());
			
			for(RegC405 c405 : c400.getRegsC405()){
				ReducaoZ redz = new ReducaoZ();
				
				redz.setIdEmp(idEmp);
				redz.setIdEst(idEst);
				redz.setIdPai(banco.getIncremento(em, "tb_importspedfiscal"));
				redz.setId_ecf(dao.buscaPorNumFab(equip.getNumSerieFabECF()).getId());
				redz.setNumCOO(c405.getNumCOOFin());
				redz.setPosicaoCRO(c405.getPosicaoCRO());
				redz.setPosicaoRDZ(c405.getPosicaoRDZ());
				redz.setDtReducaoZ(c405.getDtDoc());
				redz.setVlGrandeTotal(BigDecimal.valueOf(c405.getVlGrandeTotalFinal()));
				redz.setVlVendaBruta(BigDecimal.valueOf(c405.getVlVendaBruta()));
				
				for(RegC420 c420 : c405.getRegsC420()){
					TotParciaisRDZ totParcRdz = new TotParciaisRDZ();
					totParcRdz.setCodTotalizador(c420.getCodTotPar());
					totParcRdz.setDescNumTotalizador(c420.getDescrNrTot());
					totParcRdz.setNumTotalizador(c420.getNrTot());
					totParcRdz.setVlAcumuladoTotRedZ(c420.getVlAcumTot());
					
					for(RegC425 c425 : c420.getRegsC425()){
						
						ItensMovDiario item = new ItensMovDiario();
						item.setIdPaiRedZ(c425.getIdPaiRedZ());
						item.setCodItem(c425.getCodItem());
						item.setQtde(c425.getQtd());
						item.setUnd(c425.getUnd());
						item.setVlItem(c425.getVlItem());
						item.setVlPis(c425.getVlPis());
						item.setVlCofins(c425.getVlCofins());
						
						totParcRdz.adicionaItensMovDiario(item);
						
						
					}
					redz.adicionaTotParcRedZ(totParcRdz);
					
					
				}
				
				equip.adicionaReducoes(redz);
				retorno.add(redz);	
				
			}
			
			
			
		}
		return retorno;
	}
	
    public List<EquipamentoCFe> getEquipamentosCFe(EntityManager em ,LeitorEfdIcms leitor,String file,Long idEmp, Long idEst){
    	
    	ParseDocXML parseDocXML = new ParseDocXML();
    	File f = new File(file);
		List<EquipamentoCFe> retorno = new ArrayList<EquipamentoCFe>();
		
		Long cont=0L;
		if(banco.getIncremento(em, "tb_equipamentocfe") == 1) {
			cont = banco.getIncremento(em, "tb_equipamentocfe")-1;
			leitor.incTotEquipCFe(cont);
		}else {
			cont = banco.getIncremento(em, "tb_equipamentocfe");
			leitor.incTotEquipCFe(cont);
		}
		
		
		for(RegC860 regC860 : leitor.getRegsC860()){
			EquipamentoCFe equip = new EquipamentoCFe();
			equip.setCodModDocFiscal(regC860.getCodModDocFiscal());
			equip.setIdPai(banco.getIncremento(em, "tb_importspedfiscal"));
			equip.setDocInicial(regC860.getDocInicial());
			equip.setDocFinal(regC860.getDocFinal());
			equip.setDtEmissao(regC860.getDtEmissao());
			equip.setNumSerieEquipSat(regC860.getNumSerieEquipSat());
			
			try {

				for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
					if (regC860.getId() == idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)) {
						for (Produtos p : doc.getProds()) {

							if (doc.getIdent().getModeloDoc().equals("59")) {
								ItensMovDiarioCFe item = new ItensMovDiarioCFe();

								item.setIdPai(regC860.getId());
								System.out.println(regC860.getId());

								item.setIdPaiEmp(idEmp);
								item.setIdPaiEst(idEst);
								item.setNumCFe(doc.getIdent().getNumDoc());
								item.setNumItem(p.getNumItem());
								item.setChaveCFe(doc.getIdent().getChaveeletronica());
								item.setCodItem(p.getCodItem());
								item.setCfop(p.getCfop());
								item.setCstIcms(p.getOrig().concat(p.getCst()));
								item.setUnd(p.getUndComercial());

								item.setQtde(Double.valueOf(p.getQtdComercial()));
								item.setVlItem(Double.valueOf(p.getVlItem()));
								if (p.getvDesc() != null) {
									item.setVlDesc(Double.valueOf(p.getvDesc()));
								} else {
									item.setVlDesc(0.0);
								}

								item.setVlProd(Double.valueOf(p.getVlProduto()));
								item.setVlUnit(Double.valueOf(p.getVlUnComerial()));

								equip.adicionaItensMovDiario(item);
							}
						}

					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			
			retorno.add(equip);
		}
		
		
		return retorno;
    }


	private Long idPaiEquipCFe(String numDOc, LeitorEfdIcms leitor) {
		Long id = 0L;

		int num = Integer.valueOf(numDOc);
		for (Long key : leitor.getMpC860().keySet()) {

			if (num >= Integer.valueOf(leitor.getMpC860().get(key).getDocInicial())
					&& num <= Integer.valueOf(leitor.getMpC860().get(key).getDocFinal())) {

				if (key != null) {
					id = key;
				}

			}
		}

		return id;
	}
}
