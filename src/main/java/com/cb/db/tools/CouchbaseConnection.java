package com.cb.db.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.view.ViewQuery;
import com.couchbase.client.java.view.ViewResult;
import com.couchbase.client.java.view.ViewRow;

/**
 * @author Teja Wuppumandla 21 Feb 2016
 * 
 */

public class CouchbaseConnection {
	
	static Properties prop = new Properties();
	static InputStream input = null;
	static Bucket bucketCB;

	static String cbNodeValue = null;
	static String cbBucketName = null;
	static String cbBucketPassword = null;

	static List<String> cbNodeList = new ArrayList<>();
	
	static Cluster cluster;

	public static void createConnection(String propertiesFile){

		

		CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
				.connectTimeout(10000) // 10000ms = 10s, default is 5s
				.build();

		/* May be to connect to Couchbase 3.0 and above???
		CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
				.connectTimeout(10000) // 10000ms = 10s, default is 5s
				.queryEnabled(true).build();
		 */
		

		try {

			input = new FileInputStream(propertiesFile);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			cbNodeValue = prop.getProperty("cb.nodeList");
			cbBucketName = prop.getProperty("cb.bucketName");
			cbBucketPassword = prop.getProperty("cb.bucketPassword");

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		splitString(cbNodeValue, cbNodeList);

		cluster = CouchbaseCluster.create(env,cbNodeList);

		try{

			if (cbBucketPassword.isEmpty()) {
				bucketCB = cluster.openBucket(cbBucketName);
			}
			else{
				bucketCB = cluster.openBucket(cbBucketName, cbBucketPassword);
			}

			System.out.println("Bucket " + bucketCB.name() + " is now connected on nodes " + cbNodeValue);

			
		}
		catch(Exception e){
			cluster.disconnect();
			System.out.println(e);
		}

	}
	
	public static void closeConnection(){
		
		try{
			
			bucketCB.close();
			cluster.disconnect();
			
			System.out.println("Bucket " + bucketCB.name() + " is now closed");
		}
		catch(Exception e){
			System.out.println("Error during closing the Couchbase Connection");
			System.out.println(e);
		}
		
	}

	public static void splitString(String originalString, List<String> stringValues){

		for (String splitString: originalString.split(",")){
			stringValues.add(splitString);
			System.out.println(splitString);
		}
	}

}
