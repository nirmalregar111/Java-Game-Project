@echo off
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin -sourcepath src src\game\Main.java src\game\audio\*.java src\game\ui\*.java src\game\rooms\*.java src\game\model\*.java src\game\engine\*.java src\game\sprites\*.java
pause
