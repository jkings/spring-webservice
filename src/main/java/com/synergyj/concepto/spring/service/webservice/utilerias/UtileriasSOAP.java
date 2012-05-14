/**
 * UtileriasSOAP.java Fecha de creación: 15/11/2011 , 15:33:18
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.utilerias;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO [Descripcion de la clase]
 * @author JoClienteRecepcionNotificacionesrge Rodríguez Campos (jorge.rodriguez@synergyj.com)
 * @version 1.0
 */
public class UtileriasSOAP {

	/**
	 * Logger para todas las instancias de la clase
	 */
    private static final Log logger = LogFactory.getLog(UtileriasSOAP.class);

    /**
     * Sirve para meter basura al body para provocar un error
     */
    public static boolean induceError = false;

	/**
	 * Este metodo regresa el body en formato XML
	 * @param xml
	 * @param prefix
	 * @return Cadena en XML del body
	 */
	static public String obtenerTextoBody(String xml, String prefix) {
		String body = null;

		// find soap body start index
		String cuerpo = xml;

		// Encontramos el inicio del body
		int indiceInicioBody = cuerpo.indexOf("<" + prefix + ":Body");

		int indiceInicioFinBody = cuerpo.indexOf("</" + prefix + ":Body>");
		// Encontramos el fin del body
		int indiceFinTagBody = cuerpo.indexOf(">", indiceInicioFinBody);

		// Obtenemos el XML del body del mensaje SOAP
		body = cuerpo.substring(indiceInicioBody, indiceFinTagBody + 1);

        if (induceError) {
            body += "error";
        }

		logger.debug("El body es el siguiente: " + body);

		return body;

	}

	/**
	 * Método que calcula la firma MD5 del contenido XML, debe ser el mismo algoritmo del
	 * cliente.
	 * @param texto String para el que se calculará la firma MD5.
	 * @return String, la firma MD5 como secuencia de digitos hexadecimales.
	 */

	static public String calculateMD5Signature(String texto) {
		logger.debug("Estoy firmando :");
		logger.debug("La longitud es :" + texto.length());
		StringBuffer hexString = new StringBuffer();

		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");

			algorithm.reset();
			algorithm.update(texto.getBytes());
			byte[] digest = algorithm.digest();

			for (int i = 0; i < digest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & digest[i]));

                if (i < digest.length - 1) {
                    hexString.append(" ");
                }
			}

			logger.debug(hexString.toString());
		} catch (NoSuchAlgorithmException e) {
			logger.debug("Error al calcular el MD5", e);
		}
		return hexString.toString();
	}

}
