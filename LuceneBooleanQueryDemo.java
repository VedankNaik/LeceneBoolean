/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Default;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
//import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TopDocs;

public class LuceneBooleanQueryDemo {

   Searcher searcher;

   public static void main(String[] args) {
      LuceneBooleanQueryDemo tester;
      try {
         tester = new LuceneBooleanQueryDemo();
         tester.searchUsingBooleanQuery("have","interactively");
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }

   private void searchUsingBooleanQuery(String searchQuery1,
      String searchQuery2)throws IOException, ParseException {
       
      searcher = new Searcher();
      
      long startTime = System.currentTimeMillis();

      
      //create a term to search file name
      Term term1 = new Term("text", searchQuery1);
      // Term term1 = new Term("test");
      //create the term query object
      Query query1 = new TermQuery(term1);

      Term term2 = new Term("text", searchQuery2);
      //Term term2 = new Term("test2");
      //create the term query object
      Query query2 = new PrefixQuery(term2);
              
      
     BooleanQuery query = new BooleanQuery.Builder()
        .add(query1, BooleanClause.Occur.MUST) 
        //.add(optionalQuery, BooleanClause.Occur.SHOULD) 
        .add(query2, BooleanClause.Occur.MUST_NOT) 
        .build();
               
      
      
      
     // query.add(query1, BooleanClause.Occur.MUST);
    //  query.add(query1, BooleanClause.Occur.MUST_NOT);  
      
      //do the search
      TopDocs hits = searcher.search(query);
      long endTime = System.currentTimeMillis();

      System.out.println(hits.totalHits +
            " documents found. Time :" + (endTime - startTime) + "ms");
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
         System.out.println("File: "+ doc.get("docno"));
      }
      searcher.close();
   }
}
