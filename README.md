# ISGPRE
This is the Java implementation for running the ISGPRE program on the MAX OS. 

Our codes require support from [Java Platform and Standard Edition Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#jdk17-mac) and [LIBSVM](https://www.csie.ntu.edu.tw/~cjlin/libsvm/).

This program accepts inputs of three types of gene identifers including HGNC ID, NCBI gene ID and Ensembl gene ID. It outputs prediction scores estimating putative interferon stimulated human genes (ISGs). There are six steps to use the ISGPRE:

1) install a JDK environment on your Mac;
2) download and unzip 'ISGPRE_compiled.zip';
3) paste your gene IDs to 'GeneList.txt' in the 'ISGPRE_compiled' folder;
4) ues 'Terminal' to the directory of 'ISGPRE_compiled' (e.g., `cd Download/ISGPRE_compiled`);
5) preprocess your inputs by running `java ISGPRE_1_preprocessing`;
6) make ISG predictions for your genes by running `java ISGPRE_2_predicting`

# Mapping identifiers
You need to use some online tools such as [UniProt](https://www.uniprot.org/uploadlists/), [HGNC](https://biomart.genenames.org/martform/#!/default/HGNC?datasets=hgnc_gene_mart) to map your gene identifiers to our accepted ones. 

We also compiled two programs to map approved gene symbols or RefSeq accession numbers to Ensembl gene IDs. You can find them after unzipping 'ISGPRE_compiled.zip'. To use our mapping programs, you just need to paste your indenfiers in 'Other_identifiers.txt' and then go to the 'ISGPRE_compiled' folder to run `java Symbol_to_Ensembl` or `java RefSeq_to_Ensembl` in the Terminal tool.

# Reproduceability
All data required to reproduce our prediction method are released here. Our optimal prediction model (ISGPRE.model.txt) can be obtained by running `svm-train -b 1 -t 2 -c 1 -g 0.0135 Optimal_feature_space.txt ISGPRE.model.txt`.

# Web server
Our web server for the ISGPRE project is accessible at: http://isgpre.cvr.gla.ac.uk/.

# Citation
Chai H, Gu Q, Hughes J, Robertson DL (202X) Defining the characteristics of interferon-alpha-stimulated human genes: insight from expression data and machine-learning. GigaScience XX(X) XXXXXX.
