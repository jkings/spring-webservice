/**
 * ValidacionException.java
 * Fecha de creación: 16/11/2011 , 14:10:30
 *
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.server.excepciones;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

/**
 * Esta Excepcion se lanzara y sera convertida en un SOAP Fault regresada para el webservice
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */

@SoapFault(faultCode = FaultCode.SERVER)
public class ValidacionServerException extends RuntimeException {

    /**
     * 
     * @Constructor Contructor de la clase
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     * @param mensaje
     */
    public ValidacionServerException(String mensaje) {

        super(mensaje);
    }

}
