package scenes;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
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

public class Scene6 extends Scene{
	private MTApplication mtApp;
	private WorkPlaceScene workPlaceScene;
	
	private ITransition slideLeftTransition;
	private ITransition slideRightTransition;
	
	private MTRoundRectangle startButton;
	private static final float radius = 20;
	
		
		public Scene6(MTApplication mtApplication, String name) {
			super(mtApplication, name,6);
			this.mtApp = mtApplication;
			
			//Create a textfield
			MTTextArea textField = new MTTextArea(mtApplication, FontManager.getInstance().createFont(mtApp, "arial.ttf", 
					50, new MTColor(251, 151, 29, 255))); 
			textField.setNoFill(true);
			textField.setNoStroke(true);
			textField.setText("Ready?");
			this.getCanvas().addChild(textField);
			textField.setPositionGlobal(new Vector3D(mtApp.width/2f, mtApp.height/4f));
			textField.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
			textField.removeAllGestureEventListeners(RotateProcessor.class);
			
			
			startButton = new MTRoundRectangle(mtApp, 0, 0, 0, 200, 150 ,radius, radius);
			startButton.setPositionRelativeToParent(new Vector3D(mtApp.width/2f, mtApp.height/2f));
			startButton.setStrokeWeight(5);
			startButton.setFillColor(new MTColor(254, 201, 71, 255));
			startButton.setStrokeColor(new MTColor(251, 151, 29, 255));
			startButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
			startButton.removeAllGestureEventListeners(RotateProcessor.class);
			
			MTTextArea startButtonTextField = new MTTextArea(mtApplication, FontManager.getInstance().createFont(mtApp, "arial.ttf", 
					50, new MTColor(251, 151, 29, 255))); 
			startButtonTextField.setNoFill(true);
			startButtonTextField.setNoStroke(true);
			startButtonTextField.setText("Start");
			startButton.addChild(startButtonTextField);
			startButtonTextField.setPositionGlobal(new Vector3D(mtApp.width/2f, mtApp.height/2f));
			startButtonTextField.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
			startButtonTextField.removeAllGestureEventListeners(RotateProcessor.class);
			
			this.getCanvas().addChild(startButton);

			startButtonTextField.registerInputProcessor(new TapProcessor(mtApplication));
			startButtonTextField.addGestureListener(TapProcessor.class, new IGestureEventListener() {
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
			
			super.addButtons(6);
			
			//Set a scene transition - Flip transition only available using opengl supporting the FBO extenstion
			if (MT4jSettings.getInstance().isOpenGlMode() && GLFBO.isSupported(mtApp)){
				slideLeftTransition = new SlideTransition(mtApp, 700, true);
				slideRightTransition = new SlideTransition(mtApp, 700, false);
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
							if (workPlaceScene == null){
								workPlaceScene = new WorkPlaceScene(mtApp, "WorkPlaceScene");
								mtApp.addScene(workPlaceScene);
							}
							//Do the scene change
							mtApp.changeScene(workPlaceScene);
							break;
						case EAST:
						case NORTH_EAST:
						case SOUTH_EAST:
							setTransition(slideRightTransition); 
							if(mtApp.getScene("Scene 5") == null){
								scenePrevious = new Scene5(mtApp, "Scene 5");
								mtApp.addScene(scenePrevious);
								mtApp.changeScene(scenePrevious);
							}else{
								mtApp.changeScene(mtApp.getScene("Scene 5"));
							}
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