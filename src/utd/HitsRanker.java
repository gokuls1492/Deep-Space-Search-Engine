package utd;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class HitsRanker {
	private static HashMap<String,ArrayList<String>> outLinks;
	private static HashMap<String,ArrayList<String>> inLinks;
	private static HashMap<String,Double> hubScore;
	private static HashMap<String,Double> authScore;
	public  static TreeSet<String> baseSet = new TreeSet<String>();
	public  static TreeSet<String> rootSet = new TreeSet<String>();
	public  static LinkedHashMap<String, Double> sortedUrls = new LinkedHashMap<String, Double>();
//	private static String adjMatrixFile = "C:/Users/Shakti/Downloads/Fall16/InformationRetrieval/project/linkAnalysisResult/links-big.txt";
	private static String urlMapFile = "C:\\Users\\gokul\\Search Engine\\DeepSpace\\WebContent\\Hits\\Andromeda_urlMap.txt";
	

    LinkedHashMap<String, Double> getHitsScores(TreeSet<String> results) 		
    {
    	outLinks = new HashMap<>();
    	inLinks = new HashMap<>();
    	hubScore = new HashMap<>();
    	authScore = new HashMap<>();
    	rootSet = results;
    	makeLinkMap();
    	initializeRanking();
    	compute();
    	return sortedUrls;
	}
	
    //Create links map from the files
	public static void makeLinkMap()
	{
		UrlLinkReader.getInOutLinks(rootSet, urlMapFile);
		outLinks = UrlLinkReader.outLinks_file;
		inLinks = UrlLinkReader.inLinks_file;
		baseSet = UrlLinkReader.baseSet;
	}
	
	//Initialize ranks to 1
	public static void initializeRanking(){
		double initialRank = 1;
		for(String key :baseSet){
			if(outLinks.containsKey(key))
			{
				authScore.put(key, initialRank);
				hubScore.put(key, initialRank);
			}
			else
			{
				authScore.put(key, 0.0);
				hubScore.put(key, 0.0);
			}
		}
	}
	
	public static void compute(){
	
		for(int i=0;i<10;i++){
			HashMap<String,Double> newHubRank = calcHubScore();
			HashMap<String,Double> newAuthRank = calcAuthscore();
			hubScore = newHubRank;
			authScore = newAuthRank;
		}
		//Add authority scores for all the urls in the root set
		for(String url: rootSet)
		{
			sortedUrls.put(url, authScore.get(url));
		}
		//Sort URLs in descending order of authority scores
		sortedUrls = (LinkedHashMap<String, Double>) sortByValue(sortedUrls);
				
		
	}
	
	//Calculate Hub score
	public static HashMap<String,Double> calcHubScore(){
		HashMap<String,Double> tempRank = new HashMap<>();
		double normalizingFactor = 0;
		for(String key :hubScore.keySet()){
			ArrayList<String> tempList = outLinks.get(key);
			double hubScore =0.0;
			if(tempList!=null){
				for(String dest : tempList){
               	 if(baseSet.contains(dest))
					if(authScore.containsKey(dest))
               		 hubScore+=authScore.get(dest);
					else
						System.out.println("NewURL not in authscore");
				}
			}
			normalizingFactor += Math.pow(hubScore, 2);
			tempRank.put(key, hubScore);
		}
		normalizingFactor =Math.sqrt(normalizingFactor);
        for(String key:tempRank.keySet()){
        		if(normalizingFactor!=0)
                    tempRank.put(key, (tempRank.get(key)/normalizingFactor));
        		else
        			tempRank.put(key, 0.0);
        }
		return tempRank;
	}
	
	//Calculate authority score
	public static HashMap<String,Double> calcAuthscore(){
		HashMap<String,Double> tempRank = new HashMap<>();
		double normalizingFactor = 0;
		for(String key :authScore.keySet()){
			ArrayList<String> tempList = inLinks.get(key);
			
			double authScore =0.0;
			if(tempList!=null)
			{
				for(String dest : tempList){
	           	 if(baseSet.contains(dest))
	           	 {
					authScore+=hubScore.get(dest);
	           	 }
				}
			}
			normalizingFactor += Math.pow(authScore, 2);
			tempRank.put(key, authScore);
		}
		normalizingFactor =Math.sqrt(normalizingFactor);
        for(String key:tempRank.keySet()){
        	if(normalizingFactor!=0)
                tempRank.put(key, (tempRank.get(key)/normalizingFactor));
            else
        	    tempRank.put(key, 0.0);
        }
		
		return tempRank;
	}

	//Sort hashmap based on values
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
	              .collect(Collectors.toMap(
	                Map.Entry::getKey, 
	                Map.Entry::getValue, 
	                (e1, e2) -> e1, 
	                LinkedHashMap::new
	              ));
	}

	//Read adjacency matrix into an ArrayList
	 public static ArrayList<String> getAdjacencyMatrix(String fileName){
		   ArrayList<String> lineList = new ArrayList<String>();
		   try{
			   FileReader fileReader = new FileReader(fileName);
			   BufferedReader bufferedReader = new BufferedReader(fileReader);
	            String line =null;
	            while((line = bufferedReader.readLine()) != null) {
	                lineList.add(line);
	            } 
	            bufferedReader.close();
	            fileReader.close();
		   } catch(Exception e){
			   e.printStackTrace();
		   }
		   return lineList;
	   }

	 
}
