function checkInputs() {
    const pl1 = document.getElementById('pl1').value;
    const pl2 = document.getElementById('pl2').value;
    const pl3 = document.getElementById('pl3').value;
    const newGameButton = document.getElementById('newgamebtn');

    if (pl1 !== '' && pl2 !== '' && pl3 !== '') {
        newGameButton.removeAttribute('disabled');
    } else {
        newGameButton.setAttribute('disabled', 'true');
    }
}

async function newGame() {
    try {
        const pl1 = document.getElementById('pl1').value;
        const pl2 = document.getElementById('pl2').value;
        const pl3 = document.getElementById('pl3').value;

        localStorage.setItem('Blue', pl1);
        localStorage.setItem('Green', pl2);
        localStorage.setItem('Red', pl3);

        const response = await fetch('/newGame', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            window.location.href = '/game.html';
        } else {
            console.error('Failed to start new game:', response.statusText);
            alert('Failed to start new game. Please try again.');
        }
    } catch (error) {
        console.error('Error starting new game:', error);
        alert('Failed to start new game. Please try again.');
    }
}