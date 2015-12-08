/**
 * Author: Carter Ratley
 * Date Created: 10/1/15
 * Date Last Modified: 11/30/15
 */
package template.controllers;

public class Business {
	
	private String name = ""; // Business name
	private String website = ""; // A link to the website
	private String yelpSite = ""; // Link to yelp site URL
	private double yelpRating = 0.0; // Yelp rating
	private int numReviews = 0; // The number of reviews of the business
	private double googleRating = 0.0; // The rating from the Google API
	private double averageRating = 0.0; // The average rating between Yelp and Google
	private String address = ""; // Street Address 
	private String phoneNumber = ""; // Phone Number
	private Coordinate coordinates = new Coordinate(); // Coordinates of the business
	private double latitude = 0.0; // The businesses latitude (temporarily used)
	private double longitude = 0.0; // The businesses longitude (temporarily used)
	private double distance = 0.0; // Distance from specified location
	private boolean hasDeal = false; // If the business has a deal
	private String dealInfo = ""; // The info about the possible deal
	private int price = -1; // The price of the business
	private boolean openStatus = false; // Whether or not the business is open at the time of the search
	private String photo = ""; // The URL of a photo of the business
	private String googleReviewText = ""; // A sample google review
	private double googleReviewRating = 0.0; // A sample google rating
	private static Business business = new Business(); // A Singleton object of the business
	private double weight = 0.0; // The weights (or points) assigned to the business for ranking
	
	
	
	/**
	 * Default Constructor 
	 */
	public Business(){
		
	}
	
	/**
	 * Constructor with a name parameter
	 * @param newName
	 */
	public Business(String newName){
		name = newName;
	}
	
	/**
	 * Returns an instance of the business object
	 * @return
	 */
	public static Business getInstance(){
		return business;
	}
	
	/**
	 * Gets the name of the business
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets the name of the business
	 * @param newName
	 */
	public void setName(String newName){
		name = newName;
	}
	
	/**
	 * Returns yelp website URL
	 * @return
	 */
	public String getYelpSite(){
		return yelpSite;
	}
	
	/**
	 * Sets yelp URL 
	 * @param newSite
	 */
	public void setYelpSite(String newSite){
		yelpSite = newSite;
	}
	
	/**
	 * Gets the yelp rating
	 * @return
	 */
	public double getYelpRating(){
		return yelpRating;
	}
	
	/**
	 * Sets the yelp rating
	 * @param newRating
	 */
	public void setRating(double newRating){
		yelpRating = newRating;
	}
	
	/**
	 * Returns the address of the business
	 * @return
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * Sets the address of the business
	 * @param newAddress
	 */
	public void setAddress(String newAddress){
		address = newAddress;
	}
	
	/**
	 * Returns the phone number of the business
	 * @return
	 */
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	/**
	 * Sets the phone number of the business
	 * @param newPhoneNumber
	 */
	public void setPhoneNumber(String newPhoneNumber){
		phoneNumber = newPhoneNumber;
	}
	
	
	/**
	 * Returns the coordinates
	 * @return
	 */
	public Coordinate getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Gets the latitude
	 * @return
	 */
	public double getLatitude(){
		return latitude;
	}
	
	/**
	 * Gets the longitude
	 * @return
	 */
	public double getLongitude(){
		return longitude;
	}
	
	/**
	 * Sets the latitude
	 * @param newLat
	 */
	public void setLatitude(double newLat){
		latitude = newLat;
	}
	
	/**
	 * Sets the longitude
	 * @param newLng
	 */
	public void setLongitude(double newLng){
		longitude = newLng;
	}

