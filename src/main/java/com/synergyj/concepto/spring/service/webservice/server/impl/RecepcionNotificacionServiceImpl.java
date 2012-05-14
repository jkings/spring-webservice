/**
 * RecepcionNotificacionServiceImpl.java
 * Fecha de creación: 03/11/2011 , 12:22:30
 *
 * Copyright (c) 2012 Synergyj. 
 * Todos los derechos reservados.
 *
 * Este software es información confidencial, propiedad del
 * Synergyj. Esta información confidencial
 * no deberá ser divulgada y solo se podrá utilizar de acuerdo
 * a los términos que determine la empresa.
 */
package com.synergyj.concepto.spring.service.webservice.server.impl;

import com.synergyj.concepto.spring.service.webservice.server.RecepcionNotificacionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * Clase que implementa el proceso de recepion de notificacion y persistencia de la base de
 * datos.
 * @author Juan Manuel Reyes Medina @jkingsj (reyesmjm@gmail.com)
 * @version 1.0
 * 
 */

@Service("recepcionNotificacionService")
public class RecepcionNotificacionServiceImpl implements RecepcionNotificacionService {

    private static final Log logger =
        LogFactory.getLog(RecepcionNotificacionServiceImpl.class);


    /* La documentacion de este metodo se encuentra  en la declaracion de
     * la interface o clase (non-Javadoc)
     * @see com.synergyj.concepto.spring.service.RecepcionNotificacionService#recibeNotificacion(java.lang.String, java.lang.String, int)
     */

    public String recibeNotificacion(String notificacion, String idModulo,
        int idTipoNotificacion) {

        logger.debug("Recibiendo los siguientes datos: idModulo" + idModulo
            + " idTipoIdentificacion " + idTipoNotificacion);

        logger.debug("La notificacion es: " + notificacion);

        StringBuffer buffer = new StringBuffer();

        buffer.append("<respuesta>Se proceso correctamente  </respuesta>");

        return buffer.toString();

    }

}
