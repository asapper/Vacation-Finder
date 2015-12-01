package template.controllers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Code sample for accessing the Yelp API V2.
 * 
 * This program demonstrates the capability of the Yelp API version 2.0 by using the Search API to
 * query for businesses by a search term and location, and the Business API to query additional
 * information about the top result from the search query.
 * 
 * <p>
 * See <a href="http://www.yelp.com/developers/documentation">Yelp Documentation</a> for more info.
 * 
 * 
 * Modified By: Carter Ratley
 * Date Last Modified: 11/5/15
 */
public class YelpAPI {

  private static final String API_HOST = "api.yelp.com";
  private static final int SEARCH_LIMIT = 20;
  private static final String SEARCH_PATH = "/v2/search";
  private static final String BUSINESS_PATH = "/v2/business";
  private static final String CATEGORY_FILTER = "active, all";
  public static String newLocation = "";
  public static String newTerm = "";
  public static int radiusFilter = 0;
  public static int sortFilter = 0;
  public static boolean deals = false;
  

  public static ArrayList<Business> businesses = new ArrayList<Business>();
  
  
  /*
   * Update OAuth credentials below from the Yelp Developers API site:
   * http://www.yelp.com/developers/getting_started/api_access
   */
  private static final String CONSUMER_KEY = "2LZAn8AotgQciqzxUJIg6w";
  private static final String CONSUMER_SECRET = "OiPCTKku2hB7brjoip74y5snnvk";
  private static final String TOKEN = "cvBLYbGIKUvHHVJP-UXHIZsa5vkLCLv7";
  private static final String TOKEN_SECRET = "-d7E43Yo66Fts7rzX4C07rot0bE";

  OAuthService service;
  Token accessToken;

