package template.controllers;

import java.util.ArrayList;

public class Algorithm {
	
	static void swap(ArrayList<Business> vacation, int x, int y){
			Business temp = vacation.get(x);
			vacation.set(x, vacation.get(y));
			vacation.set(y, temp);
	}
	
	static void algorithm(ArrayList<Business> businesses, boolean openNow, int maxPrice){
		
		System.out.println("%%%%%%%%%%%%% In Algorithm %%%%%%%%%%%%%");
		
		for(int i = 0; i < businesses.size(); i++){
			
			System.out.println("Biz: " + businesses.get(i).getName());
			
			//Rank is raised as a function of rating
			if(businesses.get(i).getAverageRating() == 0.0){
				//Set Rating to Median Rating if none is given
				businesses.get(i).setAverageRating(3.0);
				businesses.get(i).setNumReviews(1);
			}
			businesses.get(i).setWeight(businesses.get(i).getWeight() +  
				(businesses.get(i).getAverageRating() * 5) + Math.pow(businesses.get(i).getNumReviews(), 0.1));
//			System.out.println(" Algorithm Number of Reviews: " + businesses.get(i).getNumReviews());
			System.out.println("=== (1) weight: " + businesses.get(i).getWeight());
			
			//Rank is lowered as a function of distance from city
			businesses.get(i).setWeight(businesses.get(i).getWeight() 
					- (businesses.get(i).getDistance()/2));
//			System.out.println("Algorithm Distance: " + businesses.get(i).getDistance());
			System.out.println("=== (2) weight: " + businesses.get(i).getWeight());
			
			if(businesses.get(i).getPrice() == -1){
				//No prices for this business, set to median price
				businesses.get(i).setPrice(2);
			}
			
			System.out.println("=== (3) weight: " + businesses.get(i).getWeight());
			
			//Rank is lowered as a function of price
			businesses.get(i).setWeight(businesses.get(i).getWeight() 
					- (businesses.get(i).getPrice() * 2));
			System.out.println("=== (4) weight: " + businesses.get(i).getWeight());
			
			if(businesses.get(i).getPrice() > maxPrice){
				businesses.get(i).setWeight(businesses.get(i).getWeight() / 2);
			}
//			System.out.println("Algorithm Price: " + businesses.get(i).getPrice());
			System.out.println("=== (5) weight: " + businesses.get(i).getWeight());
			
			//Rank is lowered if no deals are offered
			if(!(businesses.get(i).hasDeal())){
				businesses.get(i).setWeight(businesses.get(i).getWeight() - 5);
			}
			System.out.println("=== (6) weight: " + businesses.get(i).getWeight());
			
			//Rank is lowered if user wanted open businesses and it's not open
			if(!(businesses.get(i).getOpenStatus()) && openNow) {
				businesses.get(i).setWeight(0);
			}
			System.out.println("=== (7) weight: " + businesses.get(i).getWeight());
		}
		
		//Sort vacations based on new rankings
		for(int i = 0; i < (businesses.size()); i++){
			for(int j = 0; j < (businesses.size()-i-1); j++){
				if(businesses.get(j).getWeight() < businesses.get(j+1).getWeight()){
					swap(businesses,j, j+1);
				}
			}	
		}	
	}
}
