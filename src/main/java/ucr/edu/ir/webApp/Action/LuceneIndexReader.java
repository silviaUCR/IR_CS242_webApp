package ucr.edu.ir.webApp.Action;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
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

/** Simple command-line based search demo. */
public class LuceneIndexReader {

    static IndexSearcher searcher = null;

    public static void doSearch(String indexPath, String searchTerm) throws IOException, ParseException {
        if (!openSearcher(indexPath)) {
            System.out.println("Could not open the index for searching.");
            return;
        }

        // Put call to search logic here
        searchIndex(searchTerm);
    }

    static boolean openSearcher(String indexPath) throws IOException {
        Directory dir = FSDirectory.open(Paths.get(indexPath));
        IndexReader reader = DirectoryReader.open(dir);
        searcher = new IndexSearcher(reader);
        return true;
    }

    static void searchIndex(String searchTerm) throws IOException, ParseException {

        QueryParser qp = new QueryParser("body", new StandardAnalyzer());
        Query idQuery = qp.parse(searchTerm);
        TopDocs topDocs = searcher.search(idQuery, 100);


        System.out.println("Searching for Term: " + searchTerm);
        System.out.println(topDocs.scoreDocs[0]);
        System.out.println(topDocs.scoreDocs[1]);
        System.out.println(topDocs.scoreDocs[2]);
        System.out.println(topDocs.scoreDocs[3]);
        System.out.println("Total hits " + topDocs.totalHits);



        // obtain the ScoreDoc (= documentID, relevanceScore) array from topDocs
        ScoreDoc[] hits = topDocs.scoreDocs;

        // retrieve each matching document from the ScoreDoc array
        for (int i = 0; i < hits.length; i++) {
            Document doc = searcher.doc(hits[i].doc);
            String urlName = doc.get("url");
            String bodyText = doc.get("body");
            System.out.print(i+" ");
            System.out.print(urlName);
            System.out.print(bodyText);
            System.out.println("");

        }
    }
}
