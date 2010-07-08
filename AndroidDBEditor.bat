@echo off rem set current path
set CURRENT_PATH=%~dp0

rem replace slashes
set CURRENT_PATH=%CURRENT_PATH:\=/%

echo sqliteeditorpath=%CURRENT_PATH%sqlitebrowser.exe > settings.ini
rem start app
java -jar AndroidDBEditor.jar