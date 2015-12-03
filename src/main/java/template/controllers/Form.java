package template.controllers;

public class Form {
	  private String city = "";
	  private int radius = 0;
	  private String activity = "";
	  private int maxPrice = 0;
	  private boolean openStatus = false;
	  private int groupSize = 0;
	  private boolean cityError = false;
	  private boolean activityError = false;
	  private double longitude = 0;
	  private double latitude = 0;
	  private boolean deals = true;
	  
	  public String getCity(){
		  return city;
	  }
	  
	  public void setCity(String newCity){
		  city = newCity;
	  }
	  
	  public int getRadius(){
		  return radius;
	  }
	  
	  public void setRadius(int newRadius){
		  radius = newRadius;
	  }
	  
	  public String getActivity(){
		  return activity;
	  }
	  
	  public void setActivity(String newActivity){
		  activity = newActivity;
	  }
	  
	  public int getMaxPrice(){
		  return maxPrice;
	  }
	  
	  public void setMaxPrice(int newMaxPrice){
		  maxPrice = newMaxPrice;
	  }
	  
	  public boolean getOpenStatus(){
		  return openStatus;
	  }
	  
	  public void setOpenStatus(boolean status){
		  openStatus = status;
	  }
	  
	  public int getGroupSize(){
		  return groupSize;
	  }
	  
	  public void setGroupSize(int newGroupSize){
		  groupSize = newGroupSize;
	  }
	  
	  public void setCityError(boolean newError){
		  cityError = newError;
	  }
	  
	  public boolean getCityError(){
		  return cityError;
	  }
	  
	  public void setActivityError(boolean newError){
		  activityError = newError;
	  }
	  
	  public boolean getActivityError(){
		  return activityError;
	  }
	  
	  public void setLongitude(double newLongitude){
	  	   longitude = newLongitude;
	  }
	  
	  public double getLongitude(){
	  		return longitude;
	  }
	  
	  public void setLatitude(double newLatitude){
	  	   latitude = newLatitude;
	  }
	  
	  public double getLatitude(){
	  		return latitude;
	  }
	  
	  public boolean getDeals(){
	  		return deals;
	  }
	  
}
