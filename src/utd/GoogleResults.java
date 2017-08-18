package utd;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.catalina.util.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

public class GoogleResults{
		//public static void main(String[] args) throws Exception{
		public JSONArray getGoogleResults(String qry) throws Exception{
		String key="AIzaSyDU6OadKOymSHrOVu3BV_JG9E-iqVsMsns";
			   // String qry="Android";//bean.getQuery(); //"Android";
			    String inc;			    
			    //GResultBean results = new GResultBean();
			    //Gson gson = new Gson();			    
			    //for (int k=1;k<=30;k=k+10){ 
				   int k=1;
			       inc = "";
				   inc="&start=";
				   inc = inc+k;
				   URL url = new URL(
			            "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=001980740002702100365:9n9akitrdk0&q="+qry.replaceAll(" ", "+")+ "&alt=json"+inc);
			    //013036536707430787589:_pqjad5hr1a& // 013224710640388606932:pkxzvmz32ck//
			    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			    conn.setRequestMethod("GET");
			    conn.setRequestProperty("Accept", "application/json");
			    HashMap<String, HashMap<String,Object>> output = new HashMap<String, HashMap<String,Object>>();
			    BufferedReader br = new BufferedReader(new InputStreamReader(
			            (conn.getInputStream())));
			    String inputLine;
	            StringBuilder response = new StringBuilder();
	            while ((inputLine = br.readLine()) != null) {
	             //   System.out.println(inputLine);
	            	response.append(inputLine);
	            }
	            JSONObject json = new JSONObject(response.toString());
	            //JSONObject d = json.getJSONObject("");
	            JSONArray results = json.getJSONArray("items");
	     
	     return results;
	}
}
