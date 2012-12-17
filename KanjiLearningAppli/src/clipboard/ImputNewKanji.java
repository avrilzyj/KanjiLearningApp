package clipboard;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

import database.dbConnector;

public class ImputNewKanji implements ClipboardOwner{
	public dbConnector db = null;
	private ArrayList<String> ridicals;
	private Boolean imputNewKanjiExist = false;
	
	private TextTransfer textTransfer;
	
	
	public void setDB(dbConnector db){
		this.db = db;
		return;
	}
	
	public void clearClipboard(){
		textTransfer.setClipboardContents("");
		return;
	}
	
	public Boolean IsExist(){
		return imputNewKanjiExist;
	}

	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<String> getRadicals(){
		return ridicals;
	}

	public ImputNewKanji(dbConnector db){
	    textTransfer = new TextTransfer();
	    ridicals = new ArrayList<String>();
	    this.db = db;
	    
		System.out.println("Clipboard contains:" + textTransfer.getClipboardContents() );
		if(db.checkKanji(textTransfer.getClipboardContents())&&!textTransfer.getClipboardContents().isEmpty()){
			ridicals = db.getRadicalsByKanji(textTransfer.getClipboardContents());
			//System.out.println("ridical0: "+ridicals.get(0)+"\n");
			//System.out.println("ridical1: "+ridicals.get(1)+"\n");	
			imputNewKanjiExist = true;
		}


	}
}
