/**
 * PruebaIntegracionWS.java Fecha de creación: 10/11/2011 , 19:33:03
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

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.xml.transform.StringSource;

/**
 * Esta es una prueba con el framework de spring para probar los endpoints
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/webServiceApplicationContext.xml")
public class PruebaIntegracionWS {

    String cadena =
        "<notificacionXMLRequest xmlns=\"http://www.synergyj.com/concepto/webservices/recepcionNotificaciones/\"><idModulo>1000</idModulo><idTipoNotificacion>1</idTipoNotificacion><notificacion>&lt;notificacion&gt;Esta es mi notificacion&lt;/notificacion&gt;</notificacion></notificacionXMLRequest>";

    
    String respuesta =
        "<ns2:respuestaXMLResponse xmlns:ns2=\"http://www.synergyj.com/concepto/webservices/recepcionNotificaciones/\"><ns2:respuesta>&lt;respuesta&gt;Se proceso correctamente  &lt;/respuesta&gt;</ns2:respuesta></ns2:respuestaXMLResponse>";
    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    /**
     * 
     * Prueba la respuesta del endpoint sin levantar el webservice
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     */
    @Test
    public void pruebaIntegracionWebservice() {
        
        Source requestPayload = new StringSource(cadena);
        

        Source responsePayload = new StringSource(respuesta);


        mockClient.sendRequest(RequestCreators.withPayload(requestPayload)).andExpect(
            ResponseMatchers.payload(responsePayload));

    }

}
