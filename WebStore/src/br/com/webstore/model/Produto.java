//#if ${Produto} == "T"

package br.com.webstore.model;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author webstore
 *
 */
@Entity
@Table(name = "Produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idProduto")
	private Integer id;

	@OneToOne(cascade = {})
	@JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria", nullable = false)
	private Categoria categoria;

	@ManyToOne(cascade = {})
	@JoinColumn(name = "idUnidadeMedida", referencedColumnName = "idUnidadeMedida", nullable = false)
	private UnidadeMedida unidadeMedida;

	@Column(name = "nrProdutoCodigo")
	private Integer numero;

	@Column(name = "dsProduto")
	private String descricao;

	@Column(name = "vlProduto", nullable = false)
	private BigDecimal valor;

	/**
	 * Return the id
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * Setter the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer codigo) {
		this.id = codigo;
	}
	
	/**
	 * Return the categoria
	 * 
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return this.categoria;
	}
	
	/**
	 * Setter the categoria
	 * 
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	/**
	 * Return the unidadeMedida
	 * 
	 * @return the unidadeMedida
	 */
	public UnidadeMedida getUnidadeMedida() {
		return this.unidadeMedida;
	}
	
	/**
	 * Setter the unidadeMedida
	 * 
	 * @param unidadeMedida
	 *            the unidadeMedida to set
	 */
	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	
	/**
	 * Return the numero
	 * 
	 * @return the numero
	 */
	public Integer getNumero() {
		return this.numero;
	}
	
	/**
	 * Setter the numero
	 * 
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	/**
	 * Return the descricao
	 * 
	 * @return the descricao
	 */
	public String getDescricao() {
		return this.descricao;
	}
	
	/**
	 * Setter the descricao
	 * 
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	/**
	 * Return the valor
	 * 
	 * @return the valor
	 */
	public BigDecimal getValor() {
		return this.valor;
	}
	
	/**
	 * Setter the valor
	 * 
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
//#endif