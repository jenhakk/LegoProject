# Lego Project
 
**This repository is created for a programming project for Häme University of Applied Sciences.**

Team:

## Description

The goal of this project was to program **Lego Mindstorms EV3 robot** to travel a route marked with black line. When the robot starts, it plays a short audio file. The robot makes small side to side movement to keep track of the line and makes changes if it notices that it is no longer on the line. There is one obstacle set in the route and the robot goes around it and find it's way back to the route. When the obstacle is detected on the second round, the robot will stop and shut down. Programming language used in this project is **Java**.


## Classes

The program is created using three threads and one assistant class which is used for transferring data.

#### `Roborun.java`
This is the main class where the program is run and also the main thread.

#### `ObstacleDetector.java`
This class contains the methods for getting the distance to the obstacle with ultra sonic sensor and saving values collected with color sensor. This is the second thread.

#### `RobotMoves.java`
This class contains all the methods for robot's movements e.g. `DetectLine()` that is used for searching the line based on brightness values and `ClearObstacle()` that is used to make an arch to go around the obstacle. This is the third thread.

#### `DataTransfer.java`
This class only contains getter and setter type of methods which are used to change data transferred between threads.


## Additional features

* The robot uses threads but it could have been made with using only one class as well. 
* Using Lejos' MovePilot feature e.g. when the robot detects the obstacle and searching the line.
* Playing audio file when starting.


## Conclusion

The project was challenging but highly interesting. Threads were completely new concept for us and it took some time to get the hang of it. Also going through Lejos documentation and how to use it took a lot of time in the beginning and some kind of search function would have been nice. However the project itself was very useful for us since it was good practice of Java after taking a break from it and we learnt a lot. Using Lejos documentation reminded us the importance of making understandable documentation. 

## For the future

* It would be interesting to add more sensors to robot and connect them to existing code.
* Making a separate thread for sounds and adding more of them (*This we already tried to achieve but run out of time.*)
* Try to make robot move using only MovePilot.
