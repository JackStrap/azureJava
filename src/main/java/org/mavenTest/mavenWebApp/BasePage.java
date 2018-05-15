package org.mavenTest.mavenWebApp;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("basePage")
@RequestScoped
public class BasePage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String renderMenu = "false";
	private boolean renderMenuB = true;
	
	
	@PostConstruct
	public void pageLoad() {
		
	}
	
	public String goToIndex() {
		
		return "index?faces-redirect=true";
    }
	
	public String goToHome() {
		return "homePage?faces-redirect=true";
    }
	
	
	// Get & Set
	public String getRenderMenu() {
		return renderMenu;
	}

	public void setRenderMenu(String renderMenu) {
		this.renderMenu = renderMenu;
	}

	public boolean isRenderMenuB() {
		return renderMenuB;
	}

	public void setRenderMenuB(boolean renderMenuB) {
		this.renderMenuB = renderMenuB;
	}
	
	
}
