var ongoingFunction = false;
var currentX;
var currentY;

window.history.replaceState(null, '', window.location.pathname);  //removes any remaining requests on header



function addLetter(x, y){

    hideRedo(); //if a letter is added then a redo should not be possible and an undo should be
    showUndo();

    var value = document.getElementById("Num"+"x"+x+"y"+y).value; //get the value from the input box

    var pos = document.getElementById("x"+x+"y"+y).className.search("val");  //checks if value is already in cell

    if(pos!=-1){
            var val = document.getElementById("x"+x+"y"+y).className[pos+3];  //gets the value of the previous number in cell
            unhighlightChanged(val, x, y);  //unhighlight the old number
            document.getElementById("x"+x+"y"+y).classList.remove("val"+val);  //remove reference to old number

    }


    highlightChanged(value);  //highlight the new number

    if(value != ""){
        if(document.getElementById("Option"+"x"+x+"y"+y)){ //remove the options from showing
            document.getElementById("Option"+"x"+x+"y"+y).innerHTML="";
        }

        document.getElementById("optionSection").classList.add("optionHidden");  //hide the options/notes tab
        document.getElementById("x"+x+"y"+y).classList.add("val"+value);

    }else{ //if removing number then put the options back
        getOptions(x, y)
    }

    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var response = JSON.parse(xhttp.responseText);
        if(response["isMistake"]){  //if response is a mistake highlight it as such
            mistakeShow("x"+x+"y"+y);
        }else{  //if not then hide the highlight
            mistakeHide("x"+x+"y"+y);
        }

        if(response["correct"]){  //if the response is correct then game is correctly done so display correct modal
            var correctModal = new bootstrap.Modal(document.getElementById("correctModal"))
            correctModal.show()
        }else if(response["correct"]==false){ //if the response is not correct then game is not correctly done so display incorrect modal
            var incorrectModal = new bootstrap.Modal(document.getElementById("incorrectModal"))
            incorrectModal.show()
        }
    }
    if(value != ""){  //request to set value to what is written or 0 if nothing is
        xhttp.open("GET", "/sudoku/play/add?func=value&x="+x+"&y="+y+"&value="+value);
    }else{
        xhttp.open("GET", "/sudoku/play/add?func=value&x="+x+"&y="+y+"&value="+0);
    }

    xhttp.send();
}

function getOptions(x, y){  //function to get the options/notes of a cell in the grid
    var request = new XMLHttpRequest();

    request.onload = function() {
        var options = JSON.parse(request.responseText);
        if(options.length<9){//if response is 9 then don't put anything in the options section
            for (let i = 0; i < options.length; i++) { //loop and fill the option section

                document.getElementById("Optionx"+x+"y"+y).innerHTML+=options[i] +" ";

            }
        }
    }

    request.open("GET", "/sudoku/play/getOptions?x="+x+"&y="+y);
    request.send();
}


function optionsSet(x, y){ //takes the option values stored in the option section for a grid and clicks in the appropriate buttons of the selector section to show what has been clicked in or not
    var vals = document.getElementById("Optionx"+x+"y"+y).innerHTML;

    if(vals != ""){ //if vals not blank

        for (let i = 1; i <= 9; i++){  //loop 1 to 9

            if(!vals.includes(i)){  //if number is not int vals then make it clicked in
                document.getElementById("option"+i).classList.add("clickedIn");
            }else{
                if(document.getElementById("option"+i).classList.contains("clickedIn")){  //if previously clicked in then remove the clicked in
                    document.getElementById("option"+i).classList.remove("clickedIn");
                }

            }

        }
    }else{  //no options set

        for (let i = 1; i <= 9; i++){   //make sure nothing is clicked in
            if(document.getElementById("option"+i).classList.contains("clickedIn")){
                document.getElementById("option"+i).classList.remove("clickedIn");
            }

        }
    }
}


function hint(){

    hideRedo(); //if a letter is added then a redo should not be possible and an undo should be
    showUndo();

    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var hint = JSON.parse(xhttp.responseText);

        document.getElementById("Numx"+hint["x"]+"y"+hint["y"]).value=hint["value"];  //add hint and remove the options from being shown
        document.getElementById("Optionx"+hint["x"]+"y"+hint["y"]).innerHTML="";

        newSelect(hint["x"], hint["y"]);  //select the square the hint was placed at

        if(hint["correct"]){  //if the response is correct then game is correctly done so display correct modal
            var correctModal = new bootstrap.Modal(document.getElementById("correctModal"))
            correctModal.show()

        }else if(hint["correct"]==false){  //if the response is not correct then game is not correctly done so display incorrect modal
            var incorrectModal = new bootstrap.Modal(document.getElementById("incorrectModal"))
            incorrectModal.show()
        }

    }

    xhttp.open("GET", "/sudoku/play/hint");
    xhttp.send();

}


