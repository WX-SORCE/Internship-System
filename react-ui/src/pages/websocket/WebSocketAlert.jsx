/*
 * @Author: 严西娟 499820911@qq.com
 * @Date: 2025-04-02 15:46:07
 * @LastEditors: 严西娟 499820911@qq.com
 * @LastEditTime: 2025-04-02 15:52:00
 * @FilePath: \react-ant-admin\src\pages\websocket\WebSocketAlert.jsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React, { useEffect, useRef, useState } from'react';
import SockJS from'sockjs-client';
import { Client } from '@stomp/stompjs';
import './WebSocketAlert.css';

export const WebSocketAlert = () => {
     console.log('开始来凝结后端');
    const [alerts, setAlerts] = useState([]);
    const clientRef = useRef(null);

    const base_url = 'http://localhost:9007/ws'
    useEffect(() => {
        const socket = new SockJS(base_url);
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                console.log('成功连接到 WebSocket 服务器');
                client.subscribe('/topic/messages', (message) => {
                    try {
                        const data = JSON.parse(message.body);
                        const newAlert = {
                            id: Date.now(),
                            content: data.message // 从解析后的 JSON 中获取 message 字段
                        };
                        setAlerts((prevAlerts) => [...prevAlerts, newAlert]);
                        setTimeout(() => {
                            setAlerts((prevAlerts) => prevAlerts.filter(alert => alert.id!== newAlert.id));
                        }, 4000);
                    } catch (error) {
                        console.error('解析消息出错:', error);
                    }
                });
            },
            onDisconnect: () => {
                console.log('与后端的 STOMP 连接已断开');
            },
            onStompError: (frame) => {
                console.error('连接到 WebSocket 服务器失败: ', frame);
            }
        });

        clientRef.current = client;
        client.activate();

        return () => {
            if (clientRef.current) {
                clientRef.current.deactivate();
            }
        };
    }, []);

    return (
        <div className="alert-container">
            {alerts.map((alert) => (
                <div key={alert.id} className="alert">
                    <svg
                        className="alert-icon"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                    >
                        <path
                            strokeLinecap="round"
                            strokeLinejoin="round"
                            strokeWidth={2}
                            d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                        />
                    </svg>
                    <div className="alert-content">{alert.content}</div>
                </div>
            ))}
        </div>
    );
};
export default WebSocketAlert;