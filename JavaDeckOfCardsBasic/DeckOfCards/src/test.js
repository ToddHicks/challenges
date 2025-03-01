const { remove } = require("lodash");

let array = [];
function solution(queries) {
    let answer = [];
    queries.forEach(function(q){
        if(q[0]=== "ADD"){
            answer.push(add(q[1]));
        }
        else if(q[0] === "EXISTS"){
            answer.push(exist(q[1]));
        }
        else if(q[0] === "REMOVE"){
            const index =  array.indexOf(q[1]);
            if(index != -1){
                array.splice(index,1);
                answer.push("true");
            }
            else{
                answer.push("false");
            }
        }
        else if(q[0] === "GET_NEXT"){
            answer.push(get_next(q[1]));
        }
    });
    return answer
}
function add(value){
    array.push(value);
    return "";
}
function exist(value) {
    const found = array.find(element => element === value);
    if (found !== undefined){
        return "true";
    }
    else {
        return "false";
    }
}
function get_next(value){
    let next = "";
    console.log("Value to test: " + value);
    array.forEach(function(index){
        if(parseInt(index) > parseInt(value)){
            if(next === ""){
                next = parseInt(index);
            }
            else if(parseInt(index) < parseInt(next)){
                next = index;
            }
        }
    });
    return ''+next;
}