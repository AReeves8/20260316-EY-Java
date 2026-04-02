// global variables
const URL = "http://localhost:8080/api/v1/movies";
let allMovies = [];
let movieToUpdate;
let movieToDelete;

/**
 * DOM - Document Object Model
 *      - is the makeup of your HTML page
 * 
 *      - in JS, we can use the 'document' object, to manipulate the DOM
 */

/**
 * EVENT LISTENERS
 *      - wait for some event to occur in the DOM, and respond when it occurs
 *          - events are interactions with your page (clicking a button, focusing on a input field, hovering over a link, etc.)
 * 
 *          - in JS, you can create your own custom events, but you fire and listen to those manually. 
 *              - similar to pub/sub design pattern  
 */

// GET request for records, when the page is loaded. 
document.addEventListener("DOMContentLoaded", () => {

    /**
     * AJAX - Asynchronous JavaScript and XML
     *      - primarialy woirk with object XmlHttpRequest (XHRs)
     */

    let xhr = new XMLHttpRequest();     // create an XHR object in readystate 0

    /**
     * readystates - stages of your request
     *      0 - unsent
     *      1 - opened
     *      2 - headers received
     *      3 - loading
     *      4 - done, request finished  --> this is the main one we care about     
     * 
     * onreadystatechange - callback that fires every time the readystate changes       
     */
    xhr.onreadystatechange = () => {

        // only access returned data when the request has completed
        if(xhr.readyState === 4) {

            // parsing the returned text into JSON data that can be converted into an object/list of objects
            let movies = JSON.parse(xhr.responseText);

            //dynamically add these movies to the table in the DOM
            movies.forEach((data) => addMovieToTable(data));
        }

    }

    // use open() to create the request (set url, method, headers, body, etc.) - moves your xhr object to readystate 1
    xhr.open("GET", URL);

    // send the request
    xhr.send();

});

// POST request for new records when form is submitted
document.getElementById("new-movie-form").addEventListener("submit", (eventInfo) => {

    /**
     * Event Listeners can take in a param that contains information about the event
     */

    // default action for submit is to refresh the page - we don't want this because then the data is lost
    eventInfo.preventDefault();     


    // need to grab data out of the form input fields
    // could do document.getElementById("inputFieldId").value() for each input field
    // easier way: FormData

    /**
     * Form Data
     *      - automatically map the data in the input fields within a form to a JS object
     *      - object properties will be named after the NAME attribute on the input tag
     */
    let inputData = new FormData(document.getElementById("new-movie-form"));

    // IMPORTANT: make sure these object property names, MATCH to what your backend endpoints are expecting
    const newMovie = {
        title: inputData.get("new-movie-title"),    
        genre: inputData.get("new-movie-genre"),
        rating: inputData.get("new-movie-rating"),
        director: {
            firstName: inputData.get("new-director-firstname"),
            lastName: inputData.get("new-director-lastname")
        }
    }
    console.log(newMovie);


    /**
     * Rather than using raw XHR objects, we can simplify with fetch
     *      
     *      - fetch() is a built in function that can send HTTP requests
     *          - returns a Promise
     *              - a Promise is some object that will have data eventually, just not right now
     *                  
     *      - HANDLING PROMISES:
     *          - asnyc and await OR .then().catch().finally()
     * 
     *              - ASYNC AND AWAIT
     *                  - await will pause code execution to wauit for a Promise to return with data
     *                  - it can only be used inside of async function
     * 
     *              - .then()
     *                  - create a callback to be executed when the promise returns with data
     *                  - .catch() to handle Promises that error out
     *                  - .finally() to run in all scenarios, even if an error occurs
     */
    postNewMovie(newMovie);     // using async/await

});

