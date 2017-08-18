package utd;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class RelevanceModelResults {	
		public static JSONArray getResults(String query)
		{
			String queryText = query;
			String Query;
			try {
				//http://localhost:8983/solr/spaceSearch/  http://solrvirtualmachine.cloudapp.net:8888/solr/spaceSearch
				Query = "http://localhost:8983/solr/spaceSearch/select?indent=on&q="
						+ query.replaceAll(" ", "+") + "&rows=10&wt=json";
				//System.out.println(Query);
				final URL url = new URL(Query);
				final URLConnection connection = url.openConnection();
				final BufferedReader in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String inputLine;
				final StringBuilder response = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				JSONObject items = null;
				JSONArray dataArray = null;
				if (response != null) {
					JSONObject obj = new JSONObject(response.toString());
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
}
