/**
 * ValidacionHandlerServer.java Fecha de creación: 15/11/2011 , 19:33:18
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.server.handlers;

import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import com.synergyj.concepto.spring.service.webservice.server.excepciones.ValidacionServerException;
import com.synergyj.concepto.spring.service.webservice.utilerias.UtileriasSOAP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;

/**
 * Clase que implementa el handler del lado del servidor
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */
public class ValidacionHandlerServer implements EndpointInterceptor {

    /**
     * Nombre del header para insertar la firma
     */
    private static final String NOMBRE_HEADER = "md5";

    /**
     * Namespace del header para insertar la firma
     */
    private static final String NAMESPACE_HEADER = "http://www.synergyj.com/concepto/ws/header";

    Log logger = LogFactory.getLog(ValidacionHandlerServer.class);

    /*
     * La documentacion de este metodo se encuentra en la declaracion de la interface o clase
     * (non-Javadoc)
     * @see
     * org.springframework.ws.server.EndpointInterceptor#afterCompletion(org.springframework
     * .ws.context.MessageContext, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(MessageContext arg0, Object arg1, Exception arg2) {

        // TODO Codificar el cuerpo del metodo
    }

    /*
     * La documentacion de este metodo se encuentra en la declaracion de la interface o clase
     * (non-Javadoc)
     * @see
     * org.springframework.ws.server.EndpointInterceptor#handleFault(org.springframework.ws
     * .context.MessageContext, java.lang.Object)
     */
    public boolean handleFault(MessageContext arg0, Object arg1) throws Exception {

        return false;
        // TODO Codificar el cuerpo del metodo
    }

    /*
     * La documentacion de este metodo se encuentra en la declaracion de la interface o clase
     * (non-Javadoc)
     * @see
     * org.springframework.ws.server.EndpointInterceptor#handleRequest(org.springframework.
     * ws.context.MessageContext, java.lang.Object)
     */
    public boolean handleRequest(MessageContext contexto, Object arg1) throws Exception {
        boolean regreso = true;
        

        SoapMessage request = (SoapMessage) contexto.getRequest();
        
        StringWriter envelope = new StringWriter();

        TransformerFactory.newInstance().newTransformer().transform(
            request.getEnvelope().getSource(), new StreamResult(envelope));

        logger.debug("imprimiendo el envelope" + envelope.toString());

        SoapHeader header = request.getEnvelope().getHeader();

        logger.debug("Revisando la firma del lado del servidor");

        String prefix = request.getEnvelope().getName().getPrefix();

        String firmaCalculada =
            UtileriasSOAP.calculateMD5Signature(UtileriasSOAP.obtenerTextoBody(
                envelope.toString(), prefix));

        logger.debug("La firma obtenida del server es: " + firmaCalculada);
        String firmaCliente = null;
        if (header != null) {

            Iterator<SoapHeaderElement>  iterador=  header.examineAllHeaderElements();
            

            while (iterador.hasNext()) {
                
                SoapHeaderElement elemento = iterador.next();

                if (elemento.getName().getLocalPart().equals(NOMBRE_HEADER)) {

                    firmaCliente = elemento.getText();
                    logger.debug("El valor de la firma enviada por el cliente es: "
                        + firmaCliente);
                }


                if (firmaCliente != null) {
                    break;

                }

            }

            if (firmaCliente == null) {
                throw new ValidacionServerException("No existe la firma");
            }

            if (!firmaCliente.equals(firmaCalculada)) {

                throw new ValidacionServerException("Las firmas no coinciden");

            }

        } else

        {
            throw new ValidacionServerException("No existe la firma");
        }

        return regreso;

    }

    /*
     * La documentacion de este metodo se encuentra en la declaracion de la interface o clase
     * (non-Javadoc)
     * @see
     * org.springframework.ws.server.EndpointInterceptor#handleResponse(org.springframework
     * .ws.context.MessageContext, java.lang.Object)
     */
    public boolean handleResponse(MessageContext arg0, Object arg1) throws Exception {

        return true;
        // TODO Codificar el cuerpo del metodo
    }

}
