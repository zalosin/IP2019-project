function postArticle(article) {
    var url = 'https://cors-anywhere.herokuapp.com/http://newsbagserver.herokuapp.com/article/';

    return fetch(url, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(article),
        contentType: 'application/json',
    }).then(function (response) {
        if (response.ok) {
            return 'Article saved with success!';
        }
        return response.status + ': ' + response.statusText;
    });
}