/*jslint browser  :  true, indent  :  2*/
/*global Audio, clearInterval, clearTimeout, document, event, FileReader, FormData, history, Image, location, name, navigator, Option, screen, sessionStorage, setInterval, setTimeout, Storage, XMLHttpRequest */
/**
 * @file object_table
 * @namespace object_table
 */
try {
	if (module !== undefined) {
		var document = require('html-element').document;
	}
} catch (e) {
	
}

 
 
 /**
* @class PartTableau
* @memberof object_table
* @description Class de création d'objet THEAD|TBODY|TFOOT pour tableau HTML
* @param {Object} obj Objet descriptif de la partie à créer  avec les attributs suivants :
 <ul>
  <li><u><b>part</b></u> : <i>String</i> Type de partie à créer (THEAD|TBODY|TFOOT)</li>
  <li><u><b>id</b></u> : <i>String</i> Id de la partie de tableau</li>
  <li><u><b>class</b></u> : <i>String</i> Class de la partie de tableau</li>
 </ul>
* @returns {Object} part;
*/
function PartTableau(obj) {
  'use strict';
  var part, i, ligne, tdoth, cell, retour;
  if (obj.part) {
    obj.part = obj.part.toUpperCase().trim();
    if (obj.part === 'THEAD' || obj.part === 'TBODY' || obj.part === 'TFOOT') {
      part = document.createElement(obj.part);
      part.ligne = [];
      if (obj.className) {
        part.className = obj.className;
      }
      if (obj.id) {
        part.id = obj.id;
      }
      /**
      * @function addLine
      * @description Fonction d'ajout de ligne dans cette partie du tableau HTML
      * @memberof object_table.PartTableau
      * @param {Object} obj Descriptif de la ligne de la forme
      <ul>
        <li><b><u>id</u></b>: <i>String</i> id de la ligne (tr)</li>
        <li><b><u>class</u></b>: <i>String</i> class de la ligne (tr)</li>
        <li><b><u>cells</u></b>: <i>Array</i> Contenu de la ligne cellule par cellule chaque case étant un objet de la forme : <br/>
          {<br/>
          id : <i>String</i> Id de la cellule,<br/>
          class : <i>String</i> Class de la cellule,<br/>
          type : <i>String</i> choix du tag entre 'td' et 'th',<br/>
          sizeX : <i>String</i> Nombre de colonne occupées par la cellule,<br/>
          sizeY : <i>String</i> Nombre de ligne occupées par la cellule,<br/>
          textContent : <i>String</i> Contenu de la cellule en text plat,<br/>
          innerHTML : <i>String</i> Contenu de la cellule en text plat<br/>
          }
        </li>
      </ul>
      * @returns {undefined} undefined
      */
      part.addLine = function (obj) {
        ligne = document.createElement('tr');
        if (obj.className) {
          ligne.className = obj.className;
        }
        if (obj.id) {
          ligne.id = obj.id;
        }
        if (obj.cells) {
          for (i = 0; i < obj.cells.length; i += 1) {
            cell = null;
            if (obj.cells[i]) {
              cell = obj.cells[i];
              if (cell.type === 'th') {
                tdoth = document.createElement('th');
              } else {
                tdoth = document.createElement('td');
              }
              if (cell.id) {
                tdoth.id = cell.id;
              }
              if (cell.className) {
                tdoth.className = cell.className;
              }
              if (cell.sizeY) {
                tdoth.rowSpan = cell.sizeY;
              }
              if (cell.sizeX) {
                tdoth.colSpan = cell.sizeX;
              }
              if (cell.textContent) {
                tdoth.textContent = cell.textContent;
              }
              if (cell.innerHTML) {
                tdoth.innerHTML = cell.innerHTML;
              }
            }
            ligne.appendChild(tdoth);
          }
        }
        part.ligne.push(part.appendChild(ligne));
      };
      retour = part;
    } else {
      retour = null;
    }
  } else {
    retour = null;
  }
  return retour;
}
/**
* @class Tableau
* @memberof object_table
* @description Class de création d'objet tableau HTML
* @param {Object} obj Descriptif de création du tableau de la forme :
 <ul>
  <li><u><b>parent</b></u> : <i>Element</i> Le future parent du tableau</li>
  <li><u><b>id</b></u> : <i>String</i> Id du tableau</li>
  <li><u><b>class</b></u> : <i>String</i> Class du tableau</li>
 </ul>
* @returns {Object} tableau;
* @augments PartTableau
* @requires {@link object_table.PartTableau}
*/
function Tableau(obj) {
  'use strict';
  var tableau = document.createElement('table');
  if (obj.className) {
    tableau.className = obj.className;
  }
  if (obj.id) {
    tableau.id = obj.id;
  }
 /**
 * @function addPart
 * @description Fonction d'ajout d'une partie au tableau HTML
 * @memberof object_table.Tableau
 * @param {Object} obj Descriptif de la part du tableau crée <i>voir le constructeur de PartTableau</i>
 * @returns {undefined} undefined
 */
  tableau.addPart = function (obj) {
    if (obj.part) {
      obj.part = obj.part.toUpperCase().trim();
      tableau[obj.part] = new PartTableau(obj);
    }
  };
 /**
 * @function appendAll
 * @description Fonction d'ajout du tableau et de ses différentes parties au DOM
 * @memberof object_table.Tableau
 * @param {undefined} undefined
 * @returns {undefined} undefined
 */
  tableau.appendAll = function () {

    if (tableau.THEAD) {
      tableau.THEAD = tableau.appendChild(tableau.THEAD);
    }
    if (tableau.TBODY) {
      tableau.TBODY = tableau.appendChild(tableau.TBODY);
    }
    if (tableau.TFOOT) {
      tableau.TFOOT = tableau.appendChild(tableau.TFOOT);
    }
    if (obj.parent) {
      tableau = obj.parent.appendChild(tableau);
    }
    return tableau;
  };
  return tableau;
}
try {
	if (module !== undefined && module.exports !== undefined) {
		module.exports = Tableau;
	}
} catch (e) {
	
}