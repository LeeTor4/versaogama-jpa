package com.tor4.model.movimentacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_saldos_mensais")
public class SaldosMensais {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;
	private String cnpj;
	private String codItem;
	private String descricao;
	private String ano;
	private String mes;
	private Double totQtdeEnt;
	private Double totVlEnt;
	private Double totQteSai;
	private Double totVlSai;
	private Double saldo;
	
	
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCodItem() {
		return codItem;
	}
	public void setCodItem(String codItem) {
		this.codItem = codItem;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public Double getTotQtdeEnt() {
		return totQtdeEnt;
	}
	public void setTotQtdeEnt(Double totQtdeEnt) {
		this.totQtdeEnt = totQtdeEnt;
	}
	public Double getTotQteSai() {
		return totQteSai;
	}
	public void setTotQteSai(Double totQteSai) {
		this.totQteSai = totQteSai;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public Double getTotVlEnt() {
		return totVlEnt;
	}
	public void setTotVlEnt(Double totVlEnt) {
		this.totVlEnt = totVlEnt;
	}
	public Double getTotVlSai() {
		return totVlSai;
	}
	public void setTotVlSai(Double totVlSai) {
		this.totVlSai = totVlSai;
	}
	
    
}
