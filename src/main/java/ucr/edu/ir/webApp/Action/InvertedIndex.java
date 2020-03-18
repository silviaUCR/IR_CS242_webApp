package ucr.edu.ir.webApp.Action;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;


public class InvertedIndex  {
    public static Map<String, Double> Search(String query) throws IOException {
        System.out.println("Starting Hadoop Inverted Index Search....");
        String filePath = "C:\\Crawler Extract\\part-r-00000.txt";
        HashMap<String, String> dictionary = new HashMap<String, String>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split("\t", 2);
            if (parts.length >= 2)
            {
                String key = parts[0];
                String value = parts[1];
                dictionary.put(key, value);
            } else {
                System.out.println("ignoring line: " + line);
            }
        }

        Map<String, Integer> dfreq = new HashMap<String, Integer>();
        Map<String, Integer> tfreq = new HashMap<String, Integer>();


        for (String key : dictionary.keySet())
        {

            String HashOut[] = dictionary.get(key).replace("{","").replace("}","").replace("\"","").split(",");
            for (String hashout : HashOut) {
                String hashvalue[] = hashout.trim().split(" =");
                if (hashvalue.length >= 2) {
                    String url = hashvalue[0];
                    String tf = hashvalue[1].replace("\"","");

                    Integer tcount = tfreq.get(key);
                    if (tcount == null) {
                        tfreq.put(key, Integer.parseInt(tf));
                    }
                    else {
                        tfreq.put(key, tcount +  Integer.parseInt(tf));
                    }


                    Integer dcount = dfreq.get(url);

                    if (dcount == null) {
                        dfreq.put(url, Integer.parseInt(tf));
                    }
                    else {
                        dfreq.put(url, dcount +  Integer.parseInt(tf));
                    }


                } else {
                    System.out.println("ignoring line: " + hashout);
                }
            }
        }

        int cvol = 0;
        int dct = 0;
        for (int f : dfreq.values()) {
            cvol += f;
            dct += 1;
        }


        //String query = "Kobe is dead";
        String Term[] = query.toLowerCase().split(" ");

        Map<String, Double> langmodel = new HashMap<String, Double>();

        //double lang_model_rank = 1; //INITIALIZE... CANNOT BE ZERO
        for (String term : Term) {
            //System.out.println(st.nextToken());
            //System.out.println(term + ":" + dictionary.get(term));
            String Posting[] = dictionary.get(term).replace("{","").replace("}","").replace("\"","").split(",");
            for (String posting : Posting) {
                String postingvalue[] = posting.trim().split(" =");
                if (postingvalue.length >= 2) {
                    String url = postingvalue[0];
                    int tf = Integer.parseInt(postingvalue[1].replace("\"", ""));
                    int dvol = dfreq.get(url);
                    double cqi = tfreq.get(term);
                    double lambda = 0.5; //MAYBE IMPLEMENT SOMETHING TO CHANGE THE LAMBDA GIVEN A MORE COMPLEX QUERY. LOWER LAMBDA FOR SIMPLE.
                    double lang_model_alg = ((1-lambda) * tf/dvol) + (lambda * tf/cvol); //Jelinek Mercer Smoothing
                    //double lang_model_alg = Math.log((tf + ((cvol/dct)*cqi/cvol))/(dvol+(cvol/dct))); //Dirichlet Smoothing

                    double lm = dfreq.get(url);

                    if (lm == 0) {
                        langmodel.put(url, lang_model_alg);
                    }
                    else {
                        langmodel.put(url, lm * lang_model_alg); //Jelinek Mercer Smoothing
                        //langmodel.put(url, lm + lang_model_alg); //Dirichlet Smoothing
                    }


                } else {
                    System.out.println("ignoring line: " + postingvalue);
                }
            }

        }


        //System.out.println(list.toString());
        reader.close();

        return langmodel;
    }




}
