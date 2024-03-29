package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the commande database table.
 * 
 */
@Entity
@NamedQuery(name="Commande.findAll", query="SELECT c FROM Commande c")
public class Commande implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_commande")
	private Integer idCommande;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_enregistrement")
	private Date dateEnregistrement;

	//bi-directional many-to-one association to Client
	@ManyToOne
	@JoinColumn(name="id_client")
	private Client client;

	//bi-directional one-to-one association to Paiement
	@OneToOne
	@JoinColumn(name="id_commande", referencedColumnName="id_commande")
	private Paiement paiement;

	//bi-directional one-to-one association to Livraison
	@OneToOne
	@JoinColumn(name="id_commande")
	private Livraison livraison;

	//bi-directional many-to-one association to LigneCommande
	@OneToMany(mappedBy="commande")
	private List<LigneCommande> ligneCommandes;

	public Commande() {
	}

	public Integer getIdCommande() {
		return this.idCommande;
	}

	public void setIdCommande(Integer idCommande) {
		this.idCommande = idCommande;
	}

	public Date getDateEnregistrement() {
		return this.dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Paiement getPaiement() {
		return this.paiement;
	}

	public void setPaiement(Paiement paiement) {
		this.paiement = paiement;
	}

	public Livraison getLivraison() {
		return this.livraison;
	}

	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
	}

	public List<LigneCommande> getLigneCommandes() {
		return this.ligneCommandes;
	}

	public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
		this.ligneCommandes = ligneCommandes;
	}

	public LigneCommande addLigneCommande(LigneCommande ligneCommande) {
		getLigneCommandes().add(ligneCommande);
		ligneCommande.setCommande(this);

		return ligneCommande;
	}

	public LigneCommande removeLigneCommande(LigneCommande ligneCommande) {
		getLigneCommandes().remove(ligneCommande);
		ligneCommande.setCommande(null);

		return ligneCommande;
	}

}