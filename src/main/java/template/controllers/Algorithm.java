package template.controllers;

import java.util.ArrayList;

public class Algorithm {
	
	static void swap(ArrayList<Business> vacation, int x, int y){
			Business temp = vacation.get(x);
			vacation.set(x, vacation.get(y));
			vacation.set(y, temp);
	}
	
	static void algorithm(ArrayList<Business> businesses){
		
		for(int i = 0; i < businesses.size(); i++){
			//Rank is raised as a function of rating
			businesses.get(i).setWeight(businesses.get(i).getWeight() +  
					(businesses.get(i).getAverageRating() * 5) * businesses.get(i).getNumReviews());
			
			//Rank is lowered as a function of distance from city
			businesses.get(i).setWeight(businesses.get(i).getWeight() 
					- (businesses.get(i).getDistance()/2));
			
			//Rank is lowered as a function of price
			businesses.get(i).setWeight(businesses.get(i).getWeight() 
					- (businesses.get(i).getPrice() * 2));
			
			//Rank is lowered by 1 if no deals offered
			if(!(businesses.get(i).hasDeal())){
				businesses.get(i).setWeight(businesses.get(i).getWeight() - 5);
			}
			
			if(!(businesses.get(i).isOpenNow())){
				businesses.get(i).setWeight(businesses.get(i).getWeight() - 2);
			}
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

	public static void run(ArrayList<Business> vacation) {
		/*ASSUMPTIONS: API is already returning businesses which are within
		 * the desired distance range, and therefore we do not need to check
		 * a maximum distance
		 */
		
		//Sort vacations based off criteria
		
		for(int i = 0; i < vacation.size(); i++){
			System.out.println(vacation.get(i).getName()+": " + vacation.get(i).getWeight());
		}
		System.out.println();
		System.out.println();
		algorithm(vacation);
		
		//Print out sorted vacations based on criteria
		System.out.println("Vacation Ranks");
		for(int i = 0; i < vacation.size(); i++){
			System.out.println(vacation.get(i).getName()+": " + vacation.get(i).getWeight());
		}
		

	}

}
