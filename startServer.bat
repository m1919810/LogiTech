set SRC=.\target
set DIR=..\..\testserver
set FINA=LogiTech v1.0.1.jar
copy "%SRC%\%FINA%" "%DIR%\plugins"
cd %DIR%

test.bat