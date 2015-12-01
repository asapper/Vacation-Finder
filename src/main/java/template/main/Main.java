package template.main;
import java.util.ArrayList;

import template.controllers.*;

public class Main {
	
	private static Facade thefacade = Facade.getInstance();
	
	public static void main(String[] args) {
		System.setProperty("environment", "dev");
		Application.run(args);
		
		/*System.out.println("Testing Facade Attributes");
		ArrayList<Business> businesses = thefacade.getBusinesses();
		
		if(thefacade.formRead()){
			for(int i = 0; i < businesses.size(); i++){
				System.out.println(businesses.get(i).getName());
			}
		}
		
		System.out.println("Facade Attributes Tested");*/
		
		//thefacade.callYelpAPI(b);
		//thefacade.callGoogleAPI(b);
		//thefacade.callAlgorithm();
	}
}
