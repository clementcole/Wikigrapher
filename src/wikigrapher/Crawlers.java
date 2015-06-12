/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *Author: Clement Cole
 *Date: 01/15/2015 -> 06/12/2015
 *Wikigrapher: This is a simple web crawler I designed for wikipedia to create graphs for each article. The graphs created will
 * 		be in directories for each type of url.
 */
package wikigrapher;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



/**
 *
 * @author: Clement Cole
 * Date : 06/02/2015
 * Time : 9:45AM ==> Starting time
 * 
 */
public class Crawlers {
    public int count = 0;
    
    
    public Crawlers(){
    }//End of constructor that does nothing
    
    //This method will get the url of and concatenate the url with http://en.wikipedia.org/wiki/line, where line is the string variable holding the value of each line
    //This method has to be able to open the file and read it
    static void getURL(String line) throws IOException{ 
        
        String article_title = line;
        article_title = article_title.replaceAll("  ", "_");
        String url  = "http://en.wikipedia.org/wiki/"+article_title;
        print("Generating Graph for %s...", url);
        List<String> article_list = new ArrayList<String>();
        List<String> book_list = new ArrayList<String>();
        List<String> category_list = new ArrayList<String>();
        List<String> file_list = new ArrayList<String>();
        List<String> talk_list = new ArrayList<String>();
        List<String> p_search_list = new ArrayList<String>();
        List<String> citation_list = new ArrayList<String>();
        List<String> internal_page_list = new ArrayList<String>();
        
        Document doc        = Jsoup.connect(url).get(); //Create the Dom document with  Jsoup connect.
        Elements links      = doc.select("a[href]"); //Then we create links with the <a>link_to_another_article</a> elements
        Elements media      = doc.select("[src]");   //Select the type of media files and links to videos or images NB: Not using it for now
        Elements imports    = doc.select("link[href]"); //This will be used for importing files with links provided NB: Not using for now
        String url_pattern1 = ".*http://en.wikipedia.org/wiki/.*"; //Using this string to search for valid pattern 
        String url_pattern2 = ".*Special:BookSources/.*"; //Using this string to classify book references
        String url_pattern3 = ".*Help:Category.*"; //Using this string to classify categories, this is a <div></div> Class to an href, we don't need it.
        String url_pattern4 = ".*Category:.*"; //This is the actual link to the actual category example 
        String url_pattern5 = ".*File:.*"; //Not using anything to do with files, just articles
        String url_pattern6 = ".*Talk:.*"; //Using this string to classify talk
        String url_pattern7 = ".*(journal).*"; //Check for String (journal), which will indicate the title of the graph
        String url_pattern8 = ".*#mw-head.*"; //Check for String (#mw-head), which is the head of the file
        String url_pattern9 = ".*#p-search.*"; //Check for String (#p-search), which will 
        String url_pattern10 = ".*#cite.*";     //This will be used to remove in
        String url_pattern11 = ".*#.*"; //This pattern will by used to remove all the internal page redirection
        String url_pattern12 = ".*"+article_title+"#.*";
        String url_pattern13 = article_title+"(journal)";
        
        
        //File Graph = new File(article_title); //Create the 
        //Graph.mkdir();                        //Directory
        final File Graph = new File(article_title+"_Graph"); //Passed
        Graph.mkdir();                                      //Passed
        final String articles = article_title+"_articles_graph.txt";    //Create the file name for articles
        final String books = article_title+"_books_references_graph.txt"; //Create the file name for books
        final String categories = article_title + "_categories_graph.txt"; //Create the file name for categories
        final String files = article_title + "_files_graph.txt";    //Create the file name for files
        final String talks = article_title + "_talk_graph.txt";      //Create the file for talks
        final String searchs = article_title + "_serach_graph.txt";
        final String citations = article_title + "_citation_graph.txt"; 
        
        
        final File article_graph = new File(Graph, articles);        //Create the file for articles
        article_graph.createNewFile();                             //
        Writer articleWriter = null;
        
        final File book_graph = new File(Graph, books);           //Create the file for books
        book_graph.createNewFile();                             //
        Writer bookWriter = null;
        
        final File category_graph = new File(Graph, categories);      //Create the file for categories
        category_graph.createNewFile();                             //
        Writer categoryWriter = null;
        
        final File file_graph = new File(Graph, files);           //Create the file for files
        file_graph.createNewFile();                             //                           
        Writer fileWriter = null;
     
        final File talk_graph = new File(Graph, talks);
        talk_graph.createNewFile();
        Writer talkWriter = null;
        
        final File search_graph = new File(Graph, searchs);
        search_graph.createNewFile();
        Writer searchWriter = null;
       
        
        final File citation_graph = new File(Graph, citations);
        citation_graph.createNewFile();
        Writer citationWriter = null;
        
       
        
        
        //I'm only interested in the text links to other wikipedia articles. 
        //print("\nLinks: (%d)", links.size()); //Print out the links and their sizes
        for (Element link : links){ //Loop through all the links while assigning the value of each link to link 
            String term = link.attr("abs:href");  //Cast the link objects as strings
                if(Pattern.matches(url_pattern1, term)){ //If "http://en.wikipedia.org/wiki/ matches any of the sequence of characters produced by link.attr("abs:href"),
                    if(Pattern.matches(url_pattern2, term)){
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("Special:BookSources/", " "); //Replace Special:BookSources with space
                            term = term.replace("_", " "); //Replace _(underscore) with space //Replace all underscores with space
                            book_list.add(term);  //Finally add to the book_list
                            
                    }//End of 2nd pattern matches
                    
                    //Category List
                    else if(Pattern.matches(url_pattern4, term)){//Category pattern
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("Category:", " "); //Replace Category in url with space
                            term = term.replace("_", " "); //All underscores with space
                            category_list.add(term); //Add to the list of categories
                         
                    }
                    
                    //File list
                    else if(Pattern.matches(url_pattern5, term)){//File Pattern
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("File:", " "); //Replace all File patterns with spaces
                            term = term.replace("_", " "); //Replace _(underscores) with space
                            file_list.add(term);
                            
                    }
                    
                    //Talk list
                    else if(Pattern.matches(url_pattern6, term)){
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("Talk:", " "); 
                            term = term.replace("_"," "); 
                            talk_list.add(term);
                           
                    }
                    //psearch list
                    else if (Pattern.matches(url_pattern8, term)){ //#mw-head
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("#mw-head", " ");
                            term = term.replace("_", " ");
                            p_search_list.add(term);
                    }
                    
                    
                    else if (Pattern.matches(url_pattern9, term)){ 
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("#p-search", " ");
                            term = term.replace("_", " ");
                            p_search_list.add(term);
                                    
                    }
                    
                    
                    else if (Pattern.matches(url_pattern10, term)){
                             term = term.replace("http://en.wikipedia.org/wiki/", " ");
                             term = term.replace("#cite", " ");
                             term = term.replace("_", " ");
                             citation_list.add(term);
                    }
                    
                    
                    else if (Pattern.matches(url_pattern11, term)) {
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("_", " ");
                            article_list.add(term);
                    }
                    
                    else if (Pattern.matches(url_pattern12, term)){
                        
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("_", " ");
                            internal_page_list.add(term);
                    }
                    
                    
                    
                    else{
                            term = term.replace("http://en.wikipedia.org/wiki/", " ");
                            term = term.replace("_", " "); //Replace _(underscores) with space
                            article_list.add(term);
                    }
            
                }//End of if statement
               
           
        }//End of for loop's clause
        
        //Counting frequencies with maps
        Map<String, Integer> article_map = new HashMap<String, Integer>();
        Map<String, Integer> book_map = new HashMap<String, Integer>();
        Map<String, Integer> category_map = new HashMap<String, Integer>();
        Map<String, Integer> file_map = new HashMap<String, Integer>();
        Map<String, Integer> talk_map = new HashMap<String, Integer>();
        Map<String, Integer> p_search_map = new HashMap<String, Integer>();
        Map<String, Integer> citation_map = new HashMap<String, Integer>();
        
        
      
        //Creating the book_map
        for (String temp : book_list){
                Integer count = book_map.get(temp);
                book_map.put(temp, (count == null) ? 1 : count + 1);
        }
        
        //Creating the category map
        for (String temp : category_list){
                Integer count = book_map.get(temp);
                category_map.put(temp, (count == null) ? 1 : count + 1);
        }
        
        //Creating the file map
        for (String temp : file_list){
                Integer count = file_map.get(temp);
                file_map.put(temp, (count == null) ? 1 : count + 1);
        }
        
        //Creating the talk map
        for (String temp : talk_list){
                Integer count = talk_map.get(temp);
                talk_map.put(temp, (count == null) ? 1 : count + 1);
        }
        
        //Creating the p-search map
        for (String temp : p_search_list){
                Integer count = p_search_map.get(temp);
                p_search_map.put(temp, (count == null) ? 1 : count + 1);
        }
        
        //Creating the citation map
        for (String temp : citation_list){
                Integer count = citation_map.get(temp);
                citation_map.put(temp, (count == null) ? 1 : count + 1);
        }
        
        //Creating the article map
        for (String temp : article_list){
                Integer count = article_map.get(temp);
                article_map.put(temp, (count == null) ? 1 : count + 1);
        }
        
        
        
        //Category Graph
        System.out.println("\n\n");
        System.out.println("****************************************************\n");
        System.out.println("**************Category Graph************************\n");
        System.out.println("****************************************************\n");
	Map<String, Integer > treeMapC = new TreeMap<String, Integer>(category_map);
	printMap(treeMapC, category_graph);
        //End of Category Map
        
        //Article Graph
        System.out.println("\n\n");
        System.out.println("****************************************************\n");
        System.out.println("**************Article Graph*************************\n");
        System.out.println("****************************************************\n");
	Map<String, Integer > treeMapA = new TreeMap<String, Integer>(article_map);
	//printMap(treeMapA, article_graph);
        printMap(article_map, article_graph);
        //End of Article Map

        //Book Graph
        System.out.println("\n\n");
        System.out.println("****************************************************\n");
        System.out.println("**************Book Graph****************************\n");
        System.out.println("****************************************************\n");
	Map<String, Integer > treeMapB = new TreeMap<String, Integer>(book_map);
        //printMap(treeMapB, book_graph);
        printMap(book_map, book_graph);
        //End of Book Map
        
        //Citation
        System.out.println("\n\n");
        System.out.println("****************************************************\n");
        System.out.println("**************Citation Graph************************\n");
        System.out.println("****************************************************\n");
	Map<String, Integer > treeMapCitation = new TreeMap<String, Integer>(citation_map);
	//printMap(treeMapCitation, citation_graph);
        printMap(citation_map, citation_graph);
        //End of Citation Graph
        
        //p-search_map
        System.out.println("\n\n");
        System.out.println("****************************************************\n");
        System.out.println("**************P-Search Graph************************\n");
        System.out.println("****************************************************\n");
	Map<String, Integer > treeMapP = new TreeMap<String, Integer>(p_search_map);
	//printMap(treeMapP, search_graph);
        printMap(p_search_map, search_graph);
        //End of p-search_map
        
        //File Map
        System.out.println("\n\n");
        System.out.println("****************************************************\n");
        System.out.println("**************File Graph****************************\n");
        System.out.println("****************************************************\n");
	Map<String, Integer > treeMapF = new TreeMap<String, Integer>(file_map);
	//printMap(treeMapF, file_graph);
        printMap(file_map, file_graph);
        //End of File Map
        
        
        //Talk
        System.out.println("\n\n");
        System.out.println("****************************************************\n");
        System.out.println("**************Conferences Graph*********************\n");
        System.out.println("****************************************************\n");
	Map<String, Integer > treeMapT = new TreeMap<String, Integer>(talk_map);
	//printMap(treeMapT, talk_graph ); //End of Talk print
        printMap(talk_map, talk_graph);
        //End of Talk Map
        
     
        
    }//End of getURL() method
    
    
    
    
  
    
    //Print method for displaying anything in a String format
    //Takes message as 1st argument and Object as second argument
    //And prints them out in a string format
    private static void print(String message, Object... args) {
        System.out.println(String.format(message, args)); //To change body of generated methods, choose Tools | Templates.
    }//End of print() method

    
    
    //Count the number of occurrences for each term
    private static void printMap(Map<String, Integer>map, File somefile) throws FileNotFoundException, IOException{
        //Create the string representation
        
        Integer values;
        String keys;
        PrintWriter writer = new PrintWriter(somefile, "UTF-8");
        
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println("" + entry.getKey() + "{Frequency : " + entry.getValue() + "}");
            keys = entry.getKey();
            values = entry.getValue();
            writer.println(keys+" "+ values);  
        }
        writer.close();
        
      
    }//Static void count_frequecies()
    
    
    
}//End of Crawlers Class
