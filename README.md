# Wikigrapher is a software written in java to create graphs of wikipedia articles into specific categories. ie: (articles_pages), (books_references),
(citations_graphs), (files_graph), (search_graph) and (talk_graph). The wikigrapher is basically a crawler that crawls through wikipedia
and based on the url will copy the name of the url and place it into a directory as a file named after the name of the article. 
For instance lets say we wanted to crawl through "http://en.wikipedia.org/wiki/machine_learning.",
1. The program will ask the user to input the name of term they will search for.
2. So user types in machine learning.
3. Using pattern matching, the program will replace the space between machine and learning with '_'. So in thi case
    "machine learning" becomes "machine_learning". 
4. Further, the program will concatenate machine_learning with http://en.wikipedia.org/wiki/.
   So, the term becomes http://en.wikipedia.org/wiki/machine_learning. 
5. This gets parsed into the Crawlers.java class which will then search through the DOM object generated by jsoup and look for links.
6. Once, that is finished. Several files will be created. The heirachy of the files is as follows:
  Machine_learning_graph
  --->machine_learning_articles_graph.txt
  --->machine_learning_books_references_graph.txt
  --->machine_learning_categories_graph.txt
  --->machine_learning_citation_graph.txt
  --->machine_learning_files_graph.txt
  --->machine_learning_search_graph.txt
  --->machine_learning_talk_graph.txt
7. *The above text files will be stored in the above files. Thus "machine_learning_articles_graph.txt" will contain links to 
   http://en.wikipedia.org/wiki/Pattern_recognition{Frequency : 3} and so on and so forth.
   *Same applies to "machine_learning_books_references_graph.txt" will contain links to http://en.wikipedia.org/wiki/Special:BookSources/1558601198 {Frequency : 1} 
   will create the link to the book reference page from wikipedia and so on...
   *Same goes for "machine_learning_categories_graph.txt" which creates a list of categories for which the machine learning article falls 
   under.
   *machine_learning_files_graph.txt is just a list of urls mapping to files such as pictures, sounds and videos associated with the 
   page.
   *machine_learning_talk_graph.txt will have all list of links to conferences/talks or wikitalks associated with the machine_learning
   article.
   
                            **************************************************************
                            ***  Disection of The Codes and Documentation             ****
*************               **************************************************************
**CRAWLERS **
*************


    # STEP 1:
  
Within the method getURL(String line)
- Declare article_title of type String;
- Replace any space inside article_title with "_": hence article_title = article_title.replaceAll(" ", "_");
- Declare url of type String;
- Concatenate article_title with "http://en.wikipedia.org/wiki/": "http://en.wikipedia.org/wiki" + article_title
- Create a List of type String called article_list: List<String> article_list = new ArrayList<String>();
- Create a List of type String called book_list: List<String> book_list = new ArrayList<String>();
- Create a List of type String called file_list: List<String> file_list = new ArrayList<String>();
- Create a List of type String called talk_list: List<String> talk_list = new ArrayList<String>();
- Create a List of type String called p_search_list: List<String> p_search_list = new ArrayList<String>();
- Create a List of type String called citation_list: List<String> citation_list = new ArrayList<String>();
- Create a List of type String called internal_page_list: List<String> internal_page_list = new ArrayList<String>(); N:B: this is only optional but was never used

    STEP 2:
   --------
-Create the DOM document with Jsoup.connect(url).get() called Document doc;
-Create the Element links (a[href]) with doc.select or Jsoup.connect(url);
-Create the media elements with doc.select("[src]");
-Create the imports Elements with doc.select("link[href]");

    STEP 3:
  ---------
-Create string pattens native to wikipedia.
- ".*http://en.wikipedia.org/wiki/.*"
- ".*Special:BookSources/.*";
- ".*Help:Category.*"
- ".*Category.*"
- ".*File:.*"
- ".*Talk:.*"
- ".*(journal).*"
- ".*#mw-head.*"
- ".*#cite.*"
- ".*#.*"
-The above string pattens will enable us to differentiate between url links.

    





