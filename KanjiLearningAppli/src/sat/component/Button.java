package sat.component;

import java.io.File;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;

import scenes.WorkPlaceScene;

public class Button extends MTRoundRectangle{
	public static final float width = 70;
	public static final float height = 70;
	private static final float radius = 35;
	
	private MTApplication mTApp;
	
	private String iconPath = System.getProperty("user.dir")+File.separator+"ICONS"+File.separator;
	
	protected void addText(String text){
		MTTextArea textField = new MTTextArea(mTApp, FontManager.getInstance().createFont(mTApp, "arial.ttf", 
				20,new MTColor(251, 151, 29, 255))); 
		textField.setNoFill(true);
		textField.setNoStroke(true);
		textField.setText(text);
		this.addChild(textField);
		textField.setPositionGlobal(getCenterPointRelativeToParent());
		textField.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		textField.removeAllGestureEventListeners(RotateProcessor.class);
	}
	
	protected void addTexture(String text){
		PImage icon = mTApp.loadImage(iconPath+text+".png");
		this.setTexture(icon);
	}
	
	protected void setPosition(Vector3D pos){
		this.setPositionGlobal(pos);
	}

	public Button(MTApplication mtApplication, WorkPlaceScene workPlaceScene){
		super(mtApplication,0,0,0,width,height,radius,radius);
		
		this.mTApp = mtApplication;
		
		this.setStrokeColor(new MTColor(251, 151, 29, 255));
		this.setStrokeWeight(5);
		
		
	}
}
