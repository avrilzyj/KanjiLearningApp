package sat.component;

import java.awt.*;

import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;


import scenes.WorkPlaceScene;

public class KanjiPractice extends MTRectangle{
	public static final float width = 215;
	public static final float height = 300;
	
	private MTApplication mtApp;
	
    private KanjiPractice instance;
    
	private MTRoundRectangle clearButton;
	private MTRoundRectangle closeButton;
	private static final float radius = 30;
	private static final float rightPadding = 10;
	private MTRectangle paint;
	
	private WorkPlaceScene workPlaceS;
    
    private String imagePath = System.getProperty("user.dir") + File.separator + "IMAGES" + File.separator + "brush.png";
    private String iconPath = System.getProperty("user.dir")+File.separator+"ICONS"+File.separator;
    
    
    public void addCloseListner(final MTRoundRectangle close){
    	close.registerInputProcessor(new TapProcessor(mtApp));
    	close.addGestureListener(TapProcessor.class, new IGestureEventListener() {
 			@Override
 			public boolean processGestureEvent(MTGestureEvent ge) {
 				TapEvent te = (TapEvent)ge;
 					switch (te.getTapID()) {
 					case TapEvent.TAPPED:
 						workPlaceS.refreshPronunciationPractice();
 						break;
 					default:
 						break;
 					}
 				
 				return false;
 			}
 		});
     }
    
    public void addClearListner(final MTRoundRectangle correct){
		correct.registerInputProcessor(new TapProcessor(mtApp));
		correct.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						instance.removeAllChildren();
						
						
						closeButton = new MTRoundRectangle(mtApp, 0, 0, 0, radius*2, radius*2 ,radius, radius);
						closeButton.setPositionRelativeToParent(new Vector3D(width-radius-rightPadding,height-radius-rightPadding));
						closeButton.setNoStroke(true);
						PImage closeButtonTexture = mtApp.loadImage(iconPath+"close.png");
						closeButton.setTexture(closeButtonTexture);
						

						instance.addChild(closeButton);
						instance.addCloseListner(closeButton);
						closeButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
						closeButton.removeAllGestureEventListeners(RotateProcessor.class);
						
						
						clearButton = new MTRoundRectangle(mtApp, 0, 0, 0, radius*2, radius*2 ,radius, radius);
						clearButton.setPositionRelativeToParent(new Vector3D(radius+rightPadding,height-radius-rightPadding));
						clearButton.setNoStroke(true);
						PImage wrongIcon = mtApp.loadImage(iconPath+"clear.png");
						clearButton.setTexture(wrongIcon);
						

						instance.addChild(clearButton);
						instance.addClearListner(clearButton);
						clearButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
						clearButton.removeAllGestureEventListeners(RotateProcessor.class);
						break;
					default:
						break;
					}
				
				return false;
			}
		});
    }
    
    
    private Boolean isInCloseButton(MTRectangle paint,MTRoundRectangle closeButton){
    	Boolean isInCloseButton = true;
    	if(paint.getCenterPointRelativeToParent().distance(closeButton.getCenterPointRelativeToParent())>radius){
    		isInCloseButton = false;
    	}
    	
    	return isInCloseButton;
    }
    
    
    private Boolean isInClearButton(MTRectangle paint,MTRoundRectangle clearButton){
    	Boolean isInClearButton = true;
    	if(paint.getCenterPointRelativeToParent().distance(clearButton.getCenterPointRelativeToParent())>radius){
    		isInClearButton = false;
    	}
    	
    	return isInClearButton;
    }
	
	public KanjiPractice(MTApplication mtApplication, final WorkPlaceScene workPlaceScene){
		
		super(mtApplication,width,height);
		
		
		
		this.workPlaceS = workPlaceScene;
		this.instance = this;
		this.mtApp = mtApplication;
		this.setFillColor(new MTColor(254, 201, 71, 255));
		this.setStrokeColor(new MTColor(251, 151, 29, 255));

		this.setStrokeWeight(3);
		
		this.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		this.removeAllGestureEventListeners(RotateProcessor.class);
		
		this.setAnchor(PositionAnchor.UPPER_LEFT);
		
		
		closeButton = new MTRoundRectangle(mtApplication, 0, 0, 0, radius*2, radius*2 ,radius, radius);
		closeButton.setPositionRelativeToParent(new Vector3D(width-radius-rightPadding,height-radius-rightPadding));
		closeButton.setNoStroke(true);
		PImage closeButtonTexture = mtApplication.loadImage(iconPath+"close.png");
		closeButton.setTexture(closeButtonTexture);
		

		this.addChild(closeButton);
		this.addCloseListner(closeButton);
		closeButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		closeButton.removeAllGestureEventListeners(RotateProcessor.class);
		
		
		
		
		
		clearButton = new MTRoundRectangle(mtApplication, 0, 0, 0, radius*2, radius*2 ,radius, radius);
		clearButton.setPositionRelativeToParent(new Vector3D(radius+rightPadding,height-radius-rightPadding));
		clearButton.setNoStroke(true);
		PImage clearButtonTexture = mtApplication.loadImage(iconPath+"clear.png");
		clearButton.setTexture(clearButtonTexture);
		

		this.addChild(clearButton);
		this.addClearListner(clearButton);
		clearButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		clearButton.removeAllGestureEventListeners(RotateProcessor.class);
		
		
		
		this.registerInputProcessor(new DragProcessor(mtApplication));
		this.addGestureListener(DragProcessor.class,new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				DragEvent de = (DragEvent)ge;
				int currentX = 0, currentY = 0;
				Vector3D matherVector = new Vector3D();
				PImage texture = mtApp.loadImage(imagePath);
				paint = new MTRectangle(texture,mtApp);
				paint.setFillColor(MTColor.BLACK);
				paint.setNoStroke(true);
				paint.setHeightLocal(10);
				paint.setWidthLocal(10);
				switch (de.getId()) {
				case MTGestureEvent.GESTURE_STARTED:
					break;
				case MTGestureEvent.GESTURE_UPDATED:
                    currentX = (int)de.getDragCursor().getCurrentEvtPosX();
                    matherVector = instance.getCenterPointGlobal();
                    currentY = (int)de.getDragCursor().getCurrentEvtPosY();
                    paint.setPositionGlobal(new Vector3D(currentX+width/2-matherVector.x,currentY+height/2-matherVector.y));
                    paint.setPickable(false);
                    if(instance.isGeometryContainsPointLocal(paint.getCenterPointRelativeToParent())&&! isInClearButton(paint,clearButton)&&!isInCloseButton(paint,closeButton)){
                        instance.addChild(paint);
                    }
					break;
				case MTGestureEvent.GESTURE_ENDED:
					break;
				default:
					break;
				}		
				return false;
			}
		});
		

	}

}

