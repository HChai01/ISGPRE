# ISGPRE
This is the Java implementation for running the ISGPRE program on the mac OS. 

Our codes require support from [Java Platform and Standard Edition Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#jdk17-mac) and [LIBSVM](https://www.csie.ntu.edu.tw/~cjlin/libsvm/).

The ISGPRE program accepts `inputs` of three types of gene identifers including HGNC ID, NCBI gene ID and Ensembl gene ID. It `outputs` prediction scores estimating putative interferon stimulated human genes (ISGs). There are six steps to use the ISGPRE:

1) install a [JDK](https://www.oracle.com/java/technologies/downloads/#jdk17-mac) environment (version 8+) on your Mac;
2) download and unzip [ISGPRE_compiled.zip](https://github.com/HChai01/ISGPRE/blob/main/ISGPRE_compiled.zip);
3) paste your gene IDs to 'GeneList.txt' in the 'ISGPRE_compiled' folder;
4) ues 'Terminal' to the directory of 'ISGPRE_compiled' (e.g., `cd Download/ISGPRE_compiled`);
5) preprocess your inputs by running `java ISGPRE_1_preprocessing`;
6) make ISG predictions for your genes by running `java ISGPRE_2_predicting`

# Mapping identifiers
You need to use some online tools such as [UniProt](https://www.uniprot.org/uploadlists/), [HGNC](https://biomart.genenames.org/martform/#!/default/HGNC?datasets=hgnc_gene_mart) to map your gene identifiers to our accepted ones. 

We also compiled two programs to map approved gene symbols or RefSeq accession numbers to Ensembl gene IDs. You can find them after unzipping 'ISGPRE_compiled.zip'. To use our mapping programs, you just need to paste your indenfiers in 'Other_identifiers.txt' and then go to the 'ISGPRE_compiled' folder to run `java Symbol_to_Ensembl` or `java RefSeq_to_Ensembl` in the Terminal tool.

# Reproduceability
All data required to reproduce our prediction method are released in this Github repository. 

The feature space used for training is constructed based on vectors nomalised from the original feature values. Please read our preprint at [bioRxiv](https://doi.org/10.1101/2021.10.08.463622) for more details.

Our optimal prediction model (ISGPRE.model.txt) can be obtained by train the optimal feature space (Optimal_feature_space.txt) `svm-train -b 1 -t 2 -c 1 -g 0.0135 Optimal_feature_space.txt ISGPRE.model.txt`.

Here are some key parameters for the support vector machine (SVM) training:
1) kernel function: radial basis function;
2) gamma in the kernel function: 0.0135;
3) cost parameter: 1.

# Docker implementation
Our ISGPRE is also available on Docker. The instructions and dockerfile are tested on Ubuntu 22.04. 

Please install the latest version of the [Docker engine](https://www.docker.com/) and run `docker run hello-world` on your 'Terminal' for a simple test.

Here're instructions to use our ISGPRE on docker:
1) download the input example from this [repository](https://github.com/HChai01/ISGPRE/blob/main/docker-test-files); 
2) pull our image from [dockhub](https://hub.docker.com/) by running `docker pull hchai01/isgpre` on your 'Terminal';
3) go to the directory of the example files in your 'Terminal';
4) run docker container in background by the command: `docker run -v `pwd`:/app/inout -i -d isgpre:1.0`; 

# Web server
The web server version of our ISGPRE project is accessible at: http://isgpre.cvr.gla.ac.uk/.
