set classpath=lib/Jaccard.jar;lib/json-simple-1.1.jar;lib/substance-lite.jar;lib/jcommon-1.0.16.jar;lib/jfreechart-1.0.13.jar;lib/jfreechart-1.0.13-experimental.jar;lib/jfreechart-1.0.13-swt.jar;lib/stanford-corenlp-3.6.0.jar;lib/ejml-0.23.jar;lib/stanford-corenlp-3.6.0-models.jar;lib/slf4j-api.jar;lib/jwnl.jar;lib/commons-logging.jar;.;
javac -d . *.java

java -Xmx1000M com.Main

pause