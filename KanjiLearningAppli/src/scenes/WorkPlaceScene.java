package scenes;

import java.util.ArrayList;
import java.util.HashMap;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.shapes.MTLine;
import org.mt4j.input.gestureAction.DefaultPanAction;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragEvent;
import org.mt4j.input.inputProcessors.componentProcessors.dragProcessor.DragProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanProcessorTwoFingers;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import clipboard.ImputNewKanji;
import database.dbConnector;
import sat.component.Menu;
import sat.component.PrononciationPractice;
import sat.component.WordCard;


//this class isn't singleton
public class WorkPlaceScene extends AbstractScene {
	/**
	 * 
	 */
	private WordCard wordCard;
	private int cardNum = 10;
	private int randomSize = 0;
	public dbConnector db = null;
	private MTApplication mApp;
	private static WorkPlaceScene instance;
	private PrononciationPractice pronunciationPrc;
	private Boolean oneWordCardShowed = false;


	public ArrayList<String> cardList;
	public ArrayList<WordCard> wordCardList;
	public ArrayList<WordCard> kanjiCardList;
	public HashMap<String,PrononciationPractice> pronunciationPracticeMap;
	
	private Menu menu;
	private Vector3D menuPosition;
	
	private ImputNewKanji imputKanji;
	
	
	//Principal menu's cancel button's listener 
	public void cancelButton(){
		menu.setVisible(false);
	}
	
