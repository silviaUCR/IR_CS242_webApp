package edu.ucr.ir.actions;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/** Simple command-line based search demo. */
public class LuceneIndexReader  {

    static IndexSearcher searcher = null;

    public static void doSearch(String indexPath, String searchTerm) throws Exception {
        if (!openSearcher(indexPath))
        {
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

    static void searchIndex(String searchTerm) throws Exception
    {

        QueryParser qp = new QueryParser("body", new StandardAnalyzer());
        Query idQuery = qp.parse(searchTerm);
        TopDocs hits = searcher.search(idQuery, 10);
        System.out.println("Searching for Term: "+ searchTerm);
        System.out.println(hits.scoreDocs[0]);
        System.out.println(hits.scoreDocs[1]);
        System.out.println(hits.scoreDocs[2]);

        System.out.println("Total hits "+ hits.totalHits);
    }
}
