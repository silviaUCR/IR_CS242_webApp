package ucr.edu.ir.webApp.Action;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;





public class InvertedIndex  {


    //public static void main( String[] args ) throws IOException {
    public static void InvertedIndex (String query) throws IOException {
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


        String Term[] = query.split(" ");
        for (String term : Term) {
            //System.out.println(st.nextToken());
            System.out.println(term + ":" + dictionary.get(term));
            
        }

/*
        for (String key : dictionary.keySet())
        {
            System.out.println(key + ":" + dictionary.get(key));
        }*/
        reader.close();
    }




}
