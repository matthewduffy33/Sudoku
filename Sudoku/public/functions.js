var ongoingFunction = false;
var currentX;
var currentY;


function addLetter(x, y){

    hideRedo(); //if a letter is added then a redo should not be possible and an undo should be
    showUndo();

    var value = document.getElementById("Num"+"x"+x+"y"+y).value; //get the value from the input box

    var pos = document.getElementById("x"+x+"y"+y).className.search("val");
    if(pos!=-1){
            var val = document.getElementById("x"+x+"y"+y).className[pos+3];
            unhighlightChanged(val, x, y);
            document.getElementById("x"+x+"y"+y).classList.remove("val"+val);

    }


    highlightChanged(value);

    if(value != ""){
        if(document.getElementById("Option"+"x"+x+"y"+y)){
            document.getElementById("Option"+"x"+x+"y"+y).innerHTML="";
        }

        document.getElementById("optionSection").classList.add("optionHidden");
        document.getElementById("x"+x+"y"+y).classList.add("val"+value);

    }else{
        getOptions(x, y)
    }

    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var response = JSON.parse(xhttp.responseText);
        if(response["isMistake"]){
            mistakeShow("x"+x+"y"+y);
        }else{
            mistakeHide("x"+x+"y"+y);
        }

        if(response["correct"]){
            var correctModal = new bootstrap.Modal(document.getElementById("correctModal"))
            correctModal.show()
        }else if(response["correct"]==false){
            var incorrectModal = new bootstrap.Modal(document.getElementById("incorrectModal"))
            incorrectModal.show()
        }
    }
    if(value != ""){
        xhttp.open("GET", "/sudoku/play/add?func=value&x="+x+"&y="+y+"&value="+value);
    }else{
        xhttp.open("GET", "/sudoku/play/add?func=value&x="+x+"&y="+y+"&value="+0);
    }

    xhttp.send();
}

function getOptions(x, y){
    var request = new XMLHttpRequest();

    request.onload = function() {
        var options = JSON.parse(request.responseText);
        if(options.length<9){
            for (let i = 0; i < options.length; i++) {

                document.getElementById("Optionx"+x+"y"+y).innerHTML+=options[i] +" ";


            }
        }
    }

    request.open("GET", "/sudoku/play/getOptions?x="+x+"&y="+y);
    request.send();
}


function optionsSet(x, y){
    var vals = document.getElementById("Optionx"+x+"y"+y).innerHTML;
    if(vals != ""){
        for (let i = 1; i <= 9; i++){
            if(!vals.includes(i)){
                document.getElementById("option"+i).classList.add("clickedIn");
            }else{
                if(document.getElementById("option"+i).classList.contains("clickedIn")){
                    document.getElementById("option"+i).classList.remove("clickedIn");
                }

            }

        }
    }else{
        for (let i = 1; i <= 9; i++){
            if(document.getElementById("option"+i).classList.contains("clickedIn")){
                document.getElementById("option"+i).classList.remove("clickedIn");
            }

        }
    }
}



function hint(){
    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var hint = JSON.parse(xhttp.responseText);
        document.getElementById("Numx"+hint["x"]+"y"+hint["y"]).value=hint["value"];
        document.getElementById("Optionx"+hint["x"]+"y"+hint["y"]).innerHTML="";
        newSelect(hint["x"], hint["y"]);

        if(hint["correct"]){
            var correctModal = new bootstrap.Modal(document.getElementById("correctModal"))
            correctModal.show()

        }else if(hint["correct"]==false){
            var incorrectModal = new bootstrap.Modal(document.getElementById("incorrectModal"))
            incorrectModal.show()
        }

    }

    xhttp.open("GET", "/sudoku/play/hint");
    xhttp.send();

}


function flipMistake(){
    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var response = JSON.parse(xhttp.responseText);

        if(response["hideMistakes"]){
            document.getElementById("mistakeBtn").innerText="Show Mistakes";

            var prev = document.querySelectorAll(".mistakes");

            for (i = 0; i < prev.length; i++) {
                if(prev[i].id.length<6){
                    mistakeHide(prev[i].id);
                }

            }
        }else{

            document.getElementById("mistakeBtn").innerText="Hide Mistakes";

            for (i = 0; i < response["showMistakes"].length; i++) {

                mistakeShow(response["showMistakes"][i]);
            }

        }

    }

    xhttp.open("GET", "/sudoku/play/flipMistakes");

    xhttp.send();

}


function mistakeShow(pos){
    if(!document.getElementById(pos).classList.contains("mistakes")){
        document.getElementById(pos).classList.add("mistakes");
        document.getElementById("Num"+pos).classList.add("mistakes");
    }

}

function mistakeHide(pos){
    if(document.getElementById(pos).classList.contains("mistakes")){
        document.getElementById(pos).classList.remove("mistakes");
        document.getElementById("Num"+pos).classList.remove("mistakes");
    }

}



function maxLengthCheck(object){

    if(!object.value.match(/^[1-9]+$/)){
        object.value = object.value.slice(-1);
    }

    if(object.value.length > 1){
        object.value = object.value.slice(-1);
    }

    if(parseInt(object.value)==0){
            object.value = "";
    }

}

function shiftOption(num){
    hideRedo();
    showUndo();
    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var options = JSON.parse(xhttp.responseText);
        newOptions(options);

    }

    if(document.getElementById("option"+num).classList.contains("clickedIn")){
        document.getElementById("option"+num).classList.remove("clickedIn");
        xhttp.open("GET", "/sudoku/play/option?func=addOption&x="+currentX+"&y="+currentY+"&value="+num);

    }else{
        document.getElementById("option"+num).classList.add("clickedIn");
        xhttp.open("GET", "/sudoku/play/option?func=removeOption&x="+currentX+"&y="+currentY+"&value="+num);
    }


    xhttp.send();
    document.getElementById("Numx"+currentX+"y"+currentY).select();

}

function newOptions(options){
    document.getElementById("Optionx"+currentX+"y"+currentY).innerHTML="";


    if(options.length<9){
        for (let i = 0; i < options.length; i++) {

            document.getElementById("Optionx"+currentX+"y"+currentY).innerHTML+=options[i] +" ";

        }
    }

}


function selecting(x, y){
    if(document.getElementById("x"+x+"y"+ y).classList.contains("sudokuCellNumber")){
        document.getElementById("Numx"+x+"y"+y).select();

        if(document.getElementById("Numx"+x+"y"+y).value===""){
            if(document.getElementById("optionSection").classList.contains("optionHidden")){
                document.getElementById("optionSection").classList.remove("optionHidden");
            }
        }else{
            if(!document.getElementById("optionSection").classList.contains("optionHidden")){
                document.getElementById("optionSection").classList.add("optionHidden");
            }

        }
    }else{
        if(!document.getElementById("optionSection").classList.contains("optionHidden")){
            document.getElementById("optionSection").classList.add("optionHidden");
        }
    }

    document.getElementById("x"+x+"y"+y).classList.add("selected");
    highlight(x, y, "Selected");
    optionsSet(x, y);

    currentX=x;
    currentY=y;
}



