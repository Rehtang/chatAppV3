var stompClient = null;

function connect() {
    var socket = new SockJS('/chatapp-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
        loadChatHistory();
    });
}

function sendMessage() {
    var messageContent = document.getElementById('message').value.trim();
    var senderName = document.getElementById('userName').innerText.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: senderName,
            content: messageContent,
            timestamp: new Date().getTime()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    }
}

function showMessageOutput(messageOutput) {
    var messageArea = document.getElementById('messageArea');
    var messageElement = document.createElement('div');

    var timestamp = new Date(messageOutput.timestamp);
    var timeString = timestamp.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });

    messageElement.innerHTML = `<strong>${messageOutput.sender}</strong> [${timeString}]: ${messageOutput.content}`;
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function loadChatHistory() {
    fetch('/chat/history')
        .then(response => response.json())
        .then(data => {
            data.forEach(showMessageOutput);
        })
        .catch(error => console.error('Error loading chat history:', error));
}

window.onload = function() {
    connect();
    // Получение информации о пользователе
    fetch('/user')
        .then(response => response.json())
        .then(data => {
            document.getElementById('userName').innerText = data.name;
        })
        .catch(error => console.error('Error fetching user info:', error));
};
