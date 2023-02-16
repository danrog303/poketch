const boxUtilityForm = document.querySelector("#box-utility-form");
const boxUtilityInput = document.querySelector("#box-utility-input");
const boxUtilityTable = document.querySelector("#box-utility-table");

boxUtilityForm.addEventListener("submit", (event) => {
    event.preventDefault();
    const pokedexNo = parseInt(boxUtilityInput.value);
    const boxNumber = Math.ceil(pokedexNo / 30);

    let cellNumber = pokedexNo % 30;
    if(cellNumber === 0) {
        cellNumber = 30;
    }

    let currentCellNumber = 0;
    for(let row = 0; row < 5; row++) {
        for(let column = 0; column < 6; column++) {
            currentCellNumber++;
            const td = boxUtilityTable.querySelectorAll("tr")[row].querySelectorAll("td")[column];
            if(currentCellNumber === cellNumber) {
                td.style.background = "goldenrod";
            } else {
                td.style.background = "transparent";
            }
        }
    }

    document.getElementById("box-utility-box-number").innerHTML = boxNumber.toString();
});