/**
 * RecepcionNotificacionServiceEnpoint.java Fecha de creación: 03/11/2011 , 13:38:01
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.server.impl;

import javax.annotation.Resource;

import com.synergyj.concepto.spring.entidades.jaxb.NotificacionXMLRequest;
import com.synergyj.concepto.spring.entidades.jaxb.RespuestaXMLResponse;
import com.synergyj.concepto.spring.service.webservice.server.RecepcionNotificacionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Esta clase implementa los recursos para exponer el webservice Es importante resaltar que los
 * metodos que se expongan con la anotacion PayloadRoot deben de seguir las reglas siguientes:
 * 
 * Si el metodo regresa algo es necesario que la variable termine con el sufijo Response y debe
 * de ser el mismo nombre que tiene el xsd del wsdl en la seccion types
 * 
 * Los parametros deben terminar con el sufijo Request y debe de ser el mismo nombre que tiene
 * el xsd del wsdl en la seccion types
 * 
 * 
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */

@Endpoint
public class RecepcionNotificacionServiceEnpoint {

    Log logger = LogFactory.getLog(RecepcionNotificacionServiceEnpoint.class);

    @Autowired
    RecepcionNotificacionService recepcionNotificacionService;

    /**
     * 
     * Metodo que atiende la peticion del webservice
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     * @param notificacionXMLRequest
     * @return
     */
    @PayloadRoot(namespace = "http://www.synergyj.com/concepto/webservices/recepcionNotificaciones/", localPart = "notificacionXMLRequest")
    @ResponsePayload
    public RespuestaXMLResponse recibeNotificacion(
        @RequestPayload NotificacionXMLRequest notificacionXMLRequest) {

        logger.debug("Recibiendo la notificacion del webservice");

        String respuestaString =
            recepcionNotificacionService.recibeNotificacion(
                notificacionXMLRequest.getNotificacion(),
                notificacionXMLRequest.getIdModulo(),
                notificacionXMLRequest.getIdTipoNotificacion());

        logger.debug("Regresando la respuesta al cliente " + respuestaString);

        RespuestaXMLResponse respuesta = new RespuestaXMLResponse();

        respuesta.setRespuesta(respuestaString);

        return respuesta;

    }

    /**
     * 
     * Este metodo asigna el servicio de negocio
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     * @param recepcionNotificacionService
     */
    public void setRecepcionNotificacionService(
        RecepcionNotificacionService recepcionNotificacionService) {
        this.recepcionNotificacionService = recepcionNotificacionService;
    }

}
