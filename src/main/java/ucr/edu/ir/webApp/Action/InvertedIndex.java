package ucr.edu.ir.webApp.Action;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;





public class InvertedIndex  {


    //public static void main( String[] args ) throws IOException {
    public static List<String> InvertedIndex(String query) throws IOException {

    //public static void InvertedIndex (String query) throws IOException {
        //String filePath = "C:\\Crawler Extract\\part-r-00000.txt"; //FOR WINDOWS
        String filePath = "/home/ucruser/inverted_index/output/out1/part-r-00000";
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
        Map<String, Double> langmodel = new HashMap<String, Double>();

        for (String key : dictionary.keySet())
        {

            String HashOut[] = dictionary.get(key).replace("{","").replace("}","").replace("\"","").split(",");
            for (String hashout : HashOut) {
                String hashvalue[] = hashout.trim().split(" =");
                if (hashvalue.length >= 2) {
                    String url = hashvalue[0];
                    String tf = hashvalue[1].replace("\"","");

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
        for (int f : dfreq.values()) {
            cvol += f;
        }
        System.out.println(cvol);

        //String query = "Kobe is dead";
        String Term[] = query.toLowerCase().split(" ");

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
                    double lambda = 0.5;
                    double lang_model_alg = (lambda * tf/dvol) + (lambda * tf/cvol);

                    double lm = dfreq.get(url);

                    if (lm == 0) {
                        langmodel.put(url, lang_model_alg);
                    }
                    else {
                        langmodel.put(url, lm * lang_model_alg);
                    }


                } else {
                    System.out.println("ignoring line: " + postingvalue);
                }
            }

        }


        List<String> urllist = new ArrayList<String>();

        for (String key : langmodel.keySet()) {
            urllist.add(key);
        }


        //System.out.println(list.toString());
        reader.close();

        return urllist;
    }




}
