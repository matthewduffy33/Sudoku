var parentIndex; //parent index used to counteract handlebars

function setIndex(pIndex){  //sets an index
    parentIndex=pIndex;
}

function getIndex(){   //gets an index
    return parentIndex;
}

function valuesSet(val, x){  //sets value
    document.getElementById("Numx"+x+"y"+parentIndex).value=val;
}



function changeID(x){   //change the id to include the parent index
    var y=parentIndex;

    document.getElementById("x"+x+"y").id = "x"+x+"y"+y;
    document.getElementById("Numx"+x+"y").id = "Numx"+x+"y"+y;
    document.getElementById("Optionx"+x+"y").id = "Optionx"+x+"y"+y;

    document.getElementById("x"+x+"y"+y).addEventListener("input", function (){  //when an input is made add a letter
        addLetter(x, y);
    });

}


function gridBorders(){  //makes the borders look like a sudoku grid
    for (let x = 0; x < 9; x++) {
        for (let y = 0; y < 9; y++) {
            if(x==2||x==5||x==8){
                document.getElementById("x"+x+"y"+y).classList.add("gridBorderRight");
            }

            if(x==0){
                document.getElementById("x"+x+"y"+y).classList.add("gridBorderLeft");
            }

            setClasses(x, y);

        }
    }

}


function setClasses(x, y){ //adds the appropriate row, cols and blocks classes

    document.getElementById("x"+x+"y"+y).classList.add("row"+y);
    document.getElementById("x"+x+"y"+y).classList.add("col"+x);

    var block = Math.floor(x/3)+ 3*Math.floor(y/3);

    document.getElementById("x"+x+"y"+y).classList.add("block" + block);
}


function clickGrid(x, y, values){
    var numberValue = document.getElementById("Numx"+x+"y"+y);

    if(numberValue){

        if(numberValue.value=="") { //if not a value that is set

            if(values.length<9){
                for (let i = 0; i < values.length; i++) {
                    document.getElementById("Optionx"+x+"y"+y).innerHTML+=values[i] +" ";
                }
            }

        }

        document.getElementById("x"+x+"y"+y).addEventListener("click", function (){  //what to do when you click on a grid

            if(document.getElementById("x"+x+"y"+y).classList.contains("selected")){ //if already selected then unselect it
                document.getElementById("x"+x+"y"+y).classList.remove("selected");
                unhighlight(x, y, "Selected");
                document.getElementById("optionSection").classList.add("optionHidden");

            }else{
                newSelect(x, y);
            }

        });

    }


}



