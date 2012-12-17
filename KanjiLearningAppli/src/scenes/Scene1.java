package scenes;


import org.mt4j.MTApplication;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickEvent;
import org.mt4j.input.inputProcessors.componentProcessors.flickProcessor.FlickProcessor;
import org.mt4j.sceneManagement.transition.FadeTransition;
import org.mt4j.sceneManagement.transition.ITransition;
import org.mt4j.sceneManagement.transition.SlideTransition;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.math.Vector3D;
import org.mt4j.util.opengl.GLFBO;


public class Scene1 extends Scene{
	private MTApplication mtApp;
	


	private ITransition slideLeftTransition;
	private ITransition slideRightTransition;

	
	public Scene1(MTApplication mtApplication, String name) {
		super(mtApplication, name,1);
		this.mtApp = mtApplication;
		
		addGuideText("Tap & hold to show related radicals",new Vector3D(mtApp.width/2f,mtApp.height/10f));
		addGuidePicsRectangle(1,new Vector3D(mtApp.width/2f,mtApp.height/2.15f));

		
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
							sceneNext = new Scene2(mtApp, "Scene 2");
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
							scenePrevious = new StartScene(mtApp, "SAT HomePage");
							mtApp.addScene(scenePrevious);
							mtApp.changeScene(scenePrevious);
						}else{
							mtApp.changeScene(mtApp.getScene("SAT HomePage"));
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
