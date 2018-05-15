package org.mavenTest.mavenWebApp;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("homePage")
@RequestScoped
public class HomePage implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public String goToHome() {
        return "homePage?faces-redirect=true";
    }
	
	public String goToExcel() {
        return "expXLS";
    }
	
	public String goToPfDtTbl() {
        return "pfDataTbl";
    }
	
	public String goToPF() {
        return "pf";
    }
	
	public String goToAzure() {
        return "azureHome";
    }
	
	public String goToExaCDI() {
        return "exaCDI";
    }
}
