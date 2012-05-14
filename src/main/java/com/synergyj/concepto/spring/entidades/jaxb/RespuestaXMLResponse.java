/**
 * RespuestaXMLResponse.java
 * Fecha de creaci�n: 10/11/2011 , 18:52:45
 *
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial, propiedad del
 * Synergyj. Esta informaci�n confidencial
 * no deber� ser divulgada y solo se podr� utilizar de acuerdo
 * a los t�rminos que determine la empresa.
 */
package com.synergyj.concepto.spring.entidades.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO [Agregar documentacion de la clase] 
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */
@XmlRootElement
public class RespuestaXMLResponse {

    private String respuesta;

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

}
