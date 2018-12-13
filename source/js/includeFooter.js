var content = document.querySelector('link[rel="import"]').import.querySelector('footer');
document.body.appendChild(content.cloneNode(true));