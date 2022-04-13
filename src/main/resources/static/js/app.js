let xhr = new XMLHttpRequest();
var getAllRacesUrl = new URL('http://localhost:8080/api/races/sortedByTime');
var toggleYellowUrl = new URL('http://localhost:8080/api/races/trafficlights/yellow');
var toggleGreenUrl = new URL('http://localhost:8080/api/races/trafficlights/green');
var toggleRedUrl = new URL('http://localhost:8080/api/races/trafficlights/red');
var startRaceURL = new URL('http://localhost:8080/api/races/start');
var raceStatusURL = new URL('http://localhost:8080/api/races/status');
var raceGetTokenURL = new URL('http://localhost:8080/api/races/getToken');
var addRaceURL = new URL('http://localhost:8080/api/races/create');
var time = 0;
var cartoken;
var lastId;
var id = 0;
var form;
var main;

function send() {
    xhr.open('GET', getAllRacesUrl);
    xhr.responseType = 'json';
    xhr.send();
    xhr.onload = function() {
        var object = xhr.response;
        console.log(object.data[0]['carOwner'])
        if (object.data.length>0) {
            let section = document.querySelector('.wrapper');
            main = document.createElement('main');
            main.className = "row title";
            main.innerHTML = `<ul>
                <li>ID</li>
                <li>CAR OWNER</li>
                <li><span class="title-hide">TIME</span></li>
                <li>CAR TOKEN</li>
                <li>DATE</li>
            </ul>`
            section.appendChild(main);
            for (let i=0;i<object.data.length;i++) {
                let section2 = document.createElement('section');
                section2.className = "row-fadeIn-wrapper";
                let article = document.createElement('article');
                article.className = "row fadeIn nfl";
                article.innerHTML = `<ul>
                                  <li>${object.data[i]['id']}</li>
                                  <li>${object.data[i]['carOwner']}</li>
                                  <li>${object.data[i]['time']}</li>
                                  <li>${object.data[i]['carToken']}</li>
                                  <li>${object.data[i]['date']}</li>
                                </ul>
                                <ul class="more-content">
                                  <li>Rank: ${i+1}</li>
                                </ul>`;
                section2.appendChild(article);
                section.appendChild(section2);
                lastId = parseInt(object.data[i]['id']);
                if (lastId > id) {
                    id = lastId;
                }
            }

        }
        id = id + 1;
    };
}

function toggleYellow() {
    xhr.open('GET', toggleYellowUrl);
    xhr.responseType = 'json';
    xhr.send();
}
function toggleRed() {
    xhr.open('GET', toggleRedUrl);
    xhr.responseType = 'json';
    xhr.send();
}
function toggleGreen() {
    xhr.open('GET', toggleGreenUrl);
    xhr.responseType = 'json';
    xhr.send();
}
//stopwatch
var ms = 0, s = 0, m = 0;
var timer;
var stopwatchElem = document.querySelector('.stopwatch');

function stopwatchStart() {
    if (!timer) {
        timer = setInterval(run, 10);
        xhr.open('GET', startRaceURL);
        xhr.responseType = 'json';
        xhr.send();
    }
}
function stopwatchPause() {
    clearInterval(timer);
    timer = false;
}
function stopwatchStop() {
    clearInterval(timer);
    timer = false;
    m = 0;
    s = 0;
    ms = 0;
    stopwatchElem.innerHTML = "00:00:00";
}
function run() {
    stopwatchElem.textContent = (m < 10 ? "0" + m : m) + ":"
        + (s < 10 ? "0" + s : s) + ":"
        + (ms < 10 ? "0" + ms : ms);
    ms++;
    if (ms == 100) {
        ms = 0;
        s++;
    }
    if(ms == 40 || ms == 80) {
        xhr.open('GET', raceStatusURL);
        xhr.responseType = 'json';
        xhr.send();
        xhr.onload = function () {
            var res = xhr.response;
            if (res.raceStatus == "raceEnded") {
                stopwatchPause();
                time = stopwatchElem.textContent;
                createForm();
            }
        }
    }
    if (s ==  60) {
        s = 0;
        m++;
    }
}
function createForm() {
    var container = document.querySelector('.stopwatch-container');
    form = document.createElement("form");
    form.className = 'form';
    form.id = "create-form";
    form.innerHTML = `<label for="carOwner">Car Owner</label>
                      <input type="text" name="carOwner" id="carOwner">
                      <button type="submit" class="btn btn-primary btn-ghost" onclick="hideform()">Submit</button>`;
    container.appendChild(form);
    form.addEventListener('submit', handleSubmit);
    xhr.open('GET', raceGetTokenURL);
    xhr.responseType = 'json';
    xhr.send();
    xhr.onload = function () {
        var obj = xhr.response;
        cartoken = obj.token;
    }
}

function handleSubmit(event) {
    event.preventDefault();
    const data = new FormData(event.target);
    const value = data.get('carOwner');
    if (!(value.length < 1)) {
        let dateNow = new Date();
        let output = String(dateNow.getDate()).padStart(2, '0') + '/'
            + String(dateNow.getMonth() + 1).padStart(2, '0') + '/'
            + dateNow.getFullYear();
        const race = JSON.stringify({id, carOwner: value, time, date: output, carToken: cartoken}, null, '\t');
        console.log(race);
        xhr.open('POST', addRaceURL, true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.responseType = 'json';
        xhr.send(race);
    } else {
        alert('Incorrect owner');
    }

}function hideform() {
    $("form").hide(1000);
}






