document.onkeydown = checkKey;

function checkKey() {

    var arrow = window.event;

    if (arrow.keyCode == '38') {
        arrow.preventDefault();
        upArrow();

    }else if (arrow.keyCode == '40') {
        arrow.preventDefault();
        downArrow();

    }else if (arrow.keyCode == '37') {
        arrow.preventDefault();
        leftArrow();

    }else if (arrow.keyCode == '39') {
        arrow.preventDefault();
        rightArrow();
    }

}

function upArrow(){
    var selected = document.querySelectorAll(".selected")[0];
    var x = parseInt(selected.id.substring(1,2));
    var y = parseInt(selected.id.substring(3));



    if(y>0){
        document.getElementById("x"+x+"y"+y).classList.remove("selected");
        unhighlight(x, y, "Selected");


        selecting(x, y-1)
    }
}


function downArrow(){
    var selected = document.querySelectorAll(".selected")[0];
    var x = parseInt(selected.id.substring(1,2));
    var y = parseInt(selected.id.substring(3));



    if(y<8){
        document.getElementById("x"+x+"y"+y).classList.remove("selected");
        unhighlight(x, y, "Selected");

        selecting(x, y+1)
    }
}


function leftArrow(){
    var selected = document.querySelectorAll(".selected")[0];
    var x = parseInt(selected.id.substring(1,2));
    var y = parseInt(selected.id.substring(3));



    if(x>0){
        unhighlight(x, y, "Selected");
        document.getElementById("x"+x+"y"+y).classList.remove("selected");

        selecting(x-1, y)
    }
}

function rightArrow(){
    var selected = document.querySelectorAll(".selected")[0];
    var x = parseInt(selected.id.substring(1,2));
    var y = parseInt(selected.id.substring(3));



    if(x<8){
        document.getElementById("x"+x+"y"+y).classList.remove("selected");
        unhighlight(x, y, "Selected");


        selecting(x+1, y)
    }
}




function newSelect(x, y){
    var prev = document.querySelectorAll(".selected");
    for (i = 0; i < prev.length; i++) {

        prevX = prev[i].id.substring(1,2);
        prevY = prev[i].id.substring(3);

        prev[i].classList.remove("selected");

        unhighlight(prevX, prevY, "Selected");

    }

    selecting(x, y);
}


function unselect(prev){
    var x;
    var y;

    for(let i=0; i<9; i++){
            if(prev.classList.includes("col"+i)){
                x=i;
            }else if(prev.classList.includes("row"+i)){
                y=i;
            }
    }

    unhighlight(x, y, "selected");

}

function hoverOver(elem){
    elem.classList.add("hoverSelect");
     highlight(elem.id.substring(1,2), elem.id.substring(3), "");
}

function hoverOut(elem){
    elem.classList.remove("hoverSelect");
    unhighlight(elem.id.substring(1,2), elem.id.substring(3), "");

}

function highlight(x, y, selected){
    var rows = document.querySelectorAll(".row"+y);
    for (let i = 0; i < rows.length; i++) {
        rows[i].classList.add("hover"+selected);
    }

    var col = document.querySelectorAll(".col"+x);
    for (i = 0; i < col.length; i++) {
        col[i].classList.add("hover"+selected);
    }

    var blockNum = Math.floor(x/3)+ 3*Math.floor(y/3);

    var block = document.querySelectorAll(".block"+ blockNum);
    for (i = 0; i < block.length; i++) {
        block[i].classList.add("hover"+selected);
    }

    var elem = document.getElementById("x"+x+"y"+y);

    if(!elem.classList.contains("sudokuCellNumber")){
        var val = parseInt(elem.innerHTML.replace(/^\D+/g, ''));

    }else{
        var val = document.getElementById("Numx"+x+"y"+y).value;
    }

    var values = document.querySelectorAll(".val"+ val);
    for (i = 0; i < values.length; i++) {
        values[i].classList.add("hover"+selected);
    }

}

function unhighlight(x, y, selected){
    var rows = document.querySelectorAll(".row"+y);
        for (let i = 0; i < rows.length; i++) {
            rows[i].classList.remove("hover"+selected);
        }

        var col = document.querySelectorAll(".col"+x);
        for (i = 0; i < col.length; i++) {
            col[i].classList.remove("hover"+selected);
        }

        var blockNum = Math.floor(x/3)+ 3*Math.floor(y/3);


        var block = document.querySelectorAll(".block"+ blockNum);
        for (i = 0; i < block.length; i++) {
            block[i].classList.remove("hover"+selected);
        }

        var elem = document.getElementById("x"+x+"y"+y);

        if(!elem.classList.contains("sudokuCellNumber")){
            var val = parseInt(elem.innerHTML.replace(/^\D+/g, ''));

        }else{
            var val = document.getElementById("Numx"+x+"y"+y).value;
        }

        var values = document.querySelectorAll(".val"+ val);

        for (i = 0; i < values.length; i++) {
            values[i].classList.remove("hover"+selected);
        }

}


function unhighlightChanged(val, x, y){

        var values = document.querySelectorAll(".val"+ val);
        var blockNum = Math.floor(x/3)+ 3*Math.floor(y/3);

        for (i = 0; i < values.length; i++) {
            if(!values[i].classList.contains("row"+y) && !values[i].classList.contains("col"+x) && !values[i].classList.contains("block"+blockNum)){
                values[i].classList.remove("hoverSelected");
                values[i].classList.remove("hover");
            }
        }

}

function highlightChanged(val){

        var values = document.querySelectorAll(".val"+ val);
        for (i = 0; i < values.length; i++) {
                values[i].classList.add("hoverSelected");

        }

}