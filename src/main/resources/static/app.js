//=========== List Files ==============//

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
//=========== List All Content of a directory ==============//

//TODO Run once when page initially loaded for the root folder

function onEnterFolder(folderIndex) {
    let currentFolderElement = document.getElementById('currentFolder')
    currentFolderElement.innerHTML = 'Current Directory: ' + currentFolder

    let filesContainer = document.getElementById('folderContent')
    filesContainer.innerHTML = ''

    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };

    fetch("http://localhost:80/api/folder/" + folderIndex, requestOptions)
        .then(response => response.json())
        .then(json => json.forEach(file => {
            let row = document.createElement('tr')

            let nameCol = document.createElement('td')
            let ownerCol = document.createElement('td')
            let typeCol = document.createElement('td')
            let dateCol = document.createElement('td')

            ownerCol.textContent = file.owner
            typeCol.textContent = file.type
            dateCol.textContent = file.creationDate

            let buttonDownload = document.createElement('a')
            buttonDownload.setAttribute("id", "btn_" +"_"+ file.name);
            buttonDownload.setAttribute("class", "openFile");
            buttonDownload.textContent = file.name
//            buttonDownload.setAttribute("href", "http://localhost:80/api/download/" + file.name);
            nameCol.appendChild(buttonDownload)

            row.appendChild(nameCol)
            row.appendChild(ownerCol)
            row.appendChild(typeCol)
            row.appendChild(dateCol)

            filesContainer.appendChild(row);
        }))
        .catch(error => console.log('error', error));
}
//==============On Click of a button with class openFile

//============== Create Folder
//TODO Create folder fro given name and current folder we are into, starting from the root
let createFolderBtn = document.getElementById('createFolder')
createFolderBtn.addEventListener('click', onCreateFolder);
let currentFolder = "/home/"
let currentFolderId = "0"

function onCreateFolder(event) {
    let folderName = document.getElementById('folderName')
    var data = new FormData()
    data.append('name',folderName)
    data.append('fullPath', fullPath)
    data.append('id', currentFolderId)

    var requestOptions = {
        method: 'POST',
        redirect: 'follow',
        body: data
    };

    fetch("http://localhost:80/api/folder/create", requestOptions)
        .then(alert("File was uploaded"))
        .catch(error => console.log('error', error));
}