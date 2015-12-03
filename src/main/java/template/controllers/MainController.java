package template.controllers;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	Form userForm;
	ArrayList <Business> businesses = new ArrayList<>();
	Facade thefacade = Facade.getInstance();
	ArrayList<Business> listOfBusinesses;
	int expressLoc = 0;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String homeForm(Model model) {
		model.addAttribute("form", new Form());
		return "index";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String homeSubmit(@ModelAttribute Form form, Model model){
		String[] cmdArgs = {""};
		model.addAttribute("form", form);
		userForm = form;
		
		switch(form.getCity()){
			case "Amarillo":
				form.setLatitude(35.1992);
				form.setLongitude(-101.8453);
				break;
			case "Austin":
				form.setLatitude(30.2500);
				form.setLongitude(-97.7500);
				break;
			case "College Station":
				form.setLatitude(30.6014);
				form.setLongitude(-96.3144);
				break;
			case "Corpus Christi":
				form.setLatitude(27.7428);
				form.setLongitude(-97.4019);
				break;
			case "Fort Worth":
				form.setLatitude(32.7574);
				form.setLongitude(-97.3332);
				break;
			case "Houston":
				form.setLatitude(29.7604);
				form.setLongitude(-95.3698);
				break;
			case "Lubbock":
				form.setLatitude(33.5667);
				form.setLongitude(-101.8833);
				break;
			case "San Antonio":
				form.setLatitude(29.4167);
				form.setLongitude(-98.5000);
				break;
			case "Waco":
				form.setLatitude(31.5514);
				form.setLongitude(-97.1558);
				break;
			default: // init to Dallas
				form.setLatitude(32.806437331493);
				form.setLongitude(-96.802432100254);
		}
		
		thefacade.getForm(form);
		
		System.out.println("################# Calling Yelp API #################");
		thefacade.getYelpAPIResults(YelpAPI.run(cmdArgs, 
					form.getActivity(),
					form.getCity(),
					form.getRadius(),
					form.getDeals()
					));
		
		return "map";
	}
	
	@RequestMapping(value="/express1", method=RequestMethod.GET)
	public String express1Get(Model model) {
		// set location to first option
		expressLoc = 1;
		return "express";
	}
	
	@RequestMapping(value="/express2", method=RequestMethod.GET)
	public String express2Get(Model model) {
		// set location to second option
		expressLoc = 2;
		return "express";
	}
	
	@RequestMapping(value="/express3", method=RequestMethod.GET)
	public String express3Get(Model model) {
		// set location to third option
		expressLoc = 3;
		return "express";
	}
	
	@RequestMapping(value="/express", method=RequestMethod.POST)
	@ResponseBody
	public int expressPost(Model model) {
		// return express location chosen by user
		return expressLoc;
	}
	
	@RequestMapping(value="/map", method=RequestMethod.GET)
	public void mapGet(Model model) {
		model.addAttribute("biz", new Business());
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/map", method=RequestMethod.POST)
	@ResponseBody
	public JSONArray mapPost(@ModelAttribute Business biz, Model model) {
		JSONArray jsonArr = new JSONArray();
		JSONObject tmpJson = new JSONObject();
		
		model.addAttribute("biz", biz);		
		biz.setCoordinatesGoogle();
		
		if(biz.isNull()) {
			thefacade.getGoogleAPIResults(businesses);
			listOfBusinesses = thefacade.getResults();
			
			int ranking = 1;
			System.out.println("######### Sending to main.js #########");
			
			// add businesses to array of JSONs
			for(int i = 0; i < listOfBusinesses.size(); i++) {
				// re-init tmp JSON Object
				tmpJson = new JSONObject();
				
				// add attributes
				tmpJson.put("name", listOfBusinesses.get(i).getName());
				tmpJson.put("address", listOfBusinesses.get(i).getAddress());
				tmpJson.put("phone", listOfBusinesses.get(i).formatPhoneNumber());
				tmpJson.put("rating", listOfBusinesses.get(i).getAverageRating());
				tmpJson.put("lat", listOfBusinesses.get(i).getCoordinates().getLatitude());
				tmpJson.put("lng", listOfBusinesses.get(i).getCoordinates().getLongitude());
				tmpJson.put("website", listOfBusinesses.get(i).getWebsite());
				tmpJson.put("openStatus", listOfBusinesses.get(i).getOpenStatus());
				
				System.out.println((ranking++) + ". Biz name: " + listOfBusinesses.get(i).getName() + "; AvgRating: " + listOfBusinesses.get(i).getAverageRating() + "; isOpenNow: " + listOfBusinesses.get(i).getOpenStatus() + ";price: " + listOfBusinesses.get(i).getPrice() + "; weight: " + listOfBusinesses.get(i).getWeight() + "; numReviews: " + listOfBusinesses.get(i).getNumReviews());
				
				// add JSON Object to array
				jsonArr.add(tmpJson);
			}
			
			// clear list of businesses
			businesses.clear();
			listOfBusinesses.clear();
		} else {
			businesses.add(biz);
		}
		
		return jsonArr;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getCriteria", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject sendCriteria() {
		JSONObject json = new JSONObject();
		json.put("city", userForm.getCity());
		json.put("radius", userForm.getRadius());
		json.put("type", userForm.getActivity());
		json.put("openStatus", userForm.getOpenStatus());
		
		return json;
	}
	
}
