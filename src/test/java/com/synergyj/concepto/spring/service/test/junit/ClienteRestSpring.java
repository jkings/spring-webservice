/**
 * ClienteRestSpring.java Fecha de creación: 22/11/2011 , 14:42:45
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

import com.synergyj.concepto.spring.entidades.jaxb.NotificacionXMLRequest;
import com.synergyj.concepto.spring.entidades.jaxb.RespuestaXMLResponse;
import com.synergyj.concepto.spring.service.webservice.utilerias.UtileriasSOAP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Esta clase prueba el web servicio tipo rest
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContextTest.xml" })
public class ClienteRestSpring {

    Log logger = LogFactory.getLog(ClienteRestSpring.class);

    @Resource
    RestTemplate restTemplate;

    @Test
    public void pruebaClienteRestExitosa() {

        HttpHeaders headers = new HttpHeaders();

        NotificacionXMLRequest notificacion = new NotificacionXMLRequest();

        notificacion.setIdModulo("1000");
        notificacion.setIdTipoNotificacion(1);

        logger.debug("Creando la notificacion");
        notificacion.setNotificacion("<notificacion>Esta es mi notificacion</notificacion>");



        String firmaCliente =
            UtileriasSOAP.calculateMD5Signature(notificacion.getNotificacion());

        logger.debug("Agregando la firma al header:" + firmaCliente + "longitud "
            + firmaCliente.length());

        headers.add("firmaCliente", firmaCliente);

        HttpEntity entidad = new HttpEntity(notificacion, headers);

        Assert.assertNotNull(restTemplate);

        logger.debug("Enviando el xml al webservice");

        RespuestaXMLResponse respuesta =
            restTemplate.postForObject(
                "http://localhost:8080/spring3-concepto-ws/rest/recepcionNotificacion",
                entidad, RespuestaXMLResponse.class);

        Assert.assertNotNull(respuesta);

        logger.debug("La respuesta fue" + respuesta.getRespuesta());

        Assert.assertEquals("<respuesta>Se proceso correctamente  </respuesta>",
            respuesta.getRespuesta());

    }

    /**
     * 
     * No se envia el header de la firma
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     */

    @Test
    public void pruebaClienteRestSinFirma() {

        NotificacionXMLRequest notificacion = new NotificacionXMLRequest();

        notificacion.setIdModulo("1000");
        notificacion.setIdTipoNotificacion(1);

        logger.debug("Creando la notificacion");
        notificacion.setNotificacion("<notificacion>Esta es mi notificacion</notificacion>");

        logger.debug("No agregamos la firma al header");

        HttpEntity entidad = new HttpEntity(notificacion);

        Assert.assertNotNull(restTemplate);

        logger.debug("Enviando el xml al webservice");

        RespuestaXMLResponse respuesta =
            restTemplate.postForObject(
                "http://localhost:8080/spring3-concepto-ws/rest/recepcionNotificacion",
                entidad, RespuestaXMLResponse.class);

        Assert.assertNotNull(respuesta);

        logger.debug("La respuesta fue" + respuesta.getRespuesta());

        Assert.assertEquals("<respuesta>La firma no viene incluida  </respuesta>",
            respuesta.getRespuesta());

    }

    /**
     * 
     * Se envia una firma diferente a la que se calcula
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     */
    @Test
    public void pruebaClienteRestFirmasDiferentes() {

        HttpHeaders headers = new HttpHeaders();

        NotificacionXMLRequest notificacion = new NotificacionXMLRequest();

        notificacion.setIdModulo("1000");
        notificacion.setIdTipoNotificacion(1);

        logger.debug("Creando la notificacion");
        notificacion.setNotificacion("<notificacion>Esta es mi notificacion</notificacion>");

        logger.debug("Agregando la firma al header con un caracter mas para que la firma sea diferente");
        headers.add("firmaCliente",
            UtileriasSOAP.calculateMD5Signature(notificacion.getNotificacion()) + " 1");

        HttpEntity<NotificacionXMLRequest> entidad =
            new HttpEntity<NotificacionXMLRequest>(notificacion, headers);

        Assert.assertNotNull(restTemplate);

        logger.debug("Enviando el xml al webservice");

        RespuestaXMLResponse respuesta =
            restTemplate.postForObject(
                "http://localhost:8080/spring3-concepto-ws/rest/recepcionNotificacion",
                entidad, RespuestaXMLResponse.class);

        Assert.assertNotNull(respuesta);

        logger.debug("La respuesta fue" + respuesta.getRespuesta());

        Assert.assertEquals("<respuesta>La firma no coincide  </respuesta>",
            respuesta.getRespuesta());

    }
}
