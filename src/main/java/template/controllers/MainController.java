package template.controllers;

import java.util.ArrayList;

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
		thefacade.callYelpAPI(YelpAPI.run(cmdArgs, 
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
	public JSONObject mapPost(@ModelAttribute Business biz, Model model) {
		System.out.println("%%% Inside /map POST route");
		
		JSONObject json = new JSONObject();
		json.put("name", "return full merged list of locs here (as an array of JSONObject's)!");
		
		model.addAttribute("biz", biz);
		
		System.out.println(">>> business name is: " + biz.getName());
		System.out.println("business price is: " + biz.getPrice());
		System.out.println("business website is: " + biz.getWebsite());
		System.out.println("business phone is: " + biz.getPhoneNumber() + "\n");
		System.out.println("business rating is: " + biz.getAverageRating());		
		
		if(biz.isNull()) {
			System.out.println("%%% DONE %%%");
			thefacade.callGoogleAPI(businesses);
			thefacade.printResults();
		}
		else{
			System.out.println("Adding Business " + biz.getName());
			businesses.add(biz);
		}
		
		return json;
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
