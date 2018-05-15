package org.mavenTest.mavenWebApp.poiExcel;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.mavenTest.mavenWebApp.exaCDI.LineItem;

//import net.sf.jxls.transformer.XLSTransformer;


public class zOldNotUsed {
	
	@SuppressWarnings("unused")
	private OutputStream jxlsData(OutputStream os) {
		try {
			List<LineItem> lineItems = generateSampleEmployeeData();
			
			try(InputStream is = new BufferedInputStream(getClass().getResourceAsStream("itemTemp.xlsx"))) {
				//Context context = PoiTransformer.createInitialContext();
				Context context = new Context();
				context.putVar("lineItems", lineItems);
				JxlsHelper.getInstance().processTemplate(is, os, context);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return os;
	}
	
	@SuppressWarnings("unused")
	private int getOutSize(OutputStream os) {
		int osSize = 0;
		try {
			DataOutputStream dos = new DataOutputStream(os);
			osSize = dos.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return osSize;
	}

	/*************************************************************************/
	public void jxlsExport() {
		List<LineItem> lineItems = generateSampleEmployeeData();
		
		try(InputStream is = new BufferedInputStream(getClass().getResourceAsStream("itemTemp.xlsx"))) {
			try (OutputStream os = new FileOutputStream("c:\\Tmp\\zexport_output.xlsx")) {
				//Context context = PoiTransformer.createInitialContext();
				Context context = new Context();
				context.putVar("lineItems", lineItems);
				JxlsHelper.getInstance().processTemplate(is, os, context);

				// ${lineItem.id}	${lineItem.productName}	${lineItem.price}	${lineItem.quantity}

//				InputStream xlsTemplateFileName = new BufferedInputStream(getClass().getResourceAsStream("itemTemp.xls"));
//				Map beans = new HashMap();
//				beans.put("lineItems", lineItems);
//				XLSTransformer transformer = new XLSTransformer();
//				transformer.transformXLS(xlsTemplateFileName, beans, "Demo.xls");
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
//		List<String> headers = Arrays.asList("ID", "ProductName", "Price", "Quantity");
//		SimpleExporter exporter = new SimpleExporter();
//		try (InputStream isT = new BufferedInputStream(getClass().getResourceAsStream("simple_export_template.xlsx"))) {
//            try (OutputStream os2 = new FileOutputStream("c:\\Tmp\\zexport_output2.xlsx")) {
//                exporter.registerGridTemplate(isT);
//                exporter.gridExport(headers, lineItems, "id,productName, price, quantity,", os2);
//            }
//        }
//		catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	
	public static List<LineItem> generateSampleEmployeeData() {
        List<LineItem> lineItems = new ArrayList<LineItem>();
        
        try {
			lineItems.add( new LineItem( 1200L, "Iron Widget", 49.99F, 36) );
			lineItems.add( new LineItem( 4520L, "Power-core fitness bar", 19.99F, 3) );
			lineItems.add( new LineItem( 3720L, "Cereal bar breakfast", 3.99F, 12) );
			lineItems.add( new LineItem( 1300L, "Iron Bean", 99.99F, 66) );
			lineItems.add( new LineItem( 3750L, "Power-Bar breakfast", 13.99F, 22) );
		} catch (Exception e) {
			e.printStackTrace();
		}
        return lineItems;
    }
	/*************************************************************************/
}
