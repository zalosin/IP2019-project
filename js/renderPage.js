$(document).on('ready', function () {
let id = window.location.search.substr(4, window.location.search.length);
let url = "https://cors-anywhere.herokuapp.com/http://newsbagserver.herokuapp.com/article/" + id;

fetch(url)
    .then((response) => {
        if (response.ok) {
            return response.json();
        }
    })
    .then((json) => {
            let article = json;
            var date = new Date(0);
            date.setUTCSeconds(article.createTime);
            var date_string = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
            let el = `  <h3>`+ article.title +`</h3>
                        <p>`+ article.body +`</p>`;
            $('#render-post').append(el1);
    })})
    
