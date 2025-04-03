# Approval Service 项目文档

## 一、项目需求

在 PrivateWealthSuite 项目中，审批服务（`approval-service`）负责处理业务流程中的审批操作，确保交易等业务流程在不同层级人员（如客户经理、风控人员、合规人员）的审批下有序进行。通过该服务，实现审批流程的自动化管理、状态跟踪以及审批结果的通知。

## 二、项目核心功能

1. **审批记录创建**：根据交易 ID 和顾问 ID 创建审批记录，初始审批级别为客户经理审核。
2. 审批列表查询
   - 客户经理可以查询自己负责审批的列表。
   - 客户可以查询自己交易的审批列表。
3. **审批操作**：不同层级的人员对审批记录进行审批操作，包括通过和拒绝，并更新审批状态和交易状态，同时发送审批结果通知。
4. **驳回操作**：有权限的人员可以驳回交易，更新审批状态和交易状态。

## 三、项目环境

### 开发环境

- **操作系统**：Windows 10 / macOS / Linux
- **开发工具**：IntelliJ IDEA
- **JDK 版本**：Java 17
- **Maven 版本**：Maven 3.8.x

### 运行环境

- **Spring Boot 版本**：3.1.5
- **Spring Cloud 版本**：2022.0.4
- **数据库**：MySQL 8.0
- **消息队列**：Kafka 3.5.1
- **服务注册与发现**：Eureka 2.0

## 四、项目依赖

- `spring-cloud-starter-netflix-eureka-client`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-web`
- `spring-cloud-starter-config`
- `spring-kafka`

## 五、项目结构

```plaintext
approval-service/
├── pom.xml
├── approval-service.iml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/alxy/approvalservice/
│   │   │       ├── Controller/
│   │   │       │   └── ApprovalController.java
│   │   │       ├── Dto/
│   │   │       │   └── ApprovalRequest.java
│   │   │       ├── Entity/
│   │   │       │   └── Approval.java
│   │   │       ├── Repository/
│   │   │       │   └── ApprovalRepository.java
│   │   │       ├── Service/
│   │   │       │   └── ApprovalService.java
│   │   │       └── ApprovalServiceApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
│           └── com/alxy/approvalservice/
│               └── ApprovalServiceApplicationTests.java
└── target/
    └── classes/
        └── application.yml
```

## 六、数据库设计

### 审批记录表（`approval_records`）

| 字段名        | 类型          | 描述                                                         |
| ------------- | ------------- | ------------------------------------------------------------ |
| `approval_id` | `VARCHAR(12)` | 审批记录的唯一标识符，自动生成                               |
| `trade_id`    | `VARCHAR`     | 关联的交易 ID                                                |
| `level`       | `INT`         | 审批级别，2 表示客户经理审核，3 表示风控人员审核，4 表示合规人员审核，5 表示通过 |
| `approver_id` | `VARCHAR`     | 审批人员的 ID                                                |
| `decision`    | `VARCHAR`     | 审批决定，如通过、拒绝、未处理                               |
| `comment`     | `TEXT`        | 审批意见                                                     |
| `created_at`  | `DATETIME`    | 审批记录创建时间，自动记录                                   |

## 七、接口调用 API

### 1. 创建审批记录

- **请求 URL**：`POST /v1/approval/create`
- 请求参数
  - `treadId`：交易 ID
  - `advisorId`：顾问 ID
- **响应数据**：

```json
{
    "code": 0,
    "msg": "成功",
    "data": null
}
```

### 2. 客户经理查询自己的审批列表

- **请求 URL**：`GET /v1/approval/getApprovalList`
- 请求参数
  - `userId`：客户经理的用户 ID
- **响应数据**：

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "approvalId": "123456789012",
            "tradeId": "T001",
            "level": 2,
            "approverId": "A001",
            "decision": "未处理",
            "comment": null,
            "createdAt": "2024-01-01 10:00:00"
        }
    ]
}
```

### 3. 客户查询自己的审批列表

- **请求 URL**：`GET /v1/approval/getApprovalsByClientId`
- 请求参数
  - `clientId`：客户 ID
