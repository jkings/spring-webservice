/**
 * ClienteRecepcionNotificaciones.java Fecha de creación: 15/11/2011 , 15:33:18
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.client;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.synergyj.concepto.spring.entidades.jaxb.NotificacionXMLRequest;
import com.synergyj.concepto.spring.entidades.jaxb.ObjectFactory;
import com.synergyj.concepto.spring.entidades.jaxb.RespuestaXMLResponse;

/**
 * Esta interfaz define la SEI del cliente para el webservice de envio de notificaciones.
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */
@WebService(name = "recepcionNotificaciones", targetNamespace = "http://www.synergyj.com/concepto/webservices/recepcionNotificaciones/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ ObjectFactory.class })
public interface ClienteRecepcionNotificaciones {

    /**
     * 
     * Este metodo enviara la notificacion en xml al webservice recibiendo un xml que sera la
     * respuesta todo esto envuelto en un wrapper.
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     * @param notificacionXml
     * @return La respuesta en XML
     */
    @WebMethod(action = "http://www.synergyj.com/concepto/webservices/recepcionNotificaciones/recibeNotificaciones", operationName = "recibeNotificaciones")
    @WebResult(name = "respuestaXMLResponse", targetNamespace = "http://www.synergyj.com/concepto/webservices/recepcionNotificaciones/", partName = "parameters")
    RespuestaXMLResponse enviaNotificacionWebservice(
        @WebParam(name = "notificacionXMLRequest", targetNamespace = "http://www.synergyj.com/concepto/webservices/recepcionNotificaciones/", partName = "parameters") NotificacionXMLRequest notificacionXml);

}
