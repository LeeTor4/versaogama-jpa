package com.tor4.model.cadastro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tor4.model.movimentacao.NotaFiscal;
import com.tor4.model.movimentacao.ProdutoNotaFiscal;

@Entity
@Table(name = "tb_estabelecimento")
public class Estabelecimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4937550872307764323L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="emp_id")
	private Long idPai;
	private String cnpj;
	private String ie;
	private String nome;
	private String nmFantasia;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_end")
    private Endereco end = new Endereco();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="est_id")
	private List<Produto> produtos = new ArrayList<Produto>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="est_id")
	private List<NotaFiscal> notasFiscais = new ArrayList<NotaFiscal>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="est_id")
	private List<ProdutoNotaFiscal> produtosNota = new ArrayList<ProdutoNotaFiscal>();


	public Estabelecimento() {

	}

	public Estabelecimento( String cnpj, String nome, String nmFantasia,
			Endereco end, Empresa emp) {
		super();

		this.cnpj = cnpj;
		this.nome = nome;
		this.nmFantasia = nmFantasia;
		this.end = end;

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPai() {
		return idPai;
	}

	public void setIdPai(Long idPai) {
		this.idPai = idPai;
	}



	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNmFantasia() {
		return nmFantasia;
	}

	public void setNmFantasia(String nmFantasia) {
		this.nmFantasia = nmFantasia;
	}

	public Endereco getEnd() {
		return end;
	}

	public void setEnd(Endereco end) {
		this.end = end;
	}

    
	public List<Produto> getProduto() {
		return produtos;
	}

	public void setProduto(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public void adicionaProduto(Produto produto) {
		this.produtos.add(produto);
	}
	
	public List<NotaFiscal> getNotasFiscais() {
		return notasFiscais;
	}

	public void setNotasFiscais(List<NotaFiscal> notasFiscais) {
		this.notasFiscais = notasFiscais;
	}

	public void adicionaNotasSaidas(NotaFiscal notasFiscais) {
		this.notasFiscais.add(notasFiscais);
	}

	public List<ProdutoNotaFiscal> getProdNota() {
		return produtosNota;
	}

	public void setProdNota(List<ProdutoNotaFiscal> produtosNota) {
		this.produtosNota = produtosNota;
	}
	
	public void adicionaProdNota(ProdutoNotaFiscal produtosNota) {
		this.produtosNota.add(produtosNota);
	}
	
	
}
