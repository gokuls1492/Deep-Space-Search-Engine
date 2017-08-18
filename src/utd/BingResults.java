package utd;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
public class BingResults {
	
	//public static void main(String[] args) throws Exception { // HashMap<String, HashMap<String,Object>>
	public JSONArray getBingResults(String input)throws Exception {		
		// TODO Auto-generated method stub
        final String bingKey ="5263ea7920a346048590c10c71ea9929";
        String query = URLEncoder.encode(input, Charset.defaultCharset().name());
		String BingSearchUrl = "https://api.cognitive.microsoft.com/bing/v5.0/search?q=" + query + "&mkt=en-us";
		String accountKeyEnc = Base64.getEncoder().encodeToString((bingKey + ":" + bingKey).getBytes());
        URL url = new URL(BingSearchUrl);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
        String user_agent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; FDM; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 1.1.4322)";
        //Chrome/4.0.249.0
        connection.setRequestProperty("User-Agent",user_agent);
        connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
        connection.setRequestProperty("ocp-apim-subscription-key", bingKey);
        HashMap<String, HashMap<String,Object>> output = new HashMap<String, HashMap<String,Object>>();
    	JSONArray results = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
            //    System.out.println(inputLine);
            	response.append(inputLine);
            }
            JSONObject json = new JSONObject(response.toString());
            JSONObject d = json.getJSONObject("webPages");
            results = d.getJSONArray("value");
            /*int resultsLength = results.length();
            String var;
            for (int i = 0; i < resultsLength; i++) {
                 var = "site"+(i+1);
                 JSONObject aResult = results.getJSONObject(i);
            	 output.put(var, new HashMap());
            	 output.get(var).put("url", aResult.get("displayUrl"));
            	 output.get(var).put("snippet", aResult.get("snippet"));
            	 output.get(var).put("name", aResult.get("name"));
            	 output.get(var).put("link", aResult.get("url"));
            	 //System.out.println(aResult.get("displayUrl"));
               // System.out.println(aResult.get("snippet"));
            }*/
        }
        return results;
    }
}