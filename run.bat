@echo off
cd /d "%~dp0"

echo Starting JD Platform (dev mode)...
echo Source changes will auto-restart.
echo Press Ctrl+C to stop.
echo.
call gradlew.bat bootRun
