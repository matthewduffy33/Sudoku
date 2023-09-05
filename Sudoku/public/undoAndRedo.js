function showUndo() {  //shows undo button
    if(document.getElementById("undoBtn").disabled){
        document.getElementById("undoBtn").disabled = false;

    }
}


function showRedo(){   //shows redo button
    if(document.getElementById("redoBtn").disabled){
        document.getElementById("redoBtn").disabled = false;

    }

}

function hideRedo(){   //hides redo button
    if(document.getElementById("redoBtn").disabled == false){
        document.getElementById("redoBtn").disabled = true;

    }
}



function undo(btn){

    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        if(xhttp.responseText == "error"){ //if error with undo then show error modal
           var errorModal = new bootstrap.Modal(document.getElementById("errorModal"))
           errorModal.show();

        }else{
            var response = JSON.parse(xhttp.responseText);
            responseHandler(response);

            checkUndo();//checks if there is still an undo and shows a redo
            showRedo();
            mistakeHide("x"+response["index0"]["x"] + "y"+response["index0"]["y"]); //hide any mistakes made on undone cell

        }
        btn.disabled = false;
    }
    xhttp.open("GET", "/sudoku/play/undo");
    xhttp.send();


}

function redo(btn){

    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        if(xhttp.responseText == "error"){  //if error with undo then show error modal
            var errorModal = new bootstrap.Modal(document.getElementById("errorModal"))
            errorModal.show();
        }else{

            var response = JSON.parse(xhttp.responseText);
            responseHandler(response);

            showUndo();  //checks if there is still a redo and shows an undo
            checkRedo();

            if(document.getElementById("mistakeBtn").innerText=="Hide Mistakes"){  //hide any mistakes made on undone cell
                mistakeShow("x"+response["index0"]["x"] + "y"+response["index0"]["y"]);
            }

        }

        btn.disabled = false;
    }
    xhttp.open("GET", "/sudoku/play/redo");
    xhttp.send();

}


function responseHandler(response){
    for (res in response){  //loop through response
        var x = response[res]["x"];
        var y = response[res]["y"];

        //for whichever type it is do the action
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


function checkUndo(){  //checks if there is something to undo
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

function checkRedo(){  //checks if there is something to redo
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