- **响应数据**：

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "approvalId": "123456789012",
            "tradeId": "T001",
            "level": 2,
            "approverId": "A001",
            "decision": "未处理",
            "comment": null,
            "createdAt": "2024-01-01 10:00:00"
        }
    ]
}
```

### 4. 审批操作

- **请求 URL**：`POST /v1/approval/approval`
- 请求参数
  - `approvalId`：审批记录 ID
  - `userId`：审批人员的用户 ID
- **响应数据**：

```json
{
    "code": 0,
    "msg": "成功",
    "data": null
}
```

### 5. 驳回操作

- **请求 URL**：`POST /v1/approval/resubmit`
- 请求参数
  - `tradeList`：交易 ID 列表
  - `userId`：操作人员的用户 ID
- **响应数据**：

```json
{
    "code": 0,
    "msg": "成功",
    "data": null
}
```

## 八、测试用例（使用 Postman）

### 1. 创建审批记录

- **请求 URL**：`http://localhost:8080/v1/approval/create`
- **请求方法**：`POST`
- 请求参数
  - `treadId`：`T001`
  - `advisorId`：`A001`
- **预期响应**：

```json
{
    "code": 0,
    "msg": "成功",
    "data": null
}
```

### 2. 客户经理查询自己的审批列表

- **请求 URL**：`http://localhost:8080/v1/approval/getApprovalList`
- **请求方法**：`GET`
- 请求参数
  - `userId`：`A001`
- **预期响应**：返回包含审批记录的列表

### 3. 客户查询自己的审批列表

- **请求 URL**：`http://localhost:8080/v1/approval/getApprovalsByClientId`
- **请求方法**：`GET`
- 请求参数
  - `clientId`：`C001`
- **预期响应**：返回包含审批记录的列表

### 4. 审批操作

- **请求 URL**：`http://localhost:8080/v1/approval/approval`
- **请求方法**：`POST`
- 请求参数
  - `approvalId`：`123456789012`
  - `userId`：`A001`
- **预期响应**：

```json
{
    "code": 0,
    "msg": "成功",
    "data": null
}
```

### 5. 驳回操作

- **请求 URL**：`http://localhost:8080/v1/approval/resubmit`
- **请求方法**：`POST`
- 请求参数
  - `tradeList`：`["T001"]`
  - `userId`：`A001`
- **预期响应**：

```json
{
    "code": 0,
    "msg": "成功",
    "data": null
}
```

## 九、核心业务代码展示

### 1. 创建审批记录

```java
@PostMapping("/create")
public Result<?> create(@RequestParam String treadId, @RequestParam String advisorId){
    Approval approval = new Approval();
    approval.setTradeId(treadId);
    approval.setApproverId(advisorId);
    approval.setLevel(2);
    if(approvalService.addApprovalRecord(approval)){
        return Result.success();
    }
    return Result.error("失败");
}
```

### 2. 审批操作

```java
@PostMapping("/approval")
public Result<?> approval(@RequestParam String approvalId, @RequestParam String userId) throws JsonProcessingException {
    User user = authControllerFeign.getUser(userId).getData();
    Integer identityLevel = user.getIdentityLevel();
    Approval approval = approvalRepository.findApprovalByApprovalId(approvalId);
    Trade trade = tradeControllerFeign.getByTradeId(approval.getTradeId()).getData();
    if(Objects.equals(identityLevel, approval.getLevel())){
        Map<String, Object> event = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        event.put("clientId",trade.getClientId() );
        event.put("tradeId",approval.getTradeId());
        if(approval.getLevel() == 2){
            event.put("decision","客户经理审批通过");
            event.put("comment", "无意见");
            approval.setApproverId(userId);
            approval.setLevel(approval.getLevel()+1);
            approval.setDecision("审核中");
            approvalRepository.save(approval);
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("approval-notifications", message);
        } 
        // 其他级别审批逻辑...
        return Result.success();
    }
    return Result.error("您无权通过！");
}
```

### 3. 驳回操作

```java
@PostMapping("/resubmit")
public Result<?> rejectAndSubmit(List<String> tradeList, String userId) throws JsonProcessingException {
    User user = authControllerFeign.getUser(userId).getData();
    for (String tradeId : tradeList) {
        Approval approval = approvalRepository.findApprovalByTradeId(tradeId);
        Integer identityLevel = user.getIdentityLevel();
        if(Objects.equals(identityLevel, approval.getLevel())){
            approval.setDecision("拒绝");
            approval.setLevel(0);
            approvalRepository.save(approval);
            tradeControllerFeign.changeTradeStatus("已拒绝",tradeId);
        }
    }
    return Result.success();
}
```
