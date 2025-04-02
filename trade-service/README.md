# Trade Service 项目文档

## 项目需求

本项目是一个交易服务系统，旨在处理交易相关的业务逻辑，包括执行交易、记录交易日志、与其他服务进行交互等。系统需要与数据库进行交互以存储客户信息和交易日志，同时需要与 Eureka 服务注册中心进行注册，方便其他服务发现和调用。

## 项目核心功能

1. **交易执行**：通过指定交易 ID 执行相应的交易。
2. **客户信息管理**：存储和管理客户的基本信息，包括客户 ID、姓名、性别、生日等。
3. **日志记录**：记录交易相关的日志信息，包括交易 ID、日志消息和记录时间。
4. **与其他服务交互**：通过 Feign 客户端与审批服务进行交互，创建审批请求。

## 项目环境

### 开发环境

- **操作系统**：Windows 10 / macOS / Linux
- **开发工具**：IntelliJ IDEA
- **编程语言**：Java 17
- **构建工具**：Maven 3.x
- **数据库**：MySQL 8.x
- **消息队列**：Kafka
- **服务注册中心**：Eureka
- **配置中心**：Spring Cloud Config

### 运行环境

- **服务器**：Tomcat 9.x
- **JDK**：Java 17

## 项目依赖

- spring-cloud-starter-netflix-eureka-client
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-cloud-starter-config
- lombok

## 项目结构

```plaintext
trade-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/alxy/tradeservice/
│   │   │       ├── controller/
│   │   │       │   ├── TradeController.java
│   │   │       │   └── ApprovalFeign.java
│   │   │       ├── dto/
│   │   │       │   ├── Client.java
│   │   │       │   └── Result.java
│   │   │       ├── repository/
│   │   │       │   └── LogRepository.java
│   │   │       ├── service/
│   │   │       │   └── Impl/
│   │   │       │       └── TradeServiceImpl.java
│   │   │       └── TradeServiceApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
│           └── com/alxy/tradeservice/
│               └── TradeServiceApplicationTests.java
├── target/
│   ├── classes/
│   │   └── application.yml
│   └── surefire-reports/
│       └── TEST-com.alxy.tradeservice.TradeServiceApplicationTests.xml
└── pom.xml
```

## 数据库设计

### 客户表（clients）

| 字段名        | 类型         | 描述               |
| ------------- | ------------ | ------------------ |
| client_id     | VARCHAR(12)  | 客户 ID，主键      |
| name          | VARCHAR(255) | 客户姓名           |
| gender        | VARCHAR(1)   | 客户性别           |
| birthday      | DATE         | 客户生日           |
| phone_number  | VARCHAR(255) | 客户电话号码，唯一 |
| email         | VARCHAR(255) | 客户邮箱           |
| nationality   | VARCHAR(255) | 客户国籍           |
| id_type       | VARCHAR(255) | 客户证件类型       |
| id_number     | VARCHAR(255) | 客户证件号码       |
| income_level  | INT          | 客户收入水平       |
| risk_level    | INT          | 客户风险等级       |
| total_assets  | DECIMAL      | 客户总资产         |
| register_date | DATETIME     | 客户注册日期       |
| kyc_due_date  | DATE         | 客户 KYC 到期日期  |
| status        | VARCHAR(255) | 客户状态           |
| remarks       | VARCHAR(255) | 备注信息           |
| last_updated  | DATETIME     | 最后更新时间       |
| user_id       | VARCHAR(255) | 用户 ID            |

### 日志表（logs）

| 字段名      | 类型         | 描述          |
| ----------- | ------------ | ------------- |
| log_id      | VARCHAR(255) | 日志 ID，主键 |
| trade_id    | VARCHAR(255) | 关联的交易 ID |
| log_message | VARCHAR(255) | 日志消息内容  |
| log_time    | DATETIME     | 日志记录时间  |

## 接口调用 API

### 执行交易接口

- **请求 URL**：`POST /trades/{tradeId}/execute`
- 请求参数：
  - `tradeId`：路径参数，指定要执行的交易 ID
- **响应数据**：无

### 创建审批请求接口

- **请求 URL**：`POST /v1/approval/create`
- 请求参数：
  - `treadId`：查询参数，交易 ID
  - `advisorId`：查询参数，顾问 ID
- **响应数据**：

```json
{
    "code": 0,
    "msg": "操作成功",
    "token": null,
    "data": null
}
```

## 测试用例（使用 Postman）

### 执行交易接口测试

1. 打开 Postman，选择 `POST` 请求方式。
2. 在请求 URL 中输入 `http://localhost:8080/trades/123/execute`，其中 `123` 为交易 ID。
3. 点击 `Send` 按钮发送请求。
4. 检查响应状态码是否为 200，若为 200 则表示请求成功。

### 创建审批请求接口测试

1. 打开 Postman，选择 `POST` 请求方式。
2. 在请求 URL 中输入 `http://localhost:8080/v1/approval/create`。
3. 在请求参数中添加 `treadId` 和 `advisorId`，例如 `treadId=123`，`advisorId=456`。
4. 点击 `Send` 按钮发送请求。
5. 检查响应数据是否符合预期。

## 核心业务代码展示

### 执行交易方法

```java
@PostMapping("/{tradeId}/execute")
public void executeTrade(@PathVariable String tradeId) {
    // 调用 TradeService 的 executeTrade 方法来执行交易
    tradeService.executeTrade(tradeId);
}
```

### 记录日志方法

```java
private void writeLog(String tradeId, String logMessage) {
    // 创建一个新的 Log 对象
    Log log = new Log();
    // 设置日志关联的交易 ID
    log.setTradeId(tradeId);
    // 设置日志的消息内容
    log.setLogMessage(logMessage);
    // 设置日志的记录时间为当前时间
    log.setLogTime(new Date());
    // 调用 logRepository 的 save 方法将日志对象保存到数据库
    logRepository.save(log);
}
```

### 创建审批请求接口

```java
@RestController
@FeignClient(name = "approval-service", path = "/v1/approval")
public interface ApprovalFeign {
    @PostMapping("/create")
    Result<?> create(@RequestParam String treadId, @RequestParam String advisorId);
}
```