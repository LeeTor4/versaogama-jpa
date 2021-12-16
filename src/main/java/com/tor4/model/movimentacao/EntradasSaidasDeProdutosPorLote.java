package com.tor4.model.movimentacao;

public class EntradasSaidasDeProdutosPorLote {

	private Long   id;
	private Long   idPai;
	private Long   idCodItem;
	private String operacao;
	private String cnpj;
	private String descricao;
	private String und;
	private String ano;
	private String mes;
	private String codItem;
	private String codAntItem;
	
	private Double saldoInicial;
	private Double totQtdeEnt;
	private Double totVlItemEnt;
	
	private Double totQtdeSai;
	private Double totVlItemSai;
	private Double saldoFinal;
	
	
}
