// Global theme variable
let theme = 'arialTheme';  // Default theme

/**
 * Maps the piece token to the corresponding Unicode character
 * @type {{P: string, Q: string, R: string, B: string, K: string, N: string, J: string, V: string}}
 */
const pieceMap = {
    'R': '♜',
    'N': '♞',
    'B': '♝',
    'Q': '♛',
    'K': '♚',
    'P': '♟',
    'J': '⎈',
    'V': '◈'  // Diamond shape for Vortex
};

/**
 * Maps the letters R, G, B to the corresponding color string
 * @type {{R: string, B: string, G: string}}
 */
const colorMap = {
    'R': 'Red',
    'G': 'Green',
    'B': 'Blue'
};

const pieceColors = {
    'R': '#000000',  // Deep red
    'G': '#27ae60',  // Deep green
    'B': '#2980b9'   // Deep blue
};

const pieceStrokeColors = {
    'R': '#ffffff',  // White outline for red pieces
    'G': '#ffffff',  // White outline for green pieces
    'B': '#ffffff'   // White outline for blue pieces
};

// Global timer states for each player
let timers = {
    R: { timer: null, minutes: 0, seconds: 0, isRunning: false },
    G: { timer: null, minutes: 0, seconds: 0, isRunning: false },
    B: { timer: null, minutes: 0, seconds: 0, isRunning: false }
};

// Function to format time
function formatTime(minutes, seconds) {
    const min = minutes < 10 ? '0' + minutes : minutes;
    const sec = seconds < 10 ? '0' + seconds : seconds;
    return `${min}:${sec}`;
}

/**
 * Starts the timer for a player
 * @param {string} color The color of the current player
 */
function startPlayerTimer(color) {
    if (timers[color].isRunning) return; // Prevent starting the timer if it's already running for the player
    timers[color].isRunning = true;

    timers[color].timer = setInterval(() => {
        timers[color].seconds++;
        if (timers[color].seconds === 60) {
            timers[color].seconds = 0;
            timers[color].minutes++;
        }
        document.getElementById(`timer-${color}`).textContent = formatTime(timers[color].minutes, timers[color].seconds);
    }, 1000);
}

/**
 * Stops the timer for a player
 * @param {string} color The color of the current player
 */
function stopPlayerTimer(color) {
    console.log("Stopping timer for color:", color);
    if (!timers[color]) {
        console.error(`Invalid color: ${color}`);  // Log an error for invalid color
        return;  // Exit the function if the color is not valid
    }

    if (timers[color].timer) {
        clearInterval(timers[color].timer);
        timers[color].isRunning = false;
    }
}
function updateTimerDisplay(color) {
    const timerElement = document.getElementById(`timer-${color}`);

    // Check if the element exists before trying to update it
    if (timerElement) {
        timerElement.textContent = formatTime(timers[color].minutes, timers[color].seconds);
    } else {
        console.error(`Timer element for color ${color} not found.`);
    }
}

/**
 * Updates the display of all players' timers
 */
function updateAllTimers() {
    for (const color of ['R', 'G', 'B']) {
        updateTimerDisplay(color);
    }
}

function updateTimerDisplay(color, minutes, seconds) {
    const timerElement = document.getElementById(`timer-${color.toLowerCase()}`);
    timerElement.textContent = formatTime(minutes, seconds);
}

/**
 * Updates the current player and handles timer transitions
 * @param {string} color The color of the current player
 */
function updateCurrentPlayer(color) {
    console.log("Updating current player to color:", color);
    const colorName = colorMap[color];
    const playerName = localStorage.getItem(colorName);

    const p_name = document.getElementById('pl-name');
    p_name.textContent = playerName;

    const p_colour = document.getElementById('pl-colour');
    p_colour.style.color = colorName;

    // Stop the previous player's timer and start the new player's timer
    const otherColors = ['R', 'G', 'B'].filter(c => c !== color);
    otherColors.forEach(c => stopPlayerTimer(c));
    startPlayerTimer(color);
}

/**
 * Updates the theme
 * @param {string} name Name of the theme (arialTheme, freeSerifTheme, dejaVuSansTheme)
 */
function updateTheme(name) {
    console.log('New Font: ' + name);
    theme = name;
    const textElements = document.querySelectorAll('.chess-piece');
    textElements.forEach(function(element) {
        // Remove old theme class
        element.classList.remove('arialTheme', 'freeSerifTheme', 'dejaVuSansTheme');
        // Add new theme class
        element.classList.add(name);
    });
}

/**
 * Highlights a specific set of polygons
 * @param {Array} data Set of polygons as JSON array
 */
function displayPossibleMoves(data) {
    console.log('Highlighted Polygons: ' + data);
    removeHighlighting();
    data.forEach(function (polygonId) {
        const polygon = document.getElementById(polygonId);
        if (polygon != null) {
            polygon.classList.add('highlight');
        }
    });
}

/**
 * Removes the highlighting of all polygons
 */
function removeHighlighting() {
    const polygons = document.querySelectorAll('polygon');
    polygons.forEach(polygon => polygon.classList.remove('highlight'));
}

/**
 * Updates the chessboard with the new state
 * @param {Object} response New board state with the updated piece positions, e.g. {Ba1: "BR", Ba2: "BP", ...}
 */
function updateBoard(response) {
    clearBoard();
    console.log('New Board Configuration:', response);

    let board = response['board'];
    let highlightedPolygons = response['highlightedPolygons'];
    //let winner = response['winner'];

    if (response['gameOver']) {
        stopPlayerTimer(response['winner']); // Stop the timer when the game is over
        // Placeholder for showing the game over popup
    }

    updatePieces(board);
    displayPossibleMoves(highlightedPolygons);
}

