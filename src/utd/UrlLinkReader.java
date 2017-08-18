package utd;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class UrlLinkReader {

	static HashMap<String, String> UrlFileMap = new HashMap<String, String>();
	static HashMap<String, ArrayList<String>> inLinks_file = new HashMap<String, ArrayList<String>>();
	static HashMap<String, ArrayList<String>> outLinks_file = new HashMap<String, ArrayList<String>>();
	static TreeSet<String> baseSet = new TreeSet<String>();
	//static String inlinksBasepath = "C:/Users/Shakti/Downloads/Fall16/InformationRetrieval/project/linkAnalysisResult/inlinkFiles/";
	static String outlinksBasePath = "D:\\UTD\\IR\\Project\\Source Code\\Two\\outlinkFiles\\";

	/* Reads unique url, filename to hashmap */
	public static HashMap<String, String> readUrlFiles(String Filename)
	{
		FileReader input;
		String myLine;
		try {
			input = new FileReader(Filename);
			BufferedReader reader = new BufferedReader(input); 
			while ( (myLine = reader.readLine()) != null)
			{   
				if(myLine.trim().length()==0)
					continue;
				String[] split = myLine.split(" ");
				UrlFileMap.put(split[0], split[1]);
			}
			reader.close();
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return UrlFileMap;
	}
	
	public static void getInOutLinks(TreeSet<String> rootset, String UrlMapFilename)
	{
		readUrlFiles(UrlMapFilename);
		for(String url : rootset)
		{
			if(UrlFileMap.containsKey(url))
			{
				try {
					FileReader outLinkReader = new FileReader("D:\\UTD\\IR\\Project\\Source Code\\Two\\outlinkFiles\\"+UrlFileMap.get(url)+".txt");
			        BufferedReader outRead = new BufferedReader(outLinkReader);
			        String link = "";       
			        //Add inlinks and outLinks
			        while ( (link = outRead.readLine()) != null)
					{ 
			        	//if inLink Map contains url append inlink to it
			        	link = link.trim();
			        	ArrayList<String> temp;
			        	if(link.startsWith("==="))
			        	{
			        		link = link.split(" ")[1];
			        		link = link.trim();
			        		if(inLinks_file.containsKey(url))
				        	{
				        		temp = inLinks_file.get(url);
				        	}
				        	else
				        	{
				        		temp = new ArrayList<String>();
				        	}
				        	temp.add(link);
			        		inLinks_file.put(url, temp);
			        	}
			        	else
			        	{
			        		if(outLinks_file.containsKey(url))
				        	{
				        		temp = outLinks_file.get(url);
				        	}
				        	else
				        	{
				        		temp = new ArrayList<String>();
				        	}
				        	temp.add(link);
			        		outLinks_file.put(url, temp);
			        	}
		        		baseSet.add(link);
					}
				outRead.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////
		        //Add inlinks and outlinks to new urls in the baseset
				for(String baseurl : baseSet)
				{
					if(UrlFileMap.containsKey(baseurl))
					{
						FileReader inLinkReader;
						try {
							inLinkReader = new FileReader(outlinksBasePath+UrlFileMap.get(baseurl)+".txt");
							BufferedReader inRead = new BufferedReader(inLinkReader);
					        String link = "";
					
					        while ( (link = inRead.readLine()) != null)
							{ 
					        	
					        	//if inLink Map contains url append inlink to it
					        	link = link.trim();
					        	if(link.startsWith("==="))
					        	{
					        		link = link.split(" ")[1];
					        		link = link.trim();
						        	ArrayList<String> temp;
						        	if(inLinks_file.containsKey(baseurl))
						        	{
						        		temp = inLinks_file.get(baseurl);
						        	}
						        	else
						        	{
						        		temp = new ArrayList<String>();
						        	}
						        	temp.add(link);
					        		inLinks_file.put(baseurl, temp);
					        	}
					        	else
					        	{
						        	ArrayList<String> temp;
						        	if(outLinks_file.containsKey(baseurl))
						        	{
						        		temp = outLinks_file.get(baseurl);
						        	}
						        	else
						        	{
						        		temp = new ArrayList<String>();
						        	}
						        	temp.add(link);
					        		outLinks_file.put(baseurl, temp);
					        	}
							}
					        
					       inRead.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				//Add root set urls to baseset
				baseSet.addAll(rootset);
			
}
}
