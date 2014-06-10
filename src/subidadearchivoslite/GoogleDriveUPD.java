/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package subidadearchivoslite;




import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.net.*;
import java.util.*;    

/**
 *
 * @author CS SPARE
 */
public class GoogleDriveUPD 
{
    
    
    
    private String filename, onlyname, proveedor_nombre;
    
    
    
    public GoogleDriveUPD(String absoluto, String solonombre)
    {
        this.filename = absoluto;
        this.onlyname = solonombre;
        
    }
    
    public void actualizacionGDrive(String supplier, String spreadsheetRAREWAVES, String autor, String nombreSpreadGDrive, boolean UK ) throws IOException, ServiceException
    {
        
                
        this.proveedor_nombre = supplier;
        //**************************************************************************************
        //**************************************************************************************
        
        SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration-v1");

        // TODO: Authorize the service object for a specific user (see other sections)
        service.setUserCredentials("*************@gmail.com", "**********");

        // Define the URL to request.  This should never change.
        URL SPREADSHEET_FEED_URL = new URL(
        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");

        // Make a request to the API and get all spreadsheets.
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
        
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
        
        //**************************************************************************************
        //**************************************************************************************
        
        
        
        
        //Voy a intentar de buscar el uk list....
        int fijo = 0;
        boolean found = false;
        for(int i=0; i<spreadsheets.size() && !found; i++)
        {
                
                if(spreadsheets.get(i).getTitle().getPlainText().compareTo(spreadsheetRAREWAVES) == 0)
                {
                        found = true;
                        fijo = i;
                }

        }
        //**************************************************************************************
        //**************************************************************************************
        
        
        SpreadsheetEntry spreadsheet = spreadsheets.get(fijo); //Selecciono la hoja de calculo adecuada
        
        // Get the first worksheet of the first spreadsheet.
        // TODO: Choose a worksheet more intelligently based on your
        // app's needs.
        WorksheetFeed worksheetFeed = service.getFeed(spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);		
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        WorksheetEntry worksheet = worksheets.get(0);


        // Fetch the list feed of the worksheet.
        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
        //**************************************************************************************
        //**************************************************************************************
        
        
        
        
        // Iterate through each row, printing its cell values.

        List<ListEntry> vamos  = listFeed.getEntries();
        boolean found2 = false;

       System.out.println("el tam es de: "+vamos.size());
        

        for(int j=0; j<vamos.size() && !found2; j++) //RECORREMOS TODAS LAS FILAS EN BUSCA DEL VALOR!
        {


           


            
                //1. He de encontrador el proveedor que le paso por parametros.
            //PROBANDO EL TEMA DE VER SI LA CADENA ESTA DENTRO DE OTRA...
            
            if(vamos.get(j).getCustomElements().getValue("SUPPLIER").compareTo(supplier) == 0)
            {

                    

                    
                    //SUBIDA DEL ARCHIVO
                    Massive sebas = new Massive(this.filename, this.onlyname, this.proveedor_nombre, UK); //NUEVOOOOOOOOOOOOOOOOOOO
                    sebas.accionEnviarMails(autor, nombreSpreadGDrive); //NUEVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
                    //SUBIDA DEL ARCHIVO

                    found2 = true;
                    //ACTUALIZACION DE VALORES fechaaaaaaaa
                    //vamos.get(j).getCustomElements().setValueLocal("FREQUENCY", getUSADate());
                    vamos.get(j).getCustomElements().setValueLocal("LAST.UPDATED", getUSADate());vamos.get(j).update();
                    //---------------------------------------------------


            }            
            //-------------------------------------------------------------

                



                

        }
        
        if(!found)
        {
            System.out.println("No se ha encontrado el proveedor: "+supplier);
        }
        //**************************************************************************************
        //**************************************************************************************
                
        
        
        
        
        
    }
    
    
    
    
    //AQUI OBTENGO LA HORA EN TIEMPO REAL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//Una vez tengo preparado esto puedo actualizar todo
    private String getUSADate()
    {

            Date date = new Date();   // given date
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(date);   // assigns calendar to given date 


            String dia, mes, annio, resultado = "";


            dia = Integer.toString(calendar.get(Calendar.DATE));
            mes = Integer.toString((calendar.get(Calendar.MONTH))+1);
            annio = Integer.toString(calendar.get(Calendar.YEAR)); 




            resultado = mes+"/"+dia+"/"+annio+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);

            return resultado;

    }
    
    
}
