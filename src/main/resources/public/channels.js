//Establish the WebSocket connection and set up event handlers
var channelsSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/channels/");
channelsSocket.onmessage = function (msg) { updateChannels(msg); };
channelsSocket.onclose = function () { alert("WebSocket connection closed") };
channelsSocket.onclose = function () {
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
//Send new channels name if "Send" is clicked
id("channelButton").addEventListener("click", function () {
    sendChannel(id("channelField").value);
});

//Send message if enter is pressed in the input field
id("channelField").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { sendChannel(e.target.value); }
});

var username = localStorage.getItem("name");
document.title = "Chat";
id("header").innerHTML = "Twoje imie: " + username;
var button = document.createElement("input");
button.type = "button";
button.value = "Zmien imie";
button.onclick = function() {
    channelsSocket.close();
    location.href = "index.html";
};
id("header").appendChild(button);

//Send new channel name
function sendChannel(channelName) {
    if (channelName !== "") {
        var obj = {};
        obj.channel = channelName;
        channelsSocket.send(channelName);
    }
    id(channelField).value = "";
}

//Update the channels-panel
function updateChannels(msg) {
    var data = JSON.parse(msg.data);
    id("channellist").innerHTML = "";
    data.channellist.forEach(function (channel) {
        insert("channellist", "<input type=\"button\" value=\"" + channel + "\" onclick=\"gotoChannel(\'"+channel+"\');\">");
    });
}

function gotoChannel(channel) {
    localStorage.setItem("channel", channel);
    location.href="chat.html";
}
//Helper function for inserting HTML as the first child of an element
function insert(targetId, message) {
    id(targetId).insertAdjacentHTML("afterbegin", message);
}

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}