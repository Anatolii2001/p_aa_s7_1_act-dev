$(async function () {
    await getCurrentPassenger()
    await editPersonalData()
    await editPersonalContacts()
    await editPersonalDocuments()
    await editPersonalAccountSettings()
})

// FETCH
const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },

    getCurrentUser: async () => await fetch('api/passenger/current'),
    edit: async (passenger) => await fetch('api/passenger/edit', {
        method: 'PUT',
        headers: userFetchService.head,
        body: JSON.stringify(passenger)
    })
}

// PASSENGER'S ACCOUNT MAIN PAGE
async function getCurrentPassenger() {
    let userInfo = $('#userInfo')
    let personalData = $('#personalData')
    let personalContacts = $('#personalContacts')
    let personalDocuments = $('#personalDocuments')
    let personalAccountSettings = $('#personalAccountSettings')
    userInfo.empty()
    personalData.empty()
    personalContacts.empty()
    personalDocuments.empty()
    personalAccountSettings.empty()

    let principal = await userFetchService.getCurrentUser()
    let principalJson = principal.json()

// GREETINGS
    principalJson.then(user => {
        let tableUserInfo = `
                <h4>Hello, ${user.firstName}!</h4>
            `
        userInfo.append(tableUserInfo)
    })

// PERSONAL DATA
    principalJson.then(user => {
        let tablePersonalData = `
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.middleName}</td>
            <td>${user.dateOfBirth}</td>
            <td>
                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-outline-info"
                        data-toggle="modal" data-target="#modalPersonalData">Edit</button>
            </td>            
        `
        personalData.append(tablePersonalData)
    })

// PERSONAL CONTACTS
    principalJson.then(user => {
        let tablePersonalContacts = `
            <td>${user.email}</td>
            <td>${user.phoneNumber}</td>
            <td>
                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-outline-info"
                        data-toggle="modal" data-target="#modalPersonalContacts">Edit</button>
            </td> 
        `
        personalContacts.append(tablePersonalContacts)
    })

// PERSONAL DOCUMENTS
    principalJson.then(user => {
        let rowPersonalDocuments = `
            <td>${user.passport.passportNumber}</td>
            <td>${user.passport.expiryDate}</td>
            <td>
                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-outline-info"
                        data-toggle="modal" data-target="#modalPersonalDocuments">Edit</button>
            </td> 
        `
        personalDocuments.append(rowPersonalDocuments)
    })

// PERSONAL ACCOUNT SETTINGS
    principalJson.then(user => {
        let tablePersonalAccountSettings = `
            <td>${user.email}</td>
            <td>**************</td>
            <td>
                <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-outline-info"
                        data-toggle="modal" data-target="#modalPersonalAccountSettings">Edit</button>
            </td>            
        `
        personalAccountSettings.append(tablePersonalAccountSettings)
    })
}

// EDIT PERSONAL DATA
async function editPersonalData() {
    let thisUser = await userFetchService.getCurrentUser()
    let user = thisUser.json()
    let modalForm = $(`#modalPersonalData`)
    let editButton = `<button class="btn btn-outline-info" id="editButtonPersonalData" data-dismiss="modal" data-backdrop="false">Edit</button>`
    let closeButton = `<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>`

    modalForm.find('.modal-title').html('Edit Personal Data')
    modalForm.find('.modal-footer').append(editButton)
    modalForm.find('.modal-footer').append(closeButton)

    user.then(user => {
        let bodyForm = `<form id="editPersonalData">
                            <div class="col-md-7 offset-md-3 text-center">
                                <div class="form-group">
                                    <input type="hidden" value="${user.id}" name="id" id="id" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.email}" name="email" id="email" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.password}" name="password" id="password" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">First Name</span>
                                    <input type="text" value="${user.firstName}" name="firstName" id="firstName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">Last Name</span>
                                    <input type="text" value="${user.lastName}" name="lastName" id="lastName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">Middle Name</span>
                                    <input type="text" value="${user.middleName}" name="middleName" id="middleName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.phoneNumber}" name="phoneNumber" id="phoneNumber" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">Date of Birth</span>
                                    <input type="date" value="${user.dateOfBirth}" name="dateOfBirth" id="dateOfBirth" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.id}" name="passportId" id="passportId" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.passportNumber}" name="passportNumber" id="passportNumber" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.expiryDate}" name="expiryDate" id="expiryDate" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.nationality}" name="nationality" id="nationality" class="form-control"/>
                                </div>
                                `
        modalForm.find('.modal-body').append(bodyForm)
    })

    $("#editButtonPersonalData").on('click', async () => {
        let id = modalForm.find('#id').val()
        let email = modalForm.find('#email').val()
        let password = modalForm.find('#password').val()
        let firstName = modalForm.find('#firstName').val()
        let lastName = modalForm.find('#lastName').val()
        let middleName = modalForm.find('#middleName').val()
        let phoneNumber = modalForm.find('#phoneNumber').val()
        let dateOfBirth = modalForm.find('#dateOfBirth').val()
        let passportId = modalForm.find('#passportId').val()
        let passportNumber = modalForm.find('#passportNumber').val()
        let expiryDate = modalForm.find('#expiryDate').val();
        let nationality = modalForm.find('#nationality').val()

        let data = {
            id: id,
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            middleName: middleName,
            phoneNumber: phoneNumber,
            dateOfBirth: dateOfBirth,
            passport: {
                id: passportId,
                passportNumber: passportNumber,
                expiryDate: expiryDate,
                nationality: nationality
            }
        }

        const response = await userFetchService.edit(data)
        if (response.ok) {
            window.location = window.location.href
            await getCurrentPassenger()
            modalForm.modal('hide')
        }
    })
}

