package org.mavenTest.mavenWebApp.employees;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Object collection output demo
 * @author Leonid Vysochyn
 */

@Named("objCollCtrl")
@RequestScoped
public class ObjectCollectionDemo {
    private static Logger logger = LoggerFactory.getLogger(ObjectCollectionDemo.class);

    public void expItemCtrl() throws Exception {
        logger.info("Running Object Collection demo2");
        List<Itemline> itemlines = makeItemlineData();
        //${itemline.id}	${itemline.productName}	${itemline.price}	${itemline.quantity}
        //${itemline.id}	${itemline.productName}	${itemline.price}	${itemline.quantity}
        //private int id;    	private String productName;    	private BigDecimal price;    	private int quantity;
        
        try(InputStream is = ObjectCollectionDemo.class.getResourceAsStream("object_collection_template2.xls")) {
            try (OutputStream os = new FileOutputStream("c:\\Tmp\\zobject_collection_output2.xls")) {
                Context context = new Context();
                context.putVar("itemlines", itemlines);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
            catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    
    public static List<Itemline> makeItemlineData() throws ParseException {
        List<Itemline> itemlines = new ArrayList<Itemline>();
        itemlines.add( new Itemline( 1200, "Iron Widget", 49.99, 36) );
        itemlines.add( new Itemline( 4520, "Power-core fitness bar", 19.99, 3) );
        itemlines.add( new Itemline( 3720, "Cereal bar breakfast", 3.99, 12) );
        itemlines.add( new Itemline( 1300, "Iron Bean", 99.99, 66) );
        itemlines.add( new Itemline( 3750, "Power-Bar breakfast", 13.99, 22) );
        return itemlines;
    }    
    
    
    
    /******************************************************************************/
    public void expObjCollDemo() throws Exception {
        logger.info("Running Object Collection demo");
        List<Employee> employees = generateSampleEmployeeData();
        try(InputStream is = ObjectCollectionDemo.class.getResourceAsStream("object_collection_template.xls")) {
            try (OutputStream os = new FileOutputStream("c:\\Tmp\\zobject_collection_output.xls")) {
                Context context = new Context();
                context.putVar("employees", employees);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
    }

    public static List<Employee> generateSampleEmployeeData() throws ParseException {
        List<Employee> employees = new ArrayList<Employee>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
        employees.add( new Employee("Elsa", dateFormat.parse("1970-Jul-10"), 1500, 0.15) );
        employees.add( new Employee("Oleg", dateFormat.parse("1973-Apr-30"), 2300, 0.25) );
        employees.add( new Employee("Neil", dateFormat.parse("1975-Oct-05"), 2500, 0.00) );
        employees.add( new Employee("Maria", dateFormat.parse("1978-Jan-07"), 1700, 0.15) );
        employees.add( new Employee("John", dateFormat.parse("1969-May-30"), 2800, 0.20) );
        return employees;
    }
    
}
