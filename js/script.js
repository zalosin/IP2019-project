let url = "https://cors-anywhere.herokuapp.com/http://newsbagserver.herokuapp.com/article/all";

function getArticles() {
    
    fetch(url)
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
        })
        .then((json) => {
            let articles = json;
            if ( window.location.pathname.indexOf("index.html") > -1 ){
                renderArticles(articles);
                renderEditorChoices(articles);
                renderMostRead(articles);
            }
        })

    let url2 = "https://cors-anywhere.herokuapp.com/http://newsbagserver.herokuapp.com/category/all";
    fetch(url2)
        .then((response) =>{
            if(response.ok){
                return response.json();
            }
        })
        .then((json)=>{
            renderCategories(json);
        })
}

function renderArticles(arr) {
    for (let i = 0; i < 9; i++) {
        var date = new Date(0);
        date.setUTCSeconds(arr[i].createTime);
        var date_string = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
        let el1 = `<div id="post-"` + i + ` class="col-md-4">
                <div class="post">
                    <a class="post-img" href="blog-post.html"><img src="./img/stire-6.jpg" alt=""></a>
                    <div class="post-body">
                        <div class="post-meta">
                            <a class="post-category cat-1" href="category.html">Actualitate</a>
                            <span class="post-date">` + date_string + `</span>
                        </div>
                        <h3 class="post-title">` + arr[i].title + `</h3>
                    </div>
                </div>
            </div>`;
        $('#recent-stories-div').append(el1);
    }
}

function renderEditorChoices(arr) {
    var rndStory = Math.floor(Math.random() * arr.length);
    var date = new Date(0);
    date.setUTCSeconds(arr[rndStory].createTime);
    var date_string = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
    let div = `<div id="editor-0" class="col-md-8">
                    <div class="post post-thumb">
                        <a class="post-img" href="blog-post.html"><img src="./img/stire-1.jpg" alt=""></a>
                        <div class="post-body">
                            <div class="post-meta">
                                <a class="post-category cat-1" href="category.html">Actualitate</a>
                                <span class="post-date">` + date_string + `</span>
                            </div>
                            <h3 class="post-title"><a href="blog-post.html">` + arr[rndStory].title + `</a></h3>
                        </div>
                    </div>
                </div>`
    console.log("this is the div",div)
    $("#editor-recommendations-div").append(div);
    for (let i = 1; i < 5; i++) {
        var rndStory = Math.floor(Math.random() * arr.length);
        var date = new Date(0);
        date.setUTCSeconds(arr[rndStory].createTime);
        var date_string = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
        let div = `<div id="editor-"`+i+` class="post post-widget">
                        <a class="post-img" href="blog-post.html"><img src="./img/stire-2.jpg" alt=""></a>
                        <div class="post-body">
                            <h3 class="post-title"><a href="blog-post.html">`+arr[rndStory].title+`</a></h3>
                        </div>
                    </div>`
        $("#editor-aside-div").append(div);
    }
}

function renderMostRead(arr) {
    for (let i = 0; i < 4; i++) {
        var rndStory = Math.floor(Math.random() * arr.length);
        var date = new Date(0);
        date.setUTCSeconds(arr[i].createTime);
        var date_string = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();

        let el1 = `<div id="mostRead-"` + i + ` class="col-md-12">
                        <div class="post post-row">
                            <a class="post-img" href="blog-post.html"><img src="./img/post-4.jpg" alt=""></a>
                            <div class="post-body">
                                <div class="post-meta">
                                    <a class="post-category cat-2" href="category.html">JavaScript</a>
                                    <span class="post-date">` + date_string + `</span>
                                </div>
                                <h3 class="post-title"><a href="blog-post.html">` + arr[rndStory].title + `</a></h3>
                                <p class="truncate" id="body">` + arr[rndStory].body.substr(0, 100) + "..." + `</p>
                            </div>
                        </div>
                    </div>`
        $('#most-read-div').append(el1);
    }
}

function renderCategories(arr){
    for (let i = 0; i < arr.length; i++){
        let title = ca(arr[i].title);
        let div = `<li class="cat-"`+arr[i].id+`><a href="category.html">`+title+`</a></li>`;
        let div2 = `<li><a href="#" class="cat-"`+arr[i].id+`>`+title+`<span>340</span></a></li>`;
        $("#category-navbar-ul").append(div);
        $("#bottom-category-ul").append(div2);
        $("#footer-category-ul").append(div);
    }
}

function ca(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function search(elem){
    if(event.key === 'Enter') {
        window.location.replace("search-results.html?search="+elem.value);
    }
}

function getSearchedArticles(elem) {
    elem = elem.toLowerCase();
    fetch(url)
        .then((response) => {
            if (response.ok) {
                return response.json();
            }
        })
        .then((json) => {
            let articles = json;
            for (i = 0; i < articles.length; i++){
                if((!articles[i].title.toLowerCase().includes(elem)) && (!articles[i].body.toLowerCase().includes(elem))){
                    articles.splice(i, 1);
                    i = i - 1;                    
                }
            }
            if ( window.location.pathname.indexOf("search-results.html") > -1 ){
                renderSearched(articles);
            }
        })
}

function renderSearched(arr) {
    for (let i = 0; i < arr.length; i++) {
        var rndStory = Math.floor(Math.random() * arr.length);
        var date = new Date(0);
        date.setUTCSeconds(arr[i].createTime);
        var date_string = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();

        let el1 = `<div id="mostRead-"` + i + ` class="col-md-12">
                        <div class="post post-row">
                            <a class="post-img" href="blog-post.html"><img src="./img/post-4.jpg" alt=""></a>
                            <div class="post-body">
                                <div class="post-meta">
                                    <a class="post-category cat-2" href="category.html">JavaScript</a>
                                    <span class="post-date">` + date_string + `</span>
                                </div>
                                <h3 class="post-title"><a href="blog-post.html">` + arr[rndStory].title + `</a></h3>
                                <p class="truncate" id="body">` + arr[rndStory].body.substr(0, 100) + "..." + `</p>
                            </div>
                        </div>
                    </div>`
        $('#search-result-div').append(el1);
    }
}

getArticles();