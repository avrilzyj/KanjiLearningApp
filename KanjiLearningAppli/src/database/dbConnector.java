package database;
import java.sql.*;
import java.util.ArrayList;

import bd.bean.words;

public class dbConnector {
	
	Statement stmt = null;
	
	 public ArrayList<String> getKanjiList(){
		 ArrayList<String> KanjiList = new ArrayList<String>();
		 String query = "select code from kanji where RBcode!='';";
		 try {
		      ResultSet rs = stmt.executeQuery(query); 
		      while (rs.next()) { 
		    	  KanjiList.add(rs.getString("code"));

		      }
		    } 
		    catch (Exception e) { 
		      System.out.print("get data error!"); 
		      e.printStackTrace(); 
		    } 
		 return KanjiList;
	 }
	 
	 public ArrayList<String> getRadicals(String code){
		 ArrayList<String> ridicals = new ArrayList<String>();
		 
		 String query = "select RAcode,RBcode from kanji where code="+code+";";
		 try {
		      ResultSet rs = stmt.executeQuery(query); 
		                                                              
		      if (rs.next()) { 
		    	  ridicals.add(rs.getString("RAcode"));
		    	  ridicals.add(rs.getString("RBcode"));
		      }		 
		    } 
		    catch (Exception e) { 
		      System.out.print("get data error!"); 
		      e.printStackTrace(); 
		    } 


		 return ridicals;
	 }
	 
	 public Boolean checkKanji(String kanji){
		 Boolean KanjiExist = false;
		 String query = "select * from kanji where kanji='"+kanji+"';";
		 try {
		      ResultSet rs = stmt.executeQuery(query); 
		      if (rs.next()) { 
		    	  KanjiExist = true;
		      }		 
		    } 
		    catch (Exception e) { 
		      System.out.print("get data error!"); 
		      e.printStackTrace(); 
		    } 
		 return KanjiExist;
	 }
	 
	 public ArrayList<String> getRadicalsByKanji(String kanji){
		 ArrayList<String> ridicals = new ArrayList<String>();
		 String query = "select RAcode,RBcode from kanji where kanji='"+kanji+"';";


		 try {
		      ResultSet rs = stmt.executeQuery(query); 
		                                                              
		      if (rs.next()) { 
		    	  if(!rs.getString("RAcode").isEmpty()){
			    	  ridicals.add(rs.getString("RAcode"));
		    	  }
		    	  if(!rs.getString("RBcode").isEmpty()){
			    	  ridicals.add(rs.getString("RBcode"));
		    	  }
		      }		 
		    } 
		    catch (Exception e) { 
		      System.out.print("get data error!"); 
		      e.printStackTrace(); 
		    } 


		 return ridicals;
	 }
	
	 public boolean ChangeColor(String cardNumA, String cardNumB){
		 String query = "select kanji from kanji where (RAcode='"+cardNumA+"' and RBcode='"+cardNumB+"') or (RAcode='"+cardNumB+"' and RBcode='"+cardNumA+"');";
		 boolean r = false;
		 if (cardNumA.equals(cardNumB))
		 r = true;
		 try {
		      ResultSet rs = stmt.executeQuery(query); 
		                                                              
		      if (rs.next()) { 
		        r = true;
		      }
		    } 
		    catch (Exception e) { 
		      System.out.print("get data error!"); 
		      e.printStackTrace(); 
		    } 
		 return r;
	 }
	 
