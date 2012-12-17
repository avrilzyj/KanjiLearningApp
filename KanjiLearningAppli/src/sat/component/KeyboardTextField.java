
package sat.component;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.components.visibleComponents.widgets.keyboard.MTKeyboard;

import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;

public class KeyboardTextField extends MTTextField{

	public Keyboard keyboard;

	public KeyboardTextField instance;


	public KeyboardTextField( final MTApplication mtApplication,float width, float height, final PrononciationPractice pp) {

		super(mtApplication, 0, 0, width, height, 
				FontManager.getInstance().createFont(mtApplication, "Comic Sans MS", 15, MTColor.BLACK));

		instance = this;
		

		this.removeAllGestureEventListeners(DragProcessor.class);
		
	
		this.registerInputProcessor(new TapProcessor(mtApplication));
		this.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
				switch (te.getTapID()) {
				case TapEvent.TAPPED:
					if(flagKeyboard)
					{
						instance.flagKeyboard=false;
						System.out.println("KeyBoard Text Field tapped");
						instance.setEnableCaret(true);
						keyboard = new Keyboard(mtApplication, instance);
						keyboard.addTextInputListener(instance);
						keyboard.setVisible(true);
						instance.addChild(keyboard);
						mtApplication.getCurrentScene().getCanvas().addChild(keyboard);	
						if(pp!=null)
							pp.addChild(keyboard);
						
					}
				break;
				default:
					break;
				}
				return false;
			}

		}); 
		
		
		


	}





}
