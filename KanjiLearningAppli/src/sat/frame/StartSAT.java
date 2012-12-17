package sat.frame;
 


import org.mt4j.MTApplication;

import scenes.StartScene;
import scenes.WorkPlaceScene;

 
public class StartSAT extends MTApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StartScene  scene; 
	
	public StartScene getScene() {
		return scene;
	}

	public static void main(String[] args) {
		initialize();
	}
 
	@Override
	public void startUp() {

		scene = new StartScene(this, "SAT HomePage");
	    addScene(scene);
	    
		System.out.println("ready to use");
	}
}