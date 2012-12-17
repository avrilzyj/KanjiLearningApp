package scenes;


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
import org.mt4j.sceneManagement.transition.FadeTransition;
import org.mt4j.sceneManagement.transition.ITransition;
import org.mt4j.sceneManagement.transition.SlideTransition;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;


public class StartScene extends AbstractScene{
	private MTApplication mtApp;
	private ITransition slideLeftTransition;
	private Scene scene1;
	private WorkPlaceScene workPlaceScene;
	
	private int buttonWeight = 300;
	private int buttonHeight = 200;
	
	MTRoundRectangle guideModeButton;
	MTRoundRectangle practiceModeButton;
	private static final float radius = 20;



	public StartScene(MTApplication mtApplication, String name) {
		super(mtApplication, name);
		this.mtApp = mtApplication;
		
		//Set the background color
		this.setClearColor(new MTColor(222, 237, 231, 255));
		
		
		//Create a textfield
		MTTextArea textField = new MTTextArea(mtApplication, FontManager.getInstance().createFont(mtApp, "arial.ttf", 
				70,new MTColor(251, 151, 29, 255))); 
		textField.setNoFill(true);
		textField.setNoStroke(true);
		textField.setText("Kanji  Learning  Application");
		this.getCanvas().addChild(textField);
		textField.setPositionGlobal(new Vector3D(mtApp.width/2f, mtApp.height/4.5f));
		textField.setPickable(false);
		
		
		//Create guideModeButton
		guideModeButton = new MTRoundRectangle(mtApp,0,0,0,buttonWeight,buttonHeight,radius, radius);
		guideModeButton.setPositionRelativeToParent(new Vector3D(250,500));
		guideModeButton.setStrokeWeight(5);
		guideModeButton.setFillColor(new MTColor(254, 201, 71, 255));
		guideModeButton.setStrokeColor(new MTColor(251, 151, 29, 255));
		guideModeButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		guideModeButton.removeAllGestureEventListeners(RotateProcessor.class);
		
		
		MTTextArea textFieldGuideMode = new MTTextArea(mtApplication, FontManager.getInstance().createFont(mtApp, "arial.ttf", 
				40,new MTColor(251, 151, 29, 255))); 
		textFieldGuideMode.setNoFill(true);
		textFieldGuideMode.setNoStroke(true);
		textFieldGuideMode.setText("Guide Mode");
		guideModeButton.addChild(textFieldGuideMode);
		textFieldGuideMode.setPositionRelativeToParent(new Vector3D(buttonWeight/2f, buttonHeight/2f));
		textFieldGuideMode.setPickable(false);
		this.getCanvas().addChild(guideModeButton);
		
		guideModeButton.registerInputProcessor(new TapProcessor(mtApplication));
		guideModeButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						setTransition(slideLeftTransition);
						if (scene1 == null){
							scene1 = new Scene1(mtApp, "Scene 1");
							//Add the scene to the mt application
							mtApp.addScene(scene1);
						}
						//Do the scene change
						mtApp.changeScene(scene1);
						break;
					default:
						break;
					}
				
				return false;
			}
		});	
		
		
		//Create guideModeButton
		practiceModeButton = new MTRoundRectangle(mtApp,0,0,0,buttonWeight,buttonHeight,radius, radius);
		practiceModeButton.setPositionRelativeToParent(new Vector3D(760,500));
		practiceModeButton.setStrokeWeight(5);
		practiceModeButton.setFillColor(new MTColor(254, 201, 71, 255));
		practiceModeButton.setStrokeColor(new MTColor(251, 151, 29, 255));
		practiceModeButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		practiceModeButton.removeAllGestureEventListeners(RotateProcessor.class);
		
		
		MTTextArea textFieldPracticeMode = new MTTextArea(mtApplication, FontManager.getInstance().createFont(mtApp, "arial.ttf", 
				40,new MTColor(251, 151, 29, 255))); 
		textFieldPracticeMode.setNoFill(true);
		textFieldPracticeMode.setNoStroke(true);
		textFieldPracticeMode.setText("Practice Mode");
		practiceModeButton.addChild(textFieldPracticeMode);
		textFieldPracticeMode.setPositionRelativeToParent(new Vector3D(buttonWeight/2f, buttonHeight/2f));
		textFieldPracticeMode.setPickable(false);
		this.getCanvas().addChild(practiceModeButton);
		
		practiceModeButton.registerInputProcessor(new TapProcessor(mtApplication));
		practiceModeButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						setTransition(slideLeftTransition);
						if (workPlaceScene == null){
							workPlaceScene = new WorkPlaceScene(mtApp, "WorkPlaceScene");
							mtApp.addScene(workPlaceScene);
						}
						//Do the scene change
						mtApp.changeScene(workPlaceScene);
						break;
					default:
						break;
					}
				
				return false;
			}
		});	
		
		
		//Set a scene transition - Flip transition only available using opengl supporting the FBO extenstion
		if (MT4jSettings.getInstance().isOpenGlMode() && GLFBO.isSupported(mtApp)){
			slideLeftTransition = new SlideTransition(mtApp, 700, true);
		}else{
			this.setTransition(new FadeTransition(mtApp));
		}
		
		
		//Register flick gesture with the canvas to change the scene
		getCanvas().registerInputProcessor(new FlickProcessor());
		getCanvas().addGestureListener(FlickProcessor.class, new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent ge) {
				FlickEvent e = (FlickEvent)ge;
				if (e.getId() == MTGestureEvent.GESTURE_ENDED && e.isFlick()){
					switch (e.getDirection()) {
					case WEST:
					case NORTH_WEST:
					case SOUTH_WEST:
						setTransition(slideLeftTransition);
						if (scene1 == null){
							scene1 = new Scene1(mtApp, "Scene 1");
							//Add the scene to the mt application
							mtApp.addScene(scene1);
						}
						//Do the scene change
						mtApp.changeScene(scene1);
						break;
					default:
						break;
					}
				}
				return false;
			}
		});
	}
}
