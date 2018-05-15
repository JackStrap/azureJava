package org.mavenTest.mavenWebApp.poiExcel;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiContext;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.mavenTest.mavenWebApp.exaCDI.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("xlsStressCtrl")
@RequestScoped
public class StressDemo implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(StressDemo.class);
    
	@Inject
	private ExpTblXls expXls;
	
	private static String contentTypeXLS = "application/vnd.ms-excel";
	private static String contentTypeXLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	public static final int ITEM_COUNT = 2500;

	private String xlsTemplate = "ZitemTemp";
	
	public void execStressTemp() throws Exception {
	    try {
	    	logger.info("Running Stress demo template");
	        logger.info("Generating " + ITEM_COUNT + " items..");
	        
	        String fileName = "execStressTemp";
	        fileName += "." + expXls.getRadXls();
	    	
	    	List<LineItem> lineItems = LineItem.generate(ITEM_COUNT);
	    	
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			ec.responseReset(); 
			//ec.setResponseContentType(StressDemo.contentTypeXLS);
			if (expXls.getRadXls().equalsIgnoreCase("xls")) {
				ec.setResponseContentType(StressDemo.contentTypeXLS);
			}
			else {
				ec.setResponseContentType(StressDemo.contentTypeXLSX);
			}
			
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			OutputStream output = ec.getResponseOutputStream();
			
			 xlsTemplate += "." + expXls.getRadXls();
			
			try(InputStream is = new BufferedInputStream(getClass().getResourceAsStream(xlsTemplate))) {
				Context context = new Context();
				context.putVar("lineItems", lineItems);
				JxlsHelper.getInstance().processTemplate(is, output, context);
			}

			fc.responseComplete();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execStress() throws Exception {
        logger.info("Running Stress demo");
        logger.info("Generating " + ITEM_COUNT + " items..");
        
        String fileName = "execStress";
        fileName += "." + expXls.getRadXls();
        
        List<LineItem> lineItems = LineItem.generate(ITEM_COUNT);
        logger.info("Created " + lineItems.size() + " items");
        
        FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		ec.responseReset(); 
		if (expXls.getRadXls().equalsIgnoreCase("xls")) {
			ec.setResponseContentType(StressDemo.contentTypeXLS);
		}
		else {
			ec.setResponseContentType(StressDemo.contentTypeXLSX);
		}
		
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		xlsTemplate += "." + expXls.getRadXls();
		
		try(InputStream is = StressDemo.class.getResourceAsStream(xlsTemplate)) {
			try (OutputStream output = ec.getResponseOutputStream()) {
                Transformer transformer = TransformerFactory.createTransformer(is, output);
                AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
                List<Area> xlsAreaList = areaBuilder.build();
                Area xlsArea = xlsAreaList.get(0);
                
                Context context = PoiTransformer.createInitialContext();
                context.putVar("lineItems", lineItems);
                
                long startTime = System.nanoTime();
                
                xlsArea.applyAt(new CellRef("Sheet1!A1"), context);
                xlsArea.processFormulas();
                
                long endTime = System.nanoTime();
                System.out.println("Stress demo 1 time (s): " + (endTime - startTime) / 1000000000);
                transformer.write();
            }
        }
		fc.responseComplete();
    }
	
	public void execStressPoi() throws Exception {
        logger.info("Running Stress demo POI");
        logger.info("Generating " + ITEM_COUNT + " items..");
        
        String fileName = "execStressPoi";
        fileName += "." + expXls.getRadXls();
        
        List<LineItem> lineItems = LineItem.generate(ITEM_COUNT);
        logger.info("Created " + lineItems.size() + " items");
        
        FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		ec.responseReset(); 
		
		if (expXls.getRadXls().equalsIgnoreCase("xls")) {
			ec.setResponseContentType(StressDemo.contentTypeXLS);
		}
		else {
			ec.setResponseContentType(StressDemo.contentTypeXLSX);
		}
		
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		xlsTemplate += "." + expXls.getRadXls();
		
        try(InputStream is = StressDemo.class.getResourceAsStream(xlsTemplate)) {
        	try (OutputStream output = ec.getResponseOutputStream()) {
            	Transformer transformer = PoiTransformer.createTransformer(is, output);
                AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
                List<Area> xlsAreaList = areaBuilder.build();
                Area xlsArea = xlsAreaList.get(0);
                
                Context context = new PoiContext();
                context.putVar("lineItems", lineItems);
                
                long startTime = System.nanoTime();
                
                xlsArea.applyAt(new CellRef("Sheet1!A1"), context);
                xlsArea.processFormulas();
                
                long endTime = System.nanoTime();
                
                System.out.println("Stress demo 1 time (s): " + (endTime - startTime) / 1000000000);
                transformer.write();
            }
        }
		fc.responseComplete();
    }
	
}