/**
 * Updates the pieces on the board
 * @param {Object} board The board state with pieces
 */
function updatePieces(board) {
    for (const polygonId in board) {
        const value = board[polygonId];
        const pieceColor = value[0];
        const pieceToken = value[1];

        displayPiece(polygonId, pieceToken, pieceColor);
    }
}

/**
 * Displays the piece as a text element inside an SVG
 * @param {string} polygonId ID of the polygon, e.g. Ba1, Ba2, ...
 * @param {string} pieceToken Token of the piece, e.g. R, N, B, K, Q, P
 * @param {string} pieceColor Color of the piece, e.g. R, G, B
 */
function displayPiece(polygonId, pieceToken, pieceColor) {
    const polygon = document.getElementById(polygonId);
    const existingText = polygon.nextElementSibling; // Assuming the existing text is the immediate next sibling

    const points = polygon.points;
    let x = (points.getItem(0).x + points.getItem(2).x) / 2;
    let y = (points.getItem(0).y + points.getItem(2).y) / 2;

    const textElement = getPieceText(x, y, pieceColor, pieceToken);

    // Check if there is existing text, and insert the new text after it
    if (existingText) {
        polygon.parentNode.insertBefore(textElement, existingText.nextSibling);
    } else {
        // If there's no existing text, just insert the new text after the polygon
        polygon.parentNode.insertBefore(textElement, polygon.nextSibling);
    }
}

/**
 * Creates a new SVG text element displaying a piece
 * @param {number} x Coordinate of the text element
 * @param {number} y Coordinate of the text element
 * @param {string} color Color of the displayed piece, e.g. R, G, B
 * @param {string} pieceToken Token of the displayed piece, e.g. R, N, B, K, Q, P
 * @returns {SVGTextElement} The SVG text element
 */
function getPieceText(x, y, color, pieceToken) {
    const textElement = document.createElementNS('http://www.w3.org/2000/svg', 'text');
    textElement.setAttribute('x', x);
    textElement.setAttribute('y', y);
    textElement.setAttribute("text-anchor", "middle");
    textElement.setAttribute("dominant-baseline", "middle");

    // Set piece color and stroke
    textElement.style.fill = pieceColors[color];
    textElement.style.stroke = pieceStrokeColors[color];
    textElement.style.strokeWidth = '0.5px';

    textElement.setAttribute('font-size', '55');
    textElement.setAttribute('font-weight', 'bold');
    textElement.classList.add('chess-piece', theme);
    textElement.textContent = pieceMap[pieceToken];

    return textElement;
}

/**
 * Removes all displayed pieces from the chessboard
 */
function clearBoard() {
    const textElements = document.querySelectorAll('.chess-piece');
    textElements.forEach((textElement) => {
        textElement.remove();
    });
    // Also remove any leftover filter definitions
    const defs = document.querySelectorAll('defs');
    defs.forEach(def => def.remove());
}

/**
 * Called when the HTML document finishes loading
 */
function bodyLoaded() {
    console.log("Body loaded");
    requestUpdatedBoard();
    requestCurrentPlayer();

    const polygons = document.querySelectorAll('polygon');
    polygons.forEach(function (polygon) {
        polygon.addEventListener('click', function () {
            const polygonId = polygon.id;
            sendPolygonClicked(polygonId);
            requestCurrentPlayer();
        });
    });
}

/**
 * Posts the ID of the clicked polygon to the server on /onClick endpoint
 * @param {string} polygonId ID of the clicked polygon, e.g. Ra1, Gb3, ...
 */
function sendPolygonClicked(polygonId) {
    const request = new XMLHttpRequest();
    request.open("POST", "/onClick", false);
    request.send(polygonId);

    if (request.status === 200) {
        const data = JSON.parse(request.response);
        updateBoard(data);
        const currentPlayer = data['currentPlayer'];  // Assuming this comes in the response
        stopPlayerTimer(currentPlayer);  // Stop the current player's timer after their move
        // Optionally, you can start the next player's timer here
    }
}

/**
 * Requests the new board state and displays it
 */
function requestUpdatedBoard() {
    console.log("Request Current Board");
    const request = new XMLHttpRequest();
    request.open("GET", "/board", false);
    request.send(null);

    if (request.status === 200) {
        const response = JSON.parse(request.response);
        if (response) {
            updatePieces(response);
        } else {
            console.error("Invalid board data received");
        }
    } else {
        console.error("Failed to get board state:", request.status);
        // Try to create a new game if board request fails
        const newGameReq = new XMLHttpRequest();
        newGameReq.open("GET", "/newGame", false);
        newGameReq.send(null);
        if (newGameReq.status === 200) {
            // Retry getting board state
            requestUpdatedBoard();
        }
    }
}

/**
 * Requests the current player and displays it
 */
function requestCurrentPlayer() {
    console.log("Request Current Player");
    const request = new XMLHttpRequest();
    request.open("GET", "/currentPlayer", false);
    request.send(null);

    if (request.status === 200) {
        const currentPlayerColor = request.response;
        updateCurrentPlayer(currentPlayerColor);
    }
}
updateAllTimers(); // Call the function to update all timers

document.addEventListener("DOMContentLoaded", function() {
    // Now you can safely update the timer display or do other DOM manipulations
    updateAllTimers();
});