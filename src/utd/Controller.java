package utd;
import deepspace.queryexpansion.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.javafx.collections.MappingChange.Map;

import deepspace.queryexpansion.*;
@WebServlet(name="Controller", urlPatterns={"/Controller"})
public class Controller extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    public static HashMap<String, Integer> clusterMap;
    //private String stopwords_file_Path = "C:\\Users\\gokul\\Search Engine\\DeepSpace\\WebContent\\resourcesIR\\stopwords";
    //private String common_words_file_Path = "C:\\Users\\gokul\\Search Engine\\DeepSpace\\WebContent\\resourcesIR\\common_words";
    private String wordnet_directory_file_Path = "C:\\Users\\gokul\\Search Engine\\DeepSpace\\WebContent\\resourcesIR\\dict";
    //String wordnet_directory_file_Path = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/resourcesIR/dict";
    
    
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    		
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        //PrintWriter out = response.getWriter();
        String query = request.getParameter("field");
        QueryBean bean=new QueryBean();  
        bean.setQuery(query);     	
        String expandedQuery = expandQuery_sc(query);
        String expQuery_asc = expandQuery_asc(query);
        String expQuery_met = expandQuery_met(query);
        request.setAttribute("query",query);  
        request.setAttribute("expandedQuery",expandedQuery);  
        request.setAttribute("expandedQuery_asc",expQuery_asc);
        request.setAttribute("expandedQuery_met",expQuery_met);
        clusterMap = new HashMap<String, Integer>();
        clusterMap = readFromFile();
        request.setAttribute("mapObj",clusterMap);  

        
        request.getRequestDispatcher("result.jsp").forward(request, response);
        
        
    }
    
    private String expandQuery_sc(String userInput) {        
    	//ExpandQuery expandQuery = new ExpandQuery();
        // Paths.get(".").toAbsolutePath().normalize().toString() + "/WebContent/resourcesIR/stopwords"
    	String serverURL = "http://localhost:8983/solr/spaceSearch";
        ExpandQuery eq = new ExpandQuery(serverURL, wordnet_directory_file_Path, 3, 5, false);
        
        //eq.setStop_words_list_file_path(stopwords_file_Path);
        //eq.setCommon_words_list_file_path(common_words_file_Path);
        //eq.setWordnet_directory_path(wordnet_directory_file_Path);
        //eq.setMAX_RELEVANT_PAGES(10);
        //eq.setMAX_EXPANDED_TERM_COUNT(3);

        // Required for Rocchio algorithm.
                /*ArrayList<String> rd = new ArrayList<>();
                rd.add("1");
                rd.add("4");
                rd.add("6");

                ArrayList<String> ird = new ArrayList<>();
                ird.add("2");
                ird.add("3");
                ird.add("5");*/
        	
        String expanded_query = eq.expandQueryUsingScalarClustering(userInput);
        //System.out.println("Expanded Query: " + expanded_query);

        return expanded_query;
    }
    
    
    private String expandQuery_asc(String userInput) {
        //ExpandQuery expandQuery1 = new ExpandQuery();
        // Paths.get(".").toAbsolutePath().normalize().toString() + "/WebContent/resourcesIR/stopwords"
    	String serverURL = "http://localhost:8983/solr/spaceSearch";
        ExpandQuery eq1 = new ExpandQuery(serverURL, wordnet_directory_file_Path, 3, 5, false);
        
        //expandQuery1.setStop_words_list_file_path(stopwords_file_Path);
        //expandQuery1.setCommon_words_list_file_path(common_words_file_Path);
        //expandQuery1.setWordnet_directory_path(wordnet_directory_file_Path);
        //expandQuery1.setMAX_RELEVANT_PAGES(10);
        //expandQuery1.setMAX_EXPANDED_TERM_COUNT(3);

        String expanded_query = eq1.expandQueryUsingAssociationClusters(userInput);

        return expanded_query;
    }
    
    private String expandQuery_met(String userInput) {
   //     ExpandQuery expandQuery2 = new ExpandQuery();
        // Paths.get(".").toAbsolutePath().normalize().toString() + "/WebContent/resourcesIR/stopwords"
     	String serverURL = "http://localhost:8983/solr/spaceSearch";
        ExpandQuery eq2 = new ExpandQuery(serverURL, wordnet_directory_file_Path, 3, 5, false);
   
     //   expandQuery2.setStop_words_list_file_path(stopwords_file_Path);
       // expandQuery2.setCommon_words_list_file_path(common_words_file_Path);
        //expandQuery2.setWordnet_directory_path(wordnet_directory_file_Path);
        //expandQuery2.setMAX_RELEVANT_PAGES(10);
        //expandQuery2.setMAX_EXPANDED_TERM_COUNT(3);

        String expanded_query = eq2.expandQueryUsingMetricClustering(userInput);

        return expanded_query;
    }
    
    private static HashMap<String, Integer> readFromFile() throws FileNotFoundException {
    	final String clusterFileName = "C:\\Users\\gokul\\Search Engine\\DeepSpace\\WebContent\\Cluster\\IdClusteres.txt";
    	clusterMap = new HashMap<String, Integer>();
    	Scanner in = new Scanner(new File(clusterFileName));
		String line;
		while (in.hasNextLine()) {
			line = in.nextLine();
			String[] words = line.split(",");
			if (clusterMap.containsKey(words[0])) {
			} else {
				clusterMap.put(words[0], Integer.valueOf(words[1]));
			}
		}
		return clusterMap;
	}
}