var content = document.querySelector('link[rel="import"]').import.querySelector('header');
document.body.appendChild(content.cloneNode(true));