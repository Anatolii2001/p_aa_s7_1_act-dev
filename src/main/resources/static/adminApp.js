$(async function () {
    await getActiveUserInfo()
    await tableWithUsers()
    await createAdmin()
    await createManager()
    await createUserForm()
    await getDefaultModal()

    await tableWithRoles()
    await createRole()
    await sendEmail()
})

const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    getCurrentUser: async () => await fetch(`api`),
    findAllUsers: async () => await fetch(`api/users`),
    createAdmin: async (admin) => await fetch(`api/create/admin`, {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(admin)


    }),
    createManager: async (manager) => await fetch(`api/create/manager`, {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(manager)

    }),
    findUserById: async (id) => await fetch(`api/${id}`),
    deleteUser: async (id) => await fetch(`api/${id}`, {
        method: 'DELETE',
        headers: userFetchService.head
    }),
    editUser: async (admin) => await fetch(`api/edit`, {
        method: 'PUT',
        headers: userFetchService.head,
        body: JSON.stringify(admin)
    })
}
const emailFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    sendSimpleEmail: async (toAddress,subject,message) => await fetch(`email/${toAddress}/${subject}/${message}`,{
        method: 'GET',
        headers: emailFetchService.head,
    }),
}

const roleFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllRoles: async () => await fetch(`api/roles`),
    createRole: async (role) => await fetch(`api/roles/create`,{
        method: 'POST',
        headers: roleFetchService.head,
        body: JSON.stringify(role)
    }),
    findRoleById: async (id) => await fetch(`api/roles/${id}`),
    deleteRole: async (id) => await fetch(`api/roles/delete/${id}`, {
        method: 'DELETE',
        headers: roleFetchService.head
    }),

}




//------------------------ѕользователи----------------------------------------------------
// массив ролей
const roleJson = []

fetch('api/roles')
    .then(res => res.json())
    .then(roles => roles.forEach(role => roleJson.push(role)))

// информаци€ об активном пользователе
async function getActiveUserInfo() {
    let headInfo = $('#headInfo')
    let principal = await userFetchService.getCurrentUser();
    let user = principal.json();
    user.then(user => {
        let userInfoFilling = `
       <h6> <b> ${user.email}</b> with roles: ${user.roles.map(role => role.name)}</h6>
    `
        headInfo.append(userInfoFilling)
    })
}
    // таблица с пользовател€ми
    async function tableWithUsers() {

        const usersList = document.querySelector('#tableWithUsers')
        let result = ''

        await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => users.forEach(user => {
            result += `
                    <tr>
                         <td>${user.id}</td>
                         <td>${user.email}</td>
                         <td>${user.roles.map(role => role.name)}</td>
                         <td>
                             <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info"
                             data-toggle="modal" data-target="#someDefaultModal">Edit</button>
                         </td>
                         <td>
                             <button type="button" data-userid="${user.id}" data-action="delete" class="btn btn-danger"
                             data-toggle="modal" data-target="#someDefaultModal">Delete</button>
                         </td>
                    </tr>
            `
        }))
        usersList.innerHTML = result

        $("#tableWithUsers").find('button').on('click', (event) => {
            let defaultModal = $('#someDefaultModal');

            let targetButton = $(event.target);
            let buttonUserId = targetButton.attr('data-userid');
            let buttonAction = targetButton.attr('data-action');

            defaultModal.attr('data-userid', buttonUserId);
            defaultModal.attr('data-action', buttonAction);
            defaultModal.modal('show')
        })
    }
    // создание нового пользовател€
