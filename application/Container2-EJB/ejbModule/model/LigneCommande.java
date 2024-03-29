package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ligne_commande database table.
 * 
 */
@Entity
@Table(name="ligne_commande")
@NamedQuery(name="LigneCommande.findAll", query="SELECT l FROM LigneCommande l")
public class LigneCommande implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_ligne_commande")
	private Integer idLigneCommande;

	private Integer quantite;

	//bi-directional many-to-one association to Produit
	@ManyToOne
	@JoinColumn(name="id_produit")
	private Produit produit;

	//bi-directional one-to-one association to LigneLivraison
	@OneToOne
	@JoinColumn(name="id_ligne_commande", referencedColumnName="id_ligne_commande")
	private LigneLivraison ligneLivraison;

	//bi-directional many-to-one association to Commande
	@ManyToOne
	@JoinColumn(name="id_commande")
	private Commande commande;

	public LigneCommande() {
	}

	public Integer getIdLigneCommande() {
		return this.idLigneCommande;
	}

	public void setIdLigneCommande(Integer idLigneCommande) {
		this.idLigneCommande = idLigneCommande;
	}

	public Integer getQuantite() {
		return this.quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

	public Produit getProduit() {
		return this.produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public LigneLivraison getLigneLivraison() {
		return this.ligneLivraison;
	}

	public void setLigneLivraison(LigneLivraison ligneLivraison) {
		this.ligneLivraison = ligneLivraison;
	}

	public Commande getCommande() {
		return this.commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

}