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
    public HashMap getAllPeople(@RequestParam(value = "index", defaultValue = "m") String indexType,
                                @RequestParam(value = "query", defaultValue = "") String query
    ) throws IOException {
        System.out.println("Received input. Type: " + indexType +" Query: "+ query);
        // Use indexType to determine whether to search Lucene or MapReduce index and call that function
        List<String> urllist = new ArrayList<String>();;

        if (indexType.toLowerCase()=="m")
        {
            urllist = InvertedIndex.InvertedIndex(query);

            // Call MapReduce index search
            // return SearchMapReduce(queryTerm);
        }
        else {
            // Call Lucene index reader
            // return SearchLucene(queryTerm);
        }
        // Just to generate a response
        HashMap hmDummy = new HashMap<String, String>();
        hmDummy.put("index", indexType);
        hmDummy.put("query", query);
        hmDummy.put("result",urllist);
        return hmDummy;
    }
}

