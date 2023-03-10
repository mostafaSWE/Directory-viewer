import java.io.File;



public class outputWindow{
	private static String outPut="";
	public  outputWindow(){
		outPut="";
	}
	

	protected String showFiles(File[] files, int level) {

    	for (File file : files) {

            if (file.isDirectory()) {
            	
            	long kbytes = folderSize(file)/1024;
            	outPut=""+outPut+("    ".repeat(level) + file.getName()+ " ("+kbytes+" KB) \n");
            	
            	
                showFiles(file.listFiles(), level+1); // Calls same method again.
                
            }else {
            	long kbytes = file.length()/1024;
            	outPut=""+outPut+ ("    ".repeat(level)+"-" + file.getName()+ " ("+kbytes+ " KB)\n");

            }
            	}
    	return outPut;

 
    }
	private static long folderSize(File directory) {
    long length = 0;
    for (File file : directory.listFiles()) {
        if (file.isFile())
            length += file.length();
        else
            length += folderSize(file);
    }
    return length;
}


}
