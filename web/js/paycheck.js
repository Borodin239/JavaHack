
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

        done: function (event) {
            alert("0");
            switch (event["answer"])
            {
                case "goOrderFinal":
                    document.location.href = serverUrl+"/index.html";
                    alert("switch");
                    break;

                case "error":
                    var errorText = event["error"];
                    alert(errorText);
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

function createOrder() {
    var jsonData = new Object();
    jsonData.command = "41";
    jsonData.link = $('#linkInput').val();
    jsonData.deadline = $('#deadlineInput').val();
    jsonData.price = $('#priceInput').val();
    serverConnectFunc(serverPath, JSON.stringify(jsonData));
}

function backOnMain() {
    document.location.href = serverPath + "/index.html";
}