// PUT request for updating an existing record
document.getElementById("update-movie-form").addEventListener("submit", (event) => {
    event.preventDefault();		// prevent default form actions from occuring

    // retrieving data from the update form
    const inputData = new FormData(document.getElementById("update-movie-form"));

    const movie = {
        id : movieToUpdate.id,
        title : inputData.get("update-movie-title"),         
        rating : inputData.get("update-movie-rating"),
        genre: inputData.get("update-movie-genre"),
        director : {
            id : movieToUpdate.director.id,
            firstName : inputData.get("update-director-first"),
            lastName : inputData.get("update-director-last")
        }
    }

    /**
     * alternative/preferred way to handle promises:
     *      rather than using async/await, we can use .then() and pass in a callback function
     * 
     *      .then will run whenever the promise returns succesfully
     *      .catch will run whenever the promise returns unsuccessfully
     */
    fetch(URL + `/${movieToUpdate.id}`, {
        method : "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body : JSON.stringify(movie)
    })
    .then((httpResponse) => {
        // .then() will handle all 100, 200, and 300 status code responses
        
        // we still need to serialize the response into JSON
        return httpResponse.json();
    })
    .then((movieJson) => {          // handling the promise returned by data.json (*** this is where we update the table ***)
        
        // adding the updated movie to our table
        updateMovieInTable(movieJson);

        // reset the forms
        resetAllForms();
    })
    .catch((error) => {
        // this will handle all 400 and 500 status code responses in either .then()

        console.error(error);   // generally, you never want to use console.log() - especially in a production environment
    })
});

// DELETE request to remove an existing record
document.getElementById("delete-movie-form").addEventListener("submit", (event) => {
    event.preventDefault();		// prevent default form actions from occuring

    // sending delete request
    fetch(URL + `/${movieToDelete.id}`, {
        method : "DELETE",
        headers: {
            "Content-Type": "application/json",
        }
    })
    .then((httpRespone) => {

        // delete request returns no-content so there's no need to deserialize the response and wait for that promie
        // just need to check that the response we got back is 204 - No Content and we can delete it on the front end
        if(httpRespone.status === 204) {
            // remove movie from table
            removeMovieFromTable(movieToDelete.id);

            // resetting all forms
            resetAllForms();
        }
    })
    .catch((error) => {
        console.error(error);   
    })

});

////////////////////////////////
/////// HELPER FUNCTIONS ///////
///////////////////////////////

// creating an async function for posting movies with fetch
const postNewMovie = async (newMovie) => {

    // wait for the fetch to finish the HTTP request, then returnedData will contain the response
    const httpResponse = await fetch(URL, {
        method: "POST", 
        headers: {
            "Content-Type": "application/json"      // tells the server to expect JSON data
        },
        body: JSON.stringify(newMovie)              // converts the object to JSON before sending
    });

    const movieJson = await httpResponse.json();    // grabs the JSON data from the response
    console.log(movieJson);
    addMovieToTable(movieJson);                     // adds new movie to table

    // reset the form for the user to clear the data out
    document.getElementById("new-movie-form").reset();
}

// helper function to add a movie object to the HTML table
const addMovieToTable = (newMovie) => {

    // DOM Manipulation - changing the DOM in your JS code

    // creating necessary DOM elements
    let tr = document.createElement("tr");          // creates <tr> tag
    let titleTd = document.createElement("td");     // creates <td> tag for movie title
    let genreTd = document.createElement("td");     // creates <td> tag for movie genre
    let ratingTd = document.createElement("td");    // creates <td> tag for movie rating
    let directorTd = document.createElement("td");  // creates <td> tag for movie director

    let editBtnTd = document.createElement("td");   // creates <td> tag for edit button
    let deleteBtnTd = document.createElement("td"); // creates <td> tag for delete button

    // putting data into the TD elements
    titleTd.innerText = newMovie.title;             // make sure the property you access here MATCHES the property name in the JSON
    genreTd.innerText = newMovie.genre;
    ratingTd.innerText = newMovie.rating;
    directorTd.innerText = newMovie.director.firstName + " " + newMovie.director.lastName;

    // on* - create event listeners in the HTML directly
    editBtnTd.innerHTML = `<button class="btn btn-primary p-1" id="EDIT-${newMovie.id}" onclick="activateEditForm(${newMovie.id})">Edit</button>`;
    deleteBtnTd.innerHTML = `<button class="btn btn-danger p-1" id="DEL-${newMovie.id}" onclick="activateDeleteForm(${newMovie.id})">Delete</button>`;

    // putting all the TDs into the TR
    tr.appendChild(titleTd);
    tr.appendChild(genreTd);
    tr.appendChild(ratingTd);
    tr.appendChild(directorTd);

    tr.appendChild(editBtnTd);
    tr.appendChild(deleteBtnTd);

    // make sure EACH row is unique - can run into DOM errors if they are not UNIQUE
    tr.setAttribute("id", "TR-" + newMovie.id);

    // appending the new <tr> into the end of the table body
    let tableBody = document.getElementById("movie-table-body");
    tableBody.appendChild(tr);

    // add movie to the master list of all movies
    allMovies.push(newMovie);
}

