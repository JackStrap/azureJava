package org.mavenTest.mavenWebApp;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Named("javaPdf")
@RequestScoped
public class Java2PDF implements Serializable{

	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	//private static final String PDF = "src/main/resources/pdf.pdf";
	private static final String HTML = "src/main/resources/newFile.html";
	
	private StreamedContent file;
	
	
	// ----------------------------
	// Actions
	// ----------------------------
	@PostConstruct
	public void pageLoad() {
		/*
		try {
			//generateHTMLFromPDF(PDF);
			//generatePDFFromHTML(HTML);

		} catch (IOException | ParserConfigurationException | DocumentException e) {
			e.printStackTrace();
		}
		*/
	}
	
	// ----------------------------
	// Methods
	// ----------------------------
	public void createPDF() {
		try {
			generatePDFFromHTML(HTML);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    }
	
	private static void generatePDFFromHTML(String filename) 
			throws ParserConfigurationException, IOException, DocumentException {
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		
		Document document = new Document();
		//PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/output/html.pdf"));
		PdfWriter writer = PdfWriter.getInstance(document, ec.getResponseOutputStream());
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
		document.close();
	}
	
	public void produireRapport() {
		log.debug("produireRapport: rapportInfoEmpAss");
		
		String fileName = String.format("Html2Pdf.pdf");

		InputStream dataIS = new ByteArrayInputStream(donnees);
		file = new DefaultStreamedContent(dataIS, "application/pdf", fileName);
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
