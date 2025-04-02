# Portfolio Service 项目文档

## 一、项目需求

该项目旨在开发一个投资组合服务系统，为客户提供投资组合管理的相关功能。系统需要与数据库交互，存储和管理客户的投资组合信息，并通过 RESTful API 提供服务。同时，系统要支持与 Eureka 服务注册中心集成，实现服务的注册与发现，并且能够与 Kafka 消息总线集成，实现配置的动态刷新。

## 二、项目核心功能

1. **获取客户投资组合列表**：根据客户 ID 获取该客户的所有投资组合信息。
2. **获取投资组合持仓明细**：根据投资组合 ID 获取该投资组合的详细持仓信息。
3. **计算持仓市值**：根据投资组合 ID 计算该投资组合的当前市值。
4. **计算盈亏情况**：根据投资组合 ID 和初始投资额计算该投资组合的盈亏情况。
5. **计算组合比例**：根据投资组合 ID 或客户 ID 计算投资组合的比例。
6. **创建组合草案**：客户经理可以创建投资组合的草案。
7. **计算统计数据**：包括同一种类型产品近七天每天金额的平均值、总资产变化、前五收益高的投资组合及其收益率、前五风险率的投资组合、理财指数等。

## 三、项目环境

### 开发环境

- **操作系统**：Windows 10 / macOS / Linux
- **开发工具**：IntelliJ IDEA
- **编程语言**：Java 17
- **构建工具**：Maven 3.8.x
- **数据库**：MySQL 8.x
- **消息队列**：Kafka 3.x
- **服务注册中心**：Eureka Server
- **配置中心**：Spring Cloud Config Server

### 运行环境

- **JDK**：Java 17
- **服务器**：Spring Boot Embedded Tomcat
- **数据库**：MySQL 8.x
- **消息队列**：Kafka 3.x
- **服务注册中心**：Eureka Server
- **配置中心**：Spring Cloud Config Server

## 四、项目依赖

- spring-cloud-starter-netflix-eureka-client
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-cloud-starter-config
- spring-cloud-starter-bus-kafka
- spring-boot-starter-actuator
- spring-kafka
- mysql-connector-j
- lombok
- spring-boot-starter-test
- spring-cloud-openfeign-core

## 五、项目结构

plaintext











```plaintext
portfolio-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── alxy
│   │   │           └── portfolioservice
│   │   │               ├── controller
│   │   │               │   └── PortfolioController.java
│   │   │               ├── entiy
│   │   │               │   ├── PortfolioItems.java
│   │   │               │   ├── Portfolios.java
│   │   │               │   └── PortfoliosVo
│   │   │               │       ├── PortfolioRatioVo.java
│   │   │               │       └── PortfoliosVo.java
│   │   │               ├── repository
│   │   │               │   ├── PortfolioItemsRepository.java
│   │   │               │   └── PortfoliosRepository.java
│   │   │               ├── result
│   │   │               │   └── Result.java
│   │   │               ├── service
│   │   │               │   └── PortfolioService.java
│   │   │               └── utils
│   │   │                   └── TimeUtils.java
│   │   └── resources
│   │       └── application.yml
│   └── test
│       └── java
│           └── com
│               └── alxy
│                   └── portfolioservice
│                       └── PortfolioServiceApplicationTests.java
└── pom.xml
```

## 六、数据库设计

### 1. `Portfolios` 表

| 字段名     | 类型        | 描述        |
| ---------- | ----------- | ----------- |
| id         | VARCHAR(36) | 投资组合 ID |
| client_id  | VARCHAR(36) | 客户 ID     |
| created_at | DATETIME    | 创建时间    |

### 2. `PortfolioItems` 表

| 字段名       | 类型           | 描述        |
| ------------ | -------------- | ----------- |
| id           | VARCHAR(36)    | 持仓项 ID   |
| portfolio_id | VARCHAR(36)    | 投资组合 ID |
| product_name | VARCHAR(255)   | 产品名称    |
| unit_value   | DECIMAL(10, 2) | 单位价值    |
| amount       | DECIMAL(10, 2) | 数量        |
| created_at   | DATETIME       | 创建时间    |
| type         | VARCHAR(50)    | 产品类型    |

## 七、接口调用 API

### 1. 获取客户的投资组合列表

- **请求 URL**：`GET /v1/portfolios/client/{clientId}`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "productName": "产品名称",
            "unitValue": 100.00,
            "amount": 10.00,
            "createdAt": "2025-04-02 15:55:00",
            "type": "产品类型"
        }
    ]
}
```

### 2. 获取投资组合的持仓明细

- **请求 URL**：`GET /v1/portfolios/{portfolioId}/items`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "id": "持仓项 ID",
            "portfolioId": "投资组合 ID",
            "productName": "产品名称",
            "unitValue": 100.00,
            "amount": 10.00,
            "createdAt": "2025-04-02 15:55:00",
            "type": "产品类型"
        }
    ]
}
```

