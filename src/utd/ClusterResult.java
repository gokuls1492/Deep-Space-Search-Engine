package utd;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClusterResult {

	public static Map<String, Integer> urlClusters;
		
	public JSONArray clustering(JSONArray jsonInitData,
			Map<String, Integer> clusterMap) throws Exception {
		
		/*for(int i=0; i<jsonData.length(); i++)
		{
		JSONObject results = jsonData.getJSONObject(i);
		String url =  results.get("url").toString();
		System.out.println(url);
		}*/
		urlClusters = clusterMap;
		//int count =0;
		//clusterMap = clusterMap;
		/*for (Entry<String, Integer> value : urlClusters.entrySet()){
		    System.out.println("Value = " + value.getValue()+"Key:"+value.getKey());
		    count++;
		}*/
		//System.out.println("count = " +count);
		int maxvalue = 0;
		int finalcluster = 0;
		Map<Integer, Integer> countOfClusters = new TreeMap<Integer, Integer>();
		Integer clusterValue = 0;

		JSONArray urlAarray = jsonInitData;
		int i = 0;
		while(i<10 && i< urlAarray.length()) {

			JSONObject b = urlAarray.getJSONObject(i);
			String url = b.getString("id");
			//System.out.println("temp "+url);
			clusterValue = checkingAptCluster(url);

			if (countOfClusters.containsKey(clusterValue)) {
				countOfClusters.put(clusterValue, countOfClusters.get(clusterValue) + 1);
				/*System.out.println("cluster value map :");
				for (Entry<Integer, Integer> value : countMap.entrySet())
				    System.out.println("Value = " + value.getValue()+"Key:"+value.getKey());*/
				
			} else {
				countOfClusters.put(clusterValue, 1);
			}
			i++;
		}

		for (Map.Entry<Integer, Integer> entry : countOfClusters.entrySet()) {

			if (entry.getValue() > maxvalue) {
				maxvalue = entry.getValue();
				finalcluster = entry.getKey();
			}
		}
		LinkedList<String> linkedlist = new LinkedList<String>();
		JSONArray docarray = new JSONArray();
		for (int j = 0; j < urlAarray.length(); j++) {
			JSONObject b = urlAarray.getJSONObject(j);
			String url = b.getString("id");
			//System.out.println("Final Cluster :" +finalcluster);
			if (checkingAptCluster(url) == finalcluster) {
				linkedlist.add(url);
			} 
		}
		for(String str: linkedlist)
	      {
			docarray.put(getContent(str));
	      }
		//System.out.println(docarray);
		/*JSONObject result = new JSONObject();

		result.put("docs", docarray);

		JSONObject finalResponse = new JSONObject();

		finalResponse.put("response", result);
		System.out.println(finalResponse);
		return finalResponse;*/
		return docarray;

	}

	private static Integer checkingAptCluster(String url) {

		Integer cluster = 0;
		if (urlClusters.containsKey(url)) {
			cluster = urlClusters.get(url);
		} else{
			cluster = 0;
		}
		if (cluster != null)
			return cluster;
		else
			return 0;
	}
	
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
/*	public static void main(String[] args) throws JSONException, FileNotFoundException {

		 
		FileRead fr = new FileRead();
		clusterMap = fr.readFromFile();
	}*/
}
