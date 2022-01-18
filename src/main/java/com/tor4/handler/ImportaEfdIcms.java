package com.tor4.handler;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;

import com.leetor4.handler.ParseDocXML;
import com.leetor4.model.nfe.DocumentoFiscalEltronico;
import com.leetor4.model.nfe.Produtos;
import com.tor4.dao.cadastro.EquipamentoEcfDao;
import com.tor4.dao.cadastro.ProdutoDao;
import com.tor4.dao.metadados.BancoDados;
import com.tor4.dao.movimentacao.SaldoItensTotalizadoPorLoteDao;
import com.tor4.model.cadastro.EquipamentoECF;
import com.tor4.model.cadastro.Produto;
import com.tor4.model.movimentacao.EquipamentoCFe;
import com.tor4.model.movimentacao.HistoricoItens;
import com.tor4.model.movimentacao.ItensMovDiario;
import com.tor4.model.movimentacao.ItensMovDiarioCFe;
import com.tor4.model.movimentacao.LoteImportacaoSpedFiscal;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ProdutoNotaFiscal;
import com.tor4.model.movimentacao.ReducaoZ;
import com.tor4.model.movimentacao.SaldoItensTotalizadoPorLote;
import com.tor4.model.movimentacao.TotParciaisRDZ;
import com.tor4.model.movimentacao.TotalizadorDiarioCuponsFiscais;
import com.tor4.util.UtilsEConverters;

import modulos.efdicms.entidades.Reg0000;
import modulos.efdicms.entidades.Reg0200;
import modulos.efdicms.entidades.RegC100;
import modulos.efdicms.entidades.RegC170;
import modulos.efdicms.entidades.RegC860;
import modulos.efdicms.manager.LeitorEfdIcms;

public class ImportaEfdIcms {

	private BancoDados banco = new BancoDados();
	private Map<String,Produto> mpProdXmlProprio = new HashMap<String, Produto>();
	private Set<String> listaProdutos = new LinkedHashSet<String>();

	public LoteImportacaoSpedFiscal getLoteImportacao(LeitorEfdIcms leitor, Long idEmp, Long idEst) {
		LoteImportacaoSpedFiscal importacao = new LoteImportacaoSpedFiscal();

		for (Reg0000 lote : leitor.getRegs0000()) {
			
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

	public List<Produto> getProduto(String file,LeitorEfdIcms leitor, Long idEmp, Long idEst) {
		ProdutoDao dao = new ProdutoDao();
		List<Produto> retorno = new ArrayList<Produto>();
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		for (Reg0200 prod : leitor.getRegs0200()) {
			
			 if(!dao.listaTodos().contains(insereProdutosSped(leitor, prod,idEmp, idEst))) {
				 retorno.add(insereProdutosSped(leitor, prod,idEmp, idEst));
			 }
			
		}
       

		try {
			for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
				
				for (Produtos prd : doc.getProds()) {
					
					 if(!dao.listaTodos().contains(insereProdutoXmlProprio(file, prd, idEmp, idEst))) {
						 retorno.add(insereProdutoXmlProprio(file, prd, idEmp, idEst));
					 }
					
					 mpProdXmlProprio.put(prd.getCodItem(), insereProdutoXmlProprio(file, prd, idEmp, idEst));
					 listaProdutos.add(prd.getCodItem());
				}	
				
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
     
        
        List<Produto> novoRetorno = retorno.stream().distinct().collect(Collectors.toList());
        
		return novoRetorno;
	}

	public List<NotaFiscal> getNotasFiscais(EntityManager em, LeitorEfdIcms leitor, String file, Long idEmp,
			Long idEst,Long lote) {
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		List<NotaFiscal> retorno = new ArrayList<NotaFiscal>();
       // Long contNF = banco.getIncremento("tb_notafiscal");
		for (RegC100 nota : leitor.getRegsC100()) {
			NotaFiscal nf = new NotaFiscal();
            		
			nf.setIdPai(lote);
			nf.setIdEmp(idEmp);
			nf.setIdEst(idEst);
			if (nota.getIndOper().equals("0")) {
				nf.setTipoOperacao("E");
			} else {
				nf.setTipoOperacao("S");
			}

			if (nota.getIndEmit().equals("0")) {
				nf.setIndDocProprio("S");
			} else {
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

			if (nota.getVlMerc() != null) {
				nf.setValorTotalProdutos(BigDecimal.valueOf(nota.getVlMerc()));
			} else {
				nf.setValorTotalProdutos(BigDecimal.valueOf(0.0));
			}

			if (nota.getVlFrt() != null) {
				nf.setValorFrete(BigDecimal.valueOf(nota.getVlFrt()));
			} else {
				nf.setValorFrete(BigDecimal.valueOf(0.0));
			}

			for (RegC170 pNF : nota.getProdutosNota()) {
				ProdutoNotaFiscal prod = new ProdutoNotaFiscal();
				prod.setIdPai(banco.getIncremento("tb_notafiscal"));
				prod.setNumItem(pNF.getNumItem());
				prod.setCodProduto(pNF.getCodItem());
				prod.setCfop(pNF.getCfop());
				prod.setCstA(pNF.getCstIcms().substring(0, 1));
				prod.setCstB(pNF.getCstIcms().substring(1, 3));
				prod.setQuantidade(BigDecimal.valueOf(pNF.getQtd()));
				prod.setUnidadeMedida(pNF.getUnid());
				prod.setValorBruto(BigDecimal.valueOf(pNF.getVlItem()));

							
				
				nf.adicionaProdutoNota(prod);

			}

			try {

				for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {

					for (Produtos p : doc.getProds()) {

						if (doc.getIdent().getModeloDoc().equals("55")) {

							if(nf.getChaveEletronica() != null) {
								
								if (nf.getChaveEletronica().equals(doc.getIdent().getChaveeletronica())) {
									ProdutoNotaFiscal prod = new ProdutoNotaFiscal();
									prod.setIdPai(banco.getIncremento("tb_notafiscal"));
									
									prod.setNumItem(p.getNumItem());
									prod.setCodProduto(p.getCodItem());
									prod.setCfop(p.getCfop());
									prod.setCstA(p.getOrig());
									prod.setCstB(p.getCst());
									prod.setQuantidade(new BigDecimal(p.getQtdComercial()));
									prod.setUnidadeMedida(p.getUndComercial());

									prod.setValorBruto(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));

								
									listaProdutos.add(p.getCodItem());
									
									
									nf.adicionaProdutoNota(prod);

								}
							}

						}
					}

				}
			} catch (IOException e) {

				e.printStackTrace();
			} catch (JAXBException e) {

				e.printStackTrace();
			}

			retorno.add(nf);
		}

		return retorno;

	}

	public List<ReducaoZ> getReducoes(EntityManager em, LeitorEfdIcms leitor, Long idEmp, Long idEst,Long lote) {
		EquipamentoEcfDao dao = new EquipamentoEcfDao();
		List<ReducaoZ> retorno = new ArrayList<ReducaoZ>();

		for (int i = 0; i < leitor.getRegsC400().size(); i++) {
			EquipamentoECF equip = new EquipamentoECF();
			
			equip.setNumSerieFabECF(leitor.getRegsC400().get(i).getNumSerieFabECF());
			
			for (int z = 0; z < leitor.getRegsC400().get(i).getRegsC405().size(); z++) {
				ReducaoZ redz = new ReducaoZ();
				
				redz.setIdEmp(idEmp);
				redz.setIdEst(idEst);
				redz.setIdPai(lote);
				redz.setId_ecf(dao.buscaPorNumFab(equip.getNumSerieFabECF()).getId());
				redz.setNumCOO(leitor.getRegsC400().get(i).getRegsC405().get(z).getNumCOOFin());
				redz.setPosicaoCRO(leitor.getRegsC400().get(i).getRegsC405().get(z).getPosicaoCRO());
				redz.setPosicaoRDZ(leitor.getRegsC400().get(i).getRegsC405().get(z).getPosicaoRDZ());
				redz.setDtReducaoZ(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc());
				redz.setVlGrandeTotal(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getVlGrandeTotalFinal()));
				redz.setVlVendaBruta(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getVlVendaBruta()));
				
				for (int l = 0; l < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().size() ; l++) {
					TotParciaisRDZ totParcRdz = new TotParciaisRDZ();
					
					//totParcRdz.setId(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getId());
					//totParcRdz.setIdPai(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getIdPai());
					totParcRdz.setCodTotalizador(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getCodTotPar());
					totParcRdz.setDescNumTotalizador(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getDescrNrTot());
					totParcRdz.setNumTotalizador(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getNrTot());
					totParcRdz.setVlAcumuladoTotRedZ(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getVlAcumTot());
					
					
					for (int m = 0; m < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().size() ; m++) {
						ItensMovDiario item = new ItensMovDiario();
						
						//item.setIdPai(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPai());
						item.setIdPaiRedZ(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPaiRedZ()-1);
						item.setCodItem(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem());
						item.setQtde(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getQtd());
						item.setUnd(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getUnd());
						item.setVlItem(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem());
						item.setVlPis(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlPis());
						item.setVlCofins(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlCofins());

						listaProdutos.add(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem());
					
						totParcRdz.adicionaItensMovDiario(item);
					}
					
					redz.adicionaTotParcRedZ(totParcRdz);
					
				}
				
				
				for (int n = 0; n < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().size() ; n++) {
					TotalizadorDiarioCuponsFiscais totCF = new TotalizadorDiarioCuponsFiscais();					
					
					//totCF.setIdPai(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().get(n).getIdPai());
					totCF.setCfop(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().get(n).getCfop());
					totCF.setCst(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().get(n).getCstIcms());
					totCF.setVlOperacao(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC490().get(n).getVlOperacao());
					
					
					redz.adicionaTotalCuponsFiscais(totCF);
					
				}
				
				equip.adicionaReducoes(redz);	
				retorno.add(redz);
			}

			
		}

		return retorno;
	}

	public List<EquipamentoCFe> getEquipamentosCFe(EntityManager em, LeitorEfdIcms leitor, String file, Long idEmp,
			Long idEst,Long lote) {

		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
		List<EquipamentoCFe> retorno = new ArrayList<EquipamentoCFe>();

		for (RegC860 regC860 : leitor.getRegsC860()) {
			EquipamentoCFe equip = new EquipamentoCFe();
			equip.setCodModDocFiscal(regC860.getCodModDocFiscal());
			equip.setIdPai(lote);
			equip.setDocInicial(regC860.getDocInicial());
			equip.setDocFinal(regC860.getDocFinal());
			equip.setDtEmissao(regC860.getDtEmissao());
			equip.setNumSerieEquipSat(regC860.getNumSerieEquipSat());
            
			//System.out.println(" CFe idEquip " + regC860.getId());
			try {

				for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
					if ( regC860.getId()  == idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)) {
						for (Produtos p : doc.getProds()) {

							if (doc.getIdent().getModeloDoc().equals("59")) {
								ItensMovDiarioCFe item = new ItensMovDiarioCFe();

								
								item.setIdPai(idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor));
								//System.out.println( " CFe idEquip " + regC860.getId() +  " CFe idPai " + (idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)));

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

								listaProdutos.add(p.getCodItem());
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

					id = leitor.getMpC860().get(key).getId();
				

			}
		}

		return id;
	}

	
	public List<HistoricoItens> getHistoricoItens(EntityManager em, LeitorEfdIcms leitor, String file, Long idEmp,
			Long idEst,Long lote){

		List<HistoricoItens>  retorno = new ArrayList<HistoricoItens>();
	
		for (RegC100 nota : leitor.getRegsC100()) {	
			for (RegC170 pNF : nota.getProdutosNota()) {
				if(insereNotasTerceiros(leitor,nota,pNF, file , lote).getChaveDoc() != null) {
					retorno.add(insereNotasTerceiros(leitor,nota,pNF, file , lote));
				}
				
			}					
		}
		ParseDocXML parseDocXML = new ParseDocXML();
		File f = new File(file);
			try {
				for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
					for (Produtos p : doc.getProds()) {
                        if(insereNotasProprias(leitor,p, doc, lote).getChaveDoc() !=null) {
                        	retorno.add(insereNotasProprias(leitor,p, doc, lote));
                        }
						
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
	
		
			for (int i = 0; i < leitor.getRegsC400().size(); i++) {
				for (int z = 0; z < leitor.getRegsC400().get(i).getRegsC405().size(); z++) {
					for (int l = 0; l < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().size(); l++) {
						for (int m = 0; m < leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l)
								.getRegsC425().size(); m++) {
							
							if (insereReducoes(leitor, lote, i, z, l, m) != null) {
								retorno.add(insereReducoes(leitor, lote, i, z, l, m));
							}
							
						}
					}
				}
			}
		
		for (RegC860 regC860 : leitor.getRegsC860()) {
			try {
				for (DocumentoFiscalEltronico doc : parseDocXML.validaTipoDeParseNFE(f)) {
					if (regC860.getId() == idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor)) {
						
						for (Produtos p : doc.getProds()) {
							if (doc.getIdent().getModeloDoc().equals("59")) {
								retorno.add(insereCFes(leitor,regC860, file, p, doc, lote));	
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

    private Produto insereProdutosSped(LeitorEfdIcms leitor, Reg0200 prod , Long idEmp, Long idEst) {
    	Produto p = new Produto();
    
		

			p.setIdEmp(idEmp);
			p.setIdEst(idEst);
			p.setCodUtilizEstab(prod.getCodItem());
			p.setDescricao(prod.getDescrItem());
			p.setUnidadedeMedidaPadrao(prod.getUnidInv());
			p.setNcm(prod.getCodNcm());
			p.setCodigodeBarras(prod.getCodBarra());

			listaProdutos.add(prod.getCodItem());
			
		
		
    	return p;
    }
	
	private Produto insereProdutoXmlProprio(String file, Produtos prd,Long idEmp,Long idEst) {
		Produto p = new Produto();
				
		p.setIdEmp(idEmp);
		p.setIdEst(idEst);
		p.setCodUtilizEstab(prd.getCodItem());
		p.setDescricao(prd.getDescricao());
		p.setUnidadedeMedidaPadrao(prd.getUndComercial());
		p.setNcm(prd.getNcm());
		p.setCodigodeBarras(prd.getCodEanTrib());

		
	
		return p;
	}
	private HistoricoItens insereNotasProprias(LeitorEfdIcms leitor,Produtos p, DocumentoFiscalEltronico doc,Long lote) {
		 HistoricoItens retorno = new HistoricoItens();
		 
		 
	 if(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {
		 
		 if (!leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodSit().equals("02")) {
				
			 if (doc.getIdent().getModeloDoc().equals("55")) {
				 
			 if(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null){
				 

						retorno.setIdPaiLote(lote);

						if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {
							retorno.setIdPai(leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getId()-1);
						}
						if (leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()) != null) {
							retorno.setCodSitDoc(
									leitor.getMpNFporChave().get(doc.getIdent().getChaveeletronica()).getCodSit());
							
						}

						retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());

						if (p.getCfop().startsWith("1") || p.getCfop().startsWith("2")) {
							retorno.setOperacao("E");
						} else {
							retorno.setOperacao("S");
						}

						retorno.setEcfCx("");
						retorno.setDtDoc(UtilsEConverters.getStringParaData(doc.getIdent().getDataEmissao()));
						retorno.setNumItem(p.getNumItem());
						retorno.setCodItem(p.getCodItem());
						retorno.setQtde(BigDecimal.valueOf(Double.valueOf(p.getQtdComercial())));
						retorno.setUnd(p.getUndComercial());

						retorno.setVlUnit(BigDecimal.valueOf(Double.valueOf(p.getVlUnComerial())));
						retorno.setVlBruto(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));
						retorno.setDesconto(null);

						if (p.getVlItem() != null) {
							retorno.setVlLiq(BigDecimal.valueOf(Double.valueOf(p.getVlItem())));
						}else {
							retorno.setVlLiq(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));
						}

						retorno.setCfop(p.getCfop());

						if (p.getOrig() != null && p.getCst() != null) {
							retorno.setCst(p.getOrig().concat(p.getCst()));
						}

						if ((p.getAliqIcms() != null)) {
							retorno.setAliqIcms(BigDecimal.valueOf(Double.valueOf(p.getAliqIcms())));
						} else {
							retorno.setAliqIcms(BigDecimal.valueOf(0.0));
						}

						retorno.setCodMod(doc.getIdent().getModeloDoc());

						retorno.setDescricao(p.getDescricao());

						retorno.setNumDoc(doc.getIdent().getNumDoc());
						retorno.setChaveDoc(doc.getIdent().getChaveeletronica());

						retorno.setNome(doc.getDestinatario().getNome());

						String cpfCnpj = "";
						if(doc.getDestinatario().getCnpj() != null) {
							 cpfCnpj += doc.getDestinatario().getCnpj();
						}
						if(doc.getDestinatario().getCpf() != null) {
							 cpfCnpj += doc.getDestinatario().getCpf();
						}
						if(cpfCnpj != null) {
							retorno.setCpfCnpj(cpfCnpj);
						}
						

					}
				 }
			 }
	 }
		return retorno;
	}

	private HistoricoItens insereNotasTerceiros(LeitorEfdIcms leitor,RegC100 nota,RegC170 pNF , String file,Long lote) {
	
		    HistoricoItens retorno = new HistoricoItens();
			retorno.setIdPaiLote(lote);
			retorno.setIdPai(pNF.getIdPai()-1);

			retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());

			if (nota.getIndOper().equals("0")) {
				retorno.setOperacao("E");
			} else {
				retorno.setOperacao("S");
			}
			
			
			retorno.setEcfCx("");
			retorno.setDtDoc(nota.getDtDoc());
			retorno.setNumItem(pNF.getNumItem());
			retorno.setCodItem(pNF.getCodItem());
			retorno.setQtde(BigDecimal.valueOf(pNF.getQtd()));
			retorno.setUnd(pNF.getUnid());
			
			
			retorno.setVlUnit(BigDecimal.valueOf(pNF.getVlItem()/pNF.getQtd()));
			retorno.setVlBruto(BigDecimal.valueOf(pNF.getVlItem()));
			
			//Verificar esse set do Desconto
			retorno.setDesconto(BigDecimal.valueOf(0.0));
			
			retorno.setVlLiq(BigDecimal.valueOf(pNF.getVlItem()));
			
			retorno.setCfop(pNF.getCfop());
			retorno.setCst(pNF.getCstIcms());
			
			if((pNF.getAliqIcms() != null)) {
				retorno.setAliqIcms(BigDecimal.valueOf(pNF.getAliqIcms()));
			}else {
				retorno.setAliqIcms(BigDecimal.valueOf(0.0));
			}
			
			retorno.setCodSitDoc(nota.getCodSit());
			retorno.setCodMod(nota.getCodMod());
			
			if(leitor.getMpProdTerc().get(pNF.getCodItem()) != null) {
				retorno.setDescricao(leitor.getMpProdTerc().get(pNF.getCodItem()).getDescrItem());
			}
			
			
			retorno.setNumDoc(nota.getNumDoc());
			retorno.setChaveDoc(nota.getChvNfe());
			
			if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
				retorno.setNome(leitor.getMpParticipante().get(nota.getCodPart()).getNome());
			}
			
			String doc = "";
			
            if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
            	doc += leitor.getMpParticipante().get(nota.getCodPart()).getCnpj();            	
			}
			
            if(leitor.getMpParticipante().get(nota.getCodPart()) != null) {
            	doc += leitor.getMpParticipante().get(nota.getCodPart()).getCpf();           	
			}
            
            if(doc != null) {
            	retorno.setCpfCnpj(doc);
            }

		 return retorno;	

	}
	
	public HistoricoItens insereReducoes(LeitorEfdIcms leitor,Long lote, int i, int z, int l, int m) {
		EquipamentoEcfDao dao = new EquipamentoEcfDao();
		HistoricoItens retorno = new HistoricoItens();

							
		retorno.setIdPaiLote(lote);
		retorno.setCodMod(leitor.getRegsC400().get(i).getCodModelo());
		retorno.setIdPai(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPai()-1);
		retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());
		retorno.setOperacao("S");
		retorno.setCodSitDoc("");
		retorno.setNumItem("");
		retorno.setEcfCx(dao.buscaPorNumFab(leitor.getRegsC400().get(i).getNumSerieFabECF()).getNumECF());
		retorno.setDtDoc(leitor.getRegsC400().get(i).getRegsC405().get(z).getDtDoc());
		retorno.setCodItem(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem());
		retorno.setQtde(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getQtd()));
		retorno.setUnd(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getUnd());
		retorno.setVlUnit(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()/leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getQtd()));
		retorno.setVlLiq(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()));
		retorno.setVlBruto(BigDecimal.valueOf(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getVlItem()));
		retorno.setCfop(leitor.getMpC490().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPaiRedZ()).getCfop());
		retorno.setCst(leitor.getMpC490().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getIdPaiRedZ()).getCstIcms());
		if(leitor.getMpProdTerc().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem()) != null) {
			retorno.setDescricao(leitor.getMpProdTerc().get(leitor.getRegsC400().get(i).getRegsC405().get(z).getRegsC420().get(l).getRegsC425().get(m).getCodItem()).getDescrItem());
		}
		retorno.setNumDoc(leitor.getRegsC400().get(i).getRegsC405().get(z).getNumCOOFin());
		retorno.setAliqIcms(BigDecimal.valueOf(0.0));
		retorno.setDesconto(BigDecimal.valueOf(0.0));
		retorno.setChaveDoc("");
		retorno.setNome("");
		retorno.setCpfCnpj("");
										
		return retorno;
	}
	
	public HistoricoItens insereCFes(LeitorEfdIcms leitor,RegC860 regC860,String file,Produtos p,DocumentoFiscalEltronico doc, Long lote) {
		
		HistoricoItens retorno = new HistoricoItens();
			
		retorno.setIdPaiLote(lote);
		retorno.setIdPai(idPaiEquipCFe(doc.getIdent().getNumDoc(), leitor));
		retorno.setEmpresa(leitor.getRegs0000().get(0).getCnpj());
		retorno.setOperacao("S");
		retorno.setEcfCx(regC860.getNumSerieEquipSat());
		retorno.setDtDoc(regC860.getDtEmissao());
		retorno.setCodItem(p.getCodItem());
		retorno.setQtde(BigDecimal.valueOf(Double.valueOf(p.getQtdComercial())));
		retorno.setUnd(p.getVlUnComerial());
		
		retorno.setVlUnit(BigDecimal.valueOf(Double.valueOf(p.getVlUnComerial())));
		
		retorno.setVlBruto(BigDecimal.valueOf(Double.valueOf(p.getVlProduto())));
		
		if(p.getvDesc() != null) {
			retorno.setDesconto(BigDecimal.valueOf(Double.valueOf(p.getvDesc())));
		}else {
			retorno.setDesconto(BigDecimal.valueOf(0.0));
		}
		
		
		if(p.getVlItem() != null) {
			retorno.setVlLiq(BigDecimal.valueOf(Double.valueOf(p.getVlItem())));	
		}
		
		
		retorno.setCfop(p.getCfop());
		retorno.setCst(p.getOrig().concat(p.getCst()));
		retorno.setCodSitDoc("");
		if(p.getAliqIcms() != null) {
			retorno.setAliqIcms(BigDecimal.valueOf(Double.valueOf(p.getAliqIcms())));
		}else {
			
			retorno.setAliqIcms(BigDecimal.valueOf(0.0));
		}
		
		
		retorno.setCodMod(regC860.getCodModDocFiscal());
		retorno.setDescricao(p.getDescricao());
		retorno.setNumDoc(doc.getIdent().getNumDoc());
		retorno.setNumItem(p.getNumItem());
		retorno.setChaveDoc(doc.getIdent().getChaveeletronica());
		retorno.setNome(doc.getDestinatario().getNome());
		
		String cnpjCpf = "";
		if(doc.getDestinatario().getCnpj() != null) {
			cnpjCpf += doc.getDestinatario().getCnpj();
		}
		
		if(doc.getDestinatario().getCpf() != null) {
			cnpjCpf += doc.getDestinatario().getCnpj();
		}
		
		if(cnpjCpf != null) {
			retorno.setCpfCnpj(cnpjCpf);
		}
		
		
		
		return retorno;
	}
	
	
	public List<SaldoItensTotalizadoPorLote> getSaldoItensPorLote(LeitorEfdIcms leitor,Set<String> listaProdutos,List<HistoricoItens> histItem,
			Long lote){
		List<SaldoItensTotalizadoPorLote> retorno = new ArrayList<SaldoItensTotalizadoPorLote>();
		Double qtdeEntSum = 0.0;
		Double vlEntTotalSum = 0.0;
		Double qtdeSaiSum = 0.0;
		Double vlSaiTotalSum = 0.0;
		DecimalFormat df = new DecimalFormat("#,###.00");
		for(String codigo : listaProdutos){
			
			SaldoItensTotalizadoPorLote saldo = new SaldoItensTotalizadoPorLote();
		
			qtdeEntSum = histItem.stream()
					          .filter(c -> c.getCodItem().equals(codigo))
			                  .filter(c -> (c.getOperacao().equals("E")))
			                  .filter(c -> (c.getVlLiq() != null))
			                  .map(HistoricoItens:: getQtde).mapToDouble(BigDecimal::doubleValue).sum();		
			
			vlEntTotalSum = histItem.stream()
					.filter(c -> c.getCodItem().equals(codigo))
					.filter(c -> (c.getOperacao().equals("E")))
					.filter(c -> (c.getVlLiq() != null))
					.map(HistoricoItens:: getVlLiq).mapToDouble(BigDecimal::doubleValue).sum();

			
			qtdeSaiSum = histItem.stream().filter(c -> c.getCodItem().equals(codigo))
					.filter(c -> (c.getOperacao().equals("S")))
					.filter(c -> (c.getVlLiq() != null))
					.map(HistoricoItens:: getQtde).mapToDouble(BigDecimal::doubleValue).sum();
			vlSaiTotalSum = histItem.stream().filter(c -> c.getCodItem().equals(codigo))
					.filter(c -> (c.getOperacao().equals("S")))
					.filter(c -> (c.getVlLiq() != null))
					.map(HistoricoItens::getVlLiq).mapToDouble(BigDecimal::doubleValue).sum();
			
			
			saldo.setCnpj(leitor.getRegs0000().get(0).getCnpj());
			saldo.setAno(String.valueOf(leitor.getRegs0000().get(0).getDtIni().getYear()));
			saldo.setMes(String.valueOf(leitor.getRegs0000().get(0).getDtIni().getMonthValue()));
			
			if(leitor.getMpProdTerc().get(codigo) != null) {
				saldo.setIdCodItem(leitor.getMpProdTerc().get(codigo).getId());
			}
			
			String descr = "";
			if(leitor.getMpProdTerc().get(codigo) != null) {
				descr += leitor.getMpProdTerc().get(codigo).getDescrItem();
			}else if(mpProdXmlProprio.get(codigo) != null) {
				descr += mpProdXmlProprio.get(codigo).getDescricao();
			}
			
			if(descr != null){
				saldo.setDescricao(descr);
			}
			
			saldo.setIdPaiLote(lote);
			saldo.setCodItem(codigo);
			
//			BigDecimal saldoInicial = BigDecimal.valueOf(getSaldoIncial(
//					leitor.getRegs0000().get(0).getCnpj(),
//					codigo,
//					leitor.getRegs0000().get(0).getDtIni().getMonthValue(), 
//					leitor.getRegs0000().get(0).getDtIni().getYear()));
			
			
			saldo.setSaldoIni(BigDecimal.valueOf(0));
			
			saldo.setTotQtdeEnt(BigDecimal.valueOf(qtdeEntSum));
			saldo.setTotVlEnt(BigDecimal.valueOf(vlEntTotalSum));
			
			saldo.setTotQtdeSai(BigDecimal.valueOf(qtdeSaiSum));
			saldo.setTotVlSai(BigDecimal.valueOf(vlSaiTotalSum));
			
			saldo.setSaldoFin(BigDecimal.valueOf(qtdeEntSum-qtdeSaiSum));
			
			if(saldo != null) {
				retorno.add(saldo);	
			}
			
		}
		
		return retorno;
	}
	
	
	public  String getMes(int mes) {	       
        if(mes == 1) {
        	mes = 12;
        }else {
        	mes = mes - 1;
        }        
        return String.valueOf(mes);
	}
	
	
	public  String getAno(int mes, int ano) {
		if(mes == 1) {
			ano = ano - 1;
		}
		return String.valueOf(ano);
	}
	
	public  Double getSaldoIncial(String cnpj,String codItem,int mes, int ano) {
	    SaldoItensTotalizadoPorLoteDao saldoDao = new SaldoItensTotalizadoPorLoteDao();
		Double dez = saldoDao.listaTodos().stream()
			    .filter(c -> c.getCnpj().equals(cnpj))
			    .filter(c -> c.getMes().equals(getMes(1)))
			    .filter(c -> c.getAno().equals(getAno(1, ano)))
			    .filter(c -> c.getCodItem().equals(codItem))		   
			    .map(SaldoItensTotalizadoPorLote :: getSaldoFin).mapToDouble(BigDecimal::doubleValue).sum();	
		return null;
	}
	
	
	public Set<String> getListaProdutos() {
		return listaProdutos;
	}

}
