id("nameField").addEventListener("keypress", function (e) {
    if (e.keyCode === 13) { handleName(e.target.value); }
});

id("sendName").addEventListener("click", function () {
    handleName(id("nameField").value);
});

function handleName(name) {
    if (name !== "") {
        docCookies.setItem("name", name, Infinity);
        localStorage.setItem("name", name);
        location.href="channels.html";
    }
}

function updateName() {
   id("nameField").value =
        docCookies.getItem("name");
}
document.addEventListener('DOMContentLoaded', function() {
    updateName()
}, false);

//Helper function for selecting element by id
function id(id) {
    return document.getElementById(id);
}