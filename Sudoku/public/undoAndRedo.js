function showUndo() {
    if(document.getElementById("undoBtn").disabled){
        document.getElementById("undoBtn").disabled = false;

    }
}

function undo(btn){

    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        if(xhttp.responseText == "error"){
           var errorModal = new bootstrap.Modal(document.getElementById("errorModal"))
           errorModal.show();
        }else{

            var response = JSON.parse(xhttp.responseText);
            responseHandler(response);

            checkUndo();
            showRedo();
            mistakeHide("x"+response["index0"]["x"] + "y"+response["index0"]["y"]);

        }
        btn.disabled = false;
    }
    xhttp.open("GET", "/sudoku/play/undo");
    xhttp.send();


}

function redo(btn){

    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        if(xhttp.responseText == "error"){
            var errorModal = new bootstrap.Modal(document.getElementById("errorModal"))
            errorModal.show();
        }else{

            var response = JSON.parse(xhttp.responseText);
            responseHandler(response);

            showUndo();
            checkRedo();

            if(document.getElementById("mistakeBtn").innerText=="Hide Mistakes"){
                mistakeShow("x"+response["index0"]["x"] + "y"+response["index0"]["y"]);
            }

        }

        btn.disabled = false;
    }
    xhttp.open("GET", "/sudoku/play/redo");
    xhttp.send();

}


function responseHandler(response){
    for (res in response){
        var x = response[res]["x"];
        var y = response[res]["y"];

        if(response[res]["type"]=="addValue"){
            document.getElementById("Numx"+x+"y"+y).value = response[res]["value"];
            document.getElementById("Optionx"+x+"y"+y).innerHTML=""
            document.getElementById("optionSection").classList.add("optionHidden");
            newSelect(x, y);

        }else if(response[res]["type"]=="removeValue"){
            document.getElementById("Numx"+x+"y"+y).value = "";
            getOptions(x,y);
            newSelect(x, y);

        }else if(response[res]["type"]=="addOption"){
            newSelect(x, y);
            newOptions(response[res]["options"]);
            document.getElementById("option"+response[res]["value"]).classList.remove("clickedIn");

        }else{
            newSelect(x, y);
            newOptions(response[res]["options"]);
            document.getElementById("option"+response[res]["value"]).classList.add("clickedIn");

        }

    }
}


function checkUndo(){
    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var response = xhttp.responseText;
        if(response==="false"){
            document.getElementById("undoBtn").disabled = true;
        }
    }
    xhttp.open("GET", "/sudoku/play/checkUndo");
    xhttp.send();
}

function checkRedo(){
    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var response = xhttp.responseText;
        if(response==="false"){
            document.getElementById("redoBtn").disabled = true;
        }
    }
    xhttp.open("GET", "/sudoku/play/checkRedo");
    xhttp.send();
}


function showRedo(){
    if(document.getElementById("redoBtn").disabled){
        document.getElementById("redoBtn").disabled = false;

    }

}

function hideRedo(){
    if(document.getElementById("redoBtn").disabled == false){
        document.getElementById("redoBtn").disabled = true;

    }
}