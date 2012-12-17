package sat.component;

import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

public class KeyboardTabArray extends MTRectangle{
	private MTApplication app;
	private Keyboard keyboard;
	private static final int tabNb = 2;
	
	static final float tabListWidth = 100;	//width of keyboard and tabList

	static final float tabListHeight = 40;	//height of tabList
	
	private static final float tabWidth = tabListWidth/tabNb;
	

	public void addChildLinearAlign(KeyboardTab cmp){ // add a tab in line
		float n = this.getChildCount();
		if(n != 0){
			cmp.setPositionRelativeToParent(new Vector3D(
					tabWidth*(0.5f+n),
					cmp.getPosition(TransformSpace.RELATIVE_TO_PARENT).y
					));
		}
		this.addChild(cmp);
	}
	
	public void allInLine(){
		for (int i = 0; i < this.getChildCount(); i++) {
			KeyboardTab temp = (KeyboardTab) this.getChildByIndex(i);
			temp.inLine();
		}
	}
	
	public KeyboardTabArray(MTApplication mtApplication,Keyboard kb) {
		super(mtApplication, 0, 0, 0, tabListWidth, tabListHeight);
		this.app = mtApplication;
		this.keyboard = kb;
		this.setFillColor(MTColor.GRAY);
		this.setStrokeColor(MTColor.BLACK);
		this.unregisterAllInputProcessors(); //Remove the default drag, rotate and scale gestures first

		
		KeyboardTab tab0 = new KeyboardTab(app,keyboard,tabWidth,tabListHeight-1,"ин");
		KeyboardTab tab1 = new KeyboardTab(app,keyboard,tabWidth,tabListHeight-1,"и▒");


		this.addChildLinearAlign(tab0);
		this.addChildLinearAlign(tab1);
	}
}
