# Tic-Tac-Toe Game

This is a Tic Tac Toe game built using Kotlin and Jetpack Compose for Android. It supports two game modes:
- **Computer vs User** (single-player mode against AI)
- **Player 1 vs Player 2** (local multiplayer)

## Features
- 3x3 grid layout for the Tic Tac Toe board
- Two modes: Player vs Player & Computer vs User
- Game status display (winner)
- Game reset functionality
- Simple AI for the "Computer vs User" mode (random move)

## Demo

### Screenshots
<div style="display: flex; flex-wrap: wrap; gap: 50px;">
  <img src="app/src/main/java/com/example/tictactoegame/images/demo/Img1.png" width="300" height="600" />
  <img src="app/src/main/java/com/example/tictactoegame/images/demo/Img2.png" width="300" height="600" />
  <img src="app/src/main/java/com/example/tictactoegame/images/demo/Img3.png" width="300" height="600" />
  <img src="app/src/main/java/com/example/tictactoegame/images/demo/Img6.png" width="300" height="600" />
  <img src="app/src/main/java/com/example/tictactoegame/images/demo/Img4.png" width="300" height="600" />
  <img src="app/src/main/java/com/example/tictactoegame/images/demo/Img5.png" width="300" height="600" />
</div>

### Video
[Demo Here](https://drive.google.com/drive/folders/1dPfnpYTZqDYsHVvXmAxQKrCbNSEl0GgH?usp=sharing)

## Technologies & Concepts Used
1. **Kotlin** is used for game logic, player turns, and state management.
2. **Jetpack Compose** enables declarative UI and automatic UI updates based on game state. 
3. **Game Logic** handles board state, turn management, and winner detection. 
4. **Two Game Modes**: AI plays against the user in single-player, or two players compete in multiplayer. 
5. **AI (in single-player)** utilizes algorithms like **Minimax Algorithm** for decision-making based on board evaluation.

## Getting Started
- To get started with this project, clone this repository and open it in Android Studio.
```
  git clone https://github.com/AritraC1/TicTacToeGame.git
```

- Build and run the app on your android device.