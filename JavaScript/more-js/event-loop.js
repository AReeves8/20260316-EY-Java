/**
 * JS Event Loop
 *      - this is the runtime model that JS uses
 *          - Java is top-down. JS is different. 
 *      
 *      - the event loop determines what subtasks to execute at any given moment. 
 *          - JS adds every line of code to a series of queues/stack
 *              - execution stack
 *              - macrotask queue
 *              - microtask queue
 * 
 *              - in general: execution stack > microtasks > macrotasks
 */             
console.log("1");                                           // execution stack
setTimeout(() => console.log("2"), 0);                      // macrotask queue
setTimeout(() => console.log("3"), 0);                      // macrotask queue
Promise.resolve("4").then(data => console.log(data));       // microtask queue
console.log("5");                                           // execution stack

// What will be the order of the numbers printed out?
// 1, 2, 3, 5, 4
// 1, 4, 5, 2, 3
// correct answer: 1, 5, 4, 2, 3



/**
 * PYRAMID OF DOOM
 *      - anti-pattern - meaning, DON'T write your code like this
 * 
 *      - you can nest a bunch of callback functions inside of one another, shifting your code more and more to the right with each nest
 *          - making a pyramid
 * 
 *      - this is a problem because it makes code hard to read, maintain, and you end up having to do A LOT of error handling
 *         
 *      - aka "Callback Hell"
 */

// simulating API calls with setTimeout()
function getUser(userId, orderCallback) {
    setTimeout(() => orderCallback(null, {userId, name: "Alice"}), 100);
}

function getOrders(user, detailsCallback) {
    setTimeout(() => detailsCallback(null, [{orderId: 1}, {orderId: 2}]), 100);
}

function getOrderDetails(order, paymentCallback) {
    setTimeout(() => paymentCallback(null, {...order, item: "cpu", price: 199}), 100);
}

function processPayment(details, processCallback) {
    setTimeout(() => processCallback(null, {status: "paid", details}), 100);
}

// PYRAMID OF DOOM
getUser(12, (err, user) => {
    if(err) {
        return console.error(err);
    }
    getOrders(user, (err, orders) => {
        if(err) {
            return console.error(err);
        }
        for(let order of orders) {
            getOrderDetails(order, (err, details) => {
                if(err) {
                    return console.error(err);
                }
                processPayment(details, (err, receipt) => {
                    if(err) {
                        return console.error(err);
                    }
                    console.log("Payment Complete! " + receipt);
                })
            });
        }
    });
});