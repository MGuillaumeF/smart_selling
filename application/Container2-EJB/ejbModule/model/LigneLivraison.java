package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ligne_livraison database table.
 * 
 */
@Entity
@Table(name="ligne_livraison")
@NamedQuery(name="LigneLivraison.findAll", query="SELECT l FROM LigneLivraison l")
public class LigneLivraison implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_ligne_livraison")
	private Integer idLigneLivraison;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_envoi")
	private Date dateEnvoi;

	//bi-directional many-to-one association to Livraison
	@ManyToOne
	@JoinColumn(name="id_livraison")
	private Livraison livraison;

	//bi-directional one-to-one association to LigneCommande
	@OneToOne(mappedBy="ligneLivraison")
	private LigneCommande ligneCommande;

	@Column(name="id_ligne_commande")
	private Integer idLigneCommande;
	
	
	public LigneLivraison() {
	}

	public Integer getIdLigneLivraison() {
		return this.idLigneLivraison;
	}

	public void setIdLigneLivraison(Integer idLigneLivraison) {
		this.idLigneLivraison = idLigneLivraison;
	}

	public Date getDateEnvoi() {
		return this.dateEnvoi;
	}

	public void setDateEnvoi(Date dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public Livraison getLivraison() {
		return this.livraison;
	}

	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
	}

	public LigneCommande getLigneCommande() {
		return this.ligneCommande;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	public Integer getIdLigneCommande() {
		return this.idLigneCommande;
	}

	public void setIdLigneCommande(Integer idLigneCommande) {
		this.idLigneCommande = idLigneCommande;
	}

}