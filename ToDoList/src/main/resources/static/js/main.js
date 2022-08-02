$(function() {
    const appendTask = function(data) {
        var taskCode = '<h4>' + data.name + '</h4>' + 'Срок до: ' + data.term;
        $('#task-list').append('<div>' + taskCode + '</div>');
    };

    //Loading tasks on load page
    $.get('/tasks/', function(response) {
        for (i in response) {
            appendTask(response[i]);
        }
    });

    //Show adding task form
    $('#show-add-task-form').click(function() {
        $('#shadow').css('display', 'flex');
        $('#task-form').css('display', 'flex');
    });

    //Closing adding task form
    $('task-form').click(function(event) {
        if (event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Adding task
    $('#save-task').click(function() {
        var data = $('#task-form form').serialize();
        $.ajax({
            method: "POST",
            url: "/tasks/",
            data: data,
            success: function(response) {
                $('#shadow').css('display', 'none');
                $('#task-form').css('display', 'none');
                var task = {};
                task.id = response.id;
                var dataArray = $('task-form form').serializeArray();
                for (i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);
            }
        });
        return false;
    });
});