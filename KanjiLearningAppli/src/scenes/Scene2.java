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


public class Scene2 extends Scene {

	private MTApplication mtApp;
	
	private ITransition slideLeftTransition;
	private ITransition slideRightTransition;
		
		public Scene2(MTApplication mtApplication, String name) {
			super(mtApplication, name,2);
			this.mtApp = mtApplication;
			
			addGuideText("Combine two\n related radical\n carts together\n to get more\n information",new Vector3D(mtApp.width/4.75f,mtApp.height/2.25f));
			addGuidePicsRectangle(2,new Vector3D(mtApp.width/1.5f,mtApp.height/2.25f));
			
			super.addButtons(2);
			
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
								sceneNext = new Scene3(mtApp, "Scene 3");
								mtApp.addScene(sceneNext);
							}
							//Do the scene change
							mtApp.changeScene(sceneNext);
							break;
						case EAST:
						case NORTH_EAST:
						case SOUTH_EAST:
							setTransition(slideRightTransition); 
							if(mtApp.getScene("SAT HomePage") == null){
								scenePrevious = new Scene1(mtApp, "Scene 1");
								mtApp.addScene(scenePrevious);
								mtApp.changeScene(scenePrevious);
							}else{
								mtApp.changeScene(mtApp.getScene("Scene 1"));
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
