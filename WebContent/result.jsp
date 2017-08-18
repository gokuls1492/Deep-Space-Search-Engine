<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Deep Space</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link href="style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/cufon-yui.js"></script>
<script type="text/javascript" src="js/arial.js"></script>
<script type="text/javascript" src="js/cuf_run.js"></script>
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/radius.js"></script>
<script type="text/javascript" src="js/search.js"></script>
<%@ page
import="org.json.JSONArray"
import="org.json.JSONException"
import="org.json.JSONObject"
import="java.io.BufferedReader"
import="org.json.JSONObject"
import= "utd.GoogleResults"
import= "java.util.HashMap"
%>
<script type="text/javascript">

function ontabclick(tabname)
{
	var googlediv = document.getElementById("googlediv");
	var bingdiv = document.getElementById("bingdiv");
	var googleheaderdiv = document.getElementById("googleheader");
	var bingheaderdiv = document.getElementById("bingheader");
	if(tabname == "Google"){
		googlediv.style.display = 'block';
		bingdiv.style.display = 'none';
		googleheaderdiv.classList.add("selected");
		bingheaderdiv.classList.remove("selected");
	}else{
		googlediv.style.display = 'none';
		bingdiv.style.display = 'block';	
		googleheaderdiv.classList.remove("selected");
		bingheaderdiv.classList.add("selected");
	}		
}

function onreltabclick(tabname)
{
	var reldiv1 = document.getElementById("reldiv1");
	var reldiv2 = document.getElementById("reldiv2");
	var relheaderdiv1 = document.getElementById("relheader1");
	var relheaderdiv2 = document.getElementById("relheader2");
	if(tabname == "Rel"){
		reldiv1.style.display = 'block';
		reldiv2.style.display = 'none';
		relheaderdiv1.classList.add("selected");
		relheaderdiv2.classList.remove("selected");
	}else{
		reldiv1.style.display = 'none';
		reldiv2.style.display = 'block';	
		relheaderdiv1.classList.remove("selected");
		relheaderdiv2.classList.add("selected");
	}		
}

function onprojtabclick(tabname)
{
	var reldiv = document.getElementById("reldiv");
	var clusdiv = document.getElementById("clusdiv");
	var querydiv = document.getElementById("quexpdiv");
	var relheaderdiv = document.getElementById("relheader");
	var clusheaderdiv = document.getElementById("clusheader");
	var queryheaderdiv = document.getElementById("queryheader");
	if(tabname == "Relev"){
		reldiv.style.display = 'block';
		querydiv.style.display = 'none';
		clusdiv.style.display = 'none';
		relheaderdiv.classList.add("selected");
		queryheaderdiv.classList.remove("selected");
		clusheaderdiv.classList.remove("selected");
	}else if(tabname == "Clust"){
		clusdiv.style.display = 'block';
		querydiv.style.display = 'none';
		reldiv.style.display = 'none';
		clusheaderdiv.classList.add("selected");
		queryheaderdiv.classList.remove("selected");
		relheaderdiv.classList.remove("selected");
	}
	else{
		reldiv.style.display = 'none';
		querydiv.style.display = 'block';
		clusdiv.style.display = 'none';
		relheaderdiv.classList.remove("selected");
		queryheaderdiv.classList.add("selected");
		clusheaderdiv.classList.remove("selected");
	}		
}

function onquerexptabclick(tabname)
{
	var reldiv1 = document.getElementById("quexpdiv1");
	var reldiv2 = document.getElementById("quexpdiv2");
	var reldiv3 = document.getElementById("quexpdiv3");
	var reldiv4 = document.getElementById("quexpdiv4");
	var relheaderdiv1 = document.getElementById("qh1");
	var relheaderdiv2 = document.getElementById("qh2");
	var relheaderdiv3 = document.getElementById("qh3");
	var relheaderdiv4 = document.getElementById("qh4");
	if(tabname == "QE1"){
		reldiv1.style.display = 'block';
		reldiv2.style.display = 'none';
		reldiv3.style.display = 'none';
		reldiv4.style.display = 'none';
		relheaderdiv1.classList.add("selected");
		relheaderdiv2.classList.remove("selected");
		relheaderdiv3.classList.remove("selected");
		relheaderdiv4.classList.remove("selected");
	}else if(tabname == "QE2"){
		reldiv2.style.display = 'block';
		reldiv1.style.display = 'none';
		reldiv3.style.display = 'none';
		reldiv4.style.display = 'none';
		relheaderdiv2.classList.add("selected");
		relheaderdiv1.classList.remove("selected");
		relheaderdiv3.classList.remove("selected");
		relheaderdiv4.classList.remove("selected");
	}
	else if(tabname == "QE3"){
		reldiv3.style.display = 'block';
		reldiv1.style.display = 'none';
		reldiv2.style.display = 'none';
		reldiv4.style.display = 'none';
		relheaderdiv3.classList.add("selected");
		relheaderdiv1.classList.remove("selected");
		relheaderdiv2.classList.remove("selected");
		relheaderdiv4.classList.remove("selected");
	}
}