// EDIT PERSONAL CONTACTS
async function editPersonalContacts() {
    let thisUser = await userFetchService.getCurrentUser()
    let user = thisUser.json()
    let modalForm = $(`#modalPersonalContacts`)
    let editButton = `<button class="btn btn-outline-info" id="editButtonPersonalContacts" data-dismiss="modal" data-backdrop="false">Edit</button>`
    let closeButton = `<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>`

    modalForm.find('.modal-title').html('Edit Personal Contacts')
    modalForm.find('.modal-footer').append(editButton)
    modalForm.find('.modal-footer').append(closeButton)

    user.then(user => {
        let bodyForm = `<form id="editPersonalContacts">
                            <div class="col-md-7 offset-md-3 text-center">
                                <div class="form-group">
                                    <input type="hidden" value="${user.id}" name="id" id="id" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">E-mail</span>
                                    <input type="email" value="${user.email}" name="email" id="email" class="form-control" readonly/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.password}" name="password" id="password" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.firstName}" name="firstName" id="firstName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.lastName}" name="lastName" id="lastName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.middleName}" name="middleName" id="middleName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">Phone Number</span>
                                    <input type="tel" value="${user.phoneNumber}" name="phoneNumber" id="phoneNumber" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.dateOfBirth}" name="dateOfBirth" id="dateOfBirth" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.id}" name="passportId" id="passportId" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.passportNumber}" name="passportNumber" id="passportNumber" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.expiryDate}" name="expiryDate" id="expiryDate" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.nationality}" name="nationality" id="nationality" class="form-control"/>
                                </div>
                                `
        modalForm.find('.modal-body').append(bodyForm)
    })


    $("#editButtonPersonalContacts").on('click', async () => {
        let id = modalForm.find('#id').val()
        let email = modalForm.find('#email').val()
        let password = modalForm.find('#password').val()
        let firstName = modalForm.find('#firstName').val()
        let lastName = modalForm.find('#lastName').val()
        let middleName = modalForm.find('#middleName').val()
        let phoneNumber = modalForm.find('#phoneNumber').val()
        let dateOfBirth = modalForm.find('#dateOfBirth').val()
        let passportId = modalForm.find('#passportId').val()
        let passportNumber = modalForm.find('#passportNumber').val()
        let expiryDate = modalForm.find('#expiryDate').val();
        let nationality = modalForm.find('#nationality').val()

        let data = {
            id: id,
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            middleName: middleName,
            phoneNumber: phoneNumber,
            dateOfBirth: dateOfBirth,
            passport: {
                id: passportId,
                passportNumber: passportNumber,
                expiryDate: expiryDate,
                nationality: nationality
            }
        }

        const response = await userFetchService.edit(data)
        if (response.ok) {
            window.location = window.location.href
            await getCurrentPassenger()
            modalForm.modal('hide')
        }
    })
}

