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
	private static ArrayList<Business> yelpBusinesses = new ArrayList<>();
	private static ArrayList<Business> googleBusinesses = new ArrayList<>();
	private static ArrayList<Business> combinedBusinesses;
	private static HashSet<Business> businesses = new HashSet<>();
	private static boolean formRead = false;
	
	private Facade(){
	}
	
	public static Facade getInstance(){
		return instance;
	}
	
	public void getYelpAPIResults(ArrayList<Business> arrayList){
		formRead = true;
		yelpBusinesses = arrayList;
	}
	
	public void getGoogleAPIResults(ArrayList<Business> arrayList){
		formRead = true;
		googleBusinesses = arrayList;
	}
	
	public void callAlgorithm(){
		Algorithm.algorithm(combinedBusinesses);
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
		return yelpBusinesses;
	}
	
	public ArrayList<Business> getGoogleBusiness(){
		return googleBusinesses;
	}
	
	/**
	 * Combines the lists, runs the businesses through the algorithm, and prints results
	 */
	public ArrayList<Business> getResults(){
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
		for(int i = 0; i < yelpBusinesses.size(); i++){
			int hashCode = Arrays.hashCode(new Object[] {yelpBusinesses.get(i).getAddress().substring(0, 7)});
			integrationMap.put(hashCode, yelpBusinesses.get(i));
			yelpBusinesses.get(i).setAverageRating(yelpBusinesses.get(i).getYelpRating());
		}
		
		for(int i = 0; i < googleBusinesses.size(); i++){
			int hashCode = Arrays.hashCode(new Object[] {googleBusinesses.get(i).getAddress().substring(0, 7)});
			googleBusinesses.get(i).setAverageRating(googleBusinesses.get(i).getGoogleRating());
			if(integrationMap.containsKey(hashCode)){
				Business tempYelpBusiness = integrationMap.get(hashCode);
				Business tempGoogleBusiness = googleBusinesses.get(i);
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
				integrationMap.put(hashCode, googleBusinesses.get(i));
			}
		}
		
		combinedBusinesses = new ArrayList<Business>(integrationMap.values());

		
		callAlgorithm();
		
		System.out.println("\nTop 10 Sorted Vacations\n");
		
		for(int i = 0; i < 10; i++){
			System.out.println(combinedBusinesses.get(i).getName() + "\t\t Price: "+
					combinedBusinesses.get(i).getPrice() + "\t\t Rating: " +
					combinedBusinesses.get(i).getAverageRating());
		}
		
		// return list of businesses
		return combinedBusinesses;
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
