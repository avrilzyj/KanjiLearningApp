package scenes;

import java.io.File;
import java.util.ArrayList;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.sceneManagement.Iscene;
import org.mt4j.sceneManagement.transition.FadeTransition;
import org.mt4j.sceneManagement.transition.ITransition;
import org.mt4j.sceneManagement.transition.SlideTransition;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;

import processing.core.PImage;

public class Scene extends AbstractScene{
	private MTApplication mtApp;
	private String buttonPath = System.getProperty("user.dir") + File.separator + "ICONS" + File.separator;
	private String guidePicsPath = System.getProperty("user.dir") + File.separator + "GUIDEPICS" + File.separator;
	
	private ArrayList<MTRoundRectangle> pageButtonList;
	private MTRoundRectangle pageButton;
	private final static int pageNum = 6;
	private static final float radius = 20;
	
	private MTRoundRectangle guidePicsRectangle;
	
	
	private ITransition slideLeftTransition;
	
	protected Iscene scenePrevious;
	protected Iscene sceneNext;
	
	private int currentPage = 0;
	
	
	protected void addGuideText(String text,Vector3D vector){
		MTTextArea textField = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 
				50,new MTColor(254, 201, 71, 255))); 
		textField.setNoFill(true);
		textField.setNoStroke(true);
		textField.setText(text);
		this.getCanvas().addChild(textField);
		textField.setPositionGlobal(vector);
		textField.setPickable(false);// To enable all interactive gestures
	}
	
    protected void addGuidePicsRectangle(int currentPageNum,Vector3D vector){
		guidePicsRectangle = new MTRoundRectangle(mtApp, 0, 0, 0, 610, 480 ,radius, radius);
		guidePicsRectangle.setPositionRelativeToParent(vector);
		guidePicsRectangle.setNoStroke(true);
		PImage guidePicsTesture = new PImage();
		guidePicsTesture = mtApp.loadImage(guidePicsPath +"guide"+currentPageNum +".png");
		guidePicsRectangle.setTexture(guidePicsTesture);
		this.getCanvas().addChild(guidePicsRectangle);
		guidePicsRectangle.setPickable(false);// To enable all interactive gestures
    }
	
	protected void addButtons(int currentPageNum){
		for(int i = 0; i < pageNum; i++){
			pageButton = new MTRoundRectangle(mtApp, 0, 0, 0, radius*2, radius*2 ,radius, radius);
			pageButton.setPositionRelativeToParent(new Vector3D(200+i*125,650));
			pageButton.setNoStroke(true);
			PImage pageButtonTesture = new PImage();
			if( i != currentPageNum-1){
				pageButtonTesture = mtApp.loadImage(buttonPath + "otherPage.png");
			}else{
				pageButtonTesture = mtApp.loadImage(buttonPath + "currentPage.png");
			}
			pageButton.setTexture(pageButtonTesture);
			pageButtonList.add(pageButton);
			

			this.getCanvas().addChild(pageButtonList.get(i));
			this.addButtonListner(pageButtonList.get(i),i+1);
			pageButtonList.get(i).removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
			pageButtonList.get(i).removeAllGestureEventListeners(RotateProcessor.class);

		}
	}
	
    public void addButtonListner(final MTRoundRectangle pageButton,final int i){
    	pageButton.registerInputProcessor(new TapProcessor(mtApp));
    	pageButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
		    	Scene scene = null;

				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
				    	if(mtApp.getScene("Scene "+i) == null){
				    		switch(i){
				    		case 1: 
				    			scene = new Scene1(mtApp, "Scene 1");
				    			mtApp.addScene(scene);
				    			break;
				    		case 2: 
				    			scene = new Scene2(mtApp, "Scene 2");
				    			mtApp.addScene(scene);
				    			break;
				    		case 3: 
				    			scene = new Scene3(mtApp, "Scene 3");
				    			mtApp.addScene(scene);
				    			break;
				    		case 4: 
				    			scene = new Scene4(mtApp, "Scene 4");
				    			mtApp.addScene(scene);
				    			break;
				    		case 5: 
				    			scene = new Scene5(mtApp, "Scene 5");
				    			mtApp.addScene(scene);
				    			break;
				    		default: 
				    			scene = new Scene6(mtApp, "Scene 6");
				    			mtApp.addScene(scene);
				    			break;
				    		}
				    	}
						setTransition(slideLeftTransition); 

		    			mtApp.changeScene(mtApp.getScene("Scene "+i) );

						break;
					default:
						break;
					}
				
				return false;
			}
		});
    }
    public Scene(MTApplication mtApplication, String name,final int currentPage) {
		super(mtApplication, name);
		this.mtApp = mtApplication;
		this.currentPage = currentPage;
		
		
		//Set the background color
		this.setClearColor(new MTColor(222, 237, 231, 255));
		
		pageButtonList = new  ArrayList<MTRoundRectangle>();
		
		addButtons(currentPage);
		
		//Set a scene transition - Flip transition only available using opengl supporting the FBO extenstion
		if (MT4jSettings.getInstance().isOpenGlMode() && GLFBO.isSupported(mtApp)){
			slideLeftTransition = new SlideTransition(mtApp, 700, true);
		}else{
			this.setTransition(new FadeTransition(mtApp));
		}
		

	}

	public void onEnter() {
		System.out.println("Entered scene: " +  this.getName());
	}
	
	public void onLeave() {	
		System.out.println("Left scene: " +  this.getName());
	}
	
}

