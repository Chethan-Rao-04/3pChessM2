# **Three Player Chess - Bug Free Brigade**

This document describes the architecture and design decisions for the Three Player Chess game developed by Bug Free Brigade. The game is designed to run entirely in the browser, with all logic executed locally (without any backend or server).

**Technology Stack**

## Frontend:
The entire application is built using HTML, CSS, and JavaScript. The game logic, board rendering, and player interactions are handled within the browser.

## State Management: 

Game state is managed locally using JavaScript objects, and the browser's localStorage is used to persist data across sessions (if necessary).

## Key Design Decisions

Local Game Logic:
All game logic (such as moves, turns, and game rules) is implemented in JavaScript. 
The game runs entirely within the user's browser without relying on a server.

Three Players on a Single Board: The game allows for three players, which requires custom logic for managing turns, handling player interactions, and preventing illegal moves. The game enforces the clockwise turn order.

