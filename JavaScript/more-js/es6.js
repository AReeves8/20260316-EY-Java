/**
 * JS is build on EcmaScript
 *      in 2015, ES6 came out and added a TON of features
 */


/**
 * ARROW FUNCTIONS
 *      - shorthand for full functions
 *          - one param: no need for ()
 *          - zero or two+ params: need ()
 * 
 *          - one line function: no need {}
 *          - zero or two+ lines: need {}
 */
[1,2,3].map( (param1, param2) => { param1 * param2 } );
[1,2,3].map( data => { "Hello!" } );
[1,2,3].map( data => "Hello! " + data );

function myFunc() { 

}

// creates an arrow function and assigns it to a variable (same as above)
const myArrowFunc = () => {

}




/**
 * JS Objects
 *      - closer to Maps
 *      - key: value pairs
 * 
 *      -  JavaScript Object Notation (JSON)
 */
const person = {
    age: 26,
    name: {
        firstName: "Austin",
        lastName: "Reeves"
    },
    faveFood : "French Fries"
}
console.log(person.faveFood);           // use dot notation to access properties
console.log(person.name.firstName);

person.faveColor = "gray";
console.log(person);

// const is on the OBJECT, not its properties
person.age = 27;            // can still modify properties
console.log(person.age);

// const locks the variable into being an object. you can change the object, but cannot change it away from being an object. 
//person = "Some string";     // throws TypeError
//console.log(person);

// can individually change the properties, but cannot reassign the entire object - throws TypeError
// person = {
//     numWheels: 4,
//     paintColor: "red",
//     engine: "v6"
// }

// turns the object read only (sets every property to writeable: false)
Object.freeze(person);

// these won't do anything, not even throw an error
person.age = 28;
person.location = "Dallas";
console.log(person);

/**
 * OBJECT PROPERTIES
 *      - hidden metadata within properties of objects
 * 
 *      - writeable: allows/prevents the mutation of the property (default true)
 *      - enumerable: allows/prevents the property showing up when the object is enumerated over (default true)
 *      - configurable: allows/prevents the above values from being changed for a property (default true)
 *          - once it is set to false, this cannot be undone. 
 */

const animal = {
    species: "panda",
    name: "robbie"
}

Object.defineProperty(animal, "age", { 
    writable: false,            // the value cannot be changed
    enumerable: false,          // the property cannot be enumerated over
    configurable: false,        // the above settings cannot ever be changed
    value: 20
});

// use defineProperty to change config of existing properties as well
Object.defineProperty(animal, "species", {writable: false});    // now species cannot be modifed

// Enumeration (for.. in) : looping through properties on an object. Iteration (for.. of) : looping through values in an array.
for(let property in animal) {
    console.log(`Key: ${property}. Value: ${animal[property]}`); // can also use square bracket notation to access the vvalue of a property in an object
}


/**
 * DESTRUCTURING
 *      - objects and arrays can be "destructured"
 *          - break down the object/array into its indovidual pieces  
 */

const dog = {
    name: "Ruby",
    color: "Red",
    beahvior: "bad"
}

const {name, color} = dog;

console.log(name);
console.log("Austin's doggo, Ruby, is " + color);
console.log(dog);              // original object is left unchanged
// color = "Golden Brown";     // indivvidual variables are constants so modification is not allowed. throws TypeError
// console.log(color);


const nums = [1, 2, 3, 4, 5];
const [num1, num2] = nums;      // grabs the values out of the first two indexes
console.log(num1);
console.log(num2);

const [,,,num4] = nums;         // can skip indexes if you want
console.log(num4);



/**
 * SPREAD OPERATOR
 *      ...
 * 
 *      fan out some iterable (and objects - they're the exception)
 */

// turns strings into individual characters
const charArray = [..."Bradley"];   // ["B" "r" "a" "d" "l" "e" "y"]
console.log(charArray);

// copying values to new array with spread operator
const newArray = [...charArray, "!"];
console.log(newArray);

const obj = {
    firstName: "Ahmed",
    lastName: "Sadig"
}

// can use spread operator to copy values in objects
const newObj = {
    ...obj,             
    faveColor: "Blue"
}
console.log(newObj);

// can override specific properties that may have been spread in
const newNewObj = {
    ...newObj,
    firstName: "Jon",       
    lastName: "Walker"
}
console.log(newNewObj);

// can set "default values" by setting the property before the spread
const newNewNewObj = {
    faveFood: "Veggie Straws",      
    ...newNewObj
}
console.log(newNewNewObj);


/**
 * REST OPERATOR
 *      ...
 * 
 *      consolidate items into one
 */

const daysOfTheWeek = ["Monday", "Tuesday", "Wednesday"];
const [monday, ...allOtherDays] = daysOfTheWeek;
console.log(monday);            // String
console.log(allOtherDays);      // String[]

// using rest operator to consolidate any extra params that are given into an array
const printTopThreeMovies = (firstPlace, secondPlace, thirdPlace, ...runnerUps) => {
    console.log(
        `
        1st Place: ${firstPlace}
        2nd Place: ${secondPlace}        
        3rd Place: ${thirdPlace}
        All Runner Ups: ${runnerUps}
        `
    );
}

const movies = ["Inside Out", "Dodgeball", "The Hobbit", "Project Hail Marry", "The Martian", "Happy Gilmore"];
printTopThreeMovies(...movies);     // spreading out our movies into the params of the function



/**
 * NULLISH COALESCING OPERATOR
 *      ??
 * 
 *      returns some other value, if a given value is null
 *          - basically shorthand for a ternary statement that only checks for null
 */
let portValue = null;
let portNumber = portValue ?? 5000;     // if portValue is null, port will be 5000. Otherwise, port will be the value of portValue
console.log(portNumber);


/**
 * OPTIONAL CHAINING 
 *      ?.
 * 
 *      null check for properties in an object
 *          help avoids null access errors
 */
const home = {
    // address : {
    //     street: "123 Dallas Dr.",
    //     city: "Dallas",
    //     state: "Tx"
    // },
    address: null,
    stories: 2,
    hasPool: false
}
// causes error because address is null
// if(home.address.city === "Dallas"){
//     console.log("Go Mavs!");
// }

// no error because null check. if statement is skipped
if(home.address?.city === "Dallas"){
    console.log("Go Mavs!");
}

if(home?.address?.city === "Dallas"){      
    console.log("Go Mavs!");
}


/**
 * STRICT EQUALITY
 *      ===
 * 
 *      first checks typeof, then checks value
 *          more-or-less equivalent to .equals() in java
 *      not ES6, but wanted to talk about it
 *      
 * 
 *      Logical Equals (==) only checks values
 */
console.log(2 == 2);    // true
console.log(2 == "2");  // true
console.log(2 === "2"); // false
