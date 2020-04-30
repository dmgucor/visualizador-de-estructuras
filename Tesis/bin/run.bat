@echo off
REM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
REM Universidad de los Andes (Bogot� - Colombia)
REM Departamento de Ingenier�a de Sistemas y Computaci�n
REM
REM Proyecto Visualizador Estructuras de Datos
REM Autor: Diana Marcela Gutierrez - Abril-2020
REM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
REM/


REM ---------------------------------------------------------
REM Ejecucion del programa
REM ---------------------------------------------------------
cd ..
java -cp "./jar_files/jgrapht-core-1.1.0.jar;./jar_files/jgrapht-ext-1.1.0.jar;./jar_files/jgrapht-io-1.1.0.jar;./jar_files/jgraphx-2.0.0.1.jar;./jar_files/estructuraDatosPrueba.jar;./lib/Visualizador-Estructuras.jar" app/StructureAdapter
pause
cd bin
