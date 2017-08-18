<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query Expansion Results</title>
<%@ page
import="org.json.JSONArray"
import="org.json.JSONException"
import="org.json.JSONObject"
import="java.io.BufferedReader"
import="org.json.JSONObject"
import= "utd.RelevanceModelResults"
%>

</head>
<body>
<jsp:useBean id="model" scope="session" class="utd.RelevanceModelResults"/>
<%
JSONArray aresults = model.getResults(request.getParameter("value").toString());
JSONObject results = null;
out.println("<b>"+"Requesting results from Search Engine..."+"</b>"+"<br/>");
try {
	for(int i=0; i<aresults.length(); i++)
	{
		results = aresults.getJSONObject(i);
		out.println("<a href="+results.get("url")+">"+"<b>"+"<h4>"+results.get("title")+"</h4>"+"</b>"+"</a>");
		out.println("<b>"+results.get("url")+"</b>"+"<br/>");
		//out.println(results.get("snippet")+"<br/>");
	}
} catch (JSONException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
%>
</body>
</html>