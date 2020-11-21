package examples;

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
	
   String indexDir = "C://LuceneData/example_index_lucene";
   String dataDir = "C://LuceneData/example_corpus.gz";
   Searcher searcher;

   public static void main(String[] args) {
      LuceneBooleanQueryDemo tester;
      try {
         tester = new LuceneBooleanQueryDemo();
         tester.searchUsingBooleanQuery("record2","record1");
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }

   private void searchUsingBooleanQuery(String searchQuery1,
      String searchQuery2)throws IOException, ParseException {
      searcher = new Searcher(indexDir);
      long startTime = System.currentTimeMillis();
      String FILE_PATH = "C://LuceneData/bool";
      String FILE_NAME = "C://LuceneData//bool/test.txt";
      
      //create a term to search file name
      Term term1 = new Term(dataDir, searchQuery1);
      // Term term1 = new Term("test");
      //create the term query object
      Query query1 = new TermQuery(term1);

      Term term2 = new Term(dataDir, searchQuery2);
      //Term term2 = new Term("test2");
      //create the term query object
      Query query2 = new PrefixQuery(term2);
              
      
      BooleanQuery query = new BooleanQuery();
      
      
      
      query.add(query1, BooleanClause.Occur.MUST);
      query.add(query1, BooleanClause.Occur.MUST_NOT);  
      
      //do the search
      TopDocs hits = searcher.search(query);
      long endTime = System.currentTimeMillis();

      System.out.println(hits.totalHits +
            " documents found. Time :" + (endTime - startTime) + "ms");
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
         System.out.println("File: "+ doc.get(FILE_PATH));
      }
      searcher.close();
   }
}