// EDIT PERSONAL DOCUMENTS
async function editPersonalDocuments() {
    let thisUser = await userFetchService.getCurrentUser()
    let user = thisUser.json()
    let modalForm = $(`#modalPersonalDocuments`)
    let editButton = `<button class="btn btn-outline-info" id="editButtonPersonalDocuments" data-dismiss="modal" data-backdrop="false">Edit</button>`
    let closeButton = `<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>`

    modalForm.find('.modal-title').html('Edit Personal Documents')
    modalForm.find('.modal-footer').append(editButton)
    modalForm.find('.modal-footer').append(closeButton)

    user.then(user => {
        let bodyForm = `<form id="editPersonalDocuments" novalidate>
                            <div class="col-md-7 offset-md-3 text-center">
                                <div class="form-group">
                                    <input type="hidden" value="${user.id}" name="id" id="id" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.email}" name="email" id="email" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.password}" name="password" id="password" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.firstName}" name="firstName" id="firstName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.lastName}" name="lastName" id="lastName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.middleName}" name="middleName" id="middleName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.phoneNumber}" name="phoneNumber" id="phoneNumber" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.dateOfBirth}" name="dateOfBirth" id="dateOfBirth" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.id}" name="passportId" id="passportId" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">Passport of a citizen of the Russian Federation</span>
                                    <input type="text" value="${user.passport.passportNumber}" name="passportNumber" id="passportNumber" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">Expiry Date</span>
                                    <input type="date" value="${user.passport.expiryDate}" name="expiryDate" id="expiryDate" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.nationality}" name="nationality" id="nationality" class="form-control"/>
                                </div>
                                `
        modalForm.find('.modal-body').append(bodyForm)
    })


    $("#editButtonPersonalDocuments").on('click', async () => {
        let id = modalForm.find('#id').val()
        let email = modalForm.find('#email').val()
        let password = modalForm.find('#password').val()
        let firstName = modalForm.find('#firstName').val()
        let lastName = modalForm.find('#lastName').val()
        let middleName = modalForm.find('#middleName').val()
        let phoneNumber = modalForm.find('#phoneNumber').val()
        let dateOfBirth = modalForm.find('#dateOfBirth').val()
        let passportId = modalForm.find('#passportId').val()
        let passportNumber = modalForm.find('#passportNumber').val()
        let expiryDate = modalForm.find('#expiryDate').val();
        let nationality = modalForm.find('#nationality').val()

        let data = {
            id: id,
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            middleName: middleName,
            phoneNumber: phoneNumber,
            dateOfBirth: dateOfBirth,
            passport: {
                id: passportId,
                passportNumber: passportNumber,
                expiryDate: expiryDate,
                nationality: nationality
            }
        }

        const response = await userFetchService.edit(data)
        if (response.ok) {
            window.location = window.location.href
            await getCurrentPassenger()
            modalForm.modal('hide')
        }
    })
}

// EDIT PERSONAL ACCOUNT SETTING
async function editPersonalAccountSettings() {
    let thisUser = await userFetchService.getCurrentUser()
    let user = thisUser.json()
    let modalForm = $(`#modalPersonalAccountSettings`)
    let editButton = `<button type="submit" class="btn btn-info" id="editButtonPersonalAccountSettings"
                                                                                    data-backdrop="false" disabled>Edit</button>`
    let closeButton = `<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>`

    modalForm.find('.modal-title').html('Edit Personal Account Settings')
    modalForm.find('.modal-footer').append(editButton)
    modalForm.find('.modal-footer').append(closeButton)

    user.then(user => {
        let bodyForm = `<form class="needs-validation" id="editPersonalAccountSettingsForm">
                            <div class="col-md-7 offset-md-3 text-center">
                                <div class="form-group">
                                    <input type="hidden" value="${user.id}" name="id" id="id" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">E-mail</span>
                                    <input type="email" value="${user.email}" name="emailConfirm" id="emailConfirm" class="form-control" 
                                           placeholder="Enter E-mail" aria-describedby="emailHelpBlock" onchange="toggleButton()"/>
                                        <small id="emailHelpBlock" class="form-text text-muted">
                                            E-mail field is required
                                        </small>                                         
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">Password</span>
                                    <input type="password" name="passwordConfirm" id="passwordConfirm" class="form-control" 
                                           placeholder="Enter Password" aria-describedby="passwordHelpBlock" 
                                           maxlength="20" onchange="toggleButton()"/>
                                        <small id="passwordHelpBlock" class="form-text text-muted">
                                            Password field is required, up to 20 characters
                                        </small>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.firstName}" name="firstName" id="firstName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.lastName}" name="lastName" id="lastName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.middleName}" name="middleName" id="middleName" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.phoneNumber}" name="phoneNumber" id="phoneNumber" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.dateOfBirth}" name="dateOfBirth" id="dateOfBirth" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.id}" name="passportId" id="passportId" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.passportNumber}" name="passportNumber" id="passportNumber" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.expiryDate}" name="expiryDate" id="expiryDate" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <input type="hidden" value="${user.passport.nationality}" name="nationality" id="nationality" class="form-control"/>
                                </div>
                                `
        modalForm.find('.modal-body').append(bodyForm)
    })

    $("#editButtonPersonalAccountSettings").on('click', async () => {
        let id = modalForm.find('#id').val()
        let email = modalForm.find('#emailConfirm').val()
        let password = modalForm.find('#passwordConfirm').val()
        let firstName = modalForm.find('#firstName').val()
        let lastName = modalForm.find('#lastName').val()
        let middleName = modalForm.find('#middleName').val()
        let phoneNumber = modalForm.find('#phoneNumber').val()
        let dateOfBirth = modalForm.find('#dateOfBirth').val()
        let passportId = modalForm.find('#passportId').val()
        let passportNumber = modalForm.find('#passportNumber').val()
        let expiryDate = modalForm.find('#expiryDate').val();
        let nationality = modalForm.find('#nationality').val()

        let data = {
            id: id,
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            middleName: middleName,
            phoneNumber: phoneNumber,
            dateOfBirth: dateOfBirth,
            passport: {
                id: passportId,
                passportNumber: passportNumber,
                expiryDate: expiryDate,
                nationality: nationality
            }
        }

        const response = await userFetchService.edit(data)
        if (response.ok) {
            window.location = window.location.href
            await getCurrentPassenger()
            modalForm.modal('hide')
        }
    })
}


