package sat.component;



import java.io.File;
import java.util.ArrayList;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import database.dbConnector;

import processing.core.PImage;

import bd.bean.words;

import scenes.WorkPlaceScene;

public class PrononciationPractice extends MTRectangle{
	public static final float width = 215;
	public static final float height = 300;
	public static final float rightPadding = 10;
	public static final float topPadding = 10;
	public static final float bottomPadding = 10;
	public static final float middleSpace = 20;
	public static final float bottomSpace = 70;
	public static final float lineHeight = 45;
	public static final float titleHeight = 30;

	private PronunciationDisplay pronunciationD;
	private PrononciationPracticeItem element;
	private ArrayList<PrononciationPracticeItem> elementListON;
	private ArrayList<PrononciationPracticeItem> elementListKUN;
	private PrononciationPractice instance;
	private words w;
	
	private MTRoundRectangle checkButton;
	private static final float radius = 30;
	private String imagePath = System.getProperty("user.dir")+File.separator+"ICONS"+File.separator;
	
	private int pronunciationONListSize = 0;
	private int pronunciationKUNListSize = 0;
	
	private float dynamicHeight =0;
	
	private MTApplication mtApp;
	
	private WordCard wordCard;
	
	private dbConnector db = null;
	
	public String getW(){
		return w.getKanji();
	}
	
    public Boolean IsPronunciationPracticeEmpty(){
    	Boolean isEmpty = true;
    	if(!w.getKanji().isEmpty()){
    		isEmpty = false;
    	}
    	return isEmpty;
    }

	private void showPronunciationDisplay(){
		pronunciationD.setPositionRelativeToParent(new Vector3D(0,0));
		pronunciationD.setVisible(true);
	}
	
	public void addCheckListner(final MTRoundRectangle correct){
		correct.registerInputProcessor(new TapProcessor(mtApp));
		correct.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						for(int i=0; i<pronunciationONListSize;i++){
							if(elementListON.get(i).getelementField().equals(w.getPronunciationON().get(i))){
								elementListON.get(i).ShowCorrect();
							}else{
								elementListON.get(i).ShowWrong();
							}
						}
						
						for(int i=0; i<pronunciationKUNListSize;i++){
							if(elementListKUN.get(i).getelementField().equals(w.getPronunciationKUN().get(i))){
								elementListKUN.get(i).ShowCorrect();
							}else{
								elementListKUN.get(i).ShowWrong();
							}
						}
						break;
					default:
						break;
					}
				
