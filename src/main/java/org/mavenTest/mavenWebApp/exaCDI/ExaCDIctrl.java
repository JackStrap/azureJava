package org.mavenTest.mavenWebApp.exaCDI;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;


@Named
//@SessionScoped
@RequestScoped
public class ExaCDIctrl implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<LineItem> lineItems = new ArrayList<>();

    private String importantCustomerInformation;

    private String debugMessage;

    @Inject
    @EJB
    private MemoryDatabase database;

    @Inject
    private Utility utility;

    @Inject
    private SecureDomain secureDomain;

    @PostConstruct
    public void initialise() {
        lineItems = database.defaultLineItems();
        //lineItems = LineItem.generate(25);
    }

    //public String doList() {
    public void doList() {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        //pw.printf("%s.doOrder()\n", this.getClass().getSimpleName());
        pw.printf("%s.%s\n", this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName());
        pw.printf("  shoppingCartController = %s\n", utility.debugHashCode( this ));
        pw.printf("  database = %s\n", utility.debugHashCode( database ));
        pw.printf("  utility = %s\n", utility.debugHashCode(utility));
        pw.printf("  secureDomain = %s\n", utility.debugHashCode(secureDomain));
        pw.printf("  secureDomain.domain = %s\n", secureDomain.getDomain());
        debugMessage = StringUtils.trimToEmpty(sw.toString());
        //return "exaCDI.xhtml";
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public String doOrder() {
        return "order.xhtml";
    }

    public String getImportantCustomerInformation() {
        return importantCustomerInformation;
    }

    public void setImportantCustomerInformation(String importantCustomerInformation) {
        this.importantCustomerInformation = importantCustomerInformation;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
