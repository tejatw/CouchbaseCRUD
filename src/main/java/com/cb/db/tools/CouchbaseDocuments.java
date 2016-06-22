package com.cb.db.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import org.codehaus.jackson.map.ObjectMapper;
import com.couchbase.client.java.document.JsonDocument;


/**
 * @author Teja Wuppumandla 21 Feb 2016
 * 
 */

public class CouchbaseDocuments {
	
	static InputStream input = null;

	public static void getDocumentById(String documentId){

		JsonObject documentIdResult;

		try{

			documentIdResult = CouchbaseConnection.bucketCB.get(documentId).content();
			if(documentIdResult.isEmpty()){
				System.out.println("Document with Id " + documentId + " is not found in the Database");
			}
			else{
				PrettyPrintDocument.prettyPrintDocument(documentIdResult);
			}
		}
		catch(Exception e){
			System.out.println("Document with Id " + documentId + " is not found in the Database");
		}
	}

	
	public static void createDocument(){

		JsonObject documentIdResult;

		try{

			JsonObject content = JsonObject.empty().put("uniqueKey", "sampleDoc");
			content.put("Database", "Couchbase");
			content.put("Version", "3.1");
			
			
			JsonDocument statusDoc = JsonDocument.create("sampleDoc", content);
			documentIdResult = CouchbaseConnection.bucketCB.insert(statusDoc).content();
			
			if(documentIdResult.isEmpty()){
				System.out.println("Uh Oh... Some problem creating the document");
			}
			else{
				System.out.println("New document created with the id: "+ "sampleDoc");
				PrettyPrintDocument.prettyPrintDocument(documentIdResult);
			}
		}
		catch(Exception e){
			System.out.println("Uh Oh... Some problem creating the document");
		}
	}
	
	private static String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}
	
}
