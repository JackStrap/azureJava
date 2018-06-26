package org.mavenTest.mavenWebApp;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;

@Named("basePage")
@RequestScoped
public class BasePage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String renderMenu = "false";
	private boolean renderMenuB = true;
	
	private String restVersion;
	private String jvmVersion;
	private String osVersion;
	private String serverVersion;
	private String jerseyVersion;
	
	
	@PostConstruct
	public void pageLoad() {
		
	}
	
	public String goToIndex() {
		
		return "index?faces-redirect=true";
    }
	
	public String goToHome() {
		return "homePage?faces-redirect=true";
    }
	
	public void VersionModel(ServletContext context) {
		//restVersion = RESTServlet.VERSION_STRING;
		jvmVersion = System.getProperty("java.vm.vendor") + ' ' + System.getProperty("java.version") + '-' + System.getProperty("java.vm.version");
		osVersion = System.getProperty("os.name") + ' ' + System.getProperty("os.version") + ' ' + System.getProperty("os.arch");
		serverVersion = context.getServerInfo();
		//jerseyVersion = ServletContainer.class.getPackage().getImplementationVersion();
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
	

	public String getRestVersion() {
		return restVersion;
	}

	public void setRestVersion(String restVersion) {
		this.restVersion = restVersion;
	}

	public String getJvmVersion() {
		return jvmVersion;
	}

	public void setJvmVersion(String jvmVersion) {
		this.jvmVersion = jvmVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getJerseyVersion() {
		return jerseyVersion;
	}

	public void setJerseyVersion(String jerseyVersion) {
		this.jerseyVersion = jerseyVersion;
	}

}
