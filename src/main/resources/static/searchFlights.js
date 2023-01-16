let url = "api/search";

async function refreshTable() {
    let newSearch = document.getElementById("newSearch");
    let formData = new FormData(newSearch);
    let from = formData.get('from');
    let to = formData.get('to');
    let when = formData.get('when');
    await fetch(url + '/' + from + '/' + to + '/' + when)
        .then(r => {
    let div = document.getElementById("modal-body");
    let tr = document.createElement('tr');
    tr.innerHTML = '';
    div.appendChild(tr)
        });

}


async function createSearch() {
    await refreshTable();
    let newSearch = document.getElementById("newSearch");
    let formData = new FormData(newSearch);
    let from = formData.get('from');
    let to = formData.get('to');
    let when = formData.get('when');
    await fetch(url + '/' + from + '/' + to + '/' + when)
        .then((response) => {
            return response.json();
        })
        .then((listSearch) => {
            let tbody = document.getElementById("modal-body");
            let tr = document.createElement('tr');
            listSearch.forEach((flights) => {
                tr.innerHTML =
                    '<br>' +
                    '<div>'+ when + ' there are '+ flights.length + ' flights ' + ' from ' + from + ' to ' + to + '</div>';
                flights.forEach((flight) => {
                    tr.innerHTML = tr.innerHTML +
                        '<br>' +
                        '<div className="_1YXwWzlF" id="bot-avia-js"><form className="VMV4PIP2" id="newSearch"><div className="_21sbWC6o" id="avia-controls">' +

                        '<label className="_2gBQjtZn TuPk5qeB _31Ft0Yn7 _35jdMK9A _1605ozhZ _2_piJ0Jp _3mh498_u sDZPTMsH _6AUfP-Nk _2VXqKGJZ TrU-Ts4n _3CFEopiy _1yJByOzm _1EeGlJBb _1rfy66XH _2G-XYnL7 _3LhrabYa _3n_DUhtS _3WT6dx7i _28IVP4Uf _1zrlazVu">' +
                        '<input readOnly className="_1_1Rhm09" autoComplete="off" name="id" data-test="" value="Flight # ' + flight.id + '"></label>' +


                        '<label className="_2gBQjtZn TuPk5qeB _31Ft0Yn7 _35jdMK9A _1605ozhZ _2_piJ0Jp _3mh498_u sDZPTMsH _6AUfP-Nk _2VXqKGJZ TrU-Ts4n _3CFEopiy _1yJByOzm _1EeGlJBb _1rfy66XH _2G-XYnL7 _3LhrabYa _3n_DUhtS _3WT6dx7i _28IVP4Uf _1zrlazVu">' +
                        '<input readOnly className="_1_1Rhm09" name="id" value="' + flight.aircraft + '"></label>' +

                        /*'<label className="_2gBQjtZn TuPk5qeB _31Ft0Yn7 _35jdMK9A _1605ozhZ _2_piJ0Jp _3mh498_u sDZPTMsH _6AUfP-Nk _2VXqKGJZ TrU-Ts4n _3CFEopiy _1yJByOzm _1EeGlJBb _1rfy66XH _2G-XYnL7 _3LhrabYa _3n_DUhtS _3WT6dx7i _28IVP4Uf _1zrlazVu">' +
                        '<input readOnly className="_1_1Rhm09" name="id" value="' + flight.to + '"></label>' +*/

                        '<label className="_2gBQjtZn TuPk5qeB _31Ft0Yn7 _35jdMK9A _1605ozhZ _2_piJ0Jp _3mh498_u sDZPTMsH _6AUfP-Nk _2VXqKGJZ TrU-Ts4n _3CFEopiy _1yJByOzm _1EeGlJBb _1rfy66XH _2G-XYnL7 _3LhrabYa _3n_DUhtS _3WT6dx7i _28IVP4Uf _1zrlazVu">' +
                        '<input readOnly className="_1_1Rhm09" name="id" value=' + flight.date + '></label>' +

                        '<label className="_2gBQjtZn TuPk5qeB _31Ft0Yn7 _35jdMK9A _1605ozhZ _2_piJ0Jp _3mh498_u sDZPTMsH _6AUfP-Nk _2VXqKGJZ TrU-Ts4n _3CFEopiy _1yJByOzm _1EeGlJBb _1rfy66XH _2G-XYnL7 _3LhrabYa _3n_DUhtS _3WT6dx7i _28IVP4Uf _1zrlazVu">' +
                        '<input readOnly className="_1_1Rhm09" name="id" value="' + flight.seats + ' free seats"></label>' +

                        '<label className="_2gBQjtZn TuPk5qeB _31Ft0Yn7 _35jdMK9A _1605ozhZ _2_piJ0Jp _3mh498_u sDZPTMsH _6AUfP-Nk _2VXqKGJZ TrU-Ts4n _3CFEopiy _1yJByOzm _1EeGlJBb _1rfy66XH _2G-XYnL7 _3LhrabYa _3n_DUhtS _3WT6dx7i _28IVP4Uf _1zrlazVu">' +
                        '<input readOnly className="_1_1Rhm09" name="id" value="$' + flight.fare + '"></label>' +

                        '<button type="button" class="DS__Button__root DS__Button__theme_b2c DS__Button__size_small"' +
                        ' data-toggle="modal" ' + 'data-whatever="' + flight.id +
                        '" data-target="#getFlight">Make a reservation</button>' +
                        '</div></form></div>' +
                        '<br>';
                    tbody.appendChild(tr);
                });
            });
        })
        .then($('#table_result').tab('show'))
}

async function getSearch(from, to, when) {
    let response = await fetch(url + '/{' + from + '}/{' + to + '}/{' + when + '}');
    return await response.json();
}

async function showEditModal(id) {
    document.getElementById("editId").value = id;

}
