@echo off
cd /d "%~dp0"

set "JAR=build\libs\jd-platform-0.0.1-SNAPSHOT.jar"

if not exist "%JAR%" (
    echo Building...
    call gradlew.bat bootJar
    if errorlevel 1 (
        echo Build failed
        exit /b 1
    )
)

echo Starting JD Platform...
echo Press Ctrl+C to stop.
echo.
java -jar "%JAR%"
