package jp.com.xpower.app2017.controller.websocket;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EchoHandler extends TextWebSocketHandler {
    /**
     * セッションプールです。
     */
    //private Map<String, WebSocketSession> sessionPool = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Set<WebSocketSession>> roomSessionPool = new ConcurrentHashMap<>();
    /**
     * 接続が確立したセッションをプールします。
     * @param session セッション
     * @throws Exception 例外が発生した場合
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomid = session.getUri().getQuery();

        roomSessionPool.compute(roomid, (key, sessions) -> {

            if (sessions == null) {
                sessions = new CopyOnWriteArraySet<>();
            }
            sessions.add(session);

            return sessions;
        });
    }
    /**
     * 切断された接続をプールから削除します。
     * @param session セッション
     * @param status ステータス
     * @throws Exception 例外が発生した場合
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomid = session.getUri().getQuery();

        roomSessionPool.compute(roomid, (key, sessions) -> {

            sessions.remove(session);
            if (sessions.isEmpty()) {
                // 1件もない場合はMapからクリア
                sessions = null;
            }

            return sessions;
        });
    }
    /**
     * ハンドリングしたテキストメッセージをグローバルキャストします。
     * @param session セッション
     * @param message メッセージ
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomid = session.getUri().getQuery();

        for (WebSocketSession roomSession : roomSessionPool.get(roomid)) {
            roomSession.sendMessage(message);
        }
    }
}
