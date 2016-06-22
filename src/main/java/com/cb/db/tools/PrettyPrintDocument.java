package com.cb.db.tools;

import com.couchbase.client.java.document.json.JsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author  Teja Wuppumandla 21 Feb 2016
 * 
 */

public class PrettyPrintDocument {

	public static void prettyPrintDocument(JsonObject couchbaseDocument){
		
		try{
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String prettyDocument = gson.toJson(couchbaseDocument);
			//System.out.println(couchbaseDocument);
			System.out.println("----------------------------------");
			System.out.println(prettyDocument);
			System.out.println("----------------------------------");
		}
		catch(Exception e){
			System.out.println("Problem during Pretty Printing the Document");
			System.out.println(e);
		}
	}
}
