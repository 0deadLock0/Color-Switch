# Color Switch

Contributors:

[Abhimanyu Gupta](https://github.com/0deadLock0)

[Shubham Garg](https://github.com/shubham19336)

Purpose: Course Project at [IIITD](https://www.iiitd.ac.in/)

Instructions: [PDF](/Instructions.pdf) (Credits- [Advanced Programming](http://techtree.iiitd.edu.in/viewDescription/filename?=CSE201))

[GitHub Repository](https://github.com/0deadLock0/Color-Switch)

# Platform built on:

-> Java 15.0.1

-> JavaFX 15.0.1

# Command to Compile and Run:

Note:

1) %PATH_TO_JAVAFX% is the path where javafx is present
2) Commands need to be run in the folder contaning colorswitch folder.


Compile:

javac --module-path %PATH_TO_JAVAFX% --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web ./colorswitch/*.java

Run:

java --module-path %PATH_TO_JAVAFX% --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web colorswitch.ColorSwitch

Compile and Run:

javac --module-path %PATH_TO_JAVAFX% --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web ./colorswitch/*.java & java --module-path %PATH_TO_JAVAFX% --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web colorswitch.ColorSwitch

