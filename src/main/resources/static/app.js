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
            // here we will create some elements and add them to the table.
            let row = document.createElement('tr')

            let nameCol = document.createElement('td')
            nameCol.setAttribute("onclick", "tdclick(event);");
            let pathCol = document.createElement('td')
            let ownerCol = document.createElement('td')
            let typeCol = document.createElement('td')
            let dateCol = document.createElement('td')

            nameCol.textContent = file.name
            pathCol.textContent = file.parentFolder
            ownerCol.textContent = file.owner
            typeCol.textContent = file.type
            dateCol.textContent = file.creationDate

            // add the columns to the parent row
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
//=========== Download ==============//
function onDownload(event) {
let fileName = event.srcElement.childNodes[0].nodeValue
    var requestOptions = {
               method: 'GET',
               redirect: 'follow'
       }

       let url = "http://localhost:80/api/download/" + fileName
       fetch(url, requestOptions)//TODO Directly add the attribute download and href to the listing method and remove this OnDownload()
           .then(alert("File was downloaded"))
           .then(res => console.log(res))
           .then(res => console.log(res.content))
           .then(res => res.blob())
           .then(data => {
             var a = document.createElement("a");
             a.href = window.URL.createObjectURL(data);
             a.download = "FILENAME";
             a.click();
           .catch(error => console.log('error', error));
}

//==============TODO on hover make blue color

//==============OnClick
function tdclick(event){

    onDownload(event)


    console.log('td clicked');
    console.log(event.srcElement.childNodes[0].nodeValue);
    event.stopPropagation()
};
