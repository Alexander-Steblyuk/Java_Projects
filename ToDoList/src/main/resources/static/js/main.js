$(function() {
    var linkIsVisited = 0;

    const appendTask = function(data) {
        var taskCode = '<a href="#" class="task-link" data-id="' + data.id + '">'
        + data.name + '</a>' + '<button class="delete-task" data-id="' + data.id + '">Удалить</button><br>';
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

    //Getting task
    $(document).on('click', '.task-link', function() {
        var link = $(this);
        var taskId = link.data('id');

        if (linkIsVisited === 0) {
            $.ajax({
                method: "GET",
                url: "/tasks/" + taskId,
                success: function(response) {
                    var code = '<span>Выполнить до ' + response.term + '</span>';
                    link.parent().append(code);
                },
                error: function(response) {
                    if (response.status === 404) {
                        alert("Task is not find!");
                    }
                }
            });
            linkIsVisited = 1;
        }
        return false;
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
                task.id = response;
                var dataArray = $('#task-form form').serializeArray();
                for (i in dataArray) {
                    task[dataArray[i]['name']] = dataArray[i]['value'];
                }
                appendTask(task);
            }
        });
        return false;
    });

    //Deleting task
    $(document).on('click', '.delete-task', function() {
        var button = $(this);
        var taskId = button.data('id');

        $.ajax({
            method: "DELETE",
            url: "/tasks/" + taskId,
            success: function(response) {
                button.parent().remove();
            },
            error: function(response) {
                if (response.status === 404) {
                    alert("Task is not find!");
                }
            }
        });
        return false;
    });
});