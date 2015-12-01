package template.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Facade {
	
	private static Facade instance = new Facade();
	static Business theBusiness = Business.getInstance();
	private static Form theForm = new Form();
	private static String[] cmdArgs = {""};
	private static ArrayList<Business> YelpBusinesses = new ArrayList<>();
	private static ArrayList<Business> GoogleBusinesses = new ArrayList<>();
	private static ArrayList<Business> CombinedBusinesses;
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
	
	/**
	 * Combines the lists, runs the businesses through the algorithm, and prints results
	 */
	public void printResults(){
		/*System.out.println("Yelp Businesses\n");
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
		}*/
		
		HashMap<Integer, Business> integrationMap = new HashMap<Integer, Business>();
		for(int i = 0; i < YelpBusinesses.size(); i++){
			int hashCode = Arrays.hashCode(new Object[] {YelpBusinesses.get(i).getAddress().substring(0, 7)});
			integrationMap.put(hashCode, YelpBusinesses.get(i));
			YelpBusinesses.get(i).setAverageRating(YelpBusinesses.get(i).getYelpRating());
		}
		
		for(int i = 0; i < GoogleBusinesses.size(); i++){
			int hashCode = Arrays.hashCode(new Object[] {GoogleBusinesses.get(i).getAddress().substring(0, 7)});
			GoogleBusinesses.get(i).setAverageRating(GoogleBusinesses.get(i).getGoogleRating());
			if(integrationMap.containsKey(hashCode)){
				Business tempYelpBusiness = integrationMap.get(hashCode);
				Business tempGoogleBusiness = GoogleBusinesses.get(i);
				Business combinedBusiness = new Business();
				
				combinedBusiness.setName(tempYelpBusiness.getName());
				combinedBusiness.setAddress(tempYelpBusiness.getAddress());
				combinedBusiness.setWebsite(tempGoogleBusiness.getWebsite());
				combinedBusiness.setPhoneNumber(tempYelpBusiness.getPhoneNumber());
				combinedBusiness.setCoordinates(tempYelpBusiness.getCoordinates());
				combinedBusiness.setDeal(tempYelpBusiness.hasDeal());
				combinedBusiness.setDealInfo(tempYelpBusiness.getDealInfo());
				combinedBusiness.setGoogleReviewText(tempGoogleBusiness.getGoogleReviewText());
				combinedBusiness.setGoogleSingleReviewRating(tempGoogleBusiness.getGoogleSingleReviewRating());
				combinedBusiness.setNumReviews(tempYelpBusiness.getNumReviews());
				combinedBusiness.setOpenNow(tempGoogleBusiness.isOpenNow());
				combinedBusiness.setPhoto(tempGoogleBusiness.getPhoto());		
				combinedBusiness.setAverageRating((tempYelpBusiness.getAverageRating() + tempGoogleBusiness.getAverageRating()) / 2);
				combinedBusiness.setPrice(tempGoogleBusiness.getPrice());
				
				
				
				integrationMap.remove(hashCode);
				integrationMap.put(hashCode, combinedBusiness);
			}
			else{
				integrationMap.put(hashCode, GoogleBusinesses.get(i));
			}
		}
		
		CombinedBusinesses = new ArrayList<Business>(integrationMap.values());

		
		callAlgorithm();
		
		System.out.println("\nTop 10 Sorted Vacations\n");
		
		for(int i = 0; i < 10; i++){
			System.out.println(CombinedBusinesses.get(i).getName() + "\t\t Price: "+
					CombinedBusinesses.get(i).getPrice() + "\t\t Rating: " +
					CombinedBusinesses.get(i).getAverageRating());
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
