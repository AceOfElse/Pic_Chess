## Overview
- Pic Chess is a mobile application that combines the classic strategy board game of chess with a creative twist.
- Players can engage in a traditional game of chess against the AI, or express their artistic side in a unique "Draw Mode" that uses chess pieces as brushes.

## Features
- The application offers two main game modes: Play Chess and Draw Mode.
- Play Chess allows players to engage in a traditional game of chess against the AI, with multiple difficulty levels and game modes.
- Timed and untimed game modes.
- Draw Mode uses chess pieces as brushes to create artwork on a virtual canvas.

- Additionally, the application offers various customization options, including piece sets, board styles, and more.
- Players can also share their artwork and chess games on social media, or save them to their device for later viewing.

## Technical Details
- The application is built using Java and Android Studio.
- It utilizes a custom chess engine for game logic and incorporates image processing and manipulation for Draw Mode.
- A SQLite database is used to store game data and user creations.

## Project Structure
- The project is organized into the following main directories:

- app/src/main/java/com/example/pic_chess: Java source code for the application app/src/main/res: Resource files, including layouts, drawables, and values app/src/main/assets: Asset files, including images and audio

## Key Classes and Functions
The application's main classes and functions include:

- AIvAIChessActivity: Main activity for playing chess against the AI
- DrawModeActivity: Activity for creating artwork using chess pieces
- Piece: Class representing a chess piece, including its image and movement logic
- Square: Class representing a square on the chess board, including its image and piece occupancy

## License
- Pic Chess is licensed under the MIT License.

## Acknowledgments
- Special thanks to the Android Open Source Project for providing the foundation for this application.
- Thanks also to the chess engine library used in this project.
