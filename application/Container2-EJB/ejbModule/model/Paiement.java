package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the paiement database table.
 * 
 */
@Entity
@NamedQuery(name="Paiement.findAll", query="SELECT p FROM Paiement p")
public class Paiement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_paiement")
	private Integer idPaiement;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private Float prixTTC;
	
	@Column(name="id_commande")
	private Integer idCommande;

	//bi-directional one-to-one association to Commande
	@OneToOne(mappedBy="paiement")
	private Commande commande;

	public Paiement() {
	}

	public Integer getIdPaiement() {
		return this.idPaiement;
	}

	public void setIdPaiement(Integer idPaiement) {
		this.idPaiement = idPaiement;
	}
	public Integer getIdCommande() {
		return this.idCommande;
	}

	public void setIdCommande(Integer idCommande) {
		this.idCommande = idCommande;
	}
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getPrixTTC() {
		return this.prixTTC;
	}

	public void setPrixTTC(Float prixTTC) {
		this.prixTTC = prixTTC;
	}

	public Commande getCommande() {
		return this.commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

}