import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";


let stompClient = null;



export const connectWebSocket = (onMessageReceived) => {


    stompClient = new Client({


        webSocketFactory: () =>
            new SockJS(
                "http://localhost:8080/ws"
            ),


        reconnectDelay: 5000,


        onConnect: () => {


            console.log(
                "Connected"
            );



            stompClient.subscribe(

                "/topic/messages",

                (message) => {


                    const data =
                        JSON.parse(
                            message.body
                        );


                    console.log(
                        "Received:",
                        data
                    );


                    onMessageReceived(
                        data
                    );

                }

            );

        }

    });



    stompClient.activate();

};




export const sendMessage = (message) => {


    if (
        stompClient &&
        stompClient.connected
    ) {


        stompClient.publish({


            destination:
                "/app/chat.sendMessage",


            body:
                JSON.stringify(message)

        });


        console.log(
            "Message sent"
        );


    } else {


        console.log(
            "WebSocket not connected"
        );

    }

};