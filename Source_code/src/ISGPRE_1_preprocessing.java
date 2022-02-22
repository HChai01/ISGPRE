import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;

/*
 * This Java code is designed to check if the data about your genes are compiled in our project.
 * Here we only accept three types of gene identifiers including HGNC_ID (e.g., 'HGNC:11858'), and NCBI_gene_ID (e.g., 7105) and Ensemble_gene_ID (e.g., ENSG00000000003).
 * Please use our mapping programs first if you are using identifiers like apporved_gene_symbol, RefSeq_accession, etc.
 * 
 * Citation: Haiting Chai, Quan Gu, Joseph Hughes and David L. Robertson (202X), Defining the characteristics of interferon-alpha-stimulated human genes: insight from expression data and machine-learning, XXXX, XX(X): XXX-XXX, PMID: XXXXXXXX.
 * 
 * */

public class ISGPRE_1_preprocessing {
	// Path of the main folder
	static String topPath = "/Users/imac/Documents/ISGPRE/data/";
	
	static String geneList = topPath + "GeneList.txt";
	static String featurePath = topPath + "source/Feature_vectors(74).txt";
	static String relist =  topPath + "re.list.txt";
	static String errorlist =  topPath + "error.list.txt";
	static String resPath = topPath + "test.SVM.txt";
	
	static int readpointer;
	static int temporarypointer;
	
	static String featureinfo[][] = new String[50000][10];	
	
	public static void readfeature(String Path){
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
                		Recordcut = lineTxt.split("\\s{1,}",4);
                		featureinfo[readpointer][0] = Recordcut[0];
                		featureinfo[readpointer][1] = Recordcut[1];
                		featureinfo[readpointer][2] = Recordcut[2];
                		featureinfo[readpointer][3] = Recordcut[3];            		
                		readpointer = readpointer + 1;       		
                }               
                featureinfo[0][0] = readpointer - 1 + "";
                read.close();
            }else{
            	System.out.println("Feature missing!");
            }
        } catch (Exception e) {
            System.out.println("Feature Read error!");
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
                		if(lineTxt.contains("HGNC:")) {
                			for(readpointer=1;readpointer<Integer.parseInt(featureinfo[0][0])+1;readpointer++) {
                				if(featureinfo[readpointer][0].equals(lineTxt.trim())) {
                					key = 1;
                					write(relist,lineTxt.trim()+"\n");
                					write(resPath,"5\t"+featureinfo[readpointer][3]+"\n");
                					break;
                				}
                			}
                		}                 		
                		if(lineTxt.matches("[0-9]+\\.?[0-9]*")) {
                			for(readpointer=1;readpointer<Integer.parseInt(featureinfo[0][0])+1;readpointer++) {
                				if(featureinfo[readpointer][1].equals(lineTxt.trim())) {
                					key = 1;
                					write(relist,lineTxt.trim()+"\n");
                					write(resPath,"5\t"+featureinfo[readpointer][3]+"\n");
                					break;
                				}
                			}
                		}
                		if(lineTxt.contains("ENSG0000")) {
                			for(readpointer=1;readpointer<Integer.parseInt(featureinfo[0][0])+1;readpointer++) {
                				if(featureinfo[readpointer][2].equals(lineTxt.trim())) {
                					key = 1;
                					write(relist,lineTxt.trim()+"\n");
                					write(resPath,"5\t"+featureinfo[readpointer][3]+"\n");
                					break;
                				}
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
	
	public static void main(String argv[]){
		readfeature(featurePath);
		readlist(geneList);
	}
}
