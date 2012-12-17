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

public class Scene5 extends Scene{
	private MTApplication mtApp;
	
	private ITransition slideLeftTransition;
	private ITransition slideRightTransition;
		
		public Scene5(MTApplication mtApplication, String name) {
			super(mtApplication, name,5);
			this.mtApp = mtApplication;
			
			addGuidePicsRectangle(5,new Vector3D(mtApp.width/3f,mtApp.height/1.8f));
			addGuideText("Import more kanji characters",new Vector3D(mtApp.width/2f,100));
			addGuideText("by copying kanji from",new Vector3D(610,175));
			addGuideText("outside the software",new Vector3D(650,250));
			addGuideText("and clicking inside",new Vector3D(680,325));
			addGuideText("the software",new Vector3D(750,400));

			super.addButtons(5);
			
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
								sceneNext = new Scene6(mtApp, "Scene 6");
								mtApp.addScene(sceneNext);
							}
							//Do the scene change
							mtApp.changeScene(sceneNext);
							break;
						case EAST:
						case NORTH_EAST:
						case SOUTH_EAST:
							setTransition(slideRightTransition); 
							if(mtApp.getScene("Scene 5") == null){
								scenePrevious = new Scene4(mtApp, "Scene 4");
								mtApp.addScene(scenePrevious);
								mtApp.changeScene(scenePrevious);
							}else{
								mtApp.changeScene(mtApp.getScene("Scene 4"));
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