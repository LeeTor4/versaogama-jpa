package crud;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import com.cnpj.servico.ServicoCEP;
import com.tor4.dao.cadastro.EmpresaDao;
import com.tor4.handler.ImportaEfdIcms;
import com.tor4.model.cadastro.Empresa;
import com.tor4.model.cadastro.Endereco;
import com.tor4.model.cadastro.Estabelecimento;
import com.tor4.model.cadastro.Produto;
import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ProdutoNotaFiscal;

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

		
		String ano = "2021";
		String emp = "SELLENE";
		String estab = "SAO_MATEUS";
		
        String anomes1 = ano.concat("01").concat(".txt");
		
        Path x1 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\XML").concat("\\jan"));
	    Path p1 = Paths.get("D:\\EMPRESAS".concat("\\").concat(emp).concat("\\").concat(estab).concat("\\SPED").concat("\\").concat(ano).concat("\\").concat(anomes1));
		ImportaEfdIcms lerEfd = new ImportaEfdIcms();

		LeitorEfdIcms leitor = new LeitorEfdIcms();		
		leitor.leitorSpedFiscal(p1, null,1L,0L);
		
		List<Produto> prods = lerEfd.getProduto(leitor);
		List<NotaFiscal> docs = lerEfd.getNotasFiscais(leitor, x1.toString());
		
		for(Produto p : prods){
			sao.adicionaProduto(p);
		}
		for(NotaFiscal nota :  docs){			
			sao.adicionaNotasSaidas(nota);				
		}

		sao.setNotasFiscais(docs);

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
