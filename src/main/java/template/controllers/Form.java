package template.controllers;

public class Form {
	  private String city = "";
	  private int radius = 0;
	  private String activity = "";
	  private int rating = 0;
	  private boolean deals = false;
	  private int groupSize = 0;
	  private boolean cityError = false;
	  private boolean activityError = false;
	  
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
	  
	  public int getRating(){
		  return rating;
	  }
	  
	  public void setRating(int newRating){
		  rating = newRating;
	  }
	  
	  public boolean getDeals(){
		  return deals;
	  }
	  
	  public void setDeals(boolean newDeals){
		  deals = newDeals;
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
}