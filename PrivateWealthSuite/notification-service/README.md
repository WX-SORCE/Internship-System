# Notification Service 服务模块 README

## 一、项目需求

​		该项目是一个通知服务模块，旨在处理和管理与交易相关的通知信息。主要需求包括监听 Kafka 消息主题，接收交易状态变更和审批结果的通知，将通知信息保存到数据库，并通过 WebSocket 实时推送给前端。同时，提供 RESTful API 接口，方便前端或其他服务获取通知列表和标记通知为已读。

## 二、项目核心功能

1. **Kafka 消息监听**：监听 `trade-notifications` 和 `approval-notifications` 主题，处理交易状态变更和审批结果通知。
2. **通知保存**：将接收到的通知信息保存到数据库。
3. **WebSocket 实时推送**：将通知信息实时推送给前端。
4. **RESTful API 接口**：提供根据客户 ID 获取通知列表和标记通知为已读的接口。

## 三、项目环境

- **操作系统**：不限
- **开发语言**：Java 17
- **框架**：Spring Boot 3.4.4
- **消息队列**：Kafka
- **数据库**：支持 JPA 的关系型数据库（如 MySQL、PostgreSQL 等）
- **构建工具**：Maven

## 四、项目依赖

- spring-cloud-starter-netflix-eureka-client
- spring-boot-starter-data-jpa
- spring-cloud-starter-config
- spring-boot-starter-websocket
- jackson-databind

## 五、项目结构

```plaintext
notification-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── alxy
│   │   │           └── notificationservice
│   │   │               ├── config
│   │   │               │   └── WebSocketConfig.java  # WebSocket 配置类
│   │   │               ├── controller
│   │   │               │   ├── MessageController.java  # 消息发送控制器
│   │   │               │   └── NotificationController.java  # 通知控制器
│   │   │               ├── entity
│   │   │               │   └── Notification.java  # 通知实体类
│   │   │               ├── kafka
│   │   │               │   └── KafkaConsumer.java  # Kafka 消费者类
│   │   │               ├── repository
│   │   │               │   └── NotificationRepository.java  # 通知仓库类
│   │   │               ├── service
│   │   │               │   ├── NotificationService.java  # 通知服务接口
│   │   │               │   ├── impl
│   │   │               │   │   └── NotificationServiceImpl.java  # 通知服务实现类
│   │   │               │   └── WebSocketService.java  # WebSocket 服务类
│   │   │               └── NotificationServiceApplication.java  # 应用启动类
│   │   └── resources
│   │       └── application.properties  # 应用配置文件
│   └── test
│       └── java
│           └── com
│               └── alxy
│                   └── notificationservice
│                       └── NotificationServiceApplicationTests.java  # 测试类
└── pom.xml  # Maven 项目配置文件
```

## 六、数据库设计

### 通知表（Notification）

| 字段名          | 类型    | 描述                                         |
| --------------- | ------- | -------------------------------------------- |
| notification_id | String  | 通知唯一 ID                                  |
| client_id       | String  | 客户 ID                                      |
| type            | String  | 通知类型（如 TRADE_STATUS、APPROVAL_RESULT） |
| content         | String  | 通知内容                                     |
| created_at      | Date    | 通知创建时间                                 |
| read            | Boolean | 通知是否已读                                 |

## 七、接口调用 API

### 1. 根据客户 ID 获取通知列表

- **请求 URL**：`GET /notifications/{clientId}`
- 请求参数：
  - `clientId`：客户 ID
  - `type`：通知类型（可选）
- **响应数据**：

```json
[
    {
        "notificationId": "NOTIF-xxxxxx",
        "clientId": "xxxxxx",
        "type": "TRADE_STATUS",
        "content": "您的交易 xxxx 状态已更新为：xxxx",
        "createdAt": "2024-10-01 12:00:00",
        "read": false
    },
    {
        "notificationId": "NOTIF-yyyyyy",
        "clientId": "xxxxxx",
        "type": "APPROVAL_RESULT",
        "content": "交易 xxxx 的审批结果为【xxxx】。审批意见：xxxx",
        "createdAt": "2024-10-02 13:00:00",
        "read": true
    }
]
```

### 2. 标记通知为已读

- **请求 URL**：`PUT /notifications/{notificationId}/read`
- 请求参数：
  - `notificationId`：通知 ID
- **响应数据**：

```json
{
    "notificationId": "NOTIF-xxxxxx",
    "clientId": "xxxxxx",
    "type": "TRADE_STATUS",
    "content": "您的交易 xxxx 状态已更新为：xxxx",
    "createdAt": "2024-10-01 12:00:00",
    "read": true
}
```

### 3. 触发向后端发送消息

- **请求 URL**：`GET /send-message`
- **响应数据**：

```plaintext
消息已发送
```

## 八、测试用例（使用 Postman）

### 1. 根据客户 ID 获取通知列表

- **请求 URL**：`http://localhost:8080/notifications/123?type=TRADE_STATUS`
- **请求方法**：GET
- **预期响应**：返回符合条件的通知列表

### 2. 标记通知为已读

- **请求 URL**：`http://localhost:8080/notifications/NOTIF-xxxxxx/read`
- **请求方法**：PUT
- **预期响应**：返回更新后的通知信息

### 3. 触发向后端发送消息

- **请求 URL**：`http://localhost:8080/send-message`
- **请求方法**：GET
- **预期响应**：返回 `消息已发送`

## 九、核心业务代码展示

### 1. Kafka 消费者类

```java
@Service
public class KafkaConsumer {

    @Autowired
    private WebSocketService webSocketService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "trade-notifications", groupId = "notification-group")
    public void handleTradeNotification(String message) {
        try {
            Map<String, Object> event = parseMessage(message);
            String clientId = (String) event.get("clientId");
            String tradeId = (String) event.get("tradeId");
            String status = (String) event.get("status");
            String content = String.format("您的交易 %s 状态已更新为：%s", tradeId, status);
            notificationService.saveNotification(clientId, "TRADE_STATUS", content);
            String messages = "客户编号为:" + clientId + "的用户，交易编号为:" + tradeId + "的交易，状态变为:" + status + "请处理！";
            webSocketService.sendMessageToFrontend(messages);
            System.out.println("已处理交易状态变更通知: " + content);
        } catch (JsonProcessingException e) {
            System.err.println("交易通知消息解析失败: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("处理交易通知时发生异常: " + e.getMessage());
        }
    }

    private Map<String, Object> parseMessage(String message) throws JsonProcessingException {
        return objectMapper.readValue(message, Map.class);
    }
}
```

### 2. 通知服务实现类

```java
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    @Override
    public void saveNotification(String clientId, String type, String content) {
        Notification notification = new Notification();
        notification.setNotificationId("NOTIF-" + UUID.randomUUID().toString().substring(0, 8));
        notification.setClientId(clientId);
        notification.setType(type);
        notification.setContent(content);
        notification.setCreatedAt(new Date());
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotifications(String clientId, String type) {
        if (type.equals("TRADE_STATUS")) {
            return notificationRepository.findByClientIdAndType(clientId, type);
        } else {
            return notificationRepository.findByClientId(clientId);
        }
    }

    @Transactional
    @Override
    public Notification markNotificationAsRead(String notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setRead(true);
            return notificationRepository.save(notification);
        }
        return null;
    }
}
```

### 3. WebSocket 服务类

```java
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessageToFrontend(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonMessage = objectMapper.writeValueAsString(new MessageWrapper(message));
            messagingTemplate.convertAndSend("/topic/messages", jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static class MessageWrapper {
        private String message;

        public MessageWrapper(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
```
