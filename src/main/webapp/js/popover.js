function createPopup(title, data, label) {
    var popup = document.createElement("div");
    popup.classList.value = "event fade top show";

    var content = "    <h3 class=\"title\">" + title + "</h3>\n";
    data.forEach(elem => content += label + elem + "<br/>");

    popup.innerHTML = content;
    var container = document.getElementsByClassName("event-container")[0].appendChild(popup);

    setTimeout(hide, 5000, popup);
}

function hide(element) {
    element.classList.remove('show')
}