//===========OnPageLoad================//
//Run once when page initially loaded for the root folder
let homeDirectoryID;
let currentFolderName = "/home/"
let currentFolderId;

window.onload = function () {
    let requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };
    //Get current user home folder id
    fetch("http://localhost:80/api/home/id/", requestOptions)
        .then(response => console.log(response))
        .then(response => console.log("AS JSON"+response.innerHTML()))
        .then(response => response.json())
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
    console.log("Entering folder" + folderIndex)

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

            ownerCol.textContent = file.owner
            typeCol.textContent = file.type
            dateCol.textContent = file.creationDate

            let buttonDownload = document.createElement('a')
            buttonDownload.textContent = file.name
            if (file.type == "file") {
                buttonDownload.setAttribute("id", "btn_" + "_" + file.name);
                buttonDownload.setAttribute("class", "openFile");
                buttonDownload.setAttribute("href", "http://localhost:80/api/download/" + file.name);
            } else {
                buttonDownload.setAttribute("id", "btn_" + "_" + file.uuid);
                buttonDownload.setAttribute("class", "openFolder");
                buttonDownload.setAttribute("href", "http://localhost:80/api/folder/" + file.uuid);
                buttonDownload.addEventListener('click', onOpenFolder);
            }
            nameCol.appendChild(buttonDownload)

            row.appendChild(nameCol)
            row.appendChild(ownerCol)
            row.appendChild(typeCol)
            row.appendChild(dateCol)

            filesContainer.appendChild(row);
        }))
        .then()
        .catch(error => console.log('error', error));
}

//=========== Open Folder ==============//

function onOpenFolder(event) {
    event.preventDefault();
    let uuid = event.currentTarget.id.substring(5);
    let name = event.currentTarget.innerHTML;

    currentFolderName += name;
    currentFolderId = uuid;

    onEnterFolder(uuid);
}

//=========== Upload File ==============//

let uploadFileBtn = document.getElementById('uploadFile')
uploadFileBtn.addEventListener('click', onUpload);

function onUpload(event) {
    let data = new FormData()
    let input = document.querySelector('input[type="file"]')
    // let fullPath = document.getElementById('path')
    data.append('content', input.files[0])
    data.append('parentFolderId', currentFolderId)

    let requestOptions = {
        method: 'POST',
        redirect: 'follow',
        body: data
    };

    fetch("http://localhost:80/api/upload/", requestOptions)
        .then(alert("File was uploaded"))
        .catch(error => console.log('error', error));
}

//============== Create Folder ===============//
//TODO Create folder fro given name and current folder we are into, starting from the root
let createFolderBtn = document.getElementById('createFolder');
createFolderBtn.addEventListener('click', onCreateFolder);

function onCreateFolder(event) {
    let folderName = document.getElementById('folderName');
    let data = new FormData();
    data.append('name', folderName);
    data.append('fullPath', fullPath);
    data.append('id', currentFolderId);

    let requestOptions = {
        method: 'POST',
        redirect: 'follow',
        body: data
    };

    fetch("http://localhost:80/api/folder/create", requestOptions)
        .then(alert("Folder was created"))
        .catch(error => console.log('error', error));
}

// maybe unused
// function onLoadHome() {
//     var requestOptions = {
//         method: 'GET',
//         redirect: 'follow'
//     };

//     let filesContainer = document.getElementById('files-container')
//     filesContainer.innerHTML = ''

//     fetch("http://localhost:80/api/files/", requestOptions)
//         .then(response => response.json())
//         .then(json => json.forEach(file => {
//             let row = document.createElement('tr')

//             let nameCol = document.createElement('td')
//             let pathCol = document.createElement('td')
//             let ownerCol = document.createElement('td')
//             let typeCol = document.createElement('td')
//             let dateCol = document.createElement('td')

//             pathCol.textContent = file.parentFolder
//             ownerCol.textContent = file.owner
//             typeCol.textContent = file.type
//             dateCol.textContent = file.creationDate

//             let buttonDownload = document.createElement('a')
//             buttonDownload.textContent = file.name
//             buttonDownload.setAttribute("href", "http://localhost:80/api/download/" + file.name);
//             nameCol.appendChild(buttonDownload)

//             row.appendChild(nameCol)
//             row.appendChild(pathCol)
//             row.appendChild(ownerCol)
//             row.appendChild(typeCol)
//             row.appendChild(dateCol)

//             filesContainer.appendChild(row);
//         }))
//         .catch(error => console.log('error', error));
// }

//=========== List Files ==============//

// let loadFilesBtn = document.getElementById('loadFiles')
// loadFilesBtn.addEventListener('click', onLoad);

// function onLoad(event) {
//     var requestOptions = {
//         method: 'GET',
//         redirect: 'follow'
//     };

//     let filesContainer = document.getElementById('files-container')
//     filesContainer.innerHTML = ''

//     fetch("http://localhost:80/api/files/", requestOptions)
//         .then(response => response.json())
//         .then(json => json.forEach(file => {
//             let row = document.createElement('tr')

//             let nameCol = document.createElement('td')
//             let pathCol = document.createElement('td')
//             let ownerCol = document.createElement('td')
//             let typeCol = document.createElement('td')
//             let dateCol = document.createElement('td')

//             pathCol.textContent = file.parentFolder
//             ownerCol.textContent = file.owner
//             typeCol.textContent = file.type
//             dateCol.textContent = file.creationDate

//             let buttonDownload = document.createElement('a')
//             buttonDownload.textContent = file.name
//             buttonDownload.setAttribute("href", "http://localhost:80/api/download/" + file.name);
//             nameCol.appendChild(buttonDownload)

//             row.appendChild(nameCol)
//             row.appendChild(pathCol)
//             row.appendChild(ownerCol)
//             row.appendChild(typeCol)
//             row.appendChild(dateCol)

//             filesContainer.appendChild(row);
//         }))
//         .catch(error => console.log('error', error));
// }
