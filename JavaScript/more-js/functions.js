/**
 * JS treats functions like first-class citizens
 *      - you can treat functions like variables
 *          - passing functions into another function
 *          - returning a function, from a function
 *          - can assign to a variable and pass it around
 *          - can be used as properties in an object
 */


function greeterGenerator(name) {

    // Closure - functions retain access to variables in their outside lexical enviornment
    return function() {
        console.log(`Hello, ${name}! How are you today?`);
    }

}

const greetAlejandro = greeterGenerator("Alejandro");
greetAlejandro();   // calling the function returned by the greeter generator

greeterGenerator("Aiden")();    // immediately invoking the returned function



/**
 * HIGHER ORDER FUNCTIONS (HOFs)
 *      - any function, that takes a function as a param OR returns a function
 * 
 *      - JS arrays have MANY HOFs built in
 */
const fruits = ["banana", "pineapple", "orange", "grapes", "mango", "strawberries", "blackberries", "blueberries"];

console.log("### FOR EACH ###");
// forEach lets you loop through each element and execute the provided function on it
fruits.forEach(function (fruitVariable, index, copyOfFruitsArray) {
    console.log(`${index}. ${fruitVariable}`);

    copyOfFruitsArray.push(fruitVariable + index);  // does affect the original array
});
console.log(fruits);


console.log("### MAP ###");
// map takes in a value and adjusts it according to the function. returns a new array, doesn't affect existing
const allCapsFruits = fruits.map((fruit) => {

    // copies whatever is returned to the new array
    return fruit.toUpperCase();     
});
console.log(fruits);        // original is unaffected
console.log(allCapsFruits);


console.log("### FILTER ###");
// filter returns a new array containing only items that match some criteria
const berries = fruits.filter((fruit) => {

    // filter is looking for a boolean. individual propertiues that return true will be added to new array
    return fruit.includes("berries");   // includes checks if a substring exists
});
console.log(berries);


console.log("### REDUCE ###");
// reduce takes your array and reduces it to a single value - aggregates data
const nums = [1, 2, 3, 4, 5];
const sum = nums.reduce((prevValue, currValue, index, copyOfOriginalArray) => {

    // the value returned will be prevValue on next loop
    // once all iterations are finished, prevValue is returned from the entire function
    return prevValue + currValue;
});
console.log(sum);



/**
 * CALLBACK FUNCTIONS
 *      - functions that are invoked at a later point in time
 *          - often this will be functions that are returned from another function, or passed in to a function
 * 
 *      - setTimeout and setInterval are the classic examples
 */

// pause execustion for some time, then execute the function
setTimeout(() => {

    console.log("Surprise! It's 3 seconds later!");

}, 3000);   // 3 seconds


// execute the callback, at each intervcal of the given time. clearInterval() to stop it.
let count = 10;
console.log("The spaceship will launch in...");
const countdown = setInterval(() => {
    if(count <= 0) {
        console.log("LIFTOFF!!!!");

        // will stop the interval from going any further - not a break statement (does not immediately stop execution)
        clearInterval(countdown);   
    }

    console.log(count);
    count--;

}, 1000);