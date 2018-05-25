package org.mavenTest.mavenWebApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Named("javaPdf")
@RequestScoped
public class Java2PDF implements Serializable{

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String FILEPDF = "html2PDF.pdf";
	private static final String RESTEMP = "newFile.html";
	
	private StreamedContent file;
	
	
	// ----------------------------
	// Actions
	// ----------------------------
	@PostConstruct
	public void pageLoad() {
		
	}
	
	public String goToHtml() {
		return "newFile.html";
	}
	
	// ----------------------------
	// Methods
	// ----------------------------
	public void produireRapport() {
		log.debug("produireRapport: rapportInfoEmpAss");
		
		try {
//			String filePDF = String.format("html2Pdf.pdf");
//			String fileName = "newFile.html";
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			OutputStream os = ec.getResponseOutputStream();
			
			Document document = new Document();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILEPDF));
			
			document.open();
			
			//InputStream resFile = this.getClass().getClassLoader().getResourceAsStream(fileName);
			InputStream resFile = this.getClass().getResourceAsStream(RESTEMP);
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, new InputStreamReader(resFile));
			
			document.close();

			// those next line are not necessary
			ec.setResponseContentType("application/pdf");
			ec.setResponseHeader("Expires", "0");
			ec.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			ec.setResponseHeader("Pragma", "public");
			
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + FILEPDF + "\"");
			
			ec.setResponseContentLength(baos.size());
			
			baos.writeTo(os);
			os.flush();
			os.close();
			
			InputStream dataIS = new ByteArrayInputStream(baos.toByteArray());
			file = new DefaultStreamedContent(dataIS, "application/pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ----------------------------
	// Getters && Setters
	// ----------------------------
	public StreamedContent getFile() {
	      try {
	         this.produireRapport();
	      } catch (Exception e) {
	         log.error("Une erreur est survenue dans la génération du rapport. {}", e);
	      }
	      return file;
	   }
}
