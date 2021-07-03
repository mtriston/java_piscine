#Download libs
wget -P lib/ https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar
wget -P lib/ https://repo1.maven.org/maven2/com/beust/jcommander/1.81/jcommander-1.81.jar

#Compile
javac  -cp lib/jcommander-1.81.jar:lib/JCDP-4.0.2.jar -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageConverter.java

#Extract libs to target
cd target
jar xf ../lib/jcommander-1.81.jar com
jar xf ../lib/JCDP-4.0.2.jar com
cd ..

#Copy resources to target
cp -a src/resources target

#Create jar
jar cfmv target/images-to-chars-printer.jar src/manifest.txt -C target .

#Execute program
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN