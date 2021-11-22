package com.example.security.config;

import com.example.security.core.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    //@EnableWebSocket 어노테이션을 사용해 WebSocket을 활성화 하도록 한다.
    //
    //WebSocket에 접속하기 위한 Endpoint는 /chat으로 설정하고,
    //
    //도메인이 다른 서버에서도 접속 가능하도록 CORS : setAllowedOrigins("*"); 를 추가

    //교차 출처 리소스 공유(Cross-Origin Resource Sharing, CORS)는
    // 추가 HTTP 헤더를 사용해 한 출처에서 실행 중인 웹 어플리케이션이 다른
    // 출처의 선택한 자원에 접근할 수 있는 권한을 부여하도록 브라우저에 알려주는 체제이다
    // 웹 어플리케이션은 리소스가 자신의 출처( domain, protocol, port )와 다를 때 교차
    // 출처 HTTP 요청을 실행한다. 이에 대한 응답으로 서버는 Access-Control-Allow-Origin 헤더를 다시 보낸다.

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/ws/chat")
                .setAllowedOriginPatterns("http://*:8080", "http://*.*.*.*:8080")
                .withSockJS()
                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.2/sockjs.min.js");
    }
}
