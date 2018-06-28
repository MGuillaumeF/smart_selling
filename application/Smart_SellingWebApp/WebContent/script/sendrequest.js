/*jslint browser  :  true, indent  :  2*/
/*global XMLHttpRequest */
/**
 * @file sendrequest
 * @namespace sendrequest
 */
 /**
* @function sendRequest
* @description Fonction d'émission de requêtes Ajax avec les méthode POST et GET
* @memberof sendrequest
* @param {Object} obj Descriptif des paramètres de la requête
<ul>
 <li><b><u>methode</u></b>: <i>String</i> GET ou POST</li>
 <li><b><u>header</u></b>: <i>Array</i> Header de la reqête par défaut : ["Content-type", "application/x-www-form-urlencoded"]</li>
 <li><b><u>page</u></b>: <i>String</i> La page cible de la requête (avec les paramètres de GET
 <br/><b style="color:red;">ATTENTION ! Si des paramètres GET sont inscrits dans les attributs <b>page</b> et paramGET avec les même nom paramGET est prioritaire</b></li>
 <li><b><u>send</u></b>: <i>String</i> Les données à envoyer
 <br/><b style="color:red;">ATTENTION ! Si des paramètres POST sont inscrits dans les attributs <b>send</b> et paramPOST avec les même nom paramPOST est prioritaire</b></li>
 <li><b><u></u></b>: <i>Object</i> Objet des paramètres <b>GET</b> de la forme {name1 : value1, name2 : value2, ....}</li>
 <li><b><u>paramPOST</u></b>: <i>Object</i> Objet des paramètres <b>POST</b> de la forme {name1 : value1, name2 : value2, ....}</li>
 <li><b><u>callBack</u></b>: <i>Function</i> Fonction a appeler lors de l'aboutissement de la requête</li>
</ul>
* @returns {Object} xhr
*/
function sendRequest(obj) {
  'use strict';
  var xhr = new XMLHttpRequest(), loadImg = window.document.createElement('img'), l_page = '', l_send = '', tmp, i, attributs;
  if (obj.methode === 'GET' || obj.methode === 'POST') {
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
        loadImg.parentElement.removeChild(loadImg);
        if (obj.callBack) {
          obj.callBack(xhr.responseText.trim(), xhr.status);
        }
      }
    };
    if (obj.page !== undefined) {
      l_page = obj.page;
    } else {
      l_page = '';
    }
    if (/\?/.test(l_page)) {
      tmp = l_page.split('?');
      if (/&/.test(l_page)) {
        tmp[1] = tmp[1].split('&');
        for (i = 0; i < tmp[1].length; i += 1) {
          tmp[1][i] = tmp[1][i].split('=');
          if (obj.paramGET === undefined) {
            obj.paramGET = {};
          }
          obj.paramGET[tmp[1][i][0]] = tmp[1][i][1];
        }
      } else {
        tmp[1] = tmp[1].split('=');
        if (obj.paramGET === undefined) {
          obj.paramGET = {};
        }
        obj.paramGET[tmp[1][0]] = tmp[1][1];
      }
      l_page = tmp[0];
    }
    if (obj.paramGET !== undefined) {
      for (attributs in obj.paramGET) {
        if (/\?/.test(l_page)) {
          l_page += '&';
        } else {
          l_page += '?';
        }
        l_page += attributs + '=' + encodeURIComponent(obj.paramGET[attributs]);
      }
    }
    xhr.open(obj.methode, (l_page || ''));
    if (obj.header === undefined) {
      obj.header = ["Content-type", "application/x-www-form-urlencoded"];
    } else if (obj.header[0] === undefined) {
      obj.header[0] = "Content-type";
    } else if (obj.header[1] === undefined) {
      obj.header[1] = "application/x-www-form-urlencoded";
    }
    xhr.setRequestHeader(obj.header[0], obj.header[1]);
    l_send += (obj.send || '');
    if (l_send !== '') {
      if (/&/.test(l_send)) {
        tmp = l_send.split('&');
        for (i = 0; i < tmp.length; i += 1) {
          tmp[i] = tmp[i].split('=');
          if (obj.paramPOST === undefined) {
            obj.paramPOST = {};
          }
          obj.paramPOST[tmp[i][0]] = tmp[i][1];
        }
      } else {
        tmp = l_send.split('=');
        if (obj.paramPOST === undefined) {
          obj.paramPOST = {};
        }
        obj.paramPOST[tmp[0]] = tmp[1];
      }
      l_send = '';
    }
    if (obj.paramPOST !== undefined) {
      for (attributs in obj.paramPOST) {
        l_send += attributs + '=' + encodeURIComponent(obj.paramPOST[attributs]) + '&';
      }
      l_send = l_send.slice(0, -1);
    }
    if (l_send && obj.methode === 'POST') {
      xhr.send(l_send);
    } else {
      xhr.send();
    }
    loadImg.src = 'data:image/gif;base64,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';
    loadImg.id = 'loadImgAbs';
    loadImg = window.document.getElementById('contenu').appendChild(loadImg);
  } else {
    xhr = null;
  }
  return xhr;
}