# Balatrino 

## A unique card game

The aim of this project is to recreate the game "Balatro" by the developer LocalThunk It is a poker-themed roguelike deck-building game.
The game is played by playing cards that give you a score to defeat "Blinds" (a certain score that has to be achieved). You can get higher scores by playing certain poker hands, such as Pair, Two-Pair, Full House and Royal Flush.
(to make this project feasable in the time frame I have, I have removed many features from the actual game, hence the name Balatrino)

You can also increase your score by having "Joker" cards which ewach have special abilities to enhance your score.
Every round, the player has a limited amount of hands that can be played, with each hand having 5 cards.
The game ends if the player cannot reach the total score of the blind after playing all their hands.
After every round, the player is given a random upgrade such as adding/removing cards from the deck, increasing hand size and acquiring joker cards

The aim of the game is to defeat as many Blinds as possible. 

The actual game is really fun and I felt like it would be a nice and interesting challenge to try and recreate it in my project.


## User Stories
- I want to be able to view my current Jokers
- I want to be able to add/remove cards from my deck (using rewards)
- I want to be able to add jokers (using rewards)
- I want to view my score at the end of the game
- I want to be able to view the deck during the game

- I want to be given the option to load from file at the start of the game. 
- I want to be given the option to save at the end of each round.


# Instructions for End User

- run main and press the start game button to start (main menu is the visual component)
- when the game starts theres some time to press the view deck button if you want to see the deck of cards
- then you are prompted to enter the hand to play. you have to enter 5 numbers indicating your card choices from the current hand on-screen, each separated by commas
- the goal is to choose 5 cards that have the best poker hand from (Pair, Three of a Kind, Full House, Straight, Flush, Straigh Flush). each has an associated "Chips" and "Mult" represented as [Chips, Mult]
- the score your hand gets is calculated by multiplying these 2 numbers together after all cards and jokers are applied.
- after each round won, a random upgrade is applied from (add joker, add card, remove card, increase max hands by 1) (adding multiple Xs to Y) (removing Xs from Y) 
- different jokers alter your hands chips and mult based on the cards you played.
- at the start of the game you are asked if you want to load, and after every round won, you are asked if you want to save your progress.
