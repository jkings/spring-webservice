/**
 * RecepcionNotificacionesPort.java
 * Fecha de creaci�n: 15/11/2011 , 16:02:24
 *
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial, propiedad del
 * Synergyj. Esta informaci�n confidencial
 * no deber� ser divulgada y solo se podr� utilizar de acuerdo
 * a los t�rminos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.client;

/**
 * Esta interfaz obtiene el cliente proxeado para invocar el webservice
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */
public interface RecepcionNotificacionesPort {

    /**
     * 
     * Obtiene el cliente para hacer uso del webservices
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     * @return
     */
    ClienteRecepcionNotificaciones getCliente();

}