async function createUserForm() {
    let form = $(`#createUserForm`)

    fetch('/api/roles').then(function (response) {
        form.find('#newAdminRoles').empty();
        response.json().then(roleList => {
            roleList.forEach(role => {
                form.find('#newAdminRoles')
                    .append($('<option>').val(role.id).text(role.name));
            })
        })
    })
    fetch('/api/roles').then(function (response) {
        form.find('#newManagerRoles').empty();
        response.json().then(roleList => {
            roleList.forEach(role => {
                form.find('#newManagerRoles')
                    .append($('<option>').val(role.id).text(role.name));
            })
        })
    })

}
    // создание нового админа
    async function createAdmin() {
        $('#createAdminButton').click(async () => {
            let createUserForm = $('#createUserForm')
            let email = createUserForm.find('#newAdminEmail').val().trim();
            let password = createUserForm.find('#newAdminPassword').val().trim();
            let rolesArray = createUserForm.find('#newAdminRoles').val()
            let roles = []

            for (let r of roleJson) {
                for (let i = 0; i < rolesArray.length; i++) {
                    if (r.id == rolesArray[i]) {
                        roles.push(r)
                    }
                }
            }

            let data = {
                email: email,
                password: password,
                roles: roles
            }
            const response = await userFetchService.createAdmin(data);
            if (response.ok) {
                await tableWithUsers().update
                window.location.replace('/api/users')
                             location.reload()


            }
        })
    }
    // создание нового менеджера
    async function createManager() {
        $('#createManagerButton').click(async () => {
            let createUserForm = $('#createUserForm')
            let email = createUserForm.find('#newManagerEmail').val().trim();
            let password = createUserForm.find('#newManagerPassword').val().trim();
            let rolesArray = createUserForm.find('#newAdminRoles').val()
            let roles = []

            for (let r of roleJson) {
                for (let i = 0; i < rolesArray.length; i++) {
                    if (r.id == rolesArray[i]) {
                        roles.push(r)
                    }
                }
            }

            let data = {
                email: email,
                password: password,
                roles: roles
            }
            const response = await userFetchService.createManager(data);
            if (response.ok) {
                window.location = window.location.href
                modalForm.modal('hide')

            }
            location.reload()
        })
    }


    // удаление пользовател€
    async function deleteUser(modal, id) {
        let thisUser = await userFetchService.findUserById(id)
        let user = thisUser.json()
        let modalForm = $(`#someDefaultModal`)
        let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
        let deleteButton = `<button class="btn btn-danger" id="deleteButton" data-dismiss="modal" data-backdrop="false">Delete</button>`

        modal.find('.modal-title').html('Delete user')
        modal.find('.modal-footer').append(closeButton)
        modal.find('.modal-footer').append(deleteButton)


        user.then(user => {
            let bodyForm = `<form id="deleteUser">
                                        <div class="col-md-7 offset-md-3 text-center">
                                            <div class="form-group">
                                                <span class="font-weight-bold">ID</span>
                                                <input type="text" value="${user.id}" name="id" class="form-control" readonly>
                                            </div>                           
                                            <div class="form-group">
                                                <span class="font-weight-bold">Email</span>
                                                <input type="email" value="${user.email}" name="email" class="form-control" readonly>
                                            </div>
                                            <div class="form-group">
                                                <span class="font-weight-bold">Role</span>
                                                <select multiple class="form-control" id="rolesD" size="2" readonly>`

            modal.find('.modal-body').append(bodyForm)
        })

        fetch('/api/roles').then(function (response) {
            modalForm.find('#rolesD').empty();
            response.json().then(roleList => {
                roleList.forEach(role => {
                    modalForm.find('#rolesD')
                        .append($('<option>').val(role.id).text(role.name));
                })
            })
        })

        $(`#deleteButton`).on('click', async () => {
            const response = await userFetchService.deleteUser(id);
            if (response.ok) {
                tableWithUsers()
                modalForm.modal('hide');
            }
        })
    }
    // редактирование пользовател€

    async function editUser(modal, id) {
        let thisUser = await userFetchService.findUserById(id)
        let user = thisUser.json()
        let modalForm = $(`#someDefaultModal`)

        let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`
        let editButton = `<button class="btn btn-primary" id="editAdminButton" data-dismiss="modal" data-backdrop="false">Edit</button>`

        modal.find('.modal-title').html('Edit user')
        modal.find('.modal-footer').append(closeButton)
        modal.find('.modal-footer').append(editButton)

        user.then(user => {
            let bodyForm = `<form id="editUser">
                                        <div class="col-md-7 offset-md-3 text-center">
                                            <div class="form-group">
                                                <span class="font-weight-bold">ID</span>
                                                <input type="text" value="${user.id}" name="id" id="id" class="form-control" readonly>
                                            </div>               
                                                <span class="font-weight-bold">Email</span>
                                                <input type="email" value="${user.email}" name="email" id="email" class="form-control">
                                            </div>           
                                            <div class="form-group">
                                                <span class="font-weight-bold">Password</span>
                                                <input type="password" value="${user.password}" name="password" id="password" class="form-control">
                                            </div>
                                            <div class="form-group">
                                                <span class="font-weight-bold">Role</span>
                                                <select multiple class="form-control" id="editRoles" size="2">`
            modal.find('.modal-body').append(bodyForm)
        })

        fetch('/api/roles').then(function (response) {
            modalForm.find('#editRoles').empty();
            response.json().then(roleList => {
                roleList.forEach(role => {
                    modalForm.find('#editRoles')
                        .append($('<option>').val(role.id).text(role.name));
                })
            })
        })

        $("#editAdminButton").on('click', async () => {
            let id = modal.find('#id').val()
            let email = modal.find('#email').val()
            let password = modal.find('#password').val()
            let rolesArray = modal.find('#editRoles').val()
            let roles = []

            for (let r of roleJson) {
                for (let i = 0; i < rolesArray.length; i++) {
                    if (r.id == rolesArray[i]) {
                        roles.push(r)
                    }
                }
            }

            let data = {
                id: id,
                email: email,
                password: password,
                roles: roles
            }

            const response = await userFetchService.editUser(data)
            if (response.ok) {
                await tableWithUsers()._hideModal
            }


        })

    }

//------------------------–оли-----------------------------
//таблица ролей
    async function tableWithRoles() {
        const roleList = document.querySelector('#tableWithRoles')
        let resultRole = ''

        await roleFetchService.findAllRoles()
            .then(res => res.json())
            .then(roles => roles.forEach(role => {
                resultRole += `
                            <tr>
                                <td>${role.id}</td>
                                <td>${role.name}</td>
                                <td>
                                    <button type="button" data-userid="${role.id}" data-action="delete role" class="btn btn-danger"
                                    data-toggle="modal" data-target="#someDefaultModal">Delete</button>
                                </td>
                            </tr>
                    `
            }))
        roleList.innerHTML = resultRole
        $("#tableWithRoles").find('button').on('click', (event) => {
            let defaultModal = $('#someDefaultModal');

            let targetButton = $(event.target);
            let buttonUserId = targetButton.attr('data-userid');
            let buttonAction = targetButton.attr('data-action');

            defaultModal.attr('data-userid', buttonUserId);
            defaultModal.attr('data-action', buttonAction);
            defaultModal.modal('show')
        })
    }


// создание новой роли
async function createRole() {
    $('#createRoleButton').click(async () => {
        let createRoleForm = $('#createRoleForm')
        let name = createRoleForm.find('#newRoleName').val().trim();
        let data = {
            name: name
        }
        const response = await roleFetchService.createRole(data);
        if (response.ok) {

            await tableWithRoles()
            modal.modal('hide');
        }
    })
}



// удаление роли
async function deleteRole(modal, id) {
    let thisRole = await roleFetchService.findRoleById(id)
    let role = thisRole.json()
    let modalForm = $(`#someDefaultModal`)
    let deleteRoleButton = `<button class="btn btn-danger" id="deleteRoleButton" data-dismiss="modal" data-backdrop="false">Delete</button>`
    let closeButton = `<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>`

    modalForm.find('.modal-title').html('Delete')
    modalForm.find('.modal-footer').append(deleteRoleButton)
    modalForm.find('.modal-footer').append(closeButton)

    role.then(role => {
        let bodyForm = `<form id="deleteRole">
                            <div class="col-md-7 offset-md-3 text-center">
                                <div class="form-group">
                                    <span class="font-weight-bold">ID</span>
                                    <input type="number" value="${role.id}" name="id" class="form-control" readonly>
                                </div>
                                <div class="form-group">
                                    <span class="font-weight-bold">Name</span>
                                    <input type="text" value="${role.name}" name="name" class="form-control" disabled/>
                                </div>
                            `

        modalForm.find('.modal-body').append(bodyForm)
    })


    $(`#deleteRoleButton`).on('click', async () => {
        const response = await roleFetchService.deleteRole(id)
        if (response.ok) {
            await tableWithRoles()
            modalForm.modal('hide');
        } else {
            confirm("You cannot remove a role that is in use")
        }
    })
}


