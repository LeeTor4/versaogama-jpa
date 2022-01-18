package com.tor4.dao.movimentacao;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.tor4.model.movimentacao.SaldosMensais;

public class CallableSpSaldoItensMensalNov implements Callable<List<SaldosMensais>>{

	 private String codigo;
	 private String ano;
	 private String cnpj;
	 
	 public CallableSpSaldoItensMensalNov(String codigo,String ano,String cnpj) { 
		 this.codigo = codigo;
		 this.ano = ano;
		 this.cnpj = cnpj;
	 }
	
	 @Override
	 public List<SaldosMensais> call() throws Exception {
				
			 
	   List<SaldosMensais> mar = SpSaldosMensais.spSaldosMensais(codigo, "2022", cnpj).stream()
				 .filter(c -> c.getAno().equals(ano))
				 .filter(c -> c.getMes().equals("11"))
				 .filter(c -> c.getCodItem().equals(codigo))
				 .collect(Collectors.toList());
		 
		return mar;
	}

	
}
