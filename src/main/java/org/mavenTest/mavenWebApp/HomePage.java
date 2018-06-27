package org.mavenTest.mavenWebApp;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named("homePage")
@RequestScoped
public class HomePage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String baseUrl;
	
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
	
	public String goToPDF() {
        return "java2PDF";
    }

	public String getBaseUrl() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = req.getRequestURL().toString();
		baseUrl = url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + "/";

		//baseUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
		//baseUrl = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL().toString();
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
