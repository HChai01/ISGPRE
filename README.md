# ISGPRE
This is the Java implementation for running the ISGPRE program on the MAX OS. 

Our codes require support from [Java Platform and Standard Edition Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#jdk17-mac).

There are six steps to use the ISGPRE:

1) install a JDK environment on your Mac;
2) download and unzip 'ISGPRE_compiled.zip';
3) paste your gene ids to 'GeneList.txt' in the 'ISGPRE_compiled' folder;
4) ues 'Terminal' to the directory of 'ISGPRE_compiled' (e.g., `cd Download/ISGPRE_compiled`);
5) preprocessing your input by running `java ISGPRE_1_preprocessing`;
6) make ISG predictions for your genes by running `java ISGPRE_2_predicting`


# Web server
Our web server for the ISGPRE project is accessible at: http://isgpre.cvr.gla.ac.uk/.

# Citation
Chai H, Gu Q, Hughes J, Robertson DL (2022) Defining the characteristics of interferon-alpha-stimulated human genes: insight from expression data and machine-learning. GigaScience XX(X) XXXXXX.
