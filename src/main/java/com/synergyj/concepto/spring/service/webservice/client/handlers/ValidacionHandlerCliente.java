/**
 * ValidacionHandlerCliente.java Fecha de creación: 15/11/2011 , 15:45:05
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.client.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.synergyj.concepto.spring.service.webservice.utilerias.UtileriasSOAP;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Esta clase implementa el handler para insertar la firma digital
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */
@Service("validacionHandlerCliente")
public class ValidacionHandlerCliente implements SOAPHandler<SOAPMessageContext> {

    /**
     * Logger para todas las instancias de la clase
     */
    private static final Log logger = LogFactory.getLog(ValidacionHandlerCliente.class);

    /**
     * Nombre del header para insertar la firma
     */
    private static final String NOMBRE_HEADER = "md5";

    /**
     * Namespace del header para insertar la firma
     */
    private static final String NAMESPACE_HEADER = "http://www.synergyj.com/concepto/ws/header";

    /*
     * (non-Javadoc)
     * @seejavax.xml.ws.handler.Handler#handleMessage(javax.xml.ws.handler. MessageContext)
     */
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        SOAPMessage soapMessage;
        SOAPEnvelope envelope;
        SOAPHeader header;
        SOAPBody body;
        SOAPHeaderElement headerElement;
        Boolean outboundMessage;
        QName qname;
        boolean resultado = false;

        logger.debug("Iniciando procesamiento de mensaje");
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        try {
            soapMessage = context.getMessage();
            logger.debug("Imprimiendo mensaje SOAP a la consola");

            soapMessage.writeTo(salida);

            // logger.debug("El mensaje SOAP es el siguiente " +
            // salida.toString());

            // verificando si se trata de un mensaje de salida (request)
            outboundMessage = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
            if (outboundMessage) {
                logger.debug("Mensaje de salida, anexandola firma digital en el header");
                qname = new QName(NAMESPACE_HEADER, NOMBRE_HEADER);

                envelope = soapMessage.getSOAPPart().getEnvelope();
                header = envelope.getHeader();
                body = envelope.getBody();

                logger.debug("imprimiendo el body " + body.toString());
                // se verifica la existencia del header, es opcional.
                if (header == null) {
                    logger.debug("Mensaje soap sin  header, generando uno nuevo");
                    header = envelope.addHeader();
                }
                logger.debug("Agregando una entrada al header");

                String prefix = body.getPrefix();

                String firmaDigital =
                    UtileriasSOAP.calculateMD5Signature(UtileriasSOAP.obtenerTextoBody(
                        salida.toString(), prefix));
                headerElement = header.addHeaderElement(qname);
                // actor o rol que manipula al header
                headerElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
                headerElement.addTextNode(firmaDigital);
                logger.debug("Imprimendo mensaje SOAP despues de modificar el header:");
                soapMessage.writeTo(System.out);
                logger.debug("Continuando con el siguiente handler de la cadena.");

                soapMessage.saveChanges();
                resultado = true;

            } else {
                logger.debug("Mensaje de entrada revisando un fault");
                if (soapMessage.getSOAPPart().getEnvelope().getBody().getFault() == null) {
                    resultado = true;
                } else {
                    logger.debug("Si trae un fault");
                }
            }

        } catch (SOAPException e) {
            logger.error("Error al obtener el XML del body", e);
        } catch (IOException e) {
            logger.error("Error al obtener el XML del body", e);
        } finally {
            try {
                salida.close();
            } catch (IOException e) {
                logger.error("error", e);
            }
        }

        return resultado;
    }

    /*
     * (non-Javadoc)
     * @see javax.xml.ws.handler.soap.SOAPHandler#getHeaders()
     */
    @Override
    public Set<QName> getHeaders() {
        logger.debug("En metodo getHeaders");
        return null;
    }

    /*
     * (non-Javadoc)
     * @see javax.xml.ws.handler.Handler#close(javax.xml.ws.handler.MessageContext)
     */
    @Override
    public void close(MessageContext context) {
        logger.debug("En metodo close");

    }

    /*
     * (non-Javadoc)
     * @see javax.xml.ws.handler.Handler#handleFault(javax.xml.ws.handler.MessageContext )
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logger.debug("en Metodo handleFault");
        Boolean outboundMessage =
            (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (!outboundMessage) {
            SOAPMessage mensaje = context.getMessage();
            try {
                SOAPFault fault = mensaje.getSOAPPart().getEnvelope().getBody().getFault();
                if (fault != null) {

                    logger.debug("Codigo " + fault.getFaultCode());
                    logger.debug("Cadena " + fault.getFaultString());

                    Detail detalle = fault.getDetail();

                    // Get the list of DetailEntry's
                    if (detalle != null) {
                        Iterator it = detalle.getDetailEntries();
                        while (it.hasNext()) {
                            DetailEntry entry = (DetailEntry) it.next();
                            logger.debug("El detalle es :  " + entry.getNodeName() + " "
                                + entry.getValue());
                        }
                    }
                }

            } catch (SOAPException e) {
                logger.debug("Error al obtener el fault", e);

            }
        }
        return true;
    }

}
