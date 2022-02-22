import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;

/*
 * This Java code is designed to map RefSeq accession number to Ensembl gene ID.
 * Citation: Haiting Chai, Quan Gu, Joseph Hughes and David L. Robertson (202X), Defining the characteristics of interferon-alpha-stimulated human genes: insight from expression data and machine-learning, XXXX, XX(X): XXX-XXX, PMID: XXXXXXXX.
 * 
 * */

public class RefSeq_to_Ensembl {
	// Path of the main folder
	static String topPath = "/Users/imac/Documents/ISGPRE/data/";
	static String geneList = topPath + "Other_identifiers.txt";
	static String mapPath = topPath + "source/Mapping_source.txt";
	static String errorlist =  topPath + "unmapped.list.txt";
	static String resPath = topPath + "GeneList.txt";
	
	static int readpointer;
	static int temporarypointer;
	
	static String info[][] = new String[50000][10];
	
	public static void readmap(String Path){
		String[] Recordcut;
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
                while((lineTxt = bufferedReader.readLine()) != null){
                		Recordcut = lineTxt.split("\\s{1,}");
                		info[readpointer][0] = Recordcut[6];
                		info[readpointer][1] = Recordcut[1];
                		info[readpointer][2] = Recordcut[4];         		
                		readpointer = readpointer + 1;       		
                }               
                info[0][0] = readpointer - 1 + "";
                read.close();
            }else{
            	System.out.println("MAP missing!");
            }
        } catch (Exception e) {
            System.out.println("MAP Read error!");
            e.printStackTrace();
        }
    }
	
	public static void readlist(String Path){
		int key = 0;
		try {
			String encoding = "UTF-8";
            File listfile=new File(Path);
            if(listfile.isFile() && listfile.exists()){ 
            	InputStreamReader read = new InputStreamReader(
                new FileInputStream(listfile),encoding);                    
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = "N/A";
                while((lineTxt = bufferedReader.readLine()) != null){
                		key = 0;
                		for(readpointer=1;readpointer<Integer.parseInt(info[0][0])+1;readpointer++) {
            				if(info[readpointer][1].equals(lineTxt.trim())) {
            					key = 1;
            					write(resPath,info[readpointer][0]+"\n");
            					break;
            				}
            			}                 		
                		
                		if(key == 0) {
                			write(errorlist,lineTxt+"\n");
                		}
                }               
                read.close();
            }else{
            	System.out.println("List missing!");
            }
        } catch (Exception e) {
            System.out.println("List Read error!");
            e.printStackTrace();
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
		deletetemp(resPath);
		readmap(mapPath);
		readlist(geneList);	
	}
		
}
