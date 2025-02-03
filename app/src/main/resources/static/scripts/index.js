//  Purpose: Handle the index.html page
function checkInputs() {
    const goldPlayer = document.getElementById('gold-name').value.trim();
    const silverPlayer = document.getElementById('silver-name').value.trim();
    const bronzePlayer = document.getElementById('bronze-name').value.trim();
    const newGameButton = document.getElementById('newgamebtn');

    if (goldPlayer && silverPlayer && bronzePlayer) {
        newGameButton.removeAttribute('disabled'); // Enable button
    } else {
        newGameButton.setAttribute('disabled', 'true'); // Keep it disabled
    }
}

// Event listeners to check input fields dynamically
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("gold-name").addEventListener("input", checkInputs);
    document.getElementById("silver-name").addEventListener("input", checkInputs);
    document.getElementById("bronze-name").addEventListener("input", checkInputs);
});
// Event listener for the new game button
async function newGame() {
    try {
        const goldPlayer = document.getElementById('gold-name').value.trim();
        const silverPlayer = document.getElementById('silver-name').value.trim();
        const bronzePlayer = document.getElementById('bronze-name').value.trim();

        // Store player names in localStorage
        localStorage.setItem('Gold', goldPlayer);
        localStorage.setItem('Silver', silverPlayer);
        localStorage.setItem('Bronze', bronzePlayer);

        // Send request to start a new game
        const response = await fetch('/newGame', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            window.location.href = '/game.html'; // Navigate to the game page
        } else {
            console.error('Failed to start new game:', response.statusText);
            alert('Failed to start new game. Please try again.');
        }
    } catch (error) {
        console.error('Error starting new game:', error);
        alert('Failed to start new game. Please try again.');
    }
}
