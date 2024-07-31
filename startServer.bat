set SRC=.\target
set DIR=..\..\testserver
set FINA=LogiTech v1.0.0.jar
copy "%SRC%\%FINA%" "%DIR%\plugins"
cd %DIR%

test.bat