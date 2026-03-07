@echo off
cd /d "%~dp0"

echo Building JD Platform...
call gradlew.bat bootJar
if errorlevel 1 (
    echo Build failed
    exit /b 1
)
echo.
echo Done: build\libs\jd-platform-0.0.1-SNAPSHOT.jar
