/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikigrapher;
import java.io.IOException;
import java.util.Scanner;


import java.io.IOException;


/**
 *
 * @author Clement Cole
 */
public class Wikigrapher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        //CmdExecuter Executioner = new CmdExecuter();    //Create an instance of the Executioner
            //Need a Switch case to direct the flow of these command line executions
            //Executioner.executeCmd("wget http://download.wikimedia.org/enwiki/latest/enwiki-latest-pages-articles.xml.bz2"); //1st, Download the Wikipedia File
            //Executioner.executeCmd("bunzip2 enwiki-latest-pages-articles.xml.bz2");                                          //2nd, unzip the file
            //Executioner.executeCmd("mkdir out");                                                                             //3rd, make a directory called out
            //Executioner.executeCmd("./xmldump2files.py enwiki-latest-pages-articles.xml out");                               //4th, create files for dump and place them into files 
            //Executioner.executeCmd("cd out");                                                                                //5th, Go into the out file
            //Executioner.executeCmd("ls -LR > all_articles_list.txt");                                                        //6th, list the name of all articles and place them 
                                                                                                                             //     into a file called all_articles_list.txt
            //After the above, we are ready for business
        
        Scanner user_input = new Scanner(System.in);    
        Crawlers test = new Crawlers(); //Initiate our first crawler
        String term;
        System.out.println("Please enter term: ");
        term = user_input.nextLine();
        term = term.replaceAll(" ", "_");
        test.getURL(term); //For testing purposes Passed with flying colors
    }
    
}
