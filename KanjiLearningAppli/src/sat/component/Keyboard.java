package sat.component;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.widgets.keyboard.MTKeyboard;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

public class Keyboard extends MTKeyboard{
	private MTApplication app;

	public KeyboardTextField m_textField;
	
	private KeyboardTabArray tabList;	

	
	public KeyboardTextField getTextField() {
		return m_textField;
	}

	static final float tbHeight = 245; //height of keyboard
	static final float tbWidth = 700; //height of keyboard
	
	public KeyboardTabArray getTabArray(){
		return tabList;
	}
	
	
	public Keyboard(MTApplication mtApplication, KeyboardTextField textField) {
		super(mtApplication);
		
		app=mtApplication;
		m_textField = textField;
		this.setFillColor(new MTColor(30, 30, 30, 210));

		this.setStrokeColor(new MTColor(0,0,0,255));

		this.setPositionGlobal(new Vector3D((app.width)/2-tbWidth/2,(app.height)/2-tbHeight/2,20));
		
		tabList = new KeyboardTabArray(this.app,this);
		this.addChild(tabList);
		
		// set the tab list position
		tabList.setPositionRelativeToParent(
				new Vector3D(
						KeyboardTabArray.tabListWidth/2+30,
						-KeyboardTabArray.tabListHeight/2
						)
				);

	}
}
