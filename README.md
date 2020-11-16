CSE231: Advanced Programming

GROUP PROJECT - Color Switch

Contributors:
Abhimanyu Gupta (abhimanyu19226@iiitd.ac.in)
Shubham Garg (shubham2019336@iiitd.ac.in)

Classroom:
https://classroom.google.com/u/1/c/MTI2NTMwMzgzMTgx

Instructions:
https://drive.google.com/file/d/1I3G6xzSPOAEdxKNT5Zmw-2wCCWRuIBu6/view

GitHub Repository:
https://github.com/0deadLock0/Color-Switch

Current Deadline: https://classroom.google.com/u/1/c/MTI2NTMwMzgzMTgx/a/MjA0OTk2OTUzNDYy/details

Milestone:
-> Minimum Static GUI

Things to do:
-> Create all pages
-> Playable game with a single obstacle for the movement

TODO(General):
-> Create a rough version of player movement and obstacles coming down 
-> Create an image of expected background layout

Design:

Functionality:
-> Basic play Features
Stay afloat using an assigned key(most probably- Upward Arrow)
Move upwards using the same assigned key
Pass through the obstacles, checking both your’s and obstacle’s colour
Collects stars located inside some obstacles
A colourful ball rotating around its centre of mass changing the colour of user ball
Endless mode, move until either you die or game crashes
Windows
Main Menu
Play Game
Load Game
Stats
Help
Credits
Exit
Play Menu
Score and HighScore at the top Right (Score below the HighScore)
Pause Button at the top left(also support using a key)
Boost Skills, if gained at the top centre for the time being 
Load Menu
Saved Games list with Name, Score and Date
Stats Menu
HighScore(s)
Only the highest score displayed until clicked on it to expand
Average Score
A single score displaying average score throughout history
HighScores Menu
Top 5 Scores with Name, Score and Date
Miscellaneous
User Player: a coloured ball
Background: a set of pictures changing at a high rate creating an impression of motion background
When difficult increases a rushed background for a couple of seconds
Obstacles:
N types of obstacles
Ring
Square
Triangle
Moving Horizontal Line
……………………(more need to be added later)
Other than Line every obstacle rotates around it’s Centre of Mass
Music:
Intriguing sound to keep a user engaged
a high pitched sound is played when difficulty increases
Bonus Features
Background Music
Boost Skills
Time Constrained, gained using a special star (appearing only for a small time on screen)
Shield
2x Score
Extra Life
Probabilities- shield: 0.6, 2x score: 0.3, extra life: 0.1
Time for shield and 2x score - 5s
Time displayed using an hour-glass/circular loading 
Chance to gain boost happens after crossing every ‘O’ obstacles
Shooting Ability
Gained one shot for every ‘S’ stars collected
Classes:
Player
Obstacle
Star
GAME

