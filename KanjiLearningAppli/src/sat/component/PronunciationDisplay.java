package sat.component;

import java.io.File;
import java.util.ArrayList;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;

import database.dbConnector;

import bd.bean.words;

import scenes.WorkPlaceScene;

public class PronunciationDisplay extends MTRectangle{
	public static final float width = 215;
	public static final float height = 300;
	public static final float lineHeight = 25;
	public static final float lineSpace = 10;
	public static final float rightPadding = 20;
	public static final float topPadding = 10;
	public static final float bottomPadding = 15;

	
	private KanjiPractice kanjiP;
	private words w;
	private WordCard wordCard;
	private float dynamicHeight =0;
	private MTRoundRectangle practiceButton;
	private static final float radius = 30;
	private String imagePath = System.getProperty("user.dir")+File.separator+"ICONS"+File.separator;
	
	private MTApplication mtApp;
	private PronunciationDisplay instance;
	

	private void changeIntoStrokeOrderCard(){
		wordCard.changeIntoStrokeOrderCard();
	}

     
  	private void showKanjiPractice(){
  		kanjiP.setPositionRelativeToParent(new Vector3D(0,0));
  		kanjiP.setVisible(true);
  	}
  	
	public void addPracticeListner(final MTRoundRectangle practiceButton){
		practiceButton.registerInputProcessor(new TapProcessor(mtApp));
		practiceButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						instance.setVisible(false);
						break;
					default:
						break;
					}
				
