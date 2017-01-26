//Establish the WebSocket connection and set up event handlers
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/");
webSocket.onmessage = function (msg) { updateChat(msg); };
webSocket.onclose = function () {
    var div = document.createElement("div");
    div.id = "error";
    div.innerHTML = "Utracono polaczenie.";
    var button = document.createElement("input");
    button.type = "button";
    button.value = "Odswiez";
    button.onclick = function () {
        location.reload();
    };
    id("header").appendChild(div);
    id("error").appendChild(button);
};
webSocket.onopen = function () {
    var obj = {};
    obj.name = localStorage.getItem("name");
    obj.type = "newuser";
    obj.channel = localStorage.getItem("channel");
    webSocket.send(JSON.stringify(obj));
};

//Send message if "Send" is clicked
id("send").addEventListener("click", function () {
    sendMessage(id("message").value);
});

//Send message if enter is pressed in the input field
id("message").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { sendMessage(e.target.value); }
});

var channel = localStorage.getItem("channel");
var username = localStorage.getItem("name");
document.title = "Kanal " + channel;
id("header").innerHTML = "Kanal " + channel + ", Twoje imie: " + username;
//id("header").insertAdjacentHTML("afterbegin", "<input type=\"button\" value=\"Opusc kanal\" onclick=\'location.href=\'chat.html\';\'>
var button = document.createElement("input");
button.type = "button";
button.value = "Opusc kanal";
button.onclick = function() {
    webSocket.close();
    location.href = "channels.html";
};
id("header").appendChild(button);

//Send a message if it's not empty, then clear the input field
function sendMessage(message) {
    if (message !== "") {
        var obj = {};
        obj.name = localStorage.getItem("name");
        obj.message = message;
        obj.type = "message";
        webSocket.send(JSON.stringify(obj));
        id("message").value = "";
    }
}

//Update the chat-panel, and the list of connected users
function updateChat(msg) {
    var data = JSON.parse(msg.data);
    insert("chat", data.userMessage);
    id("userlist").innerHTML = "";
    data.userlist.forEach(function (user) {
        insert("userlist", "<li>" + user + "</li>");
    });
    insert("userlist", "<li>Uzytkownicy:</li>");
}

//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}