package ucr.edu.ir.webApp.Action;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/** Simple command-line based search demo. */
public class LuceneIndexReader {
    public static Map<Double, String> doSearch(String searchTerm) throws Exception {
        HashMap<Double, String> lucenehash = new HashMap<Double, String>();
        HashMap<String, Double> luceneduperemove = new HashMap<String, Double>();

        System.out.println("Starting Lucene Index Search....");
        String indexPath = "luceneindex";
        IndexSearcher searcher = null;
        ScoreDoc[] hits = null;

        Directory dir = FSDirectory.open(Paths.get(indexPath));
        IndexReader reader = DirectoryReader.open(dir);

        searcher = new IndexSearcher(reader);
        QueryParser qp = new QueryParser("body", new StandardAnalyzer());
        Query idQuery = qp.parse(searchTerm);
        TopDocs topDocs = searcher.search(idQuery, 1000);

        // obtain the ScoreDoc (= documentID, relevanceScore) array from topDocs
        hits = topDocs.scoreDocs;

        for (int i = 0; i < hits.length; i++) {
            Document doc = searcher.doc(topDocs.scoreDocs[i].doc); //Create doc object
            double docid = topDocs.scoreDocs[i].doc; //Store Document ID
            double docScore = topDocs.scoreDocs[i].score; //Store Score
            String bodyText = doc.get("body"); //Store body to use as Key to remove duplicates
            String urlName = doc.get("url"); //Store url

//            Prints Score, Url and Body for debug
//            System.out.print(docScore);
//            System.out.print(urlName); //Prints url of document
//            System.out.print(bodyText); //Prints bodyText of document
//            System.out.println("");
            lucenehash.put(docScore, urlName);
        }
//        System.out.print(lucenehash);
        return lucenehash;
    }
}