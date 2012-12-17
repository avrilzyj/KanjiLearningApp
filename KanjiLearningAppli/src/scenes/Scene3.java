package scenes;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickProcessor;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.sceneManagement.Iscene;
import org.mt4j.sceneManagement.transition.FadeTransition;
import org.mt4j.sceneManagement.transition.ITransition;
import org.mt4j.sceneManagement.transition.SlideTransition;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;


public class Scene3 extends Scene{
	private MTApplication mtApp;
	
	private ITransition slideLeftTransition;
	private ITransition slideRightTransition;
		
		public Scene3(MTApplication mtApplication, String name) {
			super(mtApplication, name,3);
			this.mtApp = mtApplication;
			
			addGuidePicsRectangle(3,new Vector3D(mtApp.width/3f,mtApp.height/2.25f));
			addGuideText("Tap once\n on infomation\n windows to\n get next one",new Vector3D(mtApp.width/1.285f,mtApp.height/2.25f));
			
			super.addButtons(3);
			
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
							if (sceneNext == null){
								sceneNext = new Scene4(mtApp, "Scene 4");
								mtApp.addScene(sceneNext);
							}
							//Do the scene change
							mtApp.changeScene(sceneNext);
							break;
						case EAST:
						case NORTH_EAST:
						case SOUTH_EAST:
							setTransition(slideRightTransition); 
							//mtApp.popScene();
							if(mtApp.getScene("Scene 2") == null){
								scenePrevious = new Scene2(mtApp, "Scene 2");
								mtApp.addScene(scenePrevious);
								mtApp.changeScene(scenePrevious);
							}else{
								mtApp.changeScene(mtApp.getScene("Scene 2"));
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



