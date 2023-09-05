document.onkeydown = checkKey;

function checkKey() {  //allows movement with arrow keys

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

    if(y>0){   //checks you do not go out of bounds
        document.getElementById("x"+x+"y"+y).classList.remove("selected"); //unselect previous
        unhighlight(x, y, "Selected");

        selecting(x, y-1)  //select new
    }
}


function downArrow(){
    var selected = document.querySelectorAll(".selected")[0];
    var x = parseInt(selected.id.substring(1,2));
    var y = parseInt(selected.id.substring(3));

    if(y<8){  //checks you do not go out of bounds
        document.getElementById("x"+x+"y"+y).classList.remove("selected"); //unselect previous
        unhighlight(x, y, "Selected");

        selecting(x, y+1);  //select new
    }
}


function leftArrow(){
    var selected = document.querySelectorAll(".selected")[0];
    var x = parseInt(selected.id.substring(1,2));
    var y = parseInt(selected.id.substring(3));

    if(x>0){   //checks you do not go out of bounds
        unhighlight(x, y, "Selected");
        document.getElementById("x"+x+"y"+y).classList.remove("selected");  //unselect previous

        selecting(x-1, y);  //select new
    }
}

function rightArrow(){
    var selected = document.querySelectorAll(".selected")[0];
    var x = parseInt(selected.id.substring(1,2));
    var y = parseInt(selected.id.substring(3));

    if(x<8){  //checks you do not go out of bounds
        document.getElementById("x"+x+"y"+y).classList.remove("selected"); //unselect previous
        unhighlight(x, y, "Selected");

        selecting(x+1, y);  //select new
    }
}




function newSelect(x, y){
    var prev = document.querySelectorAll(".selected");
    for (i = 0; i < prev.length; i++) {  //remove previously selected

        prevX = prev[i].id.substring(1,2);
        prevY = prev[i].id.substring(3);

        prev[i].classList.remove("selected");

        unhighlight(prevX, prevY, "Selected");

    }

    selecting(x, y); //select new
}


function unselect(prev){
    var x;
    var y;

    for(let i=0; i<9; i++){  //finds position of previous x y and unhighlight it
            if(prev.classList.includes("col"+i)){
                x=i;
            }else if(prev.classList.includes("row"+i)){
                y=i;
            }
    }

    unhighlight(x, y, "selected");

}


function hoverOver(elem){  //highlight current hoverable
    elem.classList.add("hoverSelect");
    highlight(elem.id.substring(1,2), elem.id.substring(3), "");
}


function hoverOut(elem){  //unhighlight previous hoverable
    elem.classList.remove("hoverSelect");
    unhighlight(elem.id.substring(1,2), elem.id.substring(3), "");

}


function highlight(x, y, selected){
    var rows = document.querySelectorAll(".row"+y);  //highlighting everything in a row
    for (let i = 0; i < rows.length; i++) {
        rows[i].classList.add("hover"+selected);
    }

    var col = document.querySelectorAll(".col"+x);  //highlighting everything in a column
    for (i = 0; i < col.length; i++) {
        col[i].classList.add("hover"+selected);
    }

    var blockNum = Math.floor(x/3)+ 3*Math.floor(y/3);

    var block = document.querySelectorAll(".block"+ blockNum);   //highlighting everything in a block
    for (i = 0; i < block.length; i++) {
        block[i].classList.add("hover"+selected);
    }

    var elem = document.getElementById("x"+x+"y"+y);

    if(!elem.classList.contains("sudokuCellNumber")){  //gets the value of selected grid
        var val = parseInt(elem.innerHTML.replace(/^\D+/g, ''));

    }else{
        var val = document.getElementById("Numx"+x+"y"+y).value;

    }

    var values = document.querySelectorAll(".val"+ val);
    for (i = 0; i < values.length; i++) {  //highlighting everything of the same value
        values[i].classList.add("hover"+selected);
    }

}


function unhighlight(x, y, selected){
    var rows = document.querySelectorAll(".row"+y);  //unhighlighting everything in a row
        for (let i = 0; i < rows.length; i++) {
            rows[i].classList.remove("hover"+selected);
        }

        var col = document.querySelectorAll(".col"+x);  //highlighting everything in a column
        for (i = 0; i < col.length; i++) {
            col[i].classList.remove("hover"+selected);
        }

        var blockNum = Math.floor(x/3)+ 3*Math.floor(y/3);


        var block = document.querySelectorAll(".block"+ blockNum);   //unhighlighting everything in a block
        for (i = 0; i < block.length; i++) {
            block[i].classList.remove("hover"+selected);
        }

        var elem = document.getElementById("x"+x+"y"+y);

        if(!elem.classList.contains("sudokuCellNumber")){   //gets the value of selected grid
            var val = parseInt(elem.innerHTML.replace(/^\D+/g, ''));

        }else{
            var val = document.getElementById("Numx"+x+"y"+y).value;
        }

        var values = document.querySelectorAll(".val"+ val);

        for (i = 0; i < values.length; i++) {   //unhighlighting everything with the same value
            values[i].classList.remove("hover"+selected);
        }

}


function highlightChanged(val){

        var values = document.querySelectorAll(".val"+ val);
        for (i = 0; i < values.length; i++) {       //selected everything of the new changed val
            values[i].classList.add("hoverSelected");

        }

}


function unhighlightChanged(val, x, y){

        var values = document.querySelectorAll(".val"+ val);
        var blockNum = Math.floor(x/3)+ 3*Math.floor(y/3);

        for (i = 0; i < values.length; i++) {
            if(!values[i].classList.contains("row"+y) && !values[i].classList.contains("col"+x) && !values[i].classList.contains("block"+blockNum)){  //if not in the same, row, col or block deselect it
                values[i].classList.remove("hoverSelected");
                values[i].classList.remove("hover");
            }
        }

}