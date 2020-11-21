package examples;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.BooleanQuery;

public class Searcher {

    IndexSearcher indexSearcher;
    QueryParser queryParser;
    Query query;
    String pathIndex = "C://LuceneData/example_index_lucene";
    Analyzer analyzer = new Analyzer() {
        @Override
        protected Analyzer.TokenStreamComponents createComponents(String fieldName) {
            // Step 1: tokenization (Lucene's StandardTokenizer is suitable for most text retrieval occasions)
            Analyzer.TokenStreamComponents ts = new Analyzer.TokenStreamComponents(new StandardTokenizer());
            // Step 2: transforming all tokens into lowercased ones (recommended for the majority of the problems)
            ts = new Analyzer.TokenStreamComponents(ts.getSource(), new LowerCaseFilter(ts.getTokenStream()));
            // Step 3: whether to remove stop words
            // Uncomment the following line to remove stop words
            // ts = new TokenStreamComponents( ts.getTokenizer(), new StopFilter( ts.getTokenStream(), StandardAnalyzer.ENGLISH_STOP_WORDS_SET ) );
            // Step 4: whether to apply stemming
            // Uncomment the following line to apply Krovetz or Porter stemmer
            // ts = new TokenStreamComponents( ts.getTokenizer(), new KStemFilter( ts.getTokenStream() ) );
            // ts = new TokenStreamComponents( ts.getTokenizer(), new PorterStemFilter( ts.getTokenStream() ) );
            return ts;
        }
    };

    public Searcher(String indexDirectoryPath) throws IOException {
        //Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
        Directory indexDirectory = FSDirectory.open(new File(pathIndex).toPath());
        //indexSearcher = new IndexSearcher(indexDirectory);
        IndexReader index = DirectoryReader.open(indexDirectory);
        indexSearcher = new IndexSearcher(index);
        //queryParser = new QueryParser(Version.LUCENE_36, LuceneConstants.CONTENTS,
        // new StandardAnalyzer(Version.LUCENE_36));
        String field = "text"; 
        queryParser = new QueryParser(field, analyzer);
    }

    public TopDocs search(Query query)
            throws IOException, ParseException {
       // query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, 10);
    }

//    public TopDocs search(Query query) throws IOException, ParseException {
//        return indexSearcher.search(query, 10);
//    }

    public Document getDocument(ScoreDoc scoreDoc)
            throws CorruptIndexException, IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

//    public void close() throws IOException {
//        indexSearcher.close();
//    }
}
