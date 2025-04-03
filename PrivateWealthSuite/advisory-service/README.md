# 投资推荐服务

## 一、项目需求

本项目是一个投资咨询服务系统，旨在为客户提供个性化的投资组合推荐服务。系统需要与客户端服务进行交互，获取客户信息，并根据客户的风险承受能力和投资偏好，为其生成合适的投资组合建议。同时，系统需要支持客户支付功能，并将交易信息记录到数据库中。

## 二、项目核心功能

1. **客户信息获取**：通过 Feign 客户端调用客户端服务，根据用户 ID 获取客户详细信息。
2. **投资组合推荐**：根据客户的风险等级，为客户生成个性化的投资组合推荐。
3. **支付功能**：支持客户进行支付操作，并更新客户的资产信息。
4. **交易记录管理**：记录客户的交易信息，包括交易 ID、客户 ID、产品代码、交易金额等。

## 三、项目环境

### 开发环境

- **操作系统**：Windows 10
- **开发工具**：IntelliJ IDEA 2021.3.2
- **JDK 版本**：17.0.8
- **Maven 版本**：3.x

### 运行环境

- **数据库**：MySQL 8.x
- **注册中心**：Eureka Server
- **配置中心**：Spring Cloud Config Server
- **消息总线**：Kafka

## 四、项目依赖

- Spring Cloud Netflix Eureka Client
- Spring Cloud Config
- Spring Cloud Bus Kafka
- Spring Kafka
- Spring Boot Starter Actuator
- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Commons Math3
- MySQL Connector
- Lombok
- Spring Cloud OpenFeign Core
- Spring Cloud Starter OpenFeign

## 五、项目结构

```plaintext
advisory-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── alxy
│   │   │           └── advisoryservice
│   │   │               ├── config
│   │   │               │   └── DataSourceConfig.java
│   │   │               ├── controller
│   │   │               │   └── ClientFeign.java
│   │   │               ├── dto
│   │   │               │   ├── Client.java
│   │   │               │   ├── Result.java
│   │   │               │   ├── Trade.java
│   │   │               │   └── ...
│   │   │               ├── entity
│   │   │               │   └── RecommendedItem.java
│   │   │               ├── recommend
│   │   │               │   ├── PortfolioRecommendationService.java
│   │   │               │   └── PortfolioOptimizer.java
│   │   │               ├── repository
│   │   │               │   ├── RecommendationRepository.java
│   │   │               │   ├── PortfolioItemRepository.java
│   │   │               │   └── ...
│   │   │               └── AdvisoryServiceApplication.java
│   │   └── resources
│   │       └── application.yml
│   └── test
│       └── java
│           └── com
│               └── alxy
│                   └── advisoryservice
│                       └── AdvisoryServiceApplicationTests.java
└── pom.xml
```

## 六、数据库设计

### 1. 客户表（clients）

| 字段名        | 类型          | 描述                 |
| ------------- | ------------- | -------------------- |
| client_id     | String        | 客户 ID              |
| name          | String        | 客户姓名             |
| gender        | String        | 客户性别             |
| birthday      | LocalDate     | 客户生日             |
| phoneNumber   | String        | 客户电话号码         |
| email         | String        | 客户邮箱             |
| nationality   | String        | 客户国籍             |
| id_type       | String        | 客户证件类型         |
| id_number     | String        | 客户证件号码         |
| income_level  | Integer       | 客户收入水平         |
| risk_level    | Integer       | 客户风险等级         |
| total_assets  | BigDecimal    | 客户总资产           |
| register_date | LocalDateTime | 客户注册日期         |
| kyc_due_date  | LocalDate     | 客户 KYC 到期日期    |
| status        | String        | 客户状态             |
| remarks       | String        | 客户备注             |
| last_updated  | LocalDateTime | 客户信息最后更新时间 |
| userId        | String        | 用户 ID              |
| advisorId     | String        | 顾问 ID              |

### 2. 投资组合推荐表（recommendations）

