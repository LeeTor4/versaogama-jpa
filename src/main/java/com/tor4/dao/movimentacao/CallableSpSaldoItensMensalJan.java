package com.tor4.dao.movimentacao;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.tor4.model.movimentacao.SaldosMensais;

public class CallableSpSaldoItensMensalJan implements Callable<List<SaldosMensais>>{

	 private String codigo;
	 private String ano;
	 private String cnpj;
	 
	 public CallableSpSaldoItensMensalJan(String codigo,String ano,String cnpj) { 
		 this.codigo = codigo;
		 this.ano = ano;
		 this.cnpj = cnpj;
	 }
	
	 @Override
	 public List<SaldosMensais> call() throws Exception {
				
			 
	   List<SaldosMensais> jan = SpSaldosMensais.spSaldosMensais(codigo, "2022", cnpj).stream()
				 .filter(c -> c.getAno().equals(ano))
				 .filter(c -> c.getMes().equals("1"))
				 .filter(c -> c.getCodItem().equals(codigo))
				 .collect(Collectors.toList());
		 
		return jan;
	}

	
}
