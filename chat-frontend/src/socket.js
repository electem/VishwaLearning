import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

let stompClient = null;

export const connect = (onMessageReceived, username) => {

    const socket = new SockJS("http://localhost:8080/ws");

    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,

        onConnect: () => {
            console.log("Connected as " + username);

            stompClient.subscribe(
                "/user/queue/messages",
                (message) => {
                    onMessageReceived(JSON.parse(message.body));
                }
            );
        }
    });

    stompClient.activate();
};

export const sendMessage = (msg) => {
    stompClient.publish({
        destination: "/app/chat.sendMessage",
        body: JSON.stringify(msg)
    });
};