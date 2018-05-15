package org.mavenTest.mavenWebApp.restTest;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/rest/{id}")
public class RestService {
	
	@javax.ws.rs.GET
	   // Get credit score for the member id passed over the HTTP URL
	   public String getRest(@PathParam("id") String memberId) {
	      return "Credit score for MemberId- "+memberId+" = "+getCreditScoreFromStore(memberId);
	   }
	   private String getCreditScoreFromStore(String id)
	   {
	      // for demo purpose, return some random number between 200-800
	      int minimum = 200;
	      int maximum = 400;
	      return new Integer(minimum + (int)(Math.random()*maximum)).toString();             
	   }  
}
