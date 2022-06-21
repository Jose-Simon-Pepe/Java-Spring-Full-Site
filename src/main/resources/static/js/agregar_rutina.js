var rutinasCargadas = [];

function agregarRutina(){
    let nombreRutina = document.getElementById("nombre_rutina").value;
    let diaRutina = document.getElementById("dia").value;
    let tablaRutinas = document.getElementById("tabla_rutinas");
    let inputRutinas = document.getElementById("input_rutinas")
    rutinasCargadas.push({nombreRutina,diaRutina});

    tablaRutinas.insertRow(-1).innerHTML = '<td>'+nombreRutina+'</td><td>'+diaRutina+'</td>';
    inputRutinas.value = JSON.stringify(rutinasCargadas);
}