	/**
	 * Sets the coordinates
	 * @param coordinates
	 */
	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}
	
	/**
	 * Sets the coordinates of the business once we have them from Google Places
	 */
	public void setCoordinatesGoogle(){
		this.coordinates.setLatitude(latitude);
		this.coordinates.setLongitude(longitude);
	}

	/**
	 * Returns the distance from the queried location
	 * @return
	 */
	public double getDistance(){
		return distance;
	}
	
	/**
	 * Sets the distance to desired value
	 * @param newDistance
	 */
	public void setDistance(double newDistance){
		distance = newDistance;
	}
	
	/**
	 * Gets the deal info
	 * @return
	 */
	public String getDealInfo(){
		return dealInfo;
	}
	
	/**
	 * Returns if the business has a deal
	 * @return
	 */
	public boolean hasDeal(){
		return hasDeal;
	}
	
	/**
	 * Sets the boolean value if the place has a deal
	 * @param deal
	 */
	public void setDeal(boolean deal){
		hasDeal = deal;
	}
	
	
	/**
	 * Sets the deal info to the new info
	 * @param newInfo
	 */
	public void setDealInfo(String newInfo){
		dealInfo = newInfo;
	}
	
	/**
	 * Returns the numbers of reviews
	 * @return
	 */
	public int getNumReviews(){
		return numReviews;
	}
	
	/**
	 * Sets the number of reviews
	 * @param newNumReviews
	 */
	public void setNumReviews(int newNumReviews){
		numReviews = newNumReviews;
	}
	
	/**
	 * Returns the price from 0-4
	 * @return
	 */
	public int getPrice(){
		return price;
	}
	
	/**
	 * Sets the price of the business
	 * @param newPrice
	 */
	public void setPrice(int newPrice){
		price = newPrice;
	}
	
	/**
	 * Returns website
	 * @return
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Sets the website URL
	 * @param website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Gets the google rating
	 * @return
	 */
	public double getGoogleRating() {
		return googleRating;
	}

	/**
	 * Sets the google rating
	 * @param googleRating
	 */
	public void setGoogleRating(double googleRating) {
		this.googleRating = googleRating;
	}

	/**
	 * Gets the average rating
	 * @return
	 */
	public double getAverageRating() {
		return averageRating;
	}
	
	/**
	 * Sets the average rating
	 * @param newAvg
	 */
	public void setAverageRating(double newAvg){
		averageRating = newAvg;
	}

	/**
	 * Calculates the average rating of yelp and google places
	 */
	public void calculateAverageRating(){
		averageRating  = (yelpRating + googleRating) / 2;
	}
	
	/**
	 * Returns whether or not the business is open at the time of the api call
	 * @return
	 */
	public boolean getOpenStatus() {
		return openStatus;
	}

	/**
	 * Sets whether or not the business is open
	 * @param isOpenNow
	 */
	public void setOpenStatus(boolean newOpenStatus) {
		openStatus = newOpenStatus;
	}

	/**
	 * Gets the link to the photo of the business
	 * @return
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * Sets the photo link
	 * @param photo
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * Gets the text from the Google review
	 * @return
	 */
	public String getGoogleReviewText() {
		return googleReviewText;
	}

	/**
	 * Sets the google review text
	 * @param googleReviewText
	 */
	public void setGoogleReviewText(String googleReviewText) {
		this.googleReviewText = googleReviewText;
	}
	
	/**
	 * Gets the rating of the single review from google
	 * @return
	 */
	public double getGoogleSingleReviewRating() {
		return googleReviewRating;
	}

	/**
	 * Sets the rating of the single review from google
	 * @param googleReviewRating
	 */
	public void setGoogleSingleReviewRating(double googleReviewRating) {
		this.googleReviewRating = googleReviewRating;
	}

	/**
	 * Overridden toString method
	 */
	public String toString(){
		return name;
	}
	
	
	/**
	 * Returns a formatted phone number in (###) ###-#### form
	 * @return
	 */
	public String formatPhoneNumber(){
		if(phoneNumber.length() > 9 && phoneNumber.charAt(0) != '(') {
			return("(" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, 10));
		}
		
		return phoneNumber;
	}
	
	/**
	 * Gets the weight of the business
	 * @return
	 */
	public double getWeight(){
		return weight;
	}
	
	/**
	 * Sets the weight of the object
	 * @param w
	 */
	public void setWeight(double w){
		weight = w;
	}
	
	/**
	 * Checks to see if the business is null (has no name)
	 * @return
	 */
	public boolean isNull(){
		return name.equals("");
	}
}
