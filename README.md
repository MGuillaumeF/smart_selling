# Présentation de Smart Selling

## Contexte
Le projet **Smart Selling** est un projet de **site web** pour une entreprise, il a pour objectif l'enregistrement, l'expédition et la facturation de commandes.  

### 3 différents acteurs
**Commercial :** L'enregistrement des commandes  
**Responsable des stock :** L'expédition des commandes  
**Comptable :** Facturation des commandes  

### Diagramme des cas d'utilisation
![](https://github.com/MGuillaumeF/smart_selling/blob/master/UseCaseDiagram.png)

## Technologie utilisées
Les technologies choisi sont : 
1. Le langage de programmation pour le back-end : **JAVA/JEE**
1. Le langage de programmation pour le front-end : **JavaScript**
1. La base de données relationnel : **MySQL**
1. Le serveur Web : **WildFly 10**
1. Le système d'exploitation : **Ubuntu 16.04LTS**

## Base de données
La base de données MySQL est accessible via une couche de persistance Java qui permet de faire des commit dans la base de données à partir de classes Java représentant les entités de la base de données
![](https://github.com/MGuillaumeF/smart_selling/blob/master/Database.png)

Pour plus d'informations voir le Wiki
