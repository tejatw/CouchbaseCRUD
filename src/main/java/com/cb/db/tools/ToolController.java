package com.cb.db.tools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Teja Wuppumandla 21 Feb 2016
 * 
 */

public class ToolController {

	private static int userInput;
	private static String userData;

	public static void main(String[] args) {

		if (args.length == 0){

			System.out.println("Properties file is not passed as an argument");
			System.out.println("Usage: java -jar <Application Jar> /home/user/cbconfig.properties");
			System.exit(1);
		}

		createCouchbaseConnection(args[0]);

		do{
			printUserOptions();
			readUserOption();
			
			switch (userInput) {
				case 1:	readUserData("Document Id");
						if(userData.length() > 0){
							CouchbaseDocuments.getDocumentById(userData);
						}
						else{
							System.out.println("----------------------------------");
							System.out.println("Please check the input you have entered");
							printUserOptions();
							readUserOption();
						}
						break;
				case 2:	CouchbaseDocuments.createDocument();
						break;
				default:	System.out.println("----------------------------------");
							System.out.println("Please check the input you have entered");
							printUserOptions();
							readUserOption();
							break;
			}

		}while(userInput != 9);



		disconnectCouchbaseConnection();

	}

	private static void readUserData(String inputDataType){

		System.out.println("----------------------------------");
		System.out.print("Please enter the " + inputDataType + ": ");

		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			userData=br.readLine();			

		}catch(IOException io){
			io.printStackTrace();
		}	
	}

	private static void readUserOption(){

		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			userInput=Integer.parseInt(br.readLine());

		}catch(IOException io){
			userInput=999;
			io.printStackTrace();
		}
		catch(NumberFormatException nfe){
			userInput=999;
			System.out.println("You have entered a string value");
		}
	}

	private static void printUserOptions(){

		System.out.println("----------------------------------");
		System.out.println("Welcome to the Couchbase DB Query Tool");
		System.out.println("----------------------------------");
		System.out.println("");
		System.out.println("1. Get Document by Couchbase Id");
		System.out.println("2. Create a Sample Document");
		System.out.println("9. Exit the Tool");
		System.out.println("----------------------------------");
		System.out.print("Please Enter your option: ");
	}

	private static void createCouchbaseConnection(String propertyFile){
		try{

			CouchbaseConnection.createConnection(propertyFile);
		}
		catch(Exception e){

		}
	}

	private static void disconnectCouchbaseConnection(){
		try{
			CouchbaseConnection.closeConnection();
		}
		catch(Exception e){
			System.out.println("Problem disconnecting from Couchbase");
			System.out.println(e);
		}
	}

}
