package com.example.hackathon.listener;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.hackathon.entity.UserEntity;
import com.example.hackathon.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketListener {
    private final SocketIOServer server;
    private final UserRepository userRepository;


    public SocketListener(SocketIOServer server, UserRepository userRepository) {
        this.server = server;
        this.userRepository = userRepository;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", MessageSocket.class, onChatReceived());

    }

    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            Long userId = Long.parseLong(client.getHandshakeData().getSingleUrlParam("userId"));
            String lat = client.getHandshakeData().getSingleUrlParam("lat");
            String lng = client.getHandshakeData().getSingleUrlParam("lng");
            if (room != null && !room.trim().isEmpty()) {
                log.info("Room ID: {}", room);
                client.joinRoom(room);
            } else {
                log.warn("Client[{}] connected without valid room parameter", client.getSessionId());
                client.disconnect();
            }
            log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            MessageSocket messageSocket = new MessageSocket();
            messageSocket.setId(user.getId());
            messageSocket.setName(user.getName());
            messageSocket.setCode(room);
            messageSocket.setImg(user.getImg());
            messageSocket.setLat(Double.parseDouble(lat));
            messageSocket.setLng(Double.parseDouble(lng));
            server.getRoomOperations(room).sendEvent("notification", messageSocket);

        };
    }

    private DataListener<MessageSocket> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("Name: {}, Lat/Lon {}, Code: {}", data.getName(), data.getLat() + "," + data.getLng(),  data.getCode());
            server.getRoomOperations(data.getCode()).sendEvent("notification", data);
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }
}
