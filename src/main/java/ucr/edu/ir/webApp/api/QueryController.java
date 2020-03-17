package ucr.edu.ir.webApp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ucr.edu.ir.webApp.model.Person;
import ucr.edu.ir.webApp.service.PersonService;

import java.util.HashMap;
import java.util.List;

@RequestMapping("api/query")
@RestController
public class QueryController {
    @GetMapping
    public HashMap getAllPeople(@RequestParam(value = "index", defaultValue = "m") String indexType,
                                @RequestParam(value = "query", defaultValue = "") String query
    ) {
        // Use indexType to determine whether to search Lucene or MapReduce index and call that function
        if (indexType.toLowerCase()=="m")
        {
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
        hmDummy.put("result","could go here");
        return hmDummy;
    }
}