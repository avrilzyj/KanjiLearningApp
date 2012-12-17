package sat.component;

import java.io.File;
import java.util.ArrayList;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTList;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.scaleProcessor.ScaleProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;


public class PrononciationPracticeItem extends MTRectangle  {
	
	public static final float width = 150;
	public static final float height = 30;
	
	private PrononciationPractice pp;
	private KeyboardTextField elementField;
	private static final float radius = 10;
	private static final float cellHeight = 30;
	private static final float horizontalOffset = 7;
	private MTRoundRectangle correct;
	private MTRoundRectangle wrong;
	
	
	public void ShowCorrect(){
		correct.setVisible(true);
		wrong.setVisible(false);
	}
	
	public void ShowWrong(){
		correct.setVisible(false);
		wrong.setVisible(true);
	}
	
	
	public String getelementField(){
		return elementField.getText();
	}




	
	public PrononciationPracticeItem(MTApplication mtApplication,PrononciationPractice pp) {

		super(mtApplication, width, height);
		
		this.pp = pp;
		
		String imagePath = System.getProperty("user.dir")+File.separator+"ICONS"+File.separator;


		
		correct = new MTRoundRectangle(mtApplication, 0, 0, 0, radius*2, radius*2 ,radius, radius);
		correct.setPositionRelativeToParent(new Vector3D(radius+width+horizontalOffset,cellHeight/2));
		correct.setPickable(false);
		correct.setVisible(false);
		correct.setNoStroke(true);
		PImage correctIcon = mtApplication.loadImage(imagePath + "correct.png");
		correct.setTexture(correctIcon);
		
		wrong = new MTRoundRectangle(mtApplication, 0, 0, 0, radius*2, radius*2 ,radius, radius);
		wrong.setPositionRelativeToParent(new Vector3D(radius+width+horizontalOffset,cellHeight/2));
		wrong.setPickable(false);
		wrong.setVisible(false);
		wrong.setNoStroke(true);
		PImage wrongIcon = mtApplication.loadImage(imagePath + "wrong.png");
		wrong.setTexture(wrongIcon);
		
		elementField = new KeyboardTextField(mtApplication, 150,30,pp);

		elementField.setPositionRelativeToParent(new Vector3D(width/2,height/2));
		elementField.setStrokeColor(new MTColor(251, 151, 29, 255));
		elementField.setStrokeWeight(3);
		elementField.setFillColor(new MTColor(248, 243, 213, 255));
		elementField.setVisible(true);

		elementField.removeAllGestureEventListeners(DragProcessor.class);
		elementField.removeAllGestureEventListeners(RotateProcessor.class);
		elementField.removeAllGestureEventListeners(ScaleProcessor.class);
		
		this.addChild(elementField);
		this.addChild(correct);

		this.addChild(wrong);
	}

	
	

}
