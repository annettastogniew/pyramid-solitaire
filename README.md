# pyramid-solitaire
A program that generates a game of pyramid solitaire that can be played through command-line inputs.

## Table of contents
* [General info] (#general-info)
* [Technologies] (#technologies)

## General info
This project uses the MVC design pattern to create various types of the game pyramid solitaire. 

In basic pyramid solitaire, cards are dealt into a pyramid with the desired number of rows and 
draw cards. The player can remove cards from the pyramid if the sum of the card value(s) is 13
(Ace - 1, Jack - 11, Queen - 12, King - 13). Cards from the draw pile can be paired with cards
from the pyramid, or discarded on their own. The goal is to empty the pyramid.

This program can also support multi-pyramid solitaire, where the same rules apply, but cards 
are dealt into 3 overlapping pyramids. 

The final type of pyramid solitaire supported by this program is relaxed pyramid solitaire.
In basic pyramid solitaire, only exposed cards can be removed from the pyramid. In relaxed
pyramid solitaire, if a card is covered only by another card such that if the two cards'
values are added, the sum is 13, then those two cards can be removed from the pyramid.

Various models exist in this program that each support each type of gameplay, but all models
are used by a single controller and a single textual view. 

Users can run the program through the main class, PyramidSolitaire, by giving command-line
arguments to remove cards or quit the game. The format of command-line arguments are 
described in the comments of the controller class.

## Technologies
Project is created with:
* Java version 14.0.1

