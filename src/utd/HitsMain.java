package utd;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class HitsMain {
	
	//public static void main(String[] args){
	public JSONArray getHitResults(String query){
	JSONArray results = getResults(query);
	System.out.println(query);
	TreeSet<String> resultSet = new TreeSet<String>();
	LinkedHashMap<String, Double> hitsOrderedUrls = new LinkedHashMap<String, Double>();
	try {
		for(int i=0; i<results.length(); i++)
		{
			JSONObject resultob = (JSONObject) results.get(i);
			String url = resultob.getString("id");
			Double score = resultob.getDouble("score");
			System.out.println("URL: "+url+" Score: "+score);
			resultSet.add(url);
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	

	//Call hits ranking
	HitsRanker hitRank = new HitsRanker();
	hitsOrderedUrls = hitRank.getHitsScores(resultSet);
	
	//Print ordered scores
	System.out.println("After hits ordering");
	JSONArray hitsArray = new JSONArray();
	for(String url : hitsOrderedUrls.keySet())
	{
		System.out.println("URL: "+ url+ " AuthScore: "+hitsOrderedUrls.get(url));
		//Get JSON Object and append to array
		hitsArray.put(getContent(url));
	}
	return hitsArray;
	}
	
	static JSONArray getResults(String query)
	{
		String queryText = query;
		String Query;
		try {
			Query = "http://localhost:8983/solr/spaceSearch/select?fl=*,score&indent=on&q="	+ queryText.replaceAll(" ", "+") + "&rows=10&wt=json";
			System.out.println(Query);
			final URL url = new URL(Query);
			final URLConnection connection = url.openConnection();
			final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			final StringBuilder results = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				results.append(inputLine);
			}
			JSONObject items = null;
			JSONArray dataArray = null;
			if (results != null) {
				JSONObject obj = new JSONObject(results.toString());
				items = obj.getJSONObject("response");
				dataArray = items.getJSONArray("docs");
			}
			
			return(dataArray);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	//Gets JSON object for a URL
	static JSONObject getContent(String urlId)
	{
		String QueryURL;
		try {
			QueryURL = "http://localhost:8983/solr/spaceSearch/select?indent=on&q=id:\""
					+ urlId + "\"&rows=10&wt=json";
			//System.out.println(QueryURL);
			final URL url = new URL(QueryURL);
			final URLConnection connection = url.openConnection();
			final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			final StringBuilder results = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				results.append(inputLine);
			}
			JSONObject items = null;
			JSONObject dataObj = null;
			JSONArray dataArr = null;
			if (results != null) {
				JSONObject obj = new JSONObject(results.toString());
				items = obj.getJSONObject("response");
				dataArr = items.getJSONArray("docs");
				dataObj = dataArr.getJSONObject(0);
			}
			
			return(dataObj);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
