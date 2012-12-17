package sat.component;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.rotateProcessor.RotateProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import clipboard.ImputNewKanji;

import scenes.WorkPlaceScene;

public class Menu extends MTRectangle{
	public static final float width = 220;
	public static final float height = 220;
	
	private MTApplication mTApp;
	private  WorkPlaceScene workPS;
	
	public Menu(MTApplication mtApplication, WorkPlaceScene workPlaceScene){
		super(mtApplication,width,height);
		mTApp = mtApplication;
		workPS = workPlaceScene;
		this.setFillColor(new MTColor(222, 237, 231, 0));
		this.setNoStroke(true);
		
		Button reloadButton = new Button(mTApp,workPlaceScene);
		Button exitButton = new Button(mTApp,workPlaceScene);
		Button cancelButton = new Button(mTApp,workPlaceScene);
		
		reloadButton.setPositionRelativeToParent(new Vector3D(getCenterPointRelativeToParent().getX(),getCenterPointRelativeToParent().getY()*2/5));
		exitButton.setPositionRelativeToParent(new Vector3D(getCenterPointRelativeToParent().getX()/2,getCenterPointRelativeToParent().getY()*5/4));
		cancelButton.setPositionRelativeToParent(new Vector3D(getCenterPointRelativeToParent().getX()*6/4,getCenterPointRelativeToParent().getY()*5/4));
		
		reloadButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		reloadButton.removeAllGestureEventListeners(RotateProcessor.class);
		exitButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		exitButton.removeAllGestureEventListeners(RotateProcessor.class);
		cancelButton.removeAllGestureEventListeners(DragProcessor.class); // To enable drag & drop
		cancelButton.removeAllGestureEventListeners(RotateProcessor.class);
		
		reloadButton.addTexture("reload");
		exitButton.addTexture("exit");
		cancelButton.addTexture("cancel");
		
		this.addChild(reloadButton);
		this.addChild(exitButton);
		this.addChild(cancelButton);
		
		reloadButton.registerInputProcessor(new TapProcessor(mtApplication));
		reloadButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						workPS.reloadButton();
						break;
					default:
						break;
					}
				
				return false;
			}
		});	
		
		exitButton.registerInputProcessor(new TapProcessor(mtApplication));
		exitButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						workPS.exitButton();
						break;
					default:
						break;
					}
				
				return false;
			}
		});	
		
		cancelButton.registerInputProcessor(new TapProcessor(mtApplication));
		cancelButton.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						workPS.cancelButton();
						break;
					default:
						break;
					}
				
				return false;
			}
		});	
		
	}
}
