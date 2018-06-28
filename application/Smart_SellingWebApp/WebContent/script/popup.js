/*jslint browser  :  true, indent  :  2*/
/*global Audio, clearInterval, clearTimeout, document, event, FileReader, FormData, history, Image, location, name, navigator, Option, screen, sessionStorage, setInterval, setTimeout, Storage, XMLHttpRequest */
/**
 * @file popup
 * @namespace popup
 */
/**
* @class PopUp
* @memberof popup
* @param {Objet} obj <ul>
                         <li><u><b>idPopUp</b></u> : <i>String</i> Id de la popUp</li>
                         <li><u><b>classPopUp</b></u> : <i>String</i> Class de la popUp</li>
                         <li><u><b>idFade</b></u> : <i>String</i> Id de l'ombre sous la pop-up</li>
                         <li><u><b>classFade</b></u> : <i>String</i> Class de l'ombre sous la pop-up</li>
                         <li><u><b>titre</b></u> : <i>Object</i> {textContent : titre en texte plat dans le paragraphe HTML, class: Classe du titre dans le paragraphe HTML, id: Id du titre dans le paragraphe HTML}</li>                                  
                         <li><u><b>contenu</b></u> : <i>String</i> Contenu de la pop-up sous forme HTML </li>
                         <li><u><b>callBack</b></u> : <i>Function</i> Fonction exécuté à la fermeture de la Pop-up</li>
                     </ul>
* @description Methode de création de pop-up modal
* @returns {Object} PopUp;
*/
function PopUp(obj) {
  'use strict';
  var self = {}, pUp, fade,
    cross = document.createElement("div");
  self.callBack = obj.callBack;
  pUp = document.createElement("div");
  if (obj.idPopUp) {
    pUp.id = obj.idPopUp;
  }
  if (obj.classPopUp) {
    pUp.className = obj.classPopUp;
  }
  self.pUp = document.body.insertBefore(pUp, document.body.firstChild);
  if (obj.cross === true) {
    cross.className = 'ppupClose';
    cross.innerHTML = '&#10060;';
    self.cross = self.pUp.appendChild(cross);
  }
  if (obj.titre) {
    self.titreStr = '<p';
    if (obj.titre.className) {
      self.titreStr  += ' class="' + obj.titre.className + '"';//titlePopUp
    }
    if (obj.titre.id) {
      self.titreStr  += ' id="' + obj.titre.id + '"';
    }
    if (obj.titre.textContent) {
      self.titreStr += '>' + obj.titre.textContent + '</p>';
    }
    self.pUp.innerHTML += self.titreStr;
  }
  if (obj.contenu) {
    self.pUp.innerHTML +=  obj.contenu;
  }
  fade = document.createElement("div");

  if (obj.idFade) {
    fade.id = obj.idFade;
  }
  if (obj.classFade) {
    fade.className = obj.classFade;
  }
  self.fade = document.body.insertBefore(fade, document.body.firstChild);
  /**
  * @function fermeture
  * @description Fonction de fermeture de la pop-up lors du click sur la croix
  * @memberof popup.PopUp
  * @param {Event} event Évenement click sur le body
  * @returns {undefined} undefined
  */
  self.fermeture = function (event) {
    //event.stopPropagation();
    if ((event.target.className === self.cross.className &&
         event.target.parentElement.id === self.pUp.id)) {
      document.body.removeChild(self.fade);
      document.body.removeChild(self.pUp);
      document.body.removeEventListener('click', self.fermeture, false);
      if (self.callBack) {
        self.callBack();
        self = null;
      }
    }
  };
  document.body.addEventListener('click', self.fermeture, false);
  return self;
}