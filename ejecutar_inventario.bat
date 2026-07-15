@echo off
title Inventario y Ventas (Enterprise Edition)
cd /d "%~dp0\InventarioVentas"

echo [1/3] Preparando el entorno profesional...
if not exist "lib" mkdir lib
if not exist "lib\flatlaf-3.4.jar" powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4/flatlaf-3.4.jar' -OutFile 'lib\flatlaf-3.4.jar'"
:: Descargar SQLite JDBC
if not exist "lib\sqlite-jdbc-3.45.1.0.jar" powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.45.1.0/sqlite-jdbc-3.45.1.0.jar' -OutFile 'lib\sqlite-jdbc-3.45.1.0.jar'"
:: Descargar SLF4J (requerido por las versiones nuevas de SQLite JDBC)
if not exist "lib\slf4j-api-1.7.36.jar" powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar' -OutFile 'lib\slf4j-api-1.7.36.jar'"
if not exist "lib\slf4j-simple-1.7.36.jar" powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/1.7.36/slf4j-simple-1.7.36.jar' -OutFile 'lib\slf4j-simple-1.7.36.jar'"

echo [2/3] Compilando la nueva arquitectura...
if exist "target\classes" rmdir /s /q "target\classes"
mkdir target\classes
powershell -Command "javac -encoding UTF-8 -cp 'lib\*' -d target\classes (Get-ChildItem -Path 'src\main\java' -Filter '*.java' -Recurse).FullName"

echo [3/3] Iniciando el nuevo sistema...
java -cp "target\classes;lib\*" com.miportafolio.Main
pause
