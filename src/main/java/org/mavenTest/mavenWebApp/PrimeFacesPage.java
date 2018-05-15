package org.mavenTest.mavenWebApp;

import java.io.Serializable;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("pfPage")
@RequestScoped
public class PrimeFacesPage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message = "Your Java app is up and running on Azure with a back-end msg";

	private Date dtPf;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDtPf() {
		return dtPf;
	}

	public void setDtPf(Date dtPf) {
		/*
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		try {
			String tmpDate = dateFormat.format(dtPf);
			
			this.dtPf = dateFormat.parse(tmpDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		*/
		this.dtPf = dtPf;
	}

	public void click() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		//DateFormat dtFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
        context.addMessage(null, new FacesMessage("Successful",  "Your message: " + dtPf.toString()) );
		//context.addMessage(null, new FacesMessage("Successful",  "Your message: " + dtFormat.format(dtPf)) );
		
        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
        
//        PrimeFaces.current().ajax().update("form:display");
//        PrimeFaces.current().executeScript("PF('dlg').show()");
    }
}
