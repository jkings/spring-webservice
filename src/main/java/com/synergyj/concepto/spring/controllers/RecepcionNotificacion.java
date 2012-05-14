/**
 * RecepcionNotificacion.java Fecha de creación: 21/11/2011 , 13:10:37
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.controllers;


import com.synergyj.concepto.spring.entidades.jaxb.NotificacionXMLRequest;
import com.synergyj.concepto.spring.entidades.jaxb.RespuestaXMLResponse;
import com.synergyj.concepto.spring.service.webservice.server.RecepcionNotificacionService;
import com.synergyj.concepto.spring.service.webservice.utilerias.UtileriasSOAP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO [Agregar documentacion de la clase]
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */
@Controller
public class RecepcionNotificacion {

    Log logger = LogFactory.getLog(RecepcionNotificacion.class);
    
    /**
     * Servicio de negocio
     */
    @Autowired
    RecepcionNotificacionService recepcionNotificacionService;

    /**
     * 
     * Este metodo recibe y procesa la notificacion recibida de MAC
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     * @param notificacionXMLRequest
     * @param firmaCliente
     * @return La respuesta en un objeto JAXB
     */
    @RequestMapping(value = "/recepcionNotificacion", method = RequestMethod.POST)
    @ResponseBody
    public RespuestaXMLResponse recibeNotificacion(
        @RequestBody NotificacionXMLRequest notificacionXMLRequest,
        @RequestHeader String firmaCliente) {

        String respuestaCadena = null;
        RespuestaXMLResponse respuesta = new RespuestaXMLResponse();

        String firmaServer = null;

        if (firmaCliente != null) {

            logger.debug("La firma del cliente es:" + firmaCliente + "longitud"
                + firmaCliente.length());

            firmaServer =
                UtileriasSOAP.calculateMD5Signature(notificacionXMLRequest.getNotificacion());

            logger.debug("La firma del server es:" + firmaServer + "longitud"
                + firmaServer.length());

            if (!firmaServer.equals(firmaCliente)) {
                respuestaCadena = "<respuesta>La firma no coincide  </respuesta>";
            } else {
                respuestaCadena =
                    recepcionNotificacionService.recibeNotificacion(
                        notificacionXMLRequest.getNotificacion(), notificacionXMLRequest.getIdModulo(),
                        notificacionXMLRequest.getIdTipoNotificacion());
            }
        } else {
            respuestaCadena = "<respuesta> La firma no viene incluida  </respuesta>";
        }

        respuesta.setRespuesta(respuestaCadena);

        return respuesta;

    }


}
