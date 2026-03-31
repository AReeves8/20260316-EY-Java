/**
 * Java -> named after coffee because the creators liked coffee
 * 
 * JavaScript -> wanted to ride to popularity of Java
 *      - Java is related to JavaScript the way the sun is related to sunglasses. Or the way a car is related to carpet. 
 */

/**
 * run JS files with node <file_name>
 *      - make sure to be in the correct directory 
 */
console.log("Hello World!");        // print statement in JS -> can show up in the browser


/**
 * VARIABLES
 *      - var - global or function scoped (basically, never use it)
 *      - let - block scoped
 *      - const - block scoped but the value cannot be changed (similar to final in Java)
 */

var globalScopedVariable = 10;              // JS infers your type and variables can be changed as needed
globalScopedVariable = 'Austin Reeves';     // strings can be double quote or single - pick one and be consistent
console.log(globalScopedVariable);

var globalScopedVariable = 20;              // variables can be redclared with no issues
console.log(globalScopedVariable);


myFirstFunction("Austin", 8);



function myFirstFunction(name, num) {
    console.log("Function params: " + name + "; " + num);

    // Function Scoped Variables
    var functionScopedVar = 5;
    let functionScopedLet = 10;
    const functionScopedConst = 15;


    /**
     * VARIABLE HOISTING
     *      - JS will auto-move all variables (var, let, const) to the TOP of thier scope
     *      - can use a var before it is declared - will be undefined if not yet intialized
     *      - let and const will be moved to the top of their scope, BUT NOT INITIALIZED
     *          - will cause reference error if you try to access it
     */
    color = "blue"; // can give this let a color here to initialize it
    console.log(color);
    


    if(true) {
        let blockScopedLet = 20;
        var functionScopedVar = 25;     // redeclaration of above var

        console.log("IN BLOCK:\n\tLET: " + blockScopedLet + "\n\tVAR: " + functionScopedVar);
    }

    // gives error because let is out of scope
    //console.log("OUT BLOCK:\n\tLET: " + blockScopedLet + "\n\tVAR: " + functionScopedVar);
    console.log("OUT BLOCK:\n\tLET: " + functionScopedLet + "\n\tVAR: " + functionScopedVar + "\n\tCONST: " + functionScopedConst );
    
    // will be HOISTED to the top of the function (its scope)
    var color = "red";
    console.log(color);

    // JS will put into the temporal dead zone to force the variable to not be used until initialized
    let color2 = "red"; 
}

console.log("### DATA TYPES ###");
/**
 * DATA TYPES
 *      - js still does have data types, we just don't have to declare them
 *  
 *      - Primitives: number, string, boolean, undefined, null, BigInt (decalre with n (123456789n)), Symbol (js identifier)
 *      - Non-primitives: objects, arrays, and functions. 
 * 
 *      - TRUTHY AND FALSEY
 *          - inside of boolean contexts, js will convert your variables into booleans
 * 
 *          - Falsey:
 *              - "" (empty string)
 *              - 0, 0n, -0 (zeroes)
 *              - false
 *              - undefined
 *              - null
 *              - NaN
 *          - Truthy:
 *              - everything else
 *              - {} (empty objects)
 *              - [] (empty arrays)
 *              - " " 
 *              - "0"
 *              - "false"
 *              - Infinity
 */

let person = {};    // true
person = null;      // false
//let person;       // undefined -> false

console.log(typeof person);     // typeof to see what data type something is

let bigNum = 34654565n;
console.log(typeof bigNum);     // bigint

// built in functions for explicit casting
let numString = String(bigNum);
console.log(typeof numString);     // string

let numBool = Boolean(bigNum);
console.log(typeof numBool);     // boolean


/**
 * TYPE COERCION
 *      - when JS tries to auto convert one type to another
 */
let num1 = 5;
let num2 = "10";

// js has to decide if these should both be numbers or both be strings. strings usually take priority over other data types
console.log(num1 + num2);   // result -> 510.
console.log(num2 + num1);   // still treated as both Strings

// need to manually force num2 into a number
console.log(num1 + Number(num2));   // result -> 15


/**
 * TEMPLATE LITERALS
 *      - string created with backticks (`hello world`)
 *      - they print exactly how it appears in code
 *          - multi-line strings
 *          - easy way to include variables inside of the strings
 */

let templateString = `Type coercion turned ${num1} into a string!!!

    I cannot believe it. 


                        :(
`;
console.log(templateString);