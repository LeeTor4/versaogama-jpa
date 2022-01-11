package com.tor4.model.cadastro;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_produto")
public class Produto  implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 5277288841856393069L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="est_id")
	private Long idPai;
	private Long idEmp;
	private Long idEst;
	private String descricao;
	private String codUtilizEstab; 
	private String ncm;
	private String UnidadedeMedidaPadrao;
	private String codigodeBarras;
	private Boolean desativado;
	private String codigoTIPI;
	private String cest;
	
	public Produto() {
		
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
	
	public Long getIdEmp() {
		return idEmp;
	}
	public void setIdEmp(Long idEmp) {
		this.idEmp = idEmp;
	}
	public Long getIdEst() {
		return idEst;
	}
	public void setIdEst(Long idEst) {
		this.idEst = idEst;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCodUtilizEstab() {
		return codUtilizEstab;
	}
	public void setCodUtilizEstab(String codUtilizEstab) {
		this.codUtilizEstab = codUtilizEstab;
	}
	public String getNcm() {
		return ncm;
	}
	public void setNcm(String ncm) {
		this.ncm = ncm;
	}
	public String getUnidadedeMedidaPadrao() {
		return UnidadedeMedidaPadrao;
	}
	public void setUnidadedeMedidaPadrao(String unidadedeMedidaPadrao) {
		UnidadedeMedidaPadrao = unidadedeMedidaPadrao;
	}
	public String getCodigodeBarras() {
		return codigodeBarras;
	}
	public void setCodigodeBarras(String codigodeBarras) {
		this.codigodeBarras = codigodeBarras;
	}
	public Boolean getDesativado() {
		return desativado;
	}
	public void setDesativado(Boolean desativado) {
		this.desativado = desativado;
	}
	public String getCodigoTIPI() {
		return codigoTIPI;
	}
	public void setCodigoTIPI(String codigoTIPI) {
		this.codigoTIPI = codigoTIPI;
	}
	public String getCest() {
		return cest;
	}
	public void setCest(String cest) {
		this.cest = cest;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codUtilizEstab, idEmp);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(codUtilizEstab, other.codUtilizEstab) && Objects.equals(idEmp, other.idEmp);
	}

}