### 3. 计算持仓市值

- **请求 URL**：`GET /v1/portfolios/{portfolioId}/market-value`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": 1000.00
}
```

### 4. 计算盈亏情况

- **请求 URL**：`GET /v1/portfolios/{portfolioId}/profit-loss?initialInvestment=1000.00`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": 100.00
}
```

### 5. 计算组合比例

- **请求 URL**：`GET /v1/portfolios/{portfolioId}/ratio`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "type": "产品类型",
            "ratio": 0.5
        }
    ]
}
```

### 6. 客户经理创建组合草案

- **请求 URL**：`POST /v1/portfolios/draft`
- **请求体**：

```json
{
    "id": "投资组合 ID",
    "client_id": "客户 ID",
    "created_at": "2025-04-02 15:55:00"
}
```



- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": "投资组合 ID"
}
```

### 7. 计算同一种 type 的 ProductValueHistory 近七天每天金额的平均值

- **请求 URL**：`GET /v1/portfolios/client/{clientId}/average-value-last-seven-days`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "产品类型": [
            {
                "2025-04-01": 100.00
            }
        ]
    }
}
```

### 8. 总资产变化

- **请求 URL**：`GET /v1/portfolios/client/{clientId}/average-value-last-seven-days-new`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": {
        "2025-04-01": 1000.00
    }
}
```

### 9. 计算前五收益高的投资组合及其收益率

- **请求 URL**：`GET /v1/portfolios/client/{clientId}/top-five-portfolio-returns`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "portfolioId": "投资组合 ID",
            "returnRate": 0.1
        }
    ]
}
```

### 10. 计算投资组合的风险率并返回前五的组合

- **请求 URL**：`GET /v1/portfolios/client/{clientId}/top-five-risk-ratios`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "portfolioId": "投资组合 ID",
            "riskRatio": 0.2
        }
    ]
}
```

### 11. 计算理财指数

- **请求 URL**：`GET /v1/portfolios/client/{clientId}/financial-index`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": 80
}
```

### 12. 计算组合比例，接受 clientId 作为参数

- **请求 URL**：`GET /v1/portfolios/{clientId}/ratios`
- **响应数据**：

```json
{
    "code": 1,
    "msg": "success",
    "data": [
        {
            "type": "产品类型",
            "ratio": 0.5
        }
    ]
}
```

## 八、测试用例（使用 Postman）

### 1. 获取客户的投资组合列表

- **请求 URL**：`GET http://localhost:8080/v1/portfolios/client/123`
- **预期响应**：返回客户 ID 为 123 的投资组合列表。

### 2. 获取投资组合的持仓明细

- **请求 URL**：`GET http://localhost:8080/v1/portfolios/456/items`
- **预期响应**：返回投资组合 ID 为 456 的持仓明细。

### 3. 计算持仓市值

- **请求 URL**：`GET http://localhost:8080/v1/portfolios/456/market-value`
- **预期响应**：返回投资组合 ID 为 456 的持仓市值。

### 4. 计算盈亏情况

- **请求 URL**：`GET http://localhost:8080/v1/portfolios/456/profit-loss?initialInvestment=1000.00`
- **预期响应**：返回投资组合 ID 为 456 的盈亏情况。

### 5. 计算组合比例

- **请求 URL**：`GET http://localhost:8080/v1/portfolios/456/ratio`
- **预期响应**：返回投资组合 ID 为 456 的组合比例。

### 6. 客户经理创建组合草案

- **请求 URL**：`POST http://localhost:8080/v1/portfolios/draft`
- **请求体**：

```json
{
    "client_id": "123",
    "created_at": "2025-04-02 15:55:00"
}
```



- **预期响应**：返回创建的投资组合 ID。

## 九、核心业务代码展示

### 1. `PortfolioController.java`

```java
@RestController
@RequestMapping("/v1/portfolios")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    // 获取客户的投资组合列表
    @GetMapping("/client/{clientId}")
    public Result getPortfoliosByClientId(@PathVariable String clientId) {
        List<PortfoliosVo> portfoliosVosList = new ArrayList<>();
        List<Portfolios> s = portfolioService.getPortfoliosByClientId(clientId);
        s.forEach(portfolio -> {
            portfolio.getPortfolioItems().forEach(portfolioItem -> {
                PortfoliosVo build = PortfoliosVo.builder()
                       .productName(portfolioItem.getProductName())
                       .unitValue(portfolioItem.getUnitValue())
                       .amount(portfolioItem.getAmount())
                       .createdAt(portfolioItem.getCreatedAt())
                       .type(portfolioItem.getType())
                       .build();
                portfoliosVosList.add(build);
            });
        });
        return Result.success(portfoliosVosList);
    }

    // 其他接口方法...
}
```

### 2. `Result.java`

```java
@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(1, "success", null);
    }

    public static Result success(Object data) {
        return new Result(1, "success", data);
    }

    public static Result error(String msg) {
        return new Result(0, msg, null);
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
```