				return false;
			}
		});
}
	
	public PrononciationPractice(MTApplication mtApplication, WorkPlaceScene workPlaceScene,WordCard wordCard,String codeA,String codeB){
		super(mtApplication,width,height);
		
		instance = this;
		this.mtApp = mtApplication;
		this.setAnchor(PositionAnchor.UPPER_LEFT);

		this.wordCard = new WordCard(mtApplication,workPlaceScene,"12321W",false);
		this.wordCard = wordCard;
		
		//get word info
		db = WorkPlaceScene.getInstance().db;
		w = db.GetWordInfoCodeAB(codeA, codeB);
		
		
		
		IFont textFont = FontManager.getInstance().createFont(mtApplication, "SansSerif",

				15, // Font size
				new MTColor(58, 55, 98, 255), // Font fill color
				true);
		
	    pronunciationONListSize = w.getPronunciationON().size();
	    pronunciationKUNListSize = w.getPronunciationKUN().size();
	    
	    elementListON = new ArrayList<PrononciationPracticeItem>();
	    elementListKUN = new ArrayList<PrononciationPracticeItem>();
		
		MTTextArea titleArea = new MTTextArea(mtApplication, 0, 0, 200, 0,textFont);
		titleArea.setPositionRelativeToParent(new Vector3D(rightPadding+100,topPadding));
		titleArea.setNoStroke(true);
				
		titleArea.setText("What is the pronounciation:");
		this.addChild(titleArea);
		
		
		if(!w.getPronunciationON().isEmpty()){
			MTTextArea ONTitleArea = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
			ONTitleArea.setPositionRelativeToParent(new Vector3D(rightPadding,topPadding+titleHeight));
			
			ONTitleArea.setText("ON-yomi:");
			this.addChild(ONTitleArea);			
			
			for(int i=0; i<pronunciationONListSize;i++){
				element = new PrononciationPracticeItem (mtApplication,instance);
				element.setPositionRelativeToParent(new Vector3D(rightPadding*2+PrononciationPracticeItem.width/2,PrononciationPracticeItem.height/2+topPadding+lineHeight*i+titleHeight*2));
				elementListON.add(element);
				this.addChild(element);
			}
			if(!w.getPronunciationKUN().isEmpty()){
				MTTextArea KUNTitleArea = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
				KUNTitleArea.setPositionRelativeToParent(new Vector3D(rightPadding,topPadding+titleHeight*2+pronunciationONListSize*lineHeight+middleSpace));
				
				KUNTitleArea.setText("KUN-yomi:");
				this.addChild(KUNTitleArea);
				
				for(int i=0; i<pronunciationKUNListSize;i++){
					element = new PrononciationPracticeItem (mtApplication,instance);
					element.setPositionRelativeToParent(new Vector3D(rightPadding*2+PrononciationPracticeItem.width/2,topPadding+PrononciationPracticeItem.height/2+lineHeight*i+pronunciationONListSize*lineHeight+titleHeight*3+middleSpace));
					elementListKUN.add(element);
					this.addChild(element);
				}
				
				dynamicHeight = topPadding+lineHeight*pronunciationKUNListSize+pronunciationONListSize*lineHeight+titleHeight*3+bottomSpace+middleSpace;
				this.setHeightLocal(dynamicHeight); //change windows size

			}else{
				dynamicHeight = topPadding+pronunciationONListSize*lineHeight+titleHeight*2+bottomSpace;
				this.setHeightLocal(dynamicHeight); //change windows size

			}
			
		}else{
			if(!w.getPronunciationKUN().isEmpty()){
				MTTextArea KUNTitleArea = new MTTextArea(mtApplication, 0, 0, 0, 0,textFont);
				KUNTitleArea.setPositionRelativeToParent(new Vector3D(rightPadding,topPadding+titleHeight));
				
				KUNTitleArea.setText("KUN-yomi:");
				this.addChild(KUNTitleArea);
				
				for(int i=0; i<pronunciationKUNListSize;i++){
					element = new PrononciationPracticeItem (mtApplication,instance);
					element.setPositionRelativeToParent(new Vector3D(rightPadding*2+PrononciationPracticeItem.width/2,topPadding+PrononciationPracticeItem.height/2+lineHeight*i+titleHeight*2));
					elementListKUN.add(element);
					this.addChild(element);
				}
				
				dynamicHeight = topPadding+pronunciationKUNListSize*lineHeight+titleHeight*2+bottomSpace;
				this.setHeightLocal(dynamicHeight); //change windows size

			}
		}


		this.setFillColor(new MTColor(254, 201, 71, 255));

		this.setStrokeColor(new MTColor(251, 151, 29, 255));
		this.setStrokeWeight(3);
		
		this.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		this.removeAllGestureEventListeners(RotateProcessor.class);
				
		
		
		checkButton = new MTRoundRectangle(mtApplication, 0, 0, 0, radius*2, radius*2 ,radius, radius);
		checkButton.setPositionRelativeToParent(new Vector3D(width-radius-rightPadding,dynamicHeight-radius-rightPadding));
		checkButton.setNoStroke(true);
		PImage wrongIcon = mtApplication.loadImage(imagePath + "check.png");
		checkButton.setTexture(wrongIcon);
		

		this.addChild(checkButton);
		this.addCheckListner(checkButton);
		checkButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		checkButton.removeAllGestureEventListeners(RotateProcessor.class);
		
		
		//add PronunciationDisplay
		pronunciationD = new PronunciationDisplay(mtApplication,workPlaceScene,wordCard, w);
		pronunciationD.setVisible(false);	
		this.addChild(pronunciationD);
		
		//gesture for showing PronunciationDisplay
		this.registerInputProcessor(new TapProcessor(mtApplication));
		this.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						showPronunciationDisplay();
						break;
					default:
						break;
					}
				
				return false;
			}
		});
	}
}