				return false;
			}
		});
  	
	}	
	
	public PronunciationDisplay(MTApplication mtApplication, WorkPlaceScene workPlaceScene,WordCard wordCard,words w){
		super(mtApplication,width,height);
		this.setFillColor(new MTColor(254, 201, 71, 255));
		this.setStrokeColor(new MTColor(251, 151, 29, 255));
		this.setStrokeWeight(3);
	    this.setAnchor(PositionAnchor.UPPER_LEFT);
		
		this.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		this.removeAllGestureEventListeners(RotateProcessor.class);
		
		this.mtApp = mtApplication;
		this.w = w;
		this.wordCard = wordCard;
		
		this.instance = this;

		IFont textFont = FontManager.getInstance().createFont(mtApplication, "SansSerif",
				15, // Font size
				new MTColor(58, 55, 98, 255), // Font fill color
				true);
		
		
		MTTextArea kanjiCategoryONField;
	    ArrayList<MTTextArea> kanjiONArea = new  ArrayList<MTTextArea>();
	    ArrayList<MTTextArea> kanjiONAreaHiragana = new  ArrayList<MTTextArea>();;
	    MTTextArea kanjiCategoryKUNField;
		ArrayList<MTTextArea> kanjiKUNArea = new  ArrayList<MTTextArea>();;
		ArrayList<MTTextArea> kanjiKUNAreaHiragana = new  ArrayList<MTTextArea>();;
		MTTextArea meaningField;
		MTTextArea meaningArea;
		
		MTTextArea temp;
		MTTextArea temp1;
		int x = w.getPronunciationON().size();
		int y = w.getPronunciationKUN().size();
		
		
		
		meaningField = new MTTextArea(mtApplication, 0, 0, 0,0,textFont);
		meaningArea = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
	
		
		if(!w.getPronunciationON().isEmpty()){
			for(int i=0; i<w.getPronunciationON().size();i++){
				temp = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
				temp1 = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
				kanjiONArea.add(temp);
				kanjiONAreaHiragana.add(temp1);
			}	
			
			kanjiCategoryONField = new MTTextArea(mtApplication, 0, 0, 0,0,textFont);
			
			kanjiCategoryONField.setPositionRelativeToParent(new Vector3D(rightPadding,topPadding));

			for(int i=0; i<w.getPronunciationON().size();i++){
				kanjiONArea.get(i).setPositionRelativeToParent(new Vector3D(rightPadding,lineHeight*2*i+lineHeight+topPadding));
				kanjiONAreaHiragana.get(i).setPositionRelativeToParent(new Vector3D(rightPadding,lineHeight*2*i+lineHeight*2+topPadding));
			}
			
			
			kanjiCategoryONField.setText("ON-yomi:");
			this.addChild(kanjiCategoryONField);
			
			for(int i=0; i<w.getPronunciationON().size();i++){
				kanjiONAreaHiragana.get(i).setText(w.getPronunciationHiraganaON().get(i));
				this.addChild(kanjiONAreaHiragana.get(i));
				kanjiONArea.get(i).setText("/"+w.getPronunciationON().get(i)+"/");
				this.addChild(kanjiONArea.get(i));

			}

			
			if(!w.getPronunciationKUN().isEmpty()){
				for(int i=0; i<w.getPronunciationKUN().size();i++){
					temp = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
					temp1 = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
					kanjiKUNArea.add(temp);
					kanjiKUNAreaHiragana.add(temp1);
				}
			
				kanjiCategoryKUNField = new MTTextArea(mtApplication, 0, 0, 0,0,textFont);
				
				kanjiCategoryKUNField.setPositionRelativeToParent(new Vector3D(rightPadding,lineSpace+lineHeight+lineHeight*x*2+topPadding));

				for(int i=0; i<w.getPronunciationKUN().size();i++){
					kanjiKUNArea.get(i).setPositionRelativeToParent(new Vector3D(rightPadding,lineSpace+lineHeight*2*i+lineHeight*(x*2+2)+topPadding));
					kanjiKUNAreaHiragana.get(i).setPositionRelativeToParent(new Vector3D(rightPadding,lineSpace+lineHeight*2*i+lineHeight*(x*2+2)+lineHeight+topPadding));
				}
				
				
				kanjiCategoryKUNField.setText("KUN-yomi:");
				this.addChild(kanjiCategoryKUNField);				
				

			
				for(int i=0; i<w.getPronunciationKUN().size();i++){
					kanjiKUNAreaHiragana.get(i).setText(w.getPronunciationHiraganaKUN().get(i));
					this.addChild(kanjiKUNAreaHiragana.get(i));
					kanjiKUNArea.get(i).setText("/"+w.getPronunciationKUN().get(i)+"/");
					this.addChild(kanjiKUNArea.get(i));				
							
			 }

				meaningField.setPositionRelativeToParent(new Vector3D(rightPadding,(x*2+y*2+2)*lineHeight+lineSpace*2+topPadding));
				meaningArea.setPositionRelativeToParent(new Vector3D(rightPadding,lineHeight+(x*2+y*2+2)*lineHeight+lineSpace*2+topPadding));
				dynamicHeight = lineHeight+(x*2+y*2+5)*lineHeight+lineSpace*2+topPadding+bottomPadding;
				this.setHeightLocal(dynamicHeight); //change windows size
			
			}else{

				meaningArea.setPositionRelativeToParent(new Vector3D(rightPadding,lineHeight+(x*2+1)*lineHeight+lineSpace+topPadding));
				dynamicHeight = lineHeight+(x*2+4)*lineHeight+lineSpace+topPadding+bottomPadding;
				this.setHeightLocal(dynamicHeight); //change windows size
			
			}


		}else{
			if(!w.getPronunciationKUN().isEmpty()){
				for(int i=0; i<w.getPronunciationKUN().size();i++){
					temp = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
					temp1 = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
					kanjiKUNArea.add(temp);
					kanjiKUNAreaHiragana.add(temp1);
				}
			
				kanjiCategoryKUNField = new MTTextArea(mtApplication, 0, 0, 0,0,textFont);
				
				kanjiCategoryKUNField.setPositionRelativeToParent(new Vector3D(rightPadding,topPadding));

				for(int i=0; i<w.getPronunciationKUN().size();i++){
					kanjiKUNArea.get(i).setPositionRelativeToParent(new Vector3D(rightPadding,lineHeight*2*i+lineHeight+topPadding));
					kanjiKUNAreaHiragana.get(i).setPositionRelativeToParent(new Vector3D(rightPadding,lineHeight*2*i+lineHeight*2+topPadding));
	
				}
				
				
				kanjiCategoryKUNField.setText("KUN-yomi:");
				this.addChild(kanjiCategoryKUNField);				
				

				
				for(int i=0; i<w.getPronunciationKUN().size();i++){
					kanjiKUNAreaHiragana.get(i).setText(w.getPronunciationHiraganaKUN().get(i));
					this.addChild(kanjiKUNAreaHiragana.get(i));
					kanjiKUNArea.get(i).setText("/"+w.getPronunciationKUN().get(i)+"/");
					this.addChild(kanjiKUNArea.get(i));				
							
			 }

				meaningField.setPositionRelativeToParent(new Vector3D(rightPadding,(y*2+2)*lineHeight+topPadding));
				meaningArea.setPositionRelativeToParent(new Vector3D(rightPadding,lineHeight+(y*2+2)*lineHeight+topPadding));
				dynamicHeight = lineHeight+(y*2+4)*lineHeight+lineSpace+topPadding+bottomPadding;
				this.setHeightLocal(dynamicHeight); //change windows size
	
			}
		}
		
		meaningField.setText("Meaning:");
		this.addChild(meaningField);
		meaningArea.setText(w.getMeaning());
		this.addChild(meaningArea);


		practiceButton = new MTRoundRectangle(mtApplication, 0, 0, 0, radius*2, radius*2 ,radius, radius);
		practiceButton.setPositionRelativeToParent(new Vector3D(width-radius-10,dynamicHeight-radius-10));
		practiceButton.setNoStroke(true);
		PImage wrongIcon = mtApplication.loadImage(imagePath + "prac.png");
		practiceButton.setTexture(wrongIcon);

		this.addChild(practiceButton);
		this.addPracticeListner(practiceButton);
		practiceButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		practiceButton.removeAllGestureEventListeners(RotateProcessor.class);
		
		//create kanji practice page
		kanjiP= new KanjiPractice(mtApplication,workPlaceScene);
		kanjiP.setVisible(false);
		this.addChild(kanjiP);
		
		
		//gesture for showing KanjiPractice
		this.registerInputProcessor(new TapProcessor(mtApplication));
		this.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						changeIntoStrokeOrderCard();
						showKanjiPractice();
						break;
					default:
						break;
					}
				
				return false;
			}
		});			

	}
}