| 字段名              | 类型    | 描述     |
| ------------------- | ------- | -------- |
| recommendation_id   | String  | 推荐 ID  |
| recommendation_name | String  | 推荐名称 |
| risk_level          | Integer | 风险等级 |
| yield_rate          | Integer | 收益率   |
| client_id           | String  | 客户 ID  |
| advisor_id          | String  | 顾问 ID  |
| created_at          | Date    | 创建时间 |

### 3. 推荐项目表（recommended_items）

| 字段名            | 类型       | 描述     |
| ----------------- | ---------- | -------- |
| item_id           | String     | 项目 ID  |
| recommendation_id | String     | 推荐 ID  |
| product_code      | String     | 产品代码 |
| product_name      | String     | 产品名称 |
| amount            | BigDecimal | 推荐金额 |
| product_percent   | BigDecimal | 产品占比 |

### 4. 交易记录表（trades）

| 字段名       | 类型          | 描述     |
| ------------ | ------------- | -------- |
| trade_id     | String        | 交易 ID  |
| client_id    | String        | 客户 ID  |
| product_code | String        | 产品代码 |
| type         | String        | 交易类型 |
| amount       | BigDecimal    | 交易金额 |
| status       | String        | 交易状态 |
| create_time  | LocalDateTime | 创建时间 |
| update_time  | LocalDateTime | 更新时间 |

## 七、接口调用 API

### 1. 获取客户信息

- **请求 URL**：`/v1/client/getByUserId?userId={userId}`
- **请求方法**：GET
- **响应数据**：

```json
{
    "code": 0,
    "msg": "操作成功",
    "token": null,
    "data": {
        "clientId": "123456789012",
        "name": "张三",
        "gender": "男",
        "birthday": "2000-01-01",
        "phoneNumber": "13800138000",
        "email": "zhangsan@example.com",
        "nationality": "中国",
        "idType": "身份证",
        "idNumber": "123456789012345678",
        "incomeLevel": 3,
        "riskLevel": 2,
        "totalAssets": 100000.00,
        "registerDate": "2025-01-01T12:00:00",
        "kycDueDate": "2026-01-01",
        "status": "正常",
        "remarks": "",
        "lastUpdated": "2025-01-01T12:00:00",
        "userId": "user001",
        "advisorId": "advisor001"
    }
}
```

### 2. 客户支付

- **请求 URL**：`/v1/client/{clientId}/pay?total={total}`
- **请求方法**：PUT
- **响应数据**：

```json
{
    "code": 0,
    "msg": "操作成功",
    "token": null,
    "data": null
}
```

## 八、测试用例（使用 Postman）

### 1. 获取客户信息

- **请求 URL**：`http://localhost:8080/v1/client/getByUserId?userId=user001`
- **请求方法**：GET
- **预期响应**：返回客户信息，状态码为 200。

### 2. 客户支付

- **请求 URL**：`http://localhost:8080/v1/client/123456789012/pay?total=1000.00`
- **请求方法**：PUT
- **预期响应**：返回操作成功信息，状态码为 200。

## 九、核心业务代码展示

### 1. 投资组合推荐服务

```java
@Service
@Transactional
public class PortfolioRecommendationService {
    // ... 其他依赖注入
    public Recommendation generateRecommendation(String clientId, String advisorId, int riskLevel) {
        List<ProductValueHistory> historyData = loadHistoricalData();
        Map<String, ProductPrediction> predictions = predictProductReturns(historyData);
        OptimalPortfolio optimalPortfolio = optimizePortfolio(predictions, riskLevel);
        return createAndSaveRecommendation(optimalPortfolio, clientId, advisorId);
    }
    // ... 其他方法
}
```

### 2. Feign 客户端调用

```java
@FeignClient(name = "client-service", path = "/v1/client")
public interface ClientFeign {
    @GetMapping("/getByUserId")
    Result<Client> getClientById(@RequestParam String userId);

    @PutMapping("/{clientId}/pay")
    Result<?> pay(@PathVariable String clientId, @RequestParam BigDecimal total);
}
```

### 3. 通用结果返回类

```java
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private String token;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", null, data);
    }

    public static Result<Void> error(String message) {
        return new Result<>(1, message, null, null);
    }
}
```
