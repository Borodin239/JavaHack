
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

function addOrder() {
    var jsonData = new Object();
    jsonData.command = "41";
    jsonData.link = "sad";
    jsonData.deadline = "12";
    jsonData.price = $('#PriceInput').val();


    serverConnectFunc(serverPath, JSON.stringify(jsonData));
}