async function getDefaultModal() {
    $('#someDefaultModal').modal({
        keyboard: true,
        backdrop: "static",
        show: false
    }).on("show.bs.modal", (event) => {
        let thisModal = $(event.target);
        let userid = thisModal.attr('data-userid');
        let action = thisModal.attr('data-action');

        switch (action) {
            case 'edit':
                editUser(thisModal, userid);
                break;
            case 'delete':
                deleteUser(thisModal, userid);
                break;
            case 'delete role':
                deleteRole(thisModal, userid);
                break;
        }
    }).on("hidden.bs.modal", (e) => {

        let thisModal = $(e.target);
        thisModal.find('.modal-title').html('');
        thisModal.find('.modal-body').html('');
        thisModal.find('.modal-footer').html('');
    })
}
//email sending
async function sendEmail() {
    let modalForm = $(`#sendEmail`)
    let sendButton = `<button type="submit" class="btn btn-success btn-lg" id="sendEmailButton" data-toggle="modal" data-target="#modalSendEmail">ќтправить письмо</button>`
    modalForm.find('.modal-title').html('send Email')
    modalForm.find('.modal-footer').append(sendButton)

    $("#sendEmailButton").on('click', async () => {
        let textSubject = document.getElementById('textSubject').value
        let textMessage = document.getElementById('textMessage').value;
        let items = document.getElementsByName("toAddress");
        let toAddress
        for (let i = 0; i < items.length; i++) {
            if (items[i].checked) {
                toAddress = items[i].value
            }
        }

        const response = await emailFetchService.sendSimpleEmail(toAddress, textSubject, textMessage)
        if (response.ok) {
            console.log("Your message send!")
        } else {
            console.log("error")
        }
    })
}

