function getArticles(){
    let url = "https://cors-anywhere.herokuapp.com/http://newsbagserver.herokuapp.com/article/all";

    fetch(url)
        .then((response) =>{
            if(response.ok){
                return response.json();
            }
        })
        .then((json)=>{
            let articles = json;
            renderArticles(articles);
            renderEditorChoices(articles);
        })
}

function renderArticles(arr){
    for (let i = 0; i < arr.length; i++){
        var story  = document.getElementById("post-"+i);
        story.getElementsByClassName("post-title")[0].innerText = arr[i].title;
        var date = new Date(0);
        date.setUTCSeconds(arr[i].createTime);
        var date_string = date.getDate() + "." + (date.getMonth() + 1) + "." + date.getFullYear();
        story.getElementsByClassName("post-date")[0].innerText = date_string;
    }
}

function renderEditorChoices(arr){
    for (let i = 0; i < 4; i++){
        var story  = document.getElementById("editor-"+i);
        var rndStory = Math.floor(Math.random() * arr.length);
        story.getElementsByClassName("post-title")[0].innerText = arr[rndStory].title;
    }
}

// function renderMostRead(arr){
//     for (let i = 0; i < 4; i++){
//         var story  = document.getElementById("editor-"+i);
//         var rndStory = Math.floor(Math.random() * arr.length);
//         story.getElementsByClassName("post-title")[0].innerText = arr[rndStory].title;
//     }
// }

getArticles();