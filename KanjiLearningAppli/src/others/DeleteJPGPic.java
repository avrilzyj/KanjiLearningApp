package others;

import java.io.File;

public class DeleteJPGPic {
	public static void main(String args[]) {
		String path = "C:\\Users\\÷Ï”Óº—\\resources\\StrokeImage";
		
        File file = new File(path);  
        if (!file.exists()) {  
            return;  
        }  
        if (!file.isDirectory()) {  
            return;  
        }  
        
 
        String[] tempList = file.list();  
        File temp = null;  
        for (int i = 0; i < tempList.length; i++) {  
            if (path.endsWith(File.separator)) {  
                temp = new File(path + tempList[i]);  
            } else {  
                temp = new File(path + File.separator + tempList[i]);  
            }  
            
           
            
            
            if (temp.isFile() && temp.getPath().endsWith(".jpg") ) {  
                temp.delete();  
            }  
         
        } 
	}
}
