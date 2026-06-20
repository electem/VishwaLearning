import React, { useState, useEffect } from "react";
import { connect, sendMessage } from "./socket";

const ChatApp = () => {

    const [sender, setSender] = useState("Ravi");
    const [receiver, setReceiver] = useState("John");
    const [message, setMessage] = useState("");
    const [chat, setChat] = useState([]);

    useEffect(() => {
        connect((msg) => {
            setChat((prev) => [...prev, msg]);
        }, sender);
    }, [sender]);

    const handleSend = () => {

        const msg = {
            sender,
            receiver,
            content: message,
            timestamp: new Date().toLocaleTimeString()
        };

        sendMessage(msg);
        setChat((prev) => [...prev, msg]);
        setMessage("");
    };

    return (
        <div style={{ padding: 20 }}>
            <h2>1-to-1 Chat</h2>

            <div>
                <input value={sender} onChange={(e) => setSender(e.target.value)} placeholder="Your name" />
                <input value={receiver} onChange={(e) => setReceiver(e.target.value)} placeholder="Receiver name" />
            </div>

            <div style={{ border: "1px solid black", height: 200, overflow: "auto" }}>
                {chat.map((c, i) => (
                    <div key={i}>
                        <b>{c.sender}</b>: {c.content}
                    </div>
                ))}
            </div>

            <input
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                placeholder="Type message"
            />

            <button onClick={handleSend}>Send</button>
        </div>
    );
};

export default ChatApp;