// helper function to update a movie object in the HTML table
const updateMovieInTable = (movie) => {

    // can manually set innerHTML rather than creating a bunch of new tags
    document.getElementById("TR-" + movie.id).innerHTML = `
    <td>${movie.title}</td>
    <td>${movie.genre}</td>
    <td>${movie.rating}</td>
    <td>${movie.director.firstName + " " + movie.director.lastName}</td>
    <td><button class="btn btn-primary p-1" id="EDIT-${movie.id}" onclick="activateEditForm(${movie.id})">Edit</button></td>
    <td><button class="btn btn-danger p-1" id="DEL-${movie.id}" onclick="activateDeleteForm(${movie.id})">Delete</button></td>
    `;
}

// helper function to remove a movie from the HTML table
const removeMovieFromTable = (movieId) => {
    // using the table row ID to find it in the document and remove it
    const tr = document.getElementById("TR-" + movieId);
    tr.remove();
}

// helper function to enable update form
const activateEditForm = (movieId) => {

    // getting the movie data for the id that was given
    for(let movie of allMovies) {
        if(movie.id === movieId) {
            movieToUpdate = movie;
            break;
        }
    }

    // fill in the edit form with the values of the movie that is being edited
    document.getElementById("update-movie-title").value = movieToUpdate.title;
    document.getElementById("update-movie-genre").value = movieToUpdate.genre;
    document.getElementById("update-movie-rating").value = movieToUpdate.rating;
    document.getElementById("update-director-firstname").value = movieToUpdate.director.firstName;
    document.getElementById("update-director-lastname").value = movieToUpdate.director.lastName;

    // when edit is clicked, hide "new" and "delete" forms and show "update" form
    document.getElementById("update-movie-form").style.display = "block";
    document.getElementById("new-movie-form").style.display = "none";
    document.getElementById("delete-movie-form").style.display = "none";

}

// helper function to enable delete form
const activateDeleteForm = (movieId) => {
    // getting the movie data for the id that was given
    for(let movie of allMovies) {
        if(movie.id === movieId) {
            movieToDelete = movie;
            break;
        }
    }

    // fill in the delete form with the values of the movie that is being deleted
    document.getElementById("delete-movie-title").value = movieToDelete.title;
    document.getElementById("delete-movie-genre").value = movieToDelete.genre;
    document.getElementById("delete-movie-rating").value = movieToDelete.rating;
    document.getElementById("delete-director-firstname").value = movieToDelete.director.firstName;
    document.getElementById("delete-director-lastname").value = movieToDelete.director.lastName;

    // when delete is clicked, hide "new" and "update" forms and show "delete" form
    document.getElementById("delete-movie-form").style.display = "block";
    document.getElementById("new-movie-form").style.display = "none";
    document.getElementById("update-movie-form").style.display = "none";
}

// helper function to reset forms
const resetAllForms = () => {
    document.getElementById("new-movie-form").style.display = "block";
    document.getElementById("update-movie-form").style.display = "none";
    document.getElementById("delete-movie-form").style.display = "none";
}