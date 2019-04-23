$(document).on('ready', function () {
    $('#postStory').click(function () {
        var title = $('#myeditable-h1')[0].innerText;
        var body = $('#myeditable-div')[0].innerText;
        var article = {
            authorId: 456,
            body: body,
            title: title,
        };
        var message = postArticle(article);
        var status = $('#postStatus')[0];

        message.then(function (response) {
            status.hidden = false;
            status.innerText = response;
            if (response === 'Article saved with success!') {
                status.style.color = 'green';
            } else {
                status.style.color = 'red';
            }
        });
    });
    function hidePostStatus(){
        $('#postStatus')[0].hidden = true;
    }
    $('#myeditable-h1').click(hidePostStatus);
    $('#myeditable-div').click(hidePostStatus);
});