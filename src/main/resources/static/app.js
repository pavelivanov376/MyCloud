//==============OnPageLoad================//
//Run once when page initially loaded for the root folder
let homeDirectoryID;
let currentFolderId;
let currentFolderName = "/home"

window.onload = function () {
    let requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };
    //Get current user home folder id
    fetch("http://localhost:80/api/home/id/", requestOptions)
        .then(response => response.text())
        .then(id => {
            homeDirectoryID = id;
            currentFolderId = id;
            console.log(homeDirectoryID)
            onEnterFolder(homeDirectoryID);
        })
        .catch(error => console.log('error', error));
};
//=========== List All Content of a directory ==============//

function onEnterFolder(folderIndex) {
    console.log("Entering folder " + folderIndex)

    let currentFolderElement = document.getElementById('currentFolder')

    currentFolderElement.innerHTML = 'Current Directory: ' + currentFolderName

    let filesContainer = document.getElementById('folderContentContainer')
    filesContainer.innerHTML = ''

    let requestOptions = {
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
            let shareCol = document.createElement('td')
            let deleteCol = document.createElement('td')

            ownerCol.textContent = file.owner
            typeCol.textContent = file.type
            dateCol.textContent = file.creationDate

            let shareInput = document.createElement('input')
            shareInput.setAttribute("placeholder", "Username");
            shareInput.setAttribute("id", "input_" + file.type + "_share-" + file.uuid);
            if(file.name !== "/..") shareCol.appendChild(shareInput)

            let shareButton = document.createElement('button')
            shareButton.textContent = 'Share'
            shareButton.setAttribute("id", "btn_" + file.type + "_share-" + file.uuid);
            shareButton.addEventListener('click', onShare);
            if(file.name !== "/..") shareCol.appendChild(shareButton)

            let deleteButton = document.createElement('button')
            deleteButton.textContent = 'Delete'
            deleteButton.setAttribute("id", "delete_" + file.type + "-" + file.uuid);
            deleteButton.addEventListener('click', onDelete);
            if(file.name !== "/..") deleteCol.appendChild(deleteButton)

            let buttonDownload = document.createElement('a')
            buttonDownload.textContent = file.name
            if (file.type == "file") {
                buttonDownload.setAttribute("id", "btn_" + "_" + file.name);
                buttonDownload.setAttribute("href", "http://localhost:80/api/download/" + file.uuid);
            } else {
                buttonDownload.setAttribute("id", "btn_" + "_" + file.uuid);
                buttonDownload.setAttribute("href", "http://localhost:80/api/folder/" + file.uuid);
                buttonDownload.addEventListener('click', onOpenFolder);
            }
            nameCol.appendChild(buttonDownload)

            row.appendChild(nameCol)
            row.appendChild(ownerCol)
            row.appendChild(typeCol)
            row.appendChild(dateCol)
            row.appendChild(shareCol)
            row.appendChild(deleteCol)

            filesContainer.appendChild(row);
        }))
        .then()
        .catch(error => console.log('error', error));
}

//=========== Delete ===================//
function onDelete(event) {
    event.preventDefault();

    let typeIndex = event.currentTarget.id.indexOf('_');
    let uuidIndex = event.currentTarget.id.indexOf('-');

    let type = event.currentTarget.id.substring(typeIndex + 1, uuidIndex);
    let uuid = event.currentTarget.id.substring(uuidIndex + 1);

    let requestOptions = {
        method: 'DELETE',
        redirect: 'follow'
    };

    if (type === 'file') {
        fetch("http://localhost:80/api/file/" + uuid, requestOptions)
            .then(onEnterFolder(currentFolderId))
            .catch(error => console.log('error', error));
    } else {

    }
}

//=========== Share Folder ==============//
function onShare(event) {
    let typeIndex = event.currentTarget.id.indexOf('_');
    let uuidIndex = event.currentTarget.id.indexOf('-');

    let type = event.currentTarget.id.substring(typeIndex + 1, uuidIndex);
    let uuid = event.currentTarget.id.substring(uuidIndex + 1);

    let data = new FormData()
    let input = document.getElementById("input_file_share-" + uuid)

    data.append('shareWith', input.value)
    data.append('uuid', uuid)

    let requestOptions = {
        method: 'POST',
        redirect: 'follow',
        body: data
    };

    if (type === 'file_share') {
        fetch("http://localhost:80/api/file/share/", requestOptions)
            .then(onEnterFolder(currentFolderId))
            .catch(error => console.log('error', error));
    } else {

    }
}

//=========== Open Folder ==============//
function onOpenFolder(event) {
    event.preventDefault();
    let uuid = event.currentTarget.id.substring(5); //TODO coppy logic from onDelete
    let name = event.currentTarget.innerHTML;

    if (name === '/..') {
        currentFolderName = currentFolderName.substring(0, currentFolderName.lastIndexOf('/'))
    } else {
        currentFolderName += name;
    }
    currentFolderId = uuid;

    onEnterFolder(uuid);
}

//=========== Upload File ==============//
let uploadFileBtn = document.getElementById('uploadFile')
uploadFileBtn.addEventListener('click', onUpload);

function onUpload(event) {
    let data = new FormData()
    let input = document.querySelector('input[type="file"]')
    data.append('content', input.files[0])
    data.append('parentFolderId', currentFolderId)

    let requestOptions = {
        method: 'POST',
        redirect: 'follow',
        body: data
    };

    fetch("http://localhost:80/api/upload/", requestOptions)
        .then(onEnterFolder(currentFolderId))
        .catch(error => console.log('error', error));
}

//============== Create Folder ===============//
let createFolderBtn = document.getElementById('createFolder');
createFolderBtn.addEventListener('click', onCreateFolder);

function onCreateFolder(event) {
    let folderName = document.getElementById('folderName');
    let data = new FormData();
    data.append('name', folderName.value);
    data.append('parentFolderId', currentFolderId);

    let requestOptions = {
        method: 'POST',
        redirect: 'follow',
        body: data
    };

    fetch("http://localhost:80/api/folder/create", requestOptions)
        .then(alert("Folder was created"))
        .catch(error => console.log('error', error));
}