package org.mavenTest.mavenWebApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.mavenTest.mavenWebApp.exaCDI.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("pfDtTbl")
@RequestScoped
public class PfDataTbl  implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(PfDataTbl.class);
	
	private int nbrRow = 0;
	private List<LineItem> lineItems = new ArrayList<>();

	@PostConstruct
	public void initialise() {
		nbrRow = 250;
		logger.trace("# rows: " + nbrRow);
		
		lineItems = LineItem.generate(nbrRow);
	}

	
	/* Getter & Setter */
	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}
	
	public int getNbrRow() {
		return nbrRow;
	}

	public void setNbrRow(int nbrRow) {
		this.nbrRow = nbrRow;
	}

}
