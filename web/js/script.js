
var serverHostName = window.location.hostname;

var serverProtocolName = window.location.protocol;

var portName = window.location.port;

if (portName.length == 0) {
    portName = "80";
}

if (serverHostName === "localhost")
{
    serverPath = serverProtocolName + "//" + serverHostName + ":" + portName;
}
else
{
    serverPath = serverProtocolName + "//" + serverHostName;
}

function serverConnectFunc(serverUrl, jsonData) {
    $.ajax({
        url: serverUrl + "/",
        type: 'POST',
        data: jsonData,

        dataType: 'json',
        async: true,

        success: function (event) {
            switch (event["answer"])
            {
                case "error":
                    var errorText = event["error"];
                    alert(errorText);
                    break;
                case "doneDone":
                    alert("Отправлено на проверку заказчику!");
                    break;
                case "goOrder":
                    document.location.href = serverUrl + "/paycheck.html";
                    alert("switch");
                    break;

                case "ok":
                    alert("success");
                    break;

                case "names":
                    var keysList = event["list"].replace("[", ""). replace("]", "").split(",");
                    $("#table_names").empty();

                    keysList.forEach(function(item, i, arr) {
                        $("#table_names").append("<tr><td>" + item + "</td></tr>");
                    });

                    break;
            }
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function addName()
{
    var jsonData = new Object();
    jsonData.command = "1";
    jsonData.name = $('#NewNameInput').val();

    serverConnectFunc(serverPath, JSON.stringify(jsonData));
}

function addOrder() {
    document.location.href = (serverPath + "/createpaycheck.html");
}

function onDone() {
    var answerString = prompt("Введите уникальный номер заказа");
    if (isNaN(answerString)){
        alert("Уникальный номер может состоять только из цифр");
        return;
    }
    var answer = parseInt(answerString);

    var jsonData = new Object();
    jsonData.command = "6";
    jsonData.orderId = answer;

    serverConnectFunc(serverPath, JSON.stringify(jsonData));
}
