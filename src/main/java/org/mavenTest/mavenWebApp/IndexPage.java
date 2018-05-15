package org.mavenTest.mavenWebApp;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
import javax.inject.Named;


@Named("indexPage")
@RequestScoped
public class IndexPage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String usrPwd;
	private String usrName;
	
//	@Inject
//	private BasePage bp;
	
	@PostConstruct
	public void pageLoad() {
		
	}
	
	public String login() {
		//bp.setRenderMenuB(true);
		return "homePage?faces-redirect=true";
    }
	
	
	// Get & Set
	public String getUsrName() {
		if (usrName == null) {
			usrName = "jack000";
		}
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	
	public String getUsrPwd() {
		return usrPwd;
	}

	public void setUsrPwd(String usrPwd) {
		this.usrPwd = usrPwd;
	}
}
