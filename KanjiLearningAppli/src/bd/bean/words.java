package bd.bean;

import java.util.ArrayList;

public class words {
	private String kanji = "";
	private ArrayList<String> pronunciationONList = new ArrayList<String>();
	private ArrayList<String> pronunciationKUNList = new ArrayList<String>();
	private ArrayList<String> pronunciationHiraganaONList = new ArrayList<String>();
	private ArrayList<String> pronunciationHiraganaKUNList = new ArrayList<String>();
	private String meaning = "";
	
	public String getKanji() {
		return kanji;
	}
	public void setKanji(String kanji) {
		this.kanji = kanji;
	}
	public ArrayList<String> getPronunciationON() {
		return pronunciationONList;
	}
	public void setPronunciationON(ArrayList<String> pronunciationON) {
		this.pronunciationONList = pronunciationON;
	}
	public ArrayList<String> getPronunciationKUN() {
		return pronunciationKUNList;
	}
	public void setPronunciationKUN(ArrayList<String> pronunciationKUN) {
		this.pronunciationKUNList = pronunciationKUN;
	}
	public ArrayList<String> getPronunciationHiraganaON() {
		return pronunciationHiraganaONList;
	}
	public void setPronunciationHiraganaON(ArrayList<String> pronunciationHiraganaON) {
		this.pronunciationHiraganaONList = pronunciationHiraganaON;
	}
	public ArrayList<String> getPronunciationHiraganaKUN() {
		return pronunciationHiraganaKUNList;
	}
	public void setPronunciationHiraganaKUN(
			ArrayList<String> pronunciationHiraganaKUN) {
		this.pronunciationHiraganaKUNList = pronunciationHiraganaKUN;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	
	

}