  /**
   * Setup the Yelp API OAuth credentials.
   * 
   * @param consumerKey Consumer key
   * @param consumerSecret Consumer secret
   * @param token Token
   * @param tokenSecret Token secret
   */
  public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
    this.service =
        new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
            .apiSecret(consumerSecret).build();
    this.accessToken = new Token(token, tokenSecret);
  }

  /**
   * Creates and sends a request to the Search API by term and location.
   * <p>
   * See <a href="http://www.yelp.com/developers/documentation/v2/search_api">Yelp Search API V2</a>
   * for more info.
   * 
   * @param term <tt>String</tt> of the search term to be queried
   * @param location <tt>String</tt> of the location
   * @return <tt>String</tt> JSON Response
   */
  public String searchForBusinessesByLocation(String term, String location) {
    OAuthRequest request = createOAuthRequest(SEARCH_PATH);
    request.addQuerystringParameter("term", term);
    request.addQuerystringParameter("location", location);
    request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
    return sendRequestAndGetResponse(request);
  }
  
  public String searchForBusinessesByStuff(String term, String location, int radFilter, int sortValue, boolean dealsFilter){
	  OAuthRequest request = createOAuthRequest(SEARCH_PATH);
	  request.addQuerystringParameter("category_filer", CATEGORY_FILTER);
	  request.addQuerystringParameter("radius_filter", String.valueOf(radFilter));
	  request.addQuerystringParameter("term", term);
	  request.addQuerystringParameter("sort", String.valueOf(sortValue));
	  request.addQuerystringParameter("location", location);
	  request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
	  request.addQuerystringParameter("deals_filter",String.valueOf(dealsFilter));
	  
	  return sendRequestAndGetResponse(request);
  }

  /**
   * Creates and sends a request to the Business API by business ID.
   * <p>
   * See <a href="http://www.yelp.com/developers/documentation/v2/business">Yelp Business API V2</a>
   * for more info.
   * 
   * @param businessID <tt>String</tt> business ID of the requested business
   * @return <tt>String</tt> JSON Response
   */
  public String searchByBusinessId(String businessID) {
    OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
    return sendRequestAndGetResponse(request);
  }

  /**
   * Creates and returns an {@link OAuthRequest} based on the API endpoint specified.
   * 
   * @param path API endpoint to be queried
   * @return <tt>OAuthRequest</tt>
   */
  private OAuthRequest createOAuthRequest(String path) {
    OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST + path);
    return request;
  }

  /**
   * Sends an {@link OAuthRequest} and returns the {@link Response} body.
   * 
   * @param request {@link OAuthRequest} corresponding to the API request
   * @return <tt>String</tt> body of API response
   */
  private String sendRequestAndGetResponse(OAuthRequest request) {
    //System.out.println("Querying " + request.getCompleteUrl() + " ...");
    this.service.signRequest(this.accessToken, request);
    Response response = request.send();
    return response.getBody();
  }

  /**
   * Queries the Search API based on the command line arguments and takes the first result to query
   * the Business API.
   * 
   * @param yelpApi <tt>YelpAPI</tt> service instance
   * @param yelpApiCli <tt>YelpAPICLI</tt> command line arguments
   */
  private static void queryAPI(YelpAPI yelpApi, YelpAPICLI yelpApiCli) {
    String searchResponseJSON =
        yelpApi.searchForBusinessesByStuff(newTerm, newLocation, milesToMeters(radiusFilter), sortFilter, deals);

    JSONParser parser = new JSONParser();
    JSONObject response = null;
    try {
      response = (JSONObject) parser.parse(searchResponseJSON);
    } catch (ParseException pe) {
      //System.out.println("Error: could not parse JSON response:");
      //System.out.println(searchResponseJSON);
      System.exit(1);
    }
    
    businesses.clear();
    
    JSONArray businessesList = (JSONArray) response.get("businesses");
    
    
    for(int i = 0; i < businessesList.size(); i++){
    	JSONObject business = (JSONObject) businessesList.get(i);
        String businessID = business.get("id").toString();
        //System.out.println(String.format(
        //    "%s businesses found, querying business info for the top result \"%s\" ...",
        //    businesses.size(), firstBusinessID));

        // Select the first business and display business details
        String businessResponseJSON = yelpApi.searchByBusinessId(businessID.toString());
        //System.out.println(String.format("Result for business \"%s\" found:", firstBusinessID));
        
        //System.out.println(businessResponseJSON);
        Business newBusiness = parseBusiness(businessResponseJSON);
        
        // Checks to make sure business is in Texas and adds it to the list if it is
        if(newBusiness.getAddress().indexOf("TX") > 0){
        	businesses.add(newBusiness);
        }
        
    } 
    
  }

  public static StringBuffer removeUTFCharacters(String data){
	  Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
	  Matcher m = p.matcher(data);
	  StringBuffer buf = new StringBuffer(data.length());
	  while (m.find()) {
		  String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
		  m.appendReplacement(buf, Matcher.quoteReplacement(ch));
	  }
	  
	  m.appendTail(buf);
	  return buf;
  }
  
  public static Business parseBusiness(String info){
	  Business tempBusiness = new Business();
	  
	  // Finds the name
	  int nameIndex = info.indexOf("\"name\"");
	  int nameIndexOffset = nameIndex + 9;
	  int nameEndIndex = info.indexOf("\"", nameIndexOffset);
	  String tempName = info.substring(nameIndexOffset, nameEndIndex);
	  StringBuffer tempSB = removeUTFCharacters(tempName);
	  tempName = tempSB.toString();	  
	  tempBusiness.setName(tempName);
	  System.out.println(tempName);
	  
	  
	  // Finds the rating
	  int ratingIndex = info.indexOf("\"rating\"");
	  int ratingIndexOffset = ratingIndex + 10;
	  int ratingEndIndex = info.indexOf(',', ratingIndexOffset);
	  String ratingString = info.substring(ratingIndexOffset, ratingEndIndex);
	  tempBusiness.setRating(Double.parseDouble(ratingString)); 
	  
	  
	  // Finds the URL
	  int urlIndex = info.indexOf("\"url\"");
	  int urlIndexOffset = urlIndex + 8;
	  int urlEndIndex = info.indexOf("\"", urlIndexOffset);
	  tempBusiness.setYelpSite(info.substring(urlIndexOffset, urlEndIndex));
	  
	  
	  // Finds the phone number
	  int phoneIndex = info.indexOf("\"phone\"");
	  if(phoneIndex > 0){
		  int phoneIndexOffset = phoneIndex + 10;
		  int phoneEndIndex = info.indexOf("\"", phoneIndexOffset);
		  //System.out.println("Phone: " + info.substring(phoneIndexOffset, phoneEndIndex));
		  String phoneNumber = info.substring(phoneIndexOffset, phoneEndIndex);
		  tempBusiness.setPhoneNumber(phoneNumber);
	  }
	  
	  
	  int latitudeIndex = info.indexOf("\"latitude\"");
	  int longitudeIndex = info.indexOf("\"longitude\"");
	  
	  if(latitudeIndex > 0 && longitudeIndex > 0){
		  double latitude = 0;
		  double longitude = 0;
		  int latitudeIndexOffset = latitudeIndex + 12;
		  int latitudeEndIndex = info.indexOf(',', latitudeIndexOffset);
		  String latitudeString = info.substring(latitudeIndexOffset, latitudeEndIndex);
		  latitude = Double.parseDouble(latitudeString);
		  int longitudeIndexOffset = longitudeIndex + 12;
		  int longitudeEndIndex = info.indexOf('}', longitudeIndexOffset);
		  String longitudeString = info.substring(longitudeIndexOffset, longitudeEndIndex);
		  longitude = Double.parseDouble(longitudeString); 
		  
		  System.out.println(latitude + " " + longitude);
		  Coordinate tempCoordinate = new Coordinate(latitude, longitude);
		  tempBusiness.setCoordinates(tempCoordinate);
		
	  }	  
	  
	  // Finds the street address
	  int streetAddressIndex = info.indexOf("\"display_address\"");
	  int streetAddressIndexOffset = 0;
	  @SuppressWarnings("unused")
	  int streetAddressEndIndex = 0;
	  @SuppressWarnings("unused")
	  int cityStateZipCodeIndex = 0;
	  int cityStateZipCodeEndIndex = 0;
	  
	  if(streetAddressIndex > 0){
		  streetAddressIndexOffset = streetAddressIndex + 21;
		  streetAddressEndIndex = info.indexOf("\", \"", streetAddressIndexOffset);
		  cityStateZipCodeEndIndex = info.indexOf("\"]", streetAddressIndex);
		  String streetAddress = info.substring(streetAddressIndexOffset, cityStateZipCodeEndIndex);
		  streetAddress = streetAddress.replaceAll("[\"]", "");
		  //System.out.println(streetAddress);
		  tempBusiness.setAddress(streetAddress);
	  }
	  
	  int dealInfoIndex = info.indexOf("\"what_you_get\": ");
	  
	  if(dealInfoIndex > 0){
		  tempBusiness.setDeal(true);
		  int dealInfoIndexOffset = dealInfoIndex + 17;
		  int dealInfoEndIndex = info.indexOf("\", \"", dealInfoIndexOffset);
		  String dealInfo = info.substring(dealInfoIndexOffset, dealInfoEndIndex);
		  tempBusiness.setDealInfo(dealInfo);
	  }
	  
	  // Finds number of reviews
	  int reviewCountIndex = info.indexOf("\"review_count\": ");
	  
	  if(reviewCountIndex > 0){
		  int reviewCountIndexOffset = reviewCountIndex + 16;
		  int reviewCountEndIndex = info.indexOf(",", reviewCountIndexOffset);
		  int reviewCount = Integer.parseInt(info.substring(reviewCountIndexOffset, reviewCountEndIndex));
		  tempBusiness.setNumReviews(reviewCount);
		  //System.out.println(reviewCount);
		  
	  }
	  
	  return tempBusiness;
  }
  
  /**
   * Command-line interface for the sample Yelp API runner.
   */
  private static class YelpAPICLI {
    @Parameter(names = {"-q", "--term"}, description = "Search Query Term")
    public String term = newTerm;

    @Parameter(names = {"-l", "--location"}, description = "Location to be Queried")
    public String location = newLocation;
  }

  
  /**
   * Converts miles to meters for Yelp API
   */
  private static int milesToMeters(int miles){
	  int meters = (int) (miles * 1609.344); 
	  
	  return meters;
  }
  /**
   * Main entry for sample Yelp API requests.
   * <p>
   * After entering your OAuth credentials, execute <tt><b>run.sh</b></tt> to run this example.
   */
  public static ArrayList<Business> run(String[] args, String inputActivity,
		  String inputCity, int inputRadius, boolean inputDeals) {
  
    newTerm = inputActivity; 	// Category of Activity
    newLocation = inputCity; // Location of Activity
    radiusFilter = inputRadius; // Miles from location of Activity
      
	  
	YelpAPICLI yelpApiCli = new YelpAPICLI();
    new JCommander(yelpApiCli, args);

    
    
    YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
    queryAPI(yelpApi, yelpApiCli);
    
    return businesses;
    
    
  }
}
