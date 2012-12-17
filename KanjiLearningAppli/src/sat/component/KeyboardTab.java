package sat.component;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;

public class KeyboardTab extends MTRectangle {

	private MTApplication app;
	private Keyboard keyboard;
	private MTTextField tabName; //name for different keywords

	public void outLine(){
		this.setStrokeColor(MTColor.RED);
		this.setFillColor(MTColor.BLUE);
		tabName.setFontColor(MTColor.WHITE);
	}

	public void inLine(){
		this.setStrokeColor(MTColor.BLACK);
		this.setFillColor(MTColor.WHITE);
		tabName.setFontColor(MTColor.BLACK);
	}

	public KeyboardTab(MTApplication mtApplication,Keyboard kb,float width, float height, final String name) {
		super(mtApplication, 0, 0, 0, width, height);
		this.app = mtApplication;
		this.keyboard = kb;
		this.setFillColor(MTColor.WHITE);
		this.setStrokeColor(MTColor.BLACK);

		
		
		//implement gesture for tab
		this.unregisterAllInputProcessors(); //Remove the default drag, rotate and scale gestures first

		tabName = new MTTextField(mtApplication, 0, 0, this.getWidthXYGlobal(), this.getHeightXYGlobal(), 
				FontManager.getInstance().createFont(mtApplication, "arial.ttf",
						30, // Font size
						new MTColor(0, 0, 0, 255), // Font fill color
						true));

		/*
		 * Since the TextArea is a rectangle we only want to show the text
		 * So we set properties which will hide the rectangle
		 */
		tabName.setNoFill(true);
		tabName.setNoStroke(true);

		/*
		 * Set the Text in this component
		 */
		tabName.setText(name);

		// implement gesture for text 
		tabName.unregisterAllInputProcessors();
		tabName.registerInputProcessor(new TapProcessor(this.app));
		tabName.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				// TODO Auto-generated method stub
				TapEvent te = (TapEvent)ge;

				if(te.isTapDown()){

					outLine();
				}
				else if(te.isTapped()){
					keyboard.getTabArray().allInLine();
					keyboard.getTextField().appendTextKB(name);	//add content in a tab of kb to textarea
				}
				return false;
			}
		});

		this.addChild(tabName);
	}

}
