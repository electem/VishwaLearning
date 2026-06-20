import { useEffect, useState } from "react";
import {
    connectWebSocket,
    sendMessage
} from "../services/websocketService";

import "./ChatBox.css";


function ChatBox() {


    const [messages, setMessages] = useState([]);

    const [text, setText] = useState("");

    const [username, setUsername] = useState("");



    useEffect(() => {


        connectWebSocket((message) => {


            setMessages(prev => {


                const duplicate = prev.some(
                    msg =>
                        msg.sender === message.sender &&
                        msg.content === message.content
                );


                if (duplicate) {
                    return prev;
                }


                return [
                    ...prev,
                    message
                ];

            });


        });


    }, []);




    const send = () => {


        if (!username.trim() || !text.trim()) {
            return;
        }



        const message = {

            sender: username,

            receiver:
                username === "User1"
                    ? "User2"
                    : "User1",

            content: text

        };



        sendMessage(message);


        setText("");

    };



    return (

        <div className="chat-container">


            <div className="chat-header">

                <h2>
                    💬 Chat
                </h2>


            </div>



            <div className="user-box">

                <input

                    value={username}

                    onChange={
                        e => setUsername(e.target.value)
                    }

                    placeholder="Your name"

                />

            </div>




            <div className="message-box">


                {
                    messages.map((msg,index)=>(


                        <div

                            key={index}

                            className={
                                msg.sender === username
                                ? "message sent"
                                : "message received"
                            }

                        >

                            <span className="sender">
                                {msg.sender}
                            </span>


                            <span>
                                {msg.content}
                            </span>


                        </div>


                    ))
                }


            </div>




            <div className="input-area">


                <input

                    value={text}

                    onChange={
                        e => setText(e.target.value)
                    }

                    onKeyDown={
                        e => {

                            if(e.key === "Enter"){
                                send();
                            }

                        }
                    }

                    placeholder="Type a message"

                />



                <button onClick={send}>

                    ➤

                </button>


            </div>



        </div>

    );

}


export default ChatBox;