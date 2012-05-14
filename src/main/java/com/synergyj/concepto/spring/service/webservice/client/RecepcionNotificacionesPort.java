/**
 * RecepcionNotificacionesPort.java
 * Fecha de creación: 15/11/2011 , 16:02:24
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
