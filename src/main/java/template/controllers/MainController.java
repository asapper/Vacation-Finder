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
		
		System.out.println("Calling Yelp API");
		thefacade.getYelpAPIResults(YelpAPI.run(cmdArgs, 
					form.getActivity(),
					form.getCity(),
					form.getRadius(),
					form.getDeals()
					));
		
		return "map";
	}
	
	@RequestMapping(value="/map", method=RequestMethod.GET)
	public void mapGet(Model model) {
		model.addAttribute("biz", new Business());
	}
	
	@RequestMapping(value="/map", method=RequestMethod.POST)
	@ResponseBody
	public JSONArray mapPost(@ModelAttribute Business biz, Model model) {
		JSONArray jsonArr = new JSONArray();
		JSONObject tmpJson = new JSONObject();
		
		model.addAttribute("biz", biz);		
		
		if(biz.isNull()) {
			thefacade.getGoogleAPIResults(businesses);
			listOfBusinesses = thefacade.getResults();
		} else {
			businesses.add(biz);
		}
		
		// add businesses to array of JSONs
		for(Business aBiz : listOfBusinesses) {
			// re-init tmp JSON Object
			tmpJson = new JSONObject();
			
			// add attributes
			tmpJson.put("name", aBiz.getName());
			tmpJson.put("address", aBiz.getAddress());
			tmpJson.put("phone", aBiz.getPhoneNumber());
			tmpJson.put("rating", aBiz.getAverageRating());
			tmpJson.put("lat", aBiz.getLatitude());
			tmpJson.put("lng", aBiz.getLongitude());
			tmpJson.put("website", aBiz.getWebsite());
			
			// add JSON Object to array
			jsonArr.add(tmpJson);
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
		
		return json;
	}
	
}
