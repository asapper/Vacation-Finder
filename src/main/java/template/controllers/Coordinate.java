package template.controllers;

public class Coordinate {
	private double latitude = 0.0; // The businesses latitude
	private double longitude = 0.0; // The businesses longitude
	
	/**
	 * Default constructor
	 */
	Coordinate(){
		
	}

	/**
	 * Custom Constructor
	 * @param latitude
	 * @param longitude
	 */
	Coordinate(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Gets the latitude
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets teh latitude
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude
	 * @return
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
