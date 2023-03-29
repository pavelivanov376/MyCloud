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

