/**
 * ClienteJWSWebservice.java Fecha de creación: 09/11/2011 , 16:49:59
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.test.junit;

import javax.annotation.Resource;

import junit.framework.Assert;
import com.synergyj.concepto.spring.entidades.jaxb.NotificacionXMLRequest;
import com.synergyj.concepto.spring.entidades.jaxb.RespuestaXMLResponse;
import com.synergyj.concepto.spring.service.webservice.client.ClienteRecepcionNotificaciones;
import com.synergyj.concepto.spring.service.webservice.client.RecepcionNotificacionesPort;
import com.synergyj.concepto.spring.service.webservice.utilerias.UtileriasSOAP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Este clase prueba el cliente generado con JWS para hacer la invocacion del webservice de
 * prueba con spring ws
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */ 

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContextTest.xml" })
public class ClienteJWSWebservice {

    Log logger = LogFactory.getLog(ClienteJWSWebservice.class);

    @Resource
    RecepcionNotificacionesPort recepcionNotificacionesPort;

    /**
     * 
     * Prueba que envia un xml al webservice
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     */
    @Test
    public void pruebaWebservice() {

        Assert.assertNotNull(recepcionNotificacionesPort);

        ClienteRecepcionNotificaciones stub = recepcionNotificacionesPort.getCliente();

        NotificacionXMLRequest notificacion = new NotificacionXMLRequest();

        notificacion.setIdModulo("1000");
        notificacion.setIdTipoNotificacion(1);

        logger.debug("Creando la notificacion");
        notificacion.setNotificacion("<notificacion>Esta es mi notificacion</notificacion>");

        logger.debug("Enviando el xml al webservice");
        RespuestaXMLResponse respuesta = stub.enviaNotificacionWebservice(notificacion);

        logger.debug("La respuesta fue: " + respuesta.getRespuesta());

    }



    public void pruebaExepcion() {

        Assert.assertNotNull(recepcionNotificacionesPort);

        ClienteRecepcionNotificaciones stub = recepcionNotificacionesPort.getCliente();

        NotificacionXMLRequest notificacion = new NotificacionXMLRequest();

        notificacion.setIdModulo("1000");
        notificacion.setIdTipoNotificacion(1);

        logger.debug("Creando la notificacion");
        notificacion.setNotificacion("<notificacion>Esta es mi notificacion</notificacion>");

        logger.debug("Enviando el xml al webservice");
        UtileriasSOAP.induceError = true;

        RespuestaXMLResponse respuesta = stub.enviaNotificacionWebservice(notificacion);

        logger.debug("La respuesta fue: " + respuesta.getRespuesta());


    }

}
