/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package subidadearchivoslite;

import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author CS SPARE
 */
public class main
{
    
    public static void main(String args[]) 
    {
        System.out.println("Iniciando el programa...");
       
                              
            Calendar old = Calendar.getInstance();
            old.set(Calendar.HOUR_OF_DAY, 18);
            Calendar now = Calendar.getInstance();
            System.out.println("Is old before now ? : " + old.before(now));

           
           
        
        
        
        
    }
    
}
