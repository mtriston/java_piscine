javac  -cp lib/jcommander-1.81.jar:lib/JCDP-4.0.2.jar -d target src/java/edu/school21/printer/app/Program.java src/java/edu/school21/printer/logic/ImageConverter.java

cd target || exit
jar xf ../lib/jcommander-1.81.jar com
jar xf ../lib/JCDP-4.0.2.jar com
cd ..

cp -a src/resources target
jar cfmv target/images-to-chars-printer.jar src/manifest.txt -C target .

java -jar target/images-to-chars-printer.jar --white=RED --black=PINK