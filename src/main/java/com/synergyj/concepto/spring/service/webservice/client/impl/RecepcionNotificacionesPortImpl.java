/**
 * ClienteRecepcionNotificaciones.java Fecha de creación: 15/11/2011 , 15:45:05
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.client.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import com.synergyj.concepto.spring.service.webservice.client.ClienteRecepcionNotificaciones;
import com.synergyj.concepto.spring.service.webservice.client.RecepcionNotificacionesPort;
import com.synergyj.concepto.spring.service.webservice.client.handlers.ValidacionHandlerCliente;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Esta clase implementa el cliente del webservice para el envio de notificaciones.
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */
@Service("recepcionNotificacionesPort")
public class RecepcionNotificacionesPortImpl implements RecepcionNotificacionesPort {

    javax.xml.ws.Service servicio;

    /**
     * Nombre del servicio
     */
    @Value("#{properties['nombre.servicio']}")
    private String nombreServicio;

    /**
     * Name Space del servicio
     */
    @Value("#{properties['namespace.servicio']}")
    private String nameSpaceServicio;

    /**
     * Url de donde esta localizado el servicio
     */
    @Value("#{properties['url.wsdl.servicio']}")
    private String urlWsdl;

    /**
     * Define el nombre del puerto
     */
    @Value("#{properties['nombre.puerto.servicio']}")
    private String nombrePuerto;

    /**
     * Proxy generado para invokar el webservice
     */
    ClienteRecepcionNotificaciones cliente;

    /**
     * Handler para insertar la firma en el header
     */
    @Resource
    ValidacionHandlerCliente validacionHandlerCliente;

    Log logger = LogFactory.getLog(RecepcionNotificacionesPortImpl.class);

    /*
     * La documentacion de este metodo se encuentra en la declaracion de la interface o clase
     * (non-Javadoc)
     * @see com.synergyj.concepto.spring.service.webservice.client.
     * RecepcionNotificacionesPort#getCliente()
     */
    public ClienteRecepcionNotificaciones getCliente() {

        ClienteRecepcionNotificaciones stub = null;
        try {

            if (servicio == null) {
                logger.debug("Creando el servicio para crear el proxy");
                URL url;
                url = new URL(urlWsdl);
                QName nombre = new QName(nameSpaceServicio, nombreServicio);
                QName nombreQPuerto = new QName(nameSpaceServicio, nombrePuerto);

                servicio = javax.xml.ws.Service.create(url, nombre);
                servicio.setHandlerResolver(new HandlerResolver() {
                    @Override
                    public List<Handler> getHandlerChain(PortInfo portInfo) {

                        List<Handler> listaHandlers = new ArrayList<Handler>();

                        listaHandlers.add(validacionHandlerCliente);

                        return listaHandlers;

                    }
                });

                stub = servicio.getPort(nombreQPuerto, ClienteRecepcionNotificaciones.class);

            } else {

                stub = cliente;

            }

        } catch (MalformedURLException e) {

            logger.debug("Hubo un error al crear la url del wsdl ", e);

            throw new RuntimeException("Hubo un error al crear la url del wsdl ", e);

        }

        return stub;

    }

}
