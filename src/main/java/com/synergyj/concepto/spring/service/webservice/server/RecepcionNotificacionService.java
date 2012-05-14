/**
 * RecepcionNotificacionService.java Fecha de creaci�n: 03/11/2011 , 12:12:01
 * 
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial, propiedad del
 * Synergyj. Esta informaci�n confidencial
 * no deber� ser divulgada y solo se podr� utilizar de acuerdo
 * a los t�rminos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.server;


/**
 * Esta interfaz definen los metodos para recibbir una notificacion y regresar un respuesta
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */

public interface RecepcionNotificacionService {

    /**
     * 
     * Este metodo recibira la notificacion y la persistira en base de datos y regresara una
     * respuesta en XML
     * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
     * @param notificacion
     * @param idModulo
     * @param idTipoNotificacion
     * @return La respuesta
     */
    String recibeNotificacion(String notificacion, String idModulo,
        int idTipoNotificacion);
}
