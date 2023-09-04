var parentIndex;

function setIndex(pIndex){
    parentIndex=pIndex;
}

function getIndex(){
    return parentIndex;
}

function changeID(x){
    var y=parentIndex;

    document.getElementById("x"+x+"y").id = "x"+x+"y"+y;
    document.getElementById("Numx"+x+"y").id = "Numx"+x+"y"+y;
    document.getElementById("Optionx"+x+"y").id = "Optionx"+x+"y"+y;

    document.getElementById("x"+x+"y"+y).addEventListener("input", function (){
        addLetter(x, y);
    });

}

function valuesSet(val, x){
    document.getElementById("Numx"+x+"y"+parentIndex).value=val;
}

function gridBorders(){
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

function setClasses(x, y){

    document.getElementById("x"+x+"y"+y).classList.add("row"+y);
    document.getElementById("x"+x+"y"+y).classList.add("col"+x);

    var block = Math.floor(x/3)+ 3*Math.floor(y/3);

    document.getElementById("x"+x+"y"+y).classList.add("block" + block);
}

function clickGrid(x, y, values){
    var numberValue = document.getElementById("Numx"+x+"y"+y);

    if(numberValue && numberValue.value=="") {

        if(values.length<9){
            for (let i = 0; i < values.length; i++) {
                document.getElementById("Optionx"+x+"y"+y).innerHTML+=values[i] +" ";
            }
        }

        document.getElementById("x"+x+"y"+y).addEventListener("click", function (){
            if(document.getElementById("x"+x+"y"+y).classList.contains("selected")){
                document.getElementById("x"+x+"y"+y).classList.remove("selected");
                unhighlight(x, y, "Selected");
                document.getElementById("optionSection").classList.add("optionHidden");
            }else{
                newSelect(x, y);
            }

        });
    }else if(numberValue){
        document.getElementById("x"+x+"y"+y).addEventListener("click", function (){
            if(document.getElementById("x"+x+"y"+y).classList.contains("selected")){
                document.getElementById("x"+x+"y"+y).classList.remove("selected");
                unhighlight(x, y, "Selected");
                document.getElementById("optionSection").classList.add("optionHidden");
            }else{
                newSelect(x, y);

            }

        });
    }

}



