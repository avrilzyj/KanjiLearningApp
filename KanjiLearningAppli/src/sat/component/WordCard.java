package sat.component;

import java.io.File;
import java.util.ArrayList;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRectangle.PositionAnchor;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;



import processing.core.PImage;



import scenes.WorkPlaceScene;

public class WordCard extends MTRectangle{
	public static final float width = 60;
	public static final float height = 60;
	
	private MTApplication mTApp;
	
	private String wordCardCode = "";
	private  Vector3D pos = null;
	
	private Boolean checkIsKanji = false;
	
	
	String imagePath = System.getProperty("user.dir") + File.separator + "PICS" + File.separator;

	String strokeOrderImagePath = System.getProperty("user.dir")+File.separator+"StrokeImage"+File.separator;
	PImage texture;

	
	public String getWordCardCode(){
		return this.wordCardCode;
	}
	
	public void setWordCardCode(String n){
		this.wordCardCode = n;
	}
	
	public void changeIntoStrokeOrderCard(){
		texture = mTApp.loadImage(strokeOrderImagePath+wordCardCode+"S.png");
		if(texture!=null){
			this.setTexture(texture);
		}
	}
	

	public WordCard(MTApplication mtApplication, WorkPlaceScene workPlaceScene,String n,Boolean checkIsKanji){
		super(mtApplication,width,height);
		mTApp = mtApplication;
        this.wordCardCode = n;
		this.checkIsKanji = checkIsKanji;
		if(checkIsKanji){
	        texture = mtApplication.loadImage(imagePath+wordCardCode+"W.png");

		}else{
	        texture = mtApplication.loadImage(imagePath+wordCardCode+".png");
		}
		this.setFillColor(new MTColor(254, 201, 71, 255));
		this.setStrokeColor(new MTColor(251, 151, 29, 255));
		this.setStrokeWeight(3);
		this.setTexture(texture);
	
		
		this.addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApplication, this));
		this.registerInputProcessor(new TapAndHoldProcessor(mtApplication,750));
		this.addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapAndHoldEvent e = (TapAndHoldEvent)ge;
				if (e.getId() == TapAndHoldEvent.GESTURE_STARTED){
					WorkPlaceScene.getInstance().restartColor();
				}
				if (e.getId() == TapAndHoldEvent.GESTURE_ENDED && e.isHoldComplete()){
					e.stopPropagation();
					WorkPlaceScene.getInstance().jugeSimilarKanjiChangeColor(wordCardCode);
				}
				return false;
			}
		});
		
		
		

	}
	
	
}
