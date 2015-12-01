package template.managed.resources;


public class Business {
	
	private String name = ""; // Business name
	private String yelpSite = ""; // Link to yelp site URL
	private double rating = 0.0; // Yelp rating
	private String address = ""; // Street Address 
	private String phoneNumber = ""; // Phone Number
	private double latitude = 0.0; // Business location latitude
	private double longitude = 0.0; // Business location longitude
	private double distance = 0.0; // Distance from specified location
	private boolean hasDeal = false; // If the business has a deal
	private String dealInfo = ""; // The info about the possible deal
	
	
	
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
	public double getRating(){
		return rating;
	}
	
	/**
	 * Sets the yelp rating
	 * @param newRating
	 */
	public void setRating(double newRating){
		rating = newRating;
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
	 * Returns the latitude of the business
	 * @return
	 */
	public double getLatitude(){
		return latitude;
	}
	
	/**
	 * Sets the latitude of the business
	 * @param newLatitude
	 */
	public void setLatitude(double newLatitude){
		latitude = newLatitude;
	}
	
	/**
	 * Gets the longitude of the business
	 * @return
	 */
	public double getLongitude(){
		return longitude;
	}
	
	/**
	 * Sets the longitude of the business
	 * @param newLongitude
	 */
	public void setLongitude(double newLongitude){
		longitude = newLongitude;
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
	 * Overridden toString method
	 */
	public String toString(){
		return name;
	}
	
	/**
	 * Returns the latitude and longitude formatted in (###, ###) form
	 * @return
	 */
	public String formatCoordinates(){
		return ("(" + latitude + ", " + longitude + ")");
	}
	
	public String formatPhoneNumber(){
		return("(" + phoneNumber.substring(0, 3) + ")" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, 10));
	}
	
	
	
}
