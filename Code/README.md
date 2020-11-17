This directory contains all the code files for the project


# Command to Compile and Run:

Note: %PATH_TO_JAVAFX% is the path where javafx is present

Compile:

javac --module-path %PATH_TO_JAVAFX% --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web ./colorswitch/*.java

Run:

java --module-path %PATH_TO_JAVAFX% --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web colorswitch.ColorSwitch
