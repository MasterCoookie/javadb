
function getResult(arg1, arg2, result) {
  var xhttp = new XMLHttpRequest();
  
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
        document.getElementById(result).innerHTML = this.responseText;
    }
  };
  
  var ar1 = document.getElementById(arg1).value;
  var ar2 = document.getElementById(arg2).value;
  
  xhttp.open("GET", "simplecalculator?arg1=" + ar1 + "&arg2=" + ar2, true);
  xhttp.send();
}

function getTable(firstName, tableId) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     document.getElementById(tableId).innerHTML = this.responseText;
    }
  };
  
  xhttp.open("GET", "persons?firstName="+document.getElementById(firstName).value, true);
  xhttp.send();
}