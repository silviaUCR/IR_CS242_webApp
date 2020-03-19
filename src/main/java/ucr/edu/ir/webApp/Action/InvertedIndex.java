package ucr.edu.ir.webApp.Action;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class InvertedIndex  {
    public static Map<String, Double> Search(String query) throws IOException {
        System.out.println("Starting Hadoop Inverted Index Search....");
        String filePath = "C:\\Crawler Extract\\part-r-00000";
        //String filePath = "hadoopindex/part-r-00000";
        HashMap<String, String> dictionary = new HashMap<String, String>();
        HashMap<String,String> data = new HashMap<String,String>();
        Map<String, Integer> tdocfreqmap = new HashMap<String, Integer>();

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        //String query = "Kobe is dead";
        String Term[] = query.toLowerCase().split(" ");

        while ((line = reader.readLine()) != null)
        {

            String[] parts = line.split("\t", 2);
            if (parts.length >= 2)
            {

                String key = parts[0];
                String value = parts[1];

                for (String element : Term) {
                    if (key.equals(element)) {
                        dictionary.put(key, value);
                    }
                }

            } else {
                //System.out.println("ignoring line: " + line);
            }
        }



        Map<String, Integer> dfreq = new HashMap<String, Integer>();
        Map<String, Integer> tfreq = new HashMap<String, Integer>();

/*
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
        }*/




        for (String key : dictionary.keySet())
        {

            String str = dictionary.get(key);

            Map<String, Integer> tdocfreq = new HashMap<String, Integer>();

            //map2 = new Gson().fromJson(str, HashMap.class);


            Pattern p = Pattern.compile("[\\{\\}\\=\\, ]++");
            String[] split = p.split(str);

            try {
                for (int i = 1; i + 2 <= split.length; i += 2) {
                    data.put(split[i], split[i + 1]);
                    tdocfreq.put(split[i], Integer.parseInt(split[i + 1]));
                }
            }
            catch(Exception e) {
                //  Block of code to handle errors
            }

            int ct = 0;
            for (int f : tdocfreq.values()) {
                ct += f;
            }

            tdocfreqmap.put(key,ct);








            for (String page : data.keySet()) {


                    String url = page;
                    String tf = data.get(page);



                    Integer tcount = tfreq.get(key);
                try {
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
                }
                catch(Exception e) {
                    //  Block of code to handle errors
                }

            }
        }

        int cvol = 0;
        int dct = 0;
        for (int f : dfreq.values()) {
            cvol += f;
            dct += 1;
        }

        
        double lambda = Term.length*0.1;
        if (lambda > 1.0){
            lambda = 1.0;
        }

        System.out.println("cvol line: " + cvol);
        System.out.println("dct line: " + dct);
        System.out.println("data line: " + data);



        Map<String, Double> langmodel = new HashMap<String, Double>();

        //double lang_model_rank = 1; //INITIALIZE... CANNOT BE ZERO
        for (String term : Term) {
            //System.out.println(st.nextToken());
            //System.out.println(term + ":" + dictionary.get(term));

            for (String key : data.keySet()) {
                    String url = key;

                    try {
                        int tf = Integer.parseInt(data.get(key));
                        int tdf = tdocfreqmap.get(term);
                        int dvol = dfreq.get(url);
                        double cqi = tfreq.get(term);
                        double lang_model_alg = ((1 - lambda) * tf / dvol) + (lambda * tdf / cvol); //Jelinek Mercer Smoothing
                        //double lang_model_alg = Math.log((tf + ((cvol/dct)*cqi/cvol))/(dvol+(cvol/dct))); //Dirichlet Smoothing

                        double lm = dfreq.get(url);

                        if (lm == 0) {
                            langmodel.put(url, lang_model_alg);
                        } else {
                            langmodel.put(url, lm * lang_model_alg); //Jelinek Mercer Smoothing
                            //langmodel.put(url, lm + lang_model_alg); //Dirichlet Smoothing
                        }
                    } catch(Exception e) {
                        //  Block of code to handle errors
                    }

            }
        }

        //System.out.println(list.toString());
        reader.close();

        return langmodel;
    }
}
