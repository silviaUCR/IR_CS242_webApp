package ucr.edu.ir.webApp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ucr.edu.ir.webApp.Action.InvertedIndex;
import ucr.edu.ir.webApp.model.Person;
import ucr.edu.ir.webApp.service.PersonService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

@RequestMapping("api/query")
@RestController
public class QueryController {


    @GetMapping
    public List<HashMap> getQueryResults(@RequestParam(value = "index", defaultValue = "m") String indexType,
                                @RequestParam(value = "query", defaultValue = "") String query
    ) throws IOException {
        System.out.println("Received input. Type: '" + indexType + "' Query: '" + query + "'");
        // Use indexType to determine whether to search Lucene or MapReduce index and call that function
        List<String> urllist = new ArrayList<String>();;

        if (indexType.equals("m"))
        {
            urllist = InvertedIndex.Search(query);
            System.out.println("Inverted Result: " + urllist);
            // Call MapReduce index search
            // return SearchMapReduce(queryTerm);
        }
        else {
            // Call Lucene index reader
            // return SearchLucene(queryTerm);
        }
        // Just to generate a response
        List<HashMap> results=new ArrayList<HashMap>();
        HashMap hmResult1 = new HashMap<String, String>();
        hmResult1.put("result","acme");
        hmResult1.put("score",85);
        results.add(hmResult1);
        HashMap hmResult2 = new HashMap<String, String>();
        hmResult2.put("result","blah");
        hmResult2.put("score",89);
        results.add(hmResult2);
        System.out.println("Data: " + results);
        return results;
    }
}

