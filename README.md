Assignment 1 : COL333 3D TicTacToe
Creators: 2015MT60567,Siddharth Khera  && 2015CS50278,Ankur Sharma
We have implemented all three strategies described in the problem statement, which can be chosen by the user at runtime.
The program comes with a visual board for easy visualization and input from human are taken via indexed positions of the visual board.
The computer plays with the following tactic in order of preference, which can be reordered for unpredictability.
1. Self Win
2. Block Opp Win
3. Go for Cube centre
4. Go for face centres ( randomly choosing an empty one)
5. Go for Cube corners
6. Go for edge centres

There are essentially three different files.
1. Source.java - Run this file to run the game. This class contains the main functions and works as the main script ,creating object for the Intelligence class. It also manages the visual cmd commands, printing the board and managing moves called by the intelligent agent and the human.

2. Intelligence.java - This is the class which governs all the actions of the computer. The interaction between the human and intelligence is done by source by updating some variables of Intelligence. It utilises a magic cube for faster operations which is constructed and managed in the MagicCube class

3. MagicCube.java - The MagicCube class implements an algorithm to create a 3D Magic Cube of 11 surfaces, and prints them upon calling the method. It also implements some collinearity conditions to generate the three essential lists based on sum and collinearity. The Tuple class essentially is a way of storing a set of three integers as a tuple object.

Compilation instructions:-
	javac *.java
	java Source
