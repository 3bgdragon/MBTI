package com.example.security.core;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        //* payload ?
        //페이로드란 전송되는 데이터를 의미한다.. 데이터를 전송할 때, Header와 META 데이터, 에러 체크 비트 등과 같은 다양한 요소들을 함께 보내 데이터 전송 효율과 안정성을 높히게 된다. 이때, 보내고자 하는 데이터 자체를 의미하는 것이 페이로드이다. 예를 들어 택배 배송을 보내고 받을 때 택배 물건이 페이로드고 송장이나 박스 등은 부가적은 것이기 때문에 페이로드가 아니다.
        //다음 JSON에서 페이로드는 "data"이다. 나머지는 통신을 하는데 있어 용이하게 해주는 부가적 정보들이다.
        String payload = message.getPayload();
        log.info("payload : " + payload);

            for (WebSocketSession sess : list) {
                synchronized(sess) {
                    sess.sendMessage(message);
                }
            }
    }



    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info(session + "클라이언트 접속");
    }

    /* Client가 접속 해제 시 호출되는 메서드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session + "클라이언트 접속 해제");
        list.remove(session);
    }
}
