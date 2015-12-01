package template.controllers;

import java.util.ArrayList;
import java.util.HashSet;

public class Facade {
	
	private static Facade instance = new Facade();
	static Business theBusiness = Business.getInstance();
	private static Form theForm = new Form();
	private static String[] cmdArgs = {""};
	private static ArrayList<Business> YelpBusinesses = new ArrayList<>();
	private static ArrayList<Business> GoogleBusinesses = new ArrayList<>();
	private static ArrayList<Business> CombinedBusinesses = new ArrayList<>();
	private static HashSet<Business> businesses = new HashSet<>();
	private static boolean formRead = false;
	
	private Facade(){
	}
	
	public static Facade getInstance(){
		return instance;
	}
	
	public void callYelpAPI(ArrayList<Business> arrayList){
		formRead = true;
		YelpBusinesses = arrayList;
	}
	
	public void callGoogleAPI(ArrayList<Business> arrayList){
		formRead = true;
		GoogleBusinesses = arrayList;
	}
	
	public void callAlgorithm(){
		Algorithm.algorithm(CombinedBusinesses);
	}
	
	public void getForm(Form form){
		theForm = form;
	}
	
	public boolean formRead(){
		return formRead;
	}
	
	public HashSet<Business> getBusinesses(){
		return businesses;
	}
	
	public ArrayList<Business> getYelpBusinesses(){
		return YelpBusinesses;
	}
	
	public ArrayList<Business> getGoogleBusiness(){
		return GoogleBusinesses;
	}
	
	public void printResults(){
		System.out.println("Yelp Businesses\n");
		for(int i = 0; i < YelpBusinesses.size(); i++){
			System.out.println(YelpBusinesses.get(i).getName() + " Price: " +
					YelpBusinesses.get(i).getPrice() + " Rating: " +
					YelpBusinesses.get(i).getRating());
		}
		
		System.out.println("");
		
		System.out.println("Google Businesses\n");
		for(int i = 0; i < GoogleBusinesses.size(); i++){
			System.out.println(GoogleBusinesses.get(i).getName() + "\t\t Price: " +
					GoogleBusinesses.get(i).getPrice() + "\t\t Rating: " +
					GoogleBusinesses.get(i).getRating());
		}
		
		System.out.println("\nCombined Results\n");
		businesses.addAll(YelpBusinesses);
		businesses.addAll(GoogleBusinesses);
		CombinedBusinesses.addAll(businesses);
		
		for(int i = 0; i < businesses.size(); i++){
			System.out.println(CombinedBusinesses.get(i).getName());
		}
		
		callAlgorithm();
		
		System.out.println("\nTop 10 Sorted Vacations\n");
		
		for(int i = 0; i < 10; i++){
			System.out.println(CombinedBusinesses.get(i).getName() + "\t\t Price: "+
					CombinedBusinesses.get(i).getPrice() + "\t\t Rating: " +
					CombinedBusinesses.get(i).getRating());
		}
		
	}
	
	public double coordinatesToMiles(Coordinate from, Coordinate to){
		double miles = -1;
		double fromLat = from.getLatitude() * Math.PI / 180;
		double toLat = to.getLatitude() * Math.PI / 180;
		double fromLon = from.getLongitude() * Math.PI / 180;
		double toLon = to.getLongitude() * Math.PI / 180;
		double a = Math.pow(Math.sin((toLon - fromLon) / 2), 2.0);
		double b = Math.cos(fromLat) * Math.cos(toLat);
		double c = Math.pow(Math.sin((toLat - fromLat) / 2), 2.0);
		double d = Math.sqrt(c + a * b);
		double e = Math.asin(d);
		miles = 2 * 3959 * e;
		return miles;
		}
	
}
