set SRC=.\target
set DIR=..\..\testserver
set FINA=LogiTech v1.0.3.jar
set PARENT=..\
copy "%SRC%\%FINA%" "%PARENT%\testJars"
cd %PARENT%
test_copyJars.bat
cd %DIR%
test.bat