var count = 0;
window.onload = function(){
    const username = $("#email").text();

    //도배유저 체크용 메소드
    setInterval(function(){ checkUser(); }, 10000);
    function checkUser() {
        if(count > 10) {
            kickUser();
        }
        count = 0;
    }
    
    $("#disconn").on("click", (e) => {
        disconnect();
    })

    $("#button-send").on("click", (e) => {
        send();
    });

    var websocket = new SockJS("/ws/chat", null, {transports: ["websocket", "xhr-streaming", "xhr-polling"]});
    /*const websocket = new WebSocket("ws://localhost:8080/ws/chat");*/

    websocket.onmessage = onMessage;
    websocket.onopen = onOpen;
    websocket.onclose = onClose;

    function send(){

        let msg = document.getElementById("msg");

        console.log(username + ":" + msg.value);
        websocket.send(username + ":" + msg.value);
        msg.value = '';
    }

    //채팅창에서 나갔을 때
    function onClose(evt) {
        var str = username.split("@")[0] + ": 님이 방을 나가셨습니다.";
        websocket.send(str);
    }

    //채팅창에 들어왔을 때
    function onOpen(evt) {
        var str = username.split("@")[0] + ": 님이 입장하셨습니다.";
        websocket.send(str);
    }
    
    //도배방지 메시지 전송,도배유저 로그아웃처리
    function kickUser(evt) {
        websocket.send( username.split("@")[0] + ": 님은 도배방지를 위해 채팅방 연결이 해제되었습니다.");
                var str = "<div class='col-6'>";
                str += "<div class='alert alert-secondary' style='background-color: #de284a'>";
                str += "<b>" + username.split("@")[0] + " :  님은 도배방지를 위해 채팅방 연결이 해제되었습니다" + "</b>";
                str += "</div></div>";
                $("#msgArea").append(str);
                $("#msgArea").scrollTop($(document).height());

                swal("도배경고", "도배방지를 위하여 회원님은 로그아웃 처리되었습니다.", "warning").then(() => {
                    location.href="/logout";
                });
    }

    function onMessage(msg) {
        var data = msg.data;
        var arr = data.split(":");

        for(var i=0; i<arr.length; i++){
            console.log('arr[' + i + ']: ' + arr[i]);
        }

        var cur_session = username;

        //현재 세션에 로그인 한 사람
        console.log("cur_session : " + cur_session);
        sessionId = arr[0];
        message = arr[1];

        console.log("sessionID : " + sessionId);
        console.log("cur_session : " + cur_session);
        //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
        if(sessionId == cur_session){
            var str = "<div class='col-6'>";
            str += "<div class='alert alert-secondary' style='background-color: gold'>";
            str += "<b>" + sessionId.split("@")[0] + " : " + message + "</b>";
            str += "</div></div>";
            $("#msgArea").append(str);
            $("#msgArea").scrollTop($(document).height());
            count++;
        }
        else{
            var str = "<div class='col-6'>";
            str += "<div class='alert alert-warning'>";
            str += "<b>" + sessionId.split("@")[0] + " : " + message + "</b>";
            str += "</div></div>";
            $("#msgArea").append(str);
            $("#msgArea").scrollTop($(document).height());
        }
    }
}