	//Principal menu's reload button's listener 
	public void reloadButton(){
		this.getCanvas().removeAllChildren();
		menu = new Menu(mApp,instance);
		menu.setPositionGlobal(menuPosition);
		
		this.getCanvas().addChild(menu);
		
		pronunciationPracticeMap = new HashMap<String,PrononciationPractice>();

		cardList = new ArrayList<String>();
		wordCardList = new ArrayList<WordCard>();
		kanjiCardList = new ArrayList<WordCard>();
		
		cardList = GetCardsList();

		for(int i =0; i<cardNum; i++){
			addWordCard(cardList.get(i),new Vector3D((i%5*(WordCard.width+120))+160,i/5*(WordCard.height+120)+70));
		}
		
		for(int i =0; i<cardNum; i++){
			this.addShowTwoRadicalInfoListner(wordCardList.get(i),wordCardList);
			this.addShowOneRadicalInfoListner(wordCardList.get(i));
		}
		
		//update menu position after drag & drop
		menu.addGestureListener(DragProcessor.class,new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent evt) {
				DragEvent de = (DragEvent) evt;
				switch (de.getId()) {
				case MTGestureEvent.GESTURE_STARTED:
					break;
				case MTGestureEvent.GESTURE_UPDATED:
					
					break;
				case MTGestureEvent.GESTURE_ENDED:
					menuPosition = menu.getCenterPointGlobal();
					System.out.print("position: "+menuPosition+"\n");
					break;
				default:
					break;
				}
				return false;
			}

		});
	}
	
	//Principal menu's exit button's listener 
	public void exitButton(){
		System.exit(0);
	}
	
	private void addPrincipalMenu(Vector3D pos){
		menu.setVisible(true);
		menu.setPositionGlobal(pos);
	}
	
	public static WorkPlaceScene getInstance(){
		return instance;
	}

	public void restartColor(){
		for(int i =0; i<wordCardList.size(); i++){
			wordCardList.get(i).setFillColor(new MTColor(254, 201, 71, 255));	
			wordCardList.get(i).setStrokeColor(new MTColor(251, 151, 29, 255));
		}
	}
	
	public void jugeSimilarKanjiChangeColor(String cardNum){
		for(int i =0; i<wordCardList.size(); i++){
			if(db.ChangeColor(cardNum, wordCardList.get(i).getWordCardCode())){
				wordCardList.get(i).setFillColor(new MTColor(251, 151, 29, 255));	
			    wordCardList.get(i).setStrokeColor(new MTColor(254, 201, 71, 255));	
			}
			
		}
	}
	
	
	private ArrayList<String> GetCardsList(){
		ArrayList<String> kanjiList = new ArrayList<String>();
		ArrayList<String> kanjiRandomList = new ArrayList<String>();
		ArrayList<String> radicals = new ArrayList<String>();
		ArrayList<String> cardsList = new ArrayList<String>();
		int random = 0;

		kanjiList = db.getKanjiList();
		randomSize = kanjiList.size();
		for(int x = 0; x<cardNum;x++){
    		do{
    			random = (int) (Math.random()*randomSize);
    		}while(( random<0 || random>=randomSize)||(kanjiRandomList.contains(kanjiList.get(random))));
    		kanjiRandomList.add(kanjiList.get(random));
		}
		for(int i = 0; cardsList.size()<cardNum;i++){					
				radicals = db.getRadicals(kanjiRandomList.get(i));
	           if(!cardsList.contains(radicals.get(0))){
	        	   cardsList.add(radicals.get(0));
	        	   if(!cardsList.contains(radicals.get(1))&&cardsList.size()<cardNum){
		        	   cardsList.add(radicals.get(1));
	        	   }
	           }else if(!cardsList.contains(radicals.get(1))){
	        	   cardsList.add(radicals.get(1));
	           }
		}
				
		return cardsList;
	}
	
	private void showPronunciationPractice(WordCard wordCard,String codeA,String codeB){
		WordCard kanjiWordCard = new WordCard(mApp,instance,"12321W",false);
		
		for(int i =0;i<wordCardList.size(); i++){
			if(wordCardList.get(i).getWordCardCode().equals(codeA)||wordCardList.get(i).getWordCardCode().equals(codeB)){
				wordCardList.get(i).setVisible(false);
			}
		}
		String code = db.GetKanjiCode(codeA, codeB);
		kanjiWordCard = new WordCard(mApp,instance,code,true);
		kanjiWordCard.setPositionRelativeToParent(wordCard.getCenterPointRelativeToParent());
		kanjiWordCard.setHeightLocal(160);
		kanjiWordCard.setWidthLocal(160);
		
		pronunciationPrc = new PrononciationPractice(mApp,this,kanjiWordCard,codeA,codeB);
		pronunciationPracticeMap.put(code, pronunciationPrc);
		pronunciationPracticeMap.get(code).setPositionRelativeToParent(new Vector3D(0,WordCard.height+130));
		pronunciationPracticeMap.get(code).setVisible(true);
		kanjiWordCard.addChild(pronunciationPrc);
		kanjiCardList.add(kanjiWordCard);
		cardList.add(code);
		instance.getCanvas().addChild(kanjiWordCard);
	}
	
	private void showOneWordPronunciationPractice(WordCard wordCard){
		 pronunciationPrc = new PrononciationPractice(mApp,instance,wordCard,wordCard.getWordCardCode(),"");
		 pronunciationPracticeMap.remove(wordCard.getWordCardCode());
		 pronunciationPracticeMap.put(wordCard.getWordCardCode(), pronunciationPrc);
		 pronunciationPracticeMap.get(wordCard.getWordCardCode()).setPositionRelativeToParent(new Vector3D(0,WordCard.height+130));
		 pronunciationPracticeMap.get(wordCard.getWordCardCode()).setVisible(true);
		 wordCard.addChild(pronunciationPracticeMap.get(wordCard.getWordCardCode()));
		 wordCard.setHeightLocal(160);
		 wordCard.setWidthLocal(160);

	}
	
	public void refreshPronunciationPractice(){

		for(int i =0; i<(wordCardList.size()+kanjiCardList.size()); i++){
			pronunciationPracticeMap.get(cardList.get(i)).setVisible(false);
		}
		for(int i =0; i<wordCardList.size(); i++){
			wordCardList.get(i).setVisible(true);
			wordCardList.get(i).setHeightLocal(60);
			wordCardList.get(i).setWidthLocal(60);
		}
		for(int i =0; i<kanjiCardList.size(); i++){
			kanjiCardList.get(i).setVisible(false);
		}
	}
	
	private WordCard calculateMinDistance(ArrayList<WordCard> wordCardList,WordCard wordCard){
		float tempDis = 0;
		float minDis = 999;
		WordCard minWordCard = new WordCard(mApp,this,"12321W",false);
		for(int i =0; i<wordCardList.size(); i++){
			if(!wordCardList.get(i).getWordCardCode().equals(wordCard.getWordCardCode())){
				tempDis = Math.abs(wordCard.getCenterPointRelativeToParent().distance(wordCardList.get(i).getCenterPointRelativeToParent()));
				if(tempDis<minDis){
					minDis = tempDis;
					minWordCard.setWordCardCode(wordCardList.get(i).getWordCardCode());
					minWordCard.setPositionRelativeToParent(wordCardList.get(i).getCenterPointRelativeToParent());
				}
			}
		}
		return minWordCard;
	}
	

	private void addShowTwoRadicalInfoListner(final WordCard wordCard,final ArrayList<WordCard> wordCardList){
		wordCard.addGestureListener(DragProcessor.class,new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent evt) {
				WordCard minWordCard = new WordCard(mApp,instance,"12321W",false);
				boolean distance = false;
				boolean correlation = false;
				DragEvent de = (DragEvent) evt;
				switch (de.getId()) {
				case MTGestureEvent.GESTURE_STARTED:
					refreshPronunciationPractice();	
					break;
				case MTGestureEvent.GESTURE_ENDED:
					minWordCard = calculateMinDistance(wordCardList,wordCard);
					distance = Math.abs(wordCard.getCenterPointRelativeToParent().distance(minWordCard.getCenterPointRelativeToParent()))<=(WordCard.width+5);

					correlation = db.ChangeColor(wordCard.getWordCardCode(),minWordCard.getWordCardCode());	

					if(distance && correlation){
						showPronunciationPractice(wordCard,wordCard.getWordCardCode(),minWordCard.getWordCardCode());
					}else{
						//don't refresh, if user want to see one radical's info
						if(!oneWordCardShowed){
							refreshPronunciationPractice();	
						}

					}
					for(int i =0; i<wordCardList.size(); i++){
						wordCardList.get(i).setStrokeColor(new MTColor(251, 151, 29, 255));
						wordCardList.get(i).setStrokeWeight(3);			
				}
					break;
				default:
					break;
				}
				return false;
			}

		});
	}
	
	private void addShowOneRadicalInfoListner(final WordCard wordCard){
		wordCard.registerInputProcessor(new TapProcessor(mApp));
		wordCard.addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
					 if(!pronunciationPracticeMap.get(wordCard.getWordCardCode()).IsPronunciationPracticeEmpty()){
						 oneWordCardShowed = true;
						 showOneWordPronunciationPractice(wordCard);
					 }
						break;
					default:
						break;
					}
				
				return false;
			}
		});	
	}
	

	private void addWordCard(String code,Vector3D pos){
		wordCard = new WordCard(mApp,this,code,false);
		pronunciationPrc = new PrononciationPractice(mApp,this,wordCard,code,"");
		pronunciationPracticeMap.put(code, pronunciationPrc);
		pronunciationPrc.setVisible(false);
		wordCard.addChild(pronunciationPrc);
		wordCard.setPositionRelativeToParent(pos);
		this.getCanvas().addChild(wordCard);
		wordCardList.add(wordCard);
	}



	public WorkPlaceScene(final MTApplication mtApplication, final String name) {
		super(mtApplication, name);
		instance = this;
		mApp = mtApplication;
		
		db = new dbConnector();
		imputKanji= new ImputNewKanji(db);
		imputKanji.clearClipboard();
		
		this.getCanvas().registerInputProcessor(new PanProcessorTwoFingers(mtApplication));
	    this.getCanvas().addGestureListener(PanProcessorTwoFingers.class, new DefaultPanAction());
		
		
		this.getCanvas().setFrustumCulling(false);
		this.setClearColor(new MTColor(222, 237, 231, 255));
		this.registerGlobalInputProcessor(new CursorTracer(mtApplication, this)); // show touch point
		
		menu = new Menu(mApp,instance);
		menu.setVisible(false);
		//update menu position after drag & drop
		menu.addGestureListener(DragProcessor.class,new IGestureEventListener() {
			public boolean processGestureEvent(MTGestureEvent evt) {
				DragEvent de = (DragEvent) evt;
				switch (de.getId()) {
				case MTGestureEvent.GESTURE_STARTED:
					break;
				case MTGestureEvent.GESTURE_UPDATED:
					
					break;
				case MTGestureEvent.GESTURE_ENDED:
					menuPosition = menu.getCenterPointGlobal();
					System.out.print("position: "+menuPosition+"\n");
					break;
				default:
					break;
				}
				return false;
			}

		});
		
		
		menuPosition = new Vector3D(0,0);

		
		pronunciationPracticeMap = new HashMap<String,PrononciationPractice>();

		cardList = new ArrayList<String>();
		wordCardList = new ArrayList<WordCard>();
		kanjiCardList = new ArrayList<WordCard>();
		
		
/*
		
		//test all case
		cardList.add("12582A");
		cardList.add("14461W");
		cardList.add("20009W");
		cardList.add("18044W");
		cardList.add("12331A");
		cardList.add("12861W");
		cardList.add("12883W");
		cardList.add("17741W");
		cardList.add("12904A");
		cardList.add("12904B");
		cardList.add("19772W");
		cardList.add("19278W");
		cardList.add("17754W");
		cardList.add("18495W");
		cardList.add("12880W");
		cardList.add("17732W");
		cardList.add("12344A");
		cardList.add("20260W");
		
	*/	
		
		
		cardList = GetCardsList();

		
		
		for(int i =0; i<cardNum; i++){
			addWordCard(cardList.get(i),new Vector3D((i%5*(WordCard.width+120))+160,i/5*(WordCard.height+120)+70));
		}
		
		for(int i =0; i<cardNum; i++){
			this.addShowTwoRadicalInfoListner(wordCardList.get(i),wordCardList);
			this.addShowOneRadicalInfoListner(wordCardList.get(i));
		}
		
	
		MTLine l = new MTLine(mtApplication, 12, 12, 123, 123);
		l.setStrokeColor(MTColor.BLACK);
		l.setStrokeWeight(10);
		
		
		//add menu
		this.getCanvas().addChild(menu);
		
		
		//import a new word through clipboard
		this.getCanvas().registerInputProcessor(new TapProcessor(mtApplication));
		this.getCanvas().addGestureListener(TapProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapEvent te = (TapEvent)ge;
					switch (te.getTapID()) {
					case TapEvent.TAPPED:
						System.out.println("Tapped");
						imputKanji= new ImputNewKanji(db);
						if(imputKanji.IsExist()){
							if(imputKanji.getRadicals().size()==2){
								if(!cardList.contains(imputKanji.getRadicals().get(0))){
									addWordCard(imputKanji.getRadicals().get(0),te.getLocationOnScreen());	
									cardList.add(imputKanji.getRadicals().get(0));
									cardNum = cardNum +1;
									instance.addShowTwoRadicalInfoListner(wordCardList.get(cardNum-1),wordCardList);
									instance.addShowOneRadicalInfoListner(wordCardList.get(cardNum-1));
									if(!cardList.contains(imputKanji.getRadicals().get(1))){
										addWordCard(imputKanji.getRadicals().get(1),(te.getLocationOnScreen().addLocal(new Vector3D(WordCard.width+30,0))));
									    cardNum = cardNum +1;
										cardList.add(imputKanji.getRadicals().get(1));
										instance.addShowTwoRadicalInfoListner(wordCardList.get(cardNum-1),wordCardList);
										instance.addShowOneRadicalInfoListner(wordCardList.get(cardNum-1));
									}
								}else{
									if(!cardList.contains(imputKanji.getRadicals().get(1))){
										addWordCard(imputKanji.getRadicals().get(1),te.getLocationOnScreen());	
										cardNum = cardNum +1;
										cardList.add(imputKanji.getRadicals().get(1));
										instance.addShowTwoRadicalInfoListner(wordCardList.get(cardNum-1),wordCardList);
										instance.addShowOneRadicalInfoListner(wordCardList.get(cardNum-1));
									}							
								}							
							}else if(imputKanji.getRadicals().size()==1){
								if(!cardList.contains(imputKanji.getRadicals().get(0))){
									addWordCard(imputKanji.getRadicals().get(0),te.getLocationOnScreen());	
									cardList.add(imputKanji.getRadicals().get(0));
									cardNum = cardNum +1;
									instance.addShowTwoRadicalInfoListner(wordCardList.get(cardNum-1),wordCardList);
									instance.addShowOneRadicalInfoListner(wordCardList.get(cardNum-1));
							}
						
						}
						}

						break;
					default:
						break;
					}
				
				return false;
			}
		});		
		
		
		//add principal menu
		this.getCanvas().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApplication, instance.getCanvas()));
		this.getCanvas().registerInputProcessor(new TapAndHoldProcessor(mtApplication,750));
		this.getCanvas().addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				TapAndHoldEvent e = (TapAndHoldEvent)ge;
				if (e.getId() == TapAndHoldEvent.GESTURE_STARTED){
					WorkPlaceScene.getInstance().restartColor();
				}
				if (e.getId() == TapAndHoldEvent.GESTURE_ENDED && e.isHoldComplete()){
					e.stopPropagation();
					menuPosition = e.getLocationOnScreen();
					addPrincipalMenu(menuPosition);
				}
				return false;
			}
		});
		
		

	}

	public void init() { }

	public void shutDown() {}
}
