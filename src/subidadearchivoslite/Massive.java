

package subidadearchivoslite;

import com.google.gdata.util.ServiceException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author CS SPARE
 */
public class Massive
{
	
	
	private String filename, onlyname, vamos;
	
	
	//*******************************************************
        private boolean  UK;
        
        
	private String username = "raremusicorders";
	private String password = "*******";
	private String from = "******@gmail.com";
	private String to = "*******@gmail.com";	
	
	private DateFormat dateFormat, dateFormatT0M;
	private Date date;
	
	private Properties props;
	private Session session;
       
	
	
	//********************************************************
	
	
	public Massive(String absoluto, String solonombre, String vamos, boolean UK)
	{
		
		this.UK = UK;
                
                if(!this.UK) // USA
                {
                    this.username = "******.com";
                    this.password = "******";
                    this.from = "e*****";
                    this.to = "******";
                }
                
		this.filename = absoluto;
                this.onlyname = solonombre;
                this.vamos = vamos;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormatT0M = new SimpleDateFormat("ddMMyy");
		date = new Date();
		
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		this.createSession();
		
	}
	
	
	
	private void createSession()
	{
		session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
	}
        
        
        
        private String masCadena(String supplier)
        {
            
            String cadena = "";
            
            ArrayList<String> listado = new ArrayList<String>();
            listado.add("CENTRESOFT");
            listado.add("MSE WS");
            listado.add("CREATIVE");
            listado.add("LINK");
            
            Calendar old = Calendar.getInstance();
            old.set(Calendar.HOUR_OF_DAY, 15);
            Calendar now = Calendar.getInstance();
            
            
            if(listado.contains(supplier))
            {
                if(old.before(now)) cadena = "PM ";
                else cadena = "AM ";
            }
            
           return cadena;
            
            
            
            
        }
        
	
	public void accionEnviarMails(String autor, String hojaElegida ) throws IOException
	{
		//***********************+
		

		
 
		try 
		{
 
			// Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));
	         
	         String subject = "";
                 String message_body = "";
                 
	         if(autor.compareTo("Sebas")!=0)
                 { 
                     subject = this.vamos + " update "+dateFormatT0M.format(date)+" by "+autor;
                     message_body = "";
                 }   
                 else
                 {
                     subject = "SNIMPORT_"+this.vamos+"_UPD "+this.masCadena(this.vamos)+dateFormat.format(date);
                     message_body = "By "+autor;
                 }
                     
                     
			
				
				
                                       // String subject = this.onlyname.substring(0, this.onlyname.length()-4)+" "+dateFormat.format(date);
                                        //String subject = "SNIMPORT_"+this.vamos+"_UPD "+this.masCadena(this.vamos)+dateFormat.format(date);
                                       // String message_body = "By "+autor;
				
				         // Set Subject: header field
				         message.setSubject(subject);
			
				         // Create the message part 
				         BodyPart messageBodyPart = new MimeBodyPart();
			
				         // Fill the message
				         messageBodyPart.setText(message_body);
				         
				         // Create a multipar message
				         Multipart multipart = new MimeMultipart();
			
				         // Set text message part
				         multipart.addBodyPart(messageBodyPart);
			
				         // Part two is attachment
				         messageBodyPart = new MimeBodyPart();
				        // String filename = "SNIMPORT_TEST_UPD.xls";
				         
				         DataSource source = new FileDataSource(filename);
				         messageBodyPart.setDataHandler(new DataHandler(source));
				         messageBodyPart.setFileName(this.onlyname);//NOMBRE QUE TENDRA EL ARCHIVO EN EL ATTACHED FILE
				         multipart.addBodyPart(messageBodyPart);
			
				         // Send the complete message parts
				         message.setContent(multipart );
			
				         // Send message
				         Transport.send(message);
                                         System.out.println("Mensaje mandado correctamente!!!!!!!!!!");
                 
				         
				             
	        
	         
	         
	         
	        
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} 
		
		
		//***********************+ 
	}
	



	
	
}

