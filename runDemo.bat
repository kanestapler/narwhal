@echo off
cd Demos
echo "Running first demo..."
javac VolumeCalculator.java
@echo on
java VolumeCalculator pet 1 true 2.0 2 3 4
@echo off
echo.
echo "First demo complete!"
pause
