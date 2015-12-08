/**
 * Author: Evan Westermann
 * Date Created: 11/4/15
 * 
 * Last Modified: 12/6/15
 * Last Modified By: Carter Ratley
 */
package template.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Facade {
	
	private static Facade instance = new Facade(); // The instance of the Facade
	static Business theBusiness = Business.getInstance(); // An instance of the business object
	private static Form theForm = new Form(); // An instance of the form class
	private static ArrayList<Business> YelpBusinesses = new ArrayList<>(); // The list of businesses from the Yelp API
	private static ArrayList<Business> GoogleBusinesses = new ArrayList<>(); // The list of businesses from the Google Places API
	private static ArrayList<Business> combinedBusinesses; // The list of the businesses after being combined
	private static HashSet<Business> businesses = new HashSet<>(); 
	private static boolean formRead = false;
	
	/**
	 * Default Constructor
	 */
	private Facade(){
	}
	
	public static Facade getInstance(){
		return instance;
	}
	
	public void getYelpAPIResults(ArrayList<Business> arrayList){
		formRead = true;
		YelpBusinesses = arrayList;
	}
	
	public void getGoogleAPIResults(ArrayList<Business> arrayList){
		formRead = true;
		GoogleBusinesses = arrayList;
	}
	
	public void callAlgorithm(){
		Algorithm.algorithm(combinedBusinesses, theForm.getOpenStatus(), theForm.getMaxPrice());
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
	public ArrayList<Business> getResults(){
		
		// Creates a hash map based on the first 6 characters of the businesses street address
		HashMap<Integer, Business> integrationMap = new HashMap<Integer, Business>();
		
		// Hashes the Yelp API businesses
		for(int i = 0; i < YelpBusinesses.size(); i++){
			
			int hashCode = Arrays.hashCode(new Object[] {YelpBusinesses.get(i).getAddress().substring(0, 7)});
			integrationMap.put(hashCode, YelpBusinesses.get(i));
			YelpBusinesses.get(i).setAverageRating(YelpBusinesses.get(i).getYelpRating());
		}
		
		// Checks to see if there is a match for each Google business
		for(int i = 0; i < GoogleBusinesses.size(); i++){
			int hashCode = Arrays.hashCode(new Object[] {GoogleBusinesses.get(i).getAddress().substring(0, 7)});
			GoogleBusinesses.get(i).setAverageRating(GoogleBusinesses.get(i).getGoogleRating());
			
			// If there is a match, we combine the data manually
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
				combinedBusiness.setOpenStatus(tempGoogleBusiness.getOpenStatus());
				combinedBusiness.setPhoto(tempGoogleBusiness.getPhoto());		
				combinedBusiness.setAverageRating((tempYelpBusiness.getAverageRating() + tempGoogleBusiness.getAverageRating()) / 2);
				combinedBusiness.setPrice(tempGoogleBusiness.getPrice());
				
				integrationMap.remove(hashCode);
				integrationMap.put(hashCode, combinedBusiness);
			}
			
			// If it doesn't match, we add the business to the list
			else {
				integrationMap.put(hashCode, GoogleBusinesses.get(i));
			}
		}
		
		// We then take the result of the hash map and make it an array list
		combinedBusinesses = new ArrayList<Business>(integrationMap.values());
		
		Coordinate city = new Coordinate(theForm.getLatitude(), theForm.getLongitude());
		
		for(int i = 0; i < combinedBusinesses.size(); i++){
			Coordinate business = combinedBusinesses.get(i).getCoordinates();
			combinedBusinesses.get(i).setDistance(coordinatesToMiles(city,business));
		}

		callAlgorithm();
		
		return combinedBusinesses;
	}
	
	/**
	 * Converts the coordinates to the miles apart
	 * @param from
	 * @param to
	 * @return
	 */
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
