import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.DecimalFormat;

/*
 * This Java code is designed to predicting ISGs.
 * 
 * Citation: Haiting Chai, Quan Gu, Joseph Hughes and David L. Robertson (202X), Defining the characteristics of interferon-alpha-stimulated human genes: insight from expression data and machine-learning, XXXX, XX(X): XXX-XXX, PMID: XXXXXXXX.
 * 
 * */

public class ISGPRE_2_predicting {
	// Path of the main folder
	static String topPath = "/Users/imac/Documents/ISGPRE/data/";
	static String exePath = topPath + "source/svm-predict";
	static String modelPath = topPath + "source/ISGPRE.model.txt";
	static String testPath = topPath + "test.SVM.txt";
	static String outPath = topPath + "out.temp.txt";
	static String listPath = topPath + "re.list.txt";
	static String resPath = topPath + "out.SVM.txt";
	
	static int readpointer;
	static String cmd;
	
	static String list[] = new String[100000];	
	
	static DecimalFormat decimalFormat = new DecimalFormat("0.0000");
	
	public static void readlist(String Path){
		readpointer = 1;
		try {
			String encoding = "UTF-8";
            File listfile=new File(Path);
            if(listfile.isFile() && listfile.exists()){ 
            	InputStreamReader read = new InputStreamReader(
                new FileInputStream(listfile),encoding);                    
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "N/A";
                while((lineTxt = bufferedReader.readLine()) != null){
                		list[readpointer] = lineTxt;          		
                		readpointer = readpointer + 1;       		
                }               
                list[0] = readpointer - 1 + "";
                read.close();
            }else{
            	System.out.println("List missing!");
            }
        } catch (Exception e) {
            System.out.println("List Read error!");
            e.printStackTrace();
        }
    }
	
	public static void readout(String Path){
		String[] Recordcut;
		String str;
		double score;
		readpointer = 1;
		try {
			String encoding = "UTF-8";
            File listfile=new File(Path);
            if(listfile.isFile() && listfile.exists()){ 
            	InputStreamReader read = new InputStreamReader(
                new FileInputStream(listfile),encoding);                    
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "N/A";
                lineTxt = bufferedReader.readLine();
                write(resPath,"Gene\tPrediction_score\tPrediction_label(threshold=0.549)\tPrediction_label(threshold=0.5)\n");
                while((lineTxt = bufferedReader.readLine()) != null){
                		Recordcut = lineTxt.split("\\s{1,}");
                		score = Double.parseDouble(Recordcut[1]);
                		str = list[readpointer] + "\t" + decimalFormat.format(score);
                		if(score>0.549) {
                			str = str + "\tISG";
                		}else {
                			str = str + "\tNon-ISG";
                		}
                		if(score>0.5) {
                			str = str + "\tISG";
                		}else {
                			str = str + "\tNon-ISG";
                		}
                		write(resPath,str+"\n");     		
                		readpointer = readpointer + 1;       		
                }               
                read.close();
            }else{
            	System.out.println("Out missing!");
            }
        } catch (Exception e) {
            System.out.println("Out Read error!");
            e.printStackTrace();
        }
    }
	
	public static void runcommand(String command) {
		try {
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void wait(String TagPath, int Sleeptiime) {
		File Targetfile = new File(TagPath);
		while(true) {
			if(Targetfile.exists() && Targetfile.length()>0) {
				break;
			}else {
				try {
					Thread.sleep(Sleeptiime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void write(String Path, String textstr){
		try {
			File textfile = new File(Path);	
			Writer output = null;
			output = new FileWriter(textfile,true);
			output.write(textstr);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deletetemp(String TagPath) {
		File Targetfile = new File(TagPath);
		if(Targetfile.exists()) {
			Targetfile.delete();
		}
	}
	
	public static void main(String argv[]){ 
		readlist(listPath);
		cmd = exePath + " -b 1 " + testPath + " " + modelPath + " " + outPath;
		runcommand(cmd);
		wait(outPath,5000);
		readout(outPath);
		deletetemp(testPath);
		deletetemp(outPath);
	}
}