	 public String GetKanjiCode(String cardNumA,String cardNumB){
		 String query = "select code from kanji where (RAcode='"+cardNumA+"' and RBcode='"+cardNumB+"') or (RAcode='"+cardNumB+"' and RBcode='"+cardNumA+"');";
		 String code = "";
		 try {
		      ResultSet rs = stmt.executeQuery(query); 
		                                                              
		      if (rs.next()) { 
		    	  code = rs.getString("code");
		      }
		    } 
		    catch (Exception e) { 
		      System.out.print("get data error!"); 
		      e.printStackTrace(); 
		    } 
		 return code;
	 }

	 
	 public words GetWordInfoCodeAB(String cardNumA,String cardNumB){
		 String query = "select kanji,PronunciationON,PronunciationKUN,PronunciationKitakanaON,PronunciationKitakanaKUN,meaning from kanji where (RAcode='"+cardNumA+"' and RBcode='"+cardNumB+"') or (RAcode='"+cardNumB+"' and RBcode='"+cardNumA+"');";
		 words w = new words();
		 
		 String delimiter = "!";
		 String[] pronunciationONArray = null;
		 String[] pronunciationKUNArray = null;
		 String[] pronunciationHiraganaONArray = null;
		 String[] pronunciationHiraganaKUNArray = null;
	     ArrayList<String> pronunciationONList = new ArrayList<String>();
	     ArrayList<String> pronunciationKUNList =  new ArrayList<String>();
	     ArrayList<String> pronunciationHiraganaONList =  new ArrayList<String>();
	     ArrayList<String> pronunciationHiraganaKUNList =  new ArrayList<String>();
		 
		 try {
		      ResultSet rs = stmt.executeQuery(query); 
		                                                              
		      if (rs.next()) { 
		        String kanji = rs.getString("kanji");
		        String pronunciationON = rs.getString("PronunciationON");
		        String pronunciationKUN = rs.getString("PronunciationKUN");
		        String pronunciationHiraganaON = rs.getString("PronunciationKitakanaON");
		        String pronunciationHiraganaKUN = rs.getString("PronunciationKitakanaKUN");
		        String meaning = rs.getString("meaning");
		        
		        meaning = meaning.replace("!", ",");
		        

		        
		        
		        pronunciationONArray = pronunciationON.split(delimiter);
		        System.out.print("pronunciationONArray: "+pronunciationONArray+"\n");
		        pronunciationKUNArray = pronunciationKUN.split(delimiter);
		        System.out.print("pronunciationKUNArray: "+pronunciationKUNArray+"\n");
		        pronunciationHiraganaONArray = pronunciationHiraganaON.split(delimiter);
		        pronunciationHiraganaKUNArray = pronunciationHiraganaKUN.split(delimiter);

		        
		        
			   if(!pronunciationONArray[0].isEmpty()){
			       for(int i =0; i < pronunciationONArray.length ; i++){
			        	pronunciationONList.add(pronunciationONArray[i]);
			        }
			        for(int i =0; i < pronunciationHiraganaONArray.length ; i++){
			        	pronunciationHiraganaONList.add(pronunciationHiraganaONArray[i]);
			        }
			   }
			   
			   if(!pronunciationKUNArray[0].isEmpty()){
			        for(int i =0; i < pronunciationKUNArray.length ; i++){
			        	pronunciationKUNList.add(pronunciationKUNArray[i]);
			        }
			        for(int i =0; i < pronunciationHiraganaKUNArray.length ; i++){
			        	pronunciationHiraganaKUNList.add(pronunciationHiraganaKUNArray[i]);
			        }
			   }
		        w.setKanji(kanji);
		        w.setMeaning(meaning);
		        w.setPronunciationHiraganaKUN(pronunciationHiraganaKUNList);
		        w.setPronunciationHiraganaON(pronunciationHiraganaONList);
		        w.setPronunciationKUN(pronunciationKUNList);
		        w.setPronunciationON(pronunciationONList);
		        
		      }
		    } 
		    catch (Exception e) { 
		      System.out.print("get data error!"); 
		      e.printStackTrace(); 
		    } 
		 return w;
	 }
	
	 public dbConnector(){
		 try { 
		      Class.forName("com.mysql.jdbc.Driver");      
		     System.out.println("Success loading Mysql Driver!"); 
		    } 
		    catch (Exception e) { 
		      System.out.print("Error loading Mysql Driver!"); 
		      e.printStackTrace(); 
		    } 
		    try { 
		      Connection connect = DriverManager.getConnection( 
		          "jdbc:mysql://int.nak.net.it-chiba.ac.jp:3306/kanji","kanjilearner","cit2012"); 
	              //"jdbc:mysql://localhost:3306/kanji","root","105356"); 

		       
		 
		      System.out.println("Success connect Mysql server!"); 
		      stmt = connect.createStatement(); 
		    }catch (Exception e) { 
			      System.out.print("get data error!"); 
			      e.printStackTrace(); 
			    } 
	
	 }
	
	 public static void main(String args[]) { 
		 dbConnector d  = new dbConnector();
		 
		 boolean n = d.ChangeColor("12580A","14461W");
		 System.out.println(n); 
}}