</script>
</head>
<body style="height: 573px;width: 100%;overflow-x: hidden;overflow-y: hidden;">
<%! void print(JspWriter out,JSONArray aresults) throws Exception{
	out.println("<b>"+"Requesting results from Search Engine..."+"</b>"+"<br/>");
	JSONObject results = null;
	try {
		for(int i=0; i<aresults.length(); i++)
		{
			out.println("<div class='ur'>");
			results = aresults.getJSONObject(i);
			String url =  results.get("url").toString();
			url = url.substring(url.indexOf('[')+2,url.indexOf(']')-1);
			String title = "";
			if(!results.isNull("title"))
			{
				title = results.get("title").toString();
				//title=title.substring(title.indexOf('[')+2,title.indexOf(']')-1);
			}
			
			String content = results.get("content").toString();
			System.out.println(content);
			String snippet = content.substring(0, 30);
			out.println("<a href="+url+">"+"<b>"+"<h3>"+title +"</h3>"+"</b>"+"</a>");
			out.println("<div class='url'>");
			out.println("<b>"+url+"</b>"+"<br/>");
			out.println("</div>");
			out.println("<div class='link-content'>");
			out.println(snippet+"..."+"<br/>");			
			out.println("</div>");
			out.println("</div>");
		}
	} catch (JSONException e) {
	// TODO Auto-generated catch block
			e.printStackTrace();
	}
}
%>
<div id = "content" style = "width:100%;height:100%;">
	 <div id ="projectmenu" style = "width:50%;height:100%;float:left">
		<div id="tabheader">  
			<div id ="tabcontainer">
     			<div id = "relheader" class= "tab selected" onclick = 'onprojtabclick("Relev")'>Relevance Model</div>
     			<div id = "clusheader" class = "tab" onclick = 'onprojtabclick("Clust")'>Clustering</div>
     			<div id = "queryheader" class = "tab" onclick = 'onprojtabclick("Query")'>Query Expansion</div>
			</div>
		</div>
		<div id="tabcontent" style="height: calc(100% - 30px);padding-left: 10px;overflow-y:hidden;">
			<div id= "reldiv" style= "overflow-y:hidden;height:100%;">
				<div id="tabheader">  
					<div id ="tabcontainer">
     					<div id = "relheader1" class= "tab selected" onclick = 'onreltabclick("Rel")'>Page Rank</div>
     					<div id = "relheader2" class = "tab" onclick = 'onreltabclick("Hit")'>HITS Algo</div>
					</div>
				</div>
				<div id="tabcontent" style="height: calc(100% - 30px);padding-left: 10px;">
					<div id= "reldiv1" style= "overflow-y:scroll;height:100%">
						<jsp:useBean id="model" scope="session" class="utd.RelevanceModelResults"/>
						<b>Query:</b><input type="text" class="query" value="<%=request.getAttribute("query")%>"><br/>
						<%						
							JSONArray aresults = model.getResults(request.getAttribute("query").toString());
							//JSONObject results = null;				
							print(out,aresults);
						%>
					</div>
					<div id= "reldiv2" style= "overflow-y:scroll;height:100%">
						<jsp:useBean id="hit" scope="session" class="utd.HitsMain"/>
						<b>Query</b><input type="text" class="query" value="<%=request.getAttribute("query")%>"><br/>
						<%
							aresults = hit.getHitResults(request.getAttribute("query").toString());
							print(out,aresults);
						%>
					</div>
				</div>			
			</div>
			<div id= "quexpdiv" style= "overflow-y:hidden;height:100%">
				<div id="tabheader">
					<div id = "tabcontainer">	
						<div id = "qh1" class = "tab" onclick = 'onquerexptabclick("QE1")'> Scalar Clustering</div>
						<div id = "qh2" class = "tab" onclick = 'onquerexptabclick("QE2")'> Association Clusters</div>
						<div id = "qh3" class = "tab" onclick = 'onquerexptabclick("QE3")'> Metric Clustering</div>
					</div>
				</div>
				<div id="tabcontent" style="height: calc(100% - 30px);padding-left: 10px;">
					<div id= "quexpdiv1" style= "overflow-y:scroll;height:100%;overflow-x:hidden">
						<jsp:useBean id="queryexp1" scope="session" class="utd.RelevanceModelResults"/>
						<b>Query:</b><input type="text" class="query" value="<%=request.getAttribute("query")%>"/><br/>
						<b>Expanded Query:</b><input type="text" class="query" value="<%=request.getAttribute("expandedQuery")%>" style="width: 400px;"/><br/>
						<%
							aresults = queryexp1.getResults(request.getAttribute("expandedQuery").toString());
							//results = null;
							print(out,aresults);
						%>
					</div>
					<div id= "quexpdiv2" style= "overflow-y:scroll;height:100%">
					<jsp:useBean id="queryexp2" scope="session" class="utd.RelevanceModelResults"/>
					<b>Query:</b><input type="text" class="query" value="<%=request.getAttribute("query")%>"/><br/>
					<b>Expanded Query:</b><input type="text" class="query" value="<%=request.getAttribute("expandedQuery_asc")%>"style="width: 400px;"/><br/>
						
						<%
							aresults = queryexp1.getResults(request.getAttribute("expandedQuery_asc").toString());
							//results = null;
							print(out,aresults);
						%>
					</div>
					<div id= "quexpdiv3" style= "overflow-y:scroll;height:100%">
					<jsp:useBean id="queryexp3" scope="session" class="utd.RelevanceModelResults"/>
					<b>Query:</b><input type="text" class="query" value="<%=request.getAttribute("query")%>"/><br/>
					<b>Expanded Query:</b><input type="text" class="query" value="<%=request.getAttribute("expandedQuery_met")%>"style="width: 400px;"/><br/>
						<%
							aresults = queryexp1.getResults(request.getAttribute("expandedQuery_met").toString());
							//results = null;
							print(out,aresults);
						%>
					</div>
				</div>
			</div>

			<!-- Clustering -->
			<div id= "clusdiv" style= "overflow-y:scroll;height:100%">
				<jsp:useBean id="cluster" scope="session" class="utd.ClusterResult"/>
				<b>Query:</b><input type="text" class="query" value="<%=request.getAttribute("query")%>"/><br/>
				<%
					JSONArray jsonData = model.getResults(request.getAttribute("query").toString());
					HashMap<String,Integer> temp = new HashMap<String,Integer>(); 
					temp = (HashMap<String,Integer>)request.getAttribute("mapObj");
					aresults = cluster.clustering(jsonData, temp);
					//results = null;
					print(out,aresults);
				%>	
			</div>
		</div>
	</div>
	<div id="tabmenu" style="height:100%;width: 48%;float: right;border-left: black;border-left-width: thin;border-left-style: solid;">
		<div id="tabheader">  
			<div id ="tabcontainer">
    		 	<div id = "bingheader" class= "tab" onclick = 'ontabclick("Bing")'>Bing</div>
     			<div id = "googleheader" class = "tab selected" onclick = 'ontabclick("Google")'>Google</div>
			</div>
		</div>
		<div id="tabcontent" style="height: calc(100% - 30px);padding-left: 10px;">
			 <div id= "bingdiv" style= "display:none;overflow-y:scroll;height:100%">
				<jsp:useBean id="bing" scope="session" class="utd.BingResults"/>
				<b>Query:</b><input type="text" class="query" value="<%=request.getAttribute("query")%>"/><br/>
					<%
					aresults = bing.getBingResults(request.getAttribute("query").toString());
					JSONObject results = null;
					out.println("<b>"+"Requesting results from Bing.com..."+"</b>"+"<br/>");
					try {
						for(int i=0; i<aresults.length(); i++)
						{
							results = aresults.getJSONObject(i);
							out.println("<div class='link'>");
							out.println("<a href="+results.get("url")+">"+"<b>"+"<h3>"+results.get("name")+"</h3>"+"</b>"+"</a>");
							out.println("<div class='url'>"+results.get("displayUrl")+"</div>"+"<div class='link-content'>");
							//out.println("<b>"+results.get("displayUrl")+"</b>"+"<br/>");
							out.println(results.get("snippet")+"<br/>");
							out.println("</div>");
							out.println("</div>");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					%>
			</div>
			 <div id = "googlediv" style= "overflow-y:scroll;height:100%;width:calc(100% - 20px);">
				<jsp:useBean id="google" scope="session" class="utd.GoogleResults"/>
				<b>Query:</b><input type="text" class="query" value="<%=request.getAttribute("query")%>"/><br/>
					<%
					aresults = google.getGoogleResults(request.getAttribute("query").toString());
					results = null;
					out.println("<b>"+"Requesting results from Google.com..."+"</b>"+"<br/>");
					try {
						for(int i=0; i<aresults.length(); i++)
						{
							results = aresults.getJSONObject(i);
							//Object titleGoogleObject = results.get("title");
							String titleGoogle= "";
							if(!results.isNull("title"))
							{
								titleGoogle = results.get("title").toString();
	
							}
							out.println("<div class='link'>");
							out.println("<a href="+results.get("link")+">"+"<b>"+"<h3>"+ titleGoogle +"</h3>"+"</b>"+"</a>");
							out.println("<div class='url'>"+results.get("displayLink")+"</div>"+"<div class='link-content'>");
							//out.println("<b>"+results.get("displayLink")+"</b>"+"<br/>");
							out.println(results.get("snippet")+"<br/>");
							out.println("</div>");
							out.println("</div>");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					%>
			</div>
		</div>
	</div>
</div>
  <div class="footer">
    <div class="footer_resize">
      <p class="lf">Copyright &copy; 2016 <a href="#">Deep Space</a> - All Rights Reserved</p>
      <p class="rf"></p>
      <div class="clr"></div>
    </div>
  </div>
<!-- END PAGE SOURCE -->
<!--  </div> -->
</body>
</html>