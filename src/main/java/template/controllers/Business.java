package template.controllers;
public class Business {
	
	private int    price;
	private double weight;
	private double distance;
	private boolean deals = false; // Vacations with deals
	private String dealInfo = "";
	private boolean open = false; // Vacation is open
	private String name = ""; // Business name
	private String yelpSite = ""; // Link to yelp site URL
	private double rating = 0.0; // Yelp rating
	private int	   ratingNum = 0;
	private String address = ""; // Street Address 
	private String phoneNumber = ""; // Phone Number
	private double latitude = 0.0; // Business location latitude
	private double longitude = 0.0; // Business location longitude
	
	private static Business instance = new Business();
	
	public static Business getInstance(){
		return instance;
	}
	
	/**
	 * Default Constructor 
	 */
	public Business(){
		name = "";
		rating = 0;
		price = 0;
		distance = 0;
		weight = 10;
		deals = false;
	}
	
	/**
	 * Constructor with a name parameter
	 * @param newName
	 */
	public Business(String newName){
		name = newName;
		rating = 0;
		price = 0;
		distance = 0;
		weight = 10;
		deals = false;
	}
	
	public Business(String newName, double newRating, int newRatingNum,
			int newPrice, double newDistance, boolean newDeals){
		name = newName;
		rating = newRating;
		ratingNum = newRatingNum;
		price = newPrice;
		distance = newDistance;
		deals = newDeals;
		weight = 10;
	}
	
	public int getPrice(){
		return price;
	}
	
	public void setPrice(int p){
		price = p;
	}
	
	public boolean hasDeals(){
		return deals;
	}
	
	public void setDeals(boolean d){
		deals = d;
	}
	
	public void setDealInfo(String info){
		dealInfo = info;
	}
	
	public boolean isOpen(){
		return open;
	}
	
	public void setOpen(boolean o){
		open = o;
	}
	
	public void setDistance(double d){
		distance = d;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public void setWeight(double w){
		weight = w;
	}
	
	public double getWeight(){
		return weight;
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
	
	public int getRatingNum(){
		return ratingNum;
	}
	
	public void setRatingNum(int num){
		ratingNum = num;
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

	public String getWebsite() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNull() {
		return (name == "");
	}
	
	
	
}