function flipMistake(){  //function to show/hide mistakes
    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var response = JSON.parse(xhttp.responseText);

        if(response["hideMistakes"]){
            document.getElementById("mistakeBtn").innerText="Show Mistakes"; //change button to opposite

            var prev = document.querySelectorAll(".mistakes");

            for (i = 0; i < prev.length; i++) {  //hide the mistakes
                if(prev[i].id.length<6){
                    mistakeHide(prev[i].id);
                }

            }
        }else{

            document.getElementById("mistakeBtn").innerText="Hide Mistakes";  //change button to opposite

            for (i = 0; i < response["showMistakes"].length; i++) { //loop through the response and highlight all mistakes

                mistakeShow(response["showMistakes"][i]);
            }

        }

    }

    xhttp.open("GET", "/sudoku/play/flipMistakes");

    xhttp.send();

}


function mistakeShow(pos){
    if(!document.getElementById(pos).classList.contains("mistakes")){ //if not already highlighted for being a mistake then highlight ut
        document.getElementById(pos).classList.add("mistakes");
        document.getElementById("Num"+pos).classList.add("mistakes");

    }

}

function mistakeHide(pos){
    if(document.getElementById(pos).classList.contains("mistakes")){  //removes the mistake class
        document.getElementById(pos).classList.remove("mistakes");
        document.getElementById("Num"+pos).classList.remove("mistakes");
    }

}



function maxLengthCheck(object){ //ensures on numbers 1-9 can be entered

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

function shiftOption(num){ //shifts the options of a cell based on what the click in the selector
    hideRedo();
    showUndo();

    var xhttp = new XMLHttpRequest();

    xhttp.onload = function() {
        var options = JSON.parse(xhttp.responseText);
        newOptions(options); //sets the options based on the response

    }

    if(document.getElementById("option"+num).classList.contains("clickedIn")){ //if already clicked in then unclick it and add option
        document.getElementById("option"+num).classList.remove("clickedIn");
        xhttp.open("GET", "/sudoku/play/option?func=addOption&x="+currentX+"&y="+currentY+"&value="+num);

    }else{  //if not clicked in then click it and remove option
        document.getElementById("option"+num).classList.add("clickedIn");
        xhttp.open("GET", "/sudoku/play/option?func=removeOption&x="+currentX+"&y="+currentY+"&value="+num);
    }


    xhttp.send();
    document.getElementById("Numx"+currentX+"y"+currentY).select();

}

function newOptions(options){
    document.getElementById("Optionx"+currentX+"y"+currentY).innerHTML=""; //clears options

    if(options.length<9){
        for (let i = 0; i < options.length; i++) {  //adds the options based on array
            document.getElementById("Optionx"+currentX+"y"+currentY).innerHTML+=options[i] +" ";

        }
    }

}


function newOpt(x, y, options){
    document.getElementById("Optionx"+x+"y"+y).innerHTML=""; //clears options

    if(options.length<9){
        for (let i = 0; i < options.length; i++) {  //adds the options based on array
            document.getElementById("Optionx"+x+"y"+y).innerHTML+=options[i] +" ";

        }
    }

}

function selecting(x, y){   //selects and highlights appropriate cell
    if(document.getElementById("x"+x+"y"+ y).classList.contains("sudokuCellNumber")){ //if not a value that was set at the start
        document.getElementById("Numx"+x+"y"+y).select(); //select the inputbox

        if(document.getElementById("Numx"+x+"y"+y).value===""){ //sets the option section to reflect the new thing selected
            if(document.getElementById("optionSection").classList.contains("optionHidden")){
                document.getElementById("optionSection").classList.remove("optionHidden");
            }

        }else{
            if(!document.getElementById("optionSection").classList.contains("optionHidden")){
                document.getElementById("optionSection").classList.add("optionHidden");
            }

        }
    }else{
        //hide option section
        if(!document.getElementById("optionSection").classList.contains("optionHidden")){
            document.getElementById("optionSection").classList.add("optionHidden");
        }
    }

    document.getElementById("x"+x+"y"+y).classList.add("selected");//add and highlight selected
    highlight(x, y, "Selected");
    optionsSet(x, y);

    currentX=x;
    currentY=y;
}



