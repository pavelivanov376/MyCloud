//=========== List all ==============//

let loadFilesBtn = document.getElementById('loadFiles')
loadFilesBtn.addEventListener('click', onLoad);

function onLoad(event) {
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    let filesContainer = document.getElementById('files-container')
    filesContainer.innerHTML = ''

    fetch("http://localhost:80/api/files/", requestOptions)
        .then(response => response.json())
        .then(json => json.forEach(file => {
            let row = document.createElement('tr')

            let nameCol = document.createElement('td')
            let pathCol = document.createElement('td')
            let ownerCol = document.createElement('td')
            let typeCol = document.createElement('td')
            let dateCol = document.createElement('td')

            pathCol.textContent = file.parentFolder
            ownerCol.textContent = file.owner
            typeCol.textContent = file.type
            dateCol.textContent = file.creationDate

            let buttonDownload = document.createElement('a')
            buttonDownload.textContent = file.name
            buttonDownload.setAttribute("href", "http://localhost:80/api/download/" + file.name);
            nameCol.appendChild(buttonDownload)

            row.appendChild(nameCol)
            row.appendChild(pathCol)
            row.appendChild(ownerCol)
            row.appendChild(typeCol)
            row.appendChild(dateCol)

            filesContainer.appendChild(row);
        }))
        .catch(error => console.log('error', error));
}

//=========== Upload ==============//

let uploadFileBtn = document.getElementById('uploadFile')
uploadFileBtn.addEventListener('click', onUpload);

function onUpload(event) {
var data = new FormData()
var input = document.querySelector('input[type="file"]')
let fullPath = document.getElementById('path')

data.append('content', input.files[0])
data.append('fullPath', fullPath)

    var requestOptions = {
        method: 'POST',
        redirect: 'follow',
        body: data
    };

    fetch("http://localhost:80/api/upload/", requestOptions)
        .then(alert("File was uploaded"))
        .catch(error => console.log('error', error));
}

//============== Create Folder
//TODO Create folder fro given name and current folder we are into, starting from the root
let uploadFileBtn = document.getElementById('createFolder')
uploadFileBtn.addEventListener('click', onUpload);

function onUpload(event) {
var data = new FormData()
var input = document.querySelector('input[type="file"]')
let fullPath = document.getElementById('path')

data.append('content', input.files[0])
data.append('fullPath', fullPath)

    var requestOptions = {
        method: 'POST',
        redirect: 'follow',
        body: data
    };

    fetch("http://localhost:80/api/upload/", requestOptions)
        .then(alert("File was uploaded"))
        .catch(error => console.log('error', error));
}