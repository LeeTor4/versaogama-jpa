package crud;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cnpj.servico.ServicoCEP;
import com.tor4.dao.cadastro.EmpresaDao;
import com.tor4.handler.ImportaEfdIcms;
import com.tor4.model.cadastro.Empresa;
import com.tor4.model.cadastro.Endereco;
import com.tor4.model.cadastro.EquipamentoECF;
import com.tor4.model.cadastro.Estabelecimento;
import com.tor4.model.cadastro.Produto;
import com.tor4.model.movimentacao.ItensMovDiario;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ReducaoZ;
import com.tor4.model.movimentacao.TotParciaisRDZ;

import modulos.efdicms.manager.LeitorEfdIcms;

public class CadastroEmpresa {

	public static void main(String[] args) throws Exception {

		LocalDate agora = LocalDate.now();

		EmpresaDao dao = new EmpresaDao();

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Sellene Comercio e Representações Ltda");
		empresa.setNmFantasia("Sellene Matriz");
		empresa.setCnpjBase("05329222");
		empresa.setDtIniAtiv(agora);

	
		Endereco end1 = new Endereco();
		
		com.cnpj.dominio.Endereco ender1 = ServicoCEP.buscaEnderecoPeloCEP("60140140");
		
		end1.setCep(ender1.getCep());
		end1.setNmLogradouro(ender1.getLogradouro());
		end1.setNumLogradouro("205");
		end1.setBairro(ender1.getBairro());
		end1.setCodMun(ender1.getIbge().substring(2,7));
		end1.setNmMun(ender1.getLocalidade());
		end1.setCodUf(ender1.getIbge().substring(0,2));
		end1.setUf(ender1.getUf());
		
		
		Endereco end2 = new Endereco();
        com.cnpj.dominio.Endereco ender2 = ServicoCEP.buscaEnderecoPeloCEP("60115220");
		
        end2.setCep(ender2.getCep());
        end2.setNmLogradouro(ender2.getLogradouro());
        end2.setNumLogradouro("1253");
        end2.setBairro(ender2.getBairro());
        end2.setCodMun(ender2.getIbge().substring(2,7));
        end2.setNmMun(ender2.getLocalidade());
		end2.setCodUf(ender2.getIbge().substring(0,2));
		end2.setUf(ender2.getUf());

		Endereco end3 = new Endereco();
        com.cnpj.dominio.Endereco ender3 = ServicoCEP.buscaEnderecoPeloCEP("60175047");
		
        end3.setCep(ender3.getCep());
        end3.setNmLogradouro(ender3.getLogradouro());
        end3.setNumLogradouro("5753");
        end3.setBairro(ender3.getBairro());
        end3.setCodMun(ender3.getIbge().substring(2,7));
        end3.setNmMun(ender3.getLocalidade());
        end3.setCodUf(ender3.getIbge().substring(0,2));
        end3.setUf(ender3.getUf());

		Endereco end4 = new Endereco();
		com.cnpj.dominio.Endereco ender4 = ServicoCEP.buscaEnderecoPeloCEP("60160230");
			
		end4.setCep(ender4.getCep());
		end4.setNmLogradouro(ender4.getLogradouro());
		end4.setNumLogradouro("1233");
		end4.setBairro(ender4.getBairro());
		end4.setCodMun(ender4.getIbge().substring(2,7));
		end4.setNmMun(ender4.getLocalidade());
		end4.setCodUf(ender4.getIbge().substring(0,2));
		end4.setUf(ender4.getUf());

		Endereco end5 = new Endereco();
		com.cnpj.dominio.Endereco ender5 = ServicoCEP.buscaEnderecoPeloCEP("60140140");
		
		end5.setCep(ender5.getCep());
		end5.setNmLogradouro(ender5.getLogradouro());
		end5.setNumLogradouro("195");
		end5.setBairro(ender5.getBairro());
		end5.setCodMun(ender5.getIbge().substring(2,7));
		end5.setNmMun(ender5.getLocalidade());
		end5.setCodUf(ender5.getIbge().substring(0,2));
		end5.setUf(ender5.getUf());

		Estabelecimento matriz = new Estabelecimento( "05329222000176",
				"Sellene Comercio e Representações Ltda", "Matriz", end1, empresa);

		Estabelecimento mega = new Estabelecimento( "05329222000419",
				"Sellene Comercio e Representações Ltda", "Megadiet", end2, empresa);

		Estabelecimento sao = new Estabelecimento("05329222000761",
				"Sellene Comercio e Representações Ltda", "Sao Mateus", end3, empresa);

		Estabelecimento harm = new Estabelecimento( "05329222000842",
				"Sellene Comercio e Representações Ltda", "Harmony", end4, empresa);

		Estabelecimento loja3 = new Estabelecimento( "05329222000338",
				"Sellene Comercio e Representações Ltda", "Delivery", end5, empresa);

		
		List<EquipamentoECF> ecfsMegadiet = new ArrayList<EquipamentoECF>();
		EquipamentoECF ecf1 = new EquipamentoECF();
		ecf1.setCodModDocFiscal("2D");
		ecf1.setModeloEquip("ECF"); 
		ecf1.setNumSerieFabECF("BE090910100010021930");
		ecf1.setNumECF("3");
		ecfsMegadiet.add(ecf1);
		
		EquipamentoECF ecf2 = new EquipamentoECF();
		ecf2.setCodModDocFiscal("2D");
		ecf2.setModeloEquip("ECF"); 
		ecf2.setNumSerieFabECF("BE091210100011203011");
		ecf2.setNumECF("4");
		ecfsMegadiet.add(ecf2);
		
		EquipamentoECF ecf3 = new EquipamentoECF();
		ecf3.setCodModDocFiscal("2D");
		ecf3.setModeloEquip("ECF"); 
		ecf3.setNumSerieFabECF("BE091410100011250892");
		ecf3.setNumECF("6");
		ecfsMegadiet.add(ecf3);
		
		
		List<EquipamentoECF> ecfsLoja03 = new ArrayList<EquipamentoECF>();
		EquipamentoECF ecf1_loja03 = new EquipamentoECF();
		ecf1_loja03.setCodModDocFiscal("2D");
		ecf1_loja03.setModeloEquip("ECF"); 
		ecf1_loja03.setNumSerieFabECF("1234");
		ecf1_loja03.setNumECF("1");
		ecfsLoja03.add(ecf1_loja03);
		
		EquipamentoECF ecf2_loja03 = new EquipamentoECF();
		ecf2_loja03.setCodModDocFiscal("2D");
		ecf2_loja03.setModeloEquip("ECF"); 
		ecf2_loja03.setNumSerieFabECF("4321");
		ecf2_loja03.setNumECF("2");
		ecfsLoja03.add(ecf2_loja03);
		
		
		EquipamentoECF ecf3_loja03 = new EquipamentoECF();
		ecf3_loja03.setCodModDocFiscal("2D");
		ecf3_loja03.setModeloEquip("ECF"); 
		ecf3_loja03.setNumSerieFabECF("BE0107SC56000035178");
		ecf3_loja03.setNumECF("3");
		ecfsLoja03.add(ecf3_loja03);
		
		EquipamentoECF ecf4_loja03 = new EquipamentoECF();
		ecf4_loja03.setCodModDocFiscal("2D");
		ecf4_loja03.setModeloEquip("ECF"); 
		ecf4_loja03.setNumSerieFabECF("BE0107SC56000035180");
		ecf4_loja03.setNumECF("4");
		ecfsLoja03.add(ecf4_loja03);
		
		EquipamentoECF ecf5_loja03 = new EquipamentoECF();
		ecf5_loja03.setCodModDocFiscal("2D");
		ecf5_loja03.setModeloEquip("ECF"); 
		ecf5_loja03.setNumSerieFabECF("BE051175610000105333");
		ecf5_loja03.setNumECF("5");
		ecfsLoja03.add(ecf5_loja03);
		
		EquipamentoECF ecf6_loja03 = new EquipamentoECF();
		ecf6_loja03.setCodModDocFiscal("2D");
		ecf6_loja03.setModeloEquip("ECF"); 
		ecf6_loja03.setNumSerieFabECF("BE051175610000105336");
		ecf6_loja03.setNumECF("6");
		ecfsLoja03.add(ecf6_loja03);
		
		
		
		
		
		loja3.setEquipEcf(ecfsLoja03);
		mega.setEquipEcf(ecfsMegadiet);

		
		
		

		
//		String ano = "2019";
//		String emp = "SELLENE";
//		String estab = "MEGADIET";
//		
//      String anomes1 = ano.concat("01").concat(".txt");
//		
//      Path x1 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jan"));
//	    Path p1 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
//		ImportaEfdIcms lerEfd = new ImportaEfdIcms();
//
//		LeitorEfdIcms leitor = new LeitorEfdIcms();		
//		leitor.leitorSpedFiscal(p1, null,1L,0L);
//		
//		List<Produto> prods = lerEfd.getProduto(leitor);
//		List<NotaFiscal> docs = lerEfd.getNotasFiscais(leitor, x1.toString());
//		List<ReducaoZ> reducoes = lerEfd.getReducoes(leitor);
//		
//		
//		for(Produto p : prods){
//			loja3.adicionaProduto(p);
//		}
//		for(NotaFiscal nota :  docs){			
//			loja3.adicionaNotasSaidas(nota);				
//		}
//        
//		for (EquipamentoECF equip : mega.getEquipEcf()) {
//			for (ReducaoZ rdz : reducoes) {
//				System.out.println(rdz.getId_ecf()+"|"+rdz.getNumCOO());
//				equip.adicionaReducoes(rdz);
//			}
//
//		}
//	
//		
//		mega.setNotasFiscais(docs);
		
		
		
		empresa.adicionaEstab(matriz);		
		empresa.adicionaEstab(mega);
		empresa.adicionaEstab(sao);
		empresa.adicionaEstab(harm);
		empresa.adicionaEstab(loja3);
		dao.adiciona(empresa);
		
		
		
		
		
		
		
		
		
		
		
		
		
		 
//		File diretorio  = new File("D:\\XML");
//		String operacao = "S";
//		SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
//		SXSSFSheet  sheet = workbook.createSheet("Notas Saidas");
//		String dest     = "D:\\Matriz_Sai_201501.xlsx";
//		RelacaoNotasFiscais relacao = new RelacaoNotasFiscais();
//		
//		ParseDocXML parse = new ParseDocXML();
//		 
//		try {
//			relacao.exporaNotasFiscais(relacao.relacaoNotasFiscais(diretorio,operacao), workbook, sheet, dest);
//			
//			//parse.validaTipoDeParseNFE(diretorio);
//		} catch (IOException | JAXBException e) {
//		
//			e.printStackTrace();
//		}
		
		
		
		
		
		
		
		
//		Entidade entidade = ServicoCnpj.buscaInformacoesPeloCnpj("05329222000176");
//		System.out.println(entidade.getNome() +" = > "+ entidade.getNaturezaJuridica());
//		
//		List<AtividadesSecundarias> ativsSecundarias = entidade.getAtivsSecundarias();
//		for(AtividadesSecundarias atv : ativsSecundarias) {
//			System.out.println(atv.getAtividadePrincipal());
//		}
		
	}
}
