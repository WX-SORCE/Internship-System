# Client Service 项目文档

## 一、项目需求

本项目 `Client Service` 是 `PrivateWealthSuite` 项目的一部分，主要用于管理客户信息，包括客户的基本信息、资产信息、风险等级等。同时，该服务需要与其他微服务进行集成，通过配置中心获取配置信息，使用 Kafka 进行消息总线通信，并注册到 Eureka 服务注册中心。

## 二、项目核心功能

1. **客户信息管理**：支持客户信息的增删改查操作，包括客户的基本信息、资产信息、风险等级等。
2. **客户信息搜索**：支持根据客户姓名和电话号码进行模糊搜索。
3. **客户信息更新**：支持更新客户的资产信息、风险等级、收入水平等。
4. **服务注册与发现**：将服务注册到 Eureka 服务注册中心，实现服务的自动发现和负载均衡。
5. **配置管理**：通过 Spring Cloud Config 从配置中心获取配置信息，实现配置的集中管理和动态更新。
6. **消息总线**：使用 Spring Cloud Bus 和 Kafka 实现消息的发布和订阅，支持配置的动态刷新。

## 三、项目环境

### 开发环境

- **操作系统**：Windows 10 / macOS / Linux
- **开发工具**：IntelliJ IDEA
- **JDK 版本**：Java 17
- **Maven 版本**：Maven 3.8.x

### 运行环境

- **JDK 版本**：Java 17
- **数据库**：MySQL 8.x
- **Kafka 版本**：Kafka 3.x
- **Eureka 服务注册中心**：Spring Cloud Netflix Eureka Server
- **Spring Cloud Config 配置中心**：Spring Cloud Config Server

## 四、项目依赖

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Cloud Starter Config
- Spring Cloud Starter Bus Kafka
- Spring Boot Starter Data Redis
- JJWT
- Lombok
- Spring Cloud Starter Netflix Eureka Client
- Spring Boot Starter Validation
- JAXB API
- JAXB Core
- JAXB Impl
- Java Activation API
- Spring Cloud OpenFeign Core
- Java JWT

## 五、项目结构

```plaintext
client-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/alxy/clientservice/
│   │   │       ├── controller/      # 控制器层
│   │   │       ├── dto/             # 数据传输对象层
│   │   │       ├── entity/          # 实体类层
│   │   │       ├── repository/      # 数据访问层
│   │   │       └── service/         # 服务层
│   │   └── resources/
│   │       └── application.yml      # 配置文件
│   └── test/
│       └── java/
│           └── com/alxy/clientservice/
│               └── ClientServiceApplicationTests.java  # 测试类
└── pom.xml                        # Maven 配置文件
```

## 六、数据库设计

### 客户表（Client）

| 字段名      | 类型       | 描述          |
| ----------- | ---------- | ------------- |
| id          | Integer    | 客户 ID，主键 |
| clientId    | String     | 客户编号      |
| name        | String     | 客户姓名      |
| phoneNumber | String     | 客户电话号码  |
| totalAssets | BigDecimal | 客户总资产    |
| riskLevel   | Integer    | 客户风险等级  |
| incomeLevel | Integer    | 客户收入水平  |
| kycDueDate  | LocalDate  | KYC 到期日期  |
| advisorId   | String     | 顾问 ID       |
| userId      | String     | 用户 ID       |
| status      | String     | 客户状态      |

## 七、接口调用 API

### 1. 根据客户编号查询客户信息

- **请求 URL**：`GET /clients/{clientId}`
- **响应数据**：

```json
{
    "code": 0,
    "msg": "操作成功",
    "token": null,
    "data": {
        "id": 1,
        "clientId": "C001",
        "name": "张三",
        "phoneNumber": "13800138000",
        "totalAssets": 1000000.00,
        "riskLevel": 2,
        "incomeLevel": 3,
        "kycDueDate": "2025-12-31",
        "advisorId": "A001",
        "userId": "U001",
        "status": "ACTIVE"
    }
}
```

### 2. 根据姓名和电话号码搜索客户信息

- **请求 URL**：`GET /clients/search?name={name}&phoneNumber={phoneNumber}&page={page}&size={size}`
- **响应数据**：

```json
{
    "code": 0,
    "msg": "操作成功",
    "token": null,
    "data": {
        "content": [
            {
                "id": 1,
                "clientId": "C001",
                "name": "张三",
                "phoneNumber": "13800138000",
                "totalAssets": 1000000.00,
                "riskLevel": 2,
                "incomeLevel": 3,
                "kycDueDate": "2025-12-31",
                "advisorId": "A001",
                "userId": "U001",
                "status": "ACTIVE"
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageNumber": 0,
            "pageSize": 10,
            "unpaged": false,
            "paged": true
        },
        "last": true,
        "totalElements": 1,
        "totalPages": 1,
        "size": 10,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 1,
        "empty": false
    }
}
```

### 3. 更新客户信息

- **请求 URL**：`PUT /clients/{clientId}`
- **请求体**：

```json
{
    "totalAssets": 2000000.00,
    "riskLevel": 3,
    "incomeLevel": 4,
    "kycDueDate": "2026-12-31"
}
```



- **响应数据**：

```json
{
    "code": 0,
    "msg": "操作成功",
    "token": null,
    "data": true
}
```

## 八、测试用例（使用 Postman）

### 1. 根据客户编号查询客户信息

- **请求 URL**：`http://localhost:8080/clients/C001`
- **请求方法**：`GET`
- **预期响应**：返回客户信息的 JSON 数据

### 2. 根据姓名和电话号码搜索客户信息

- **请求 URL**：`http://localhost:8080/clients/search?name=张三&phoneNumber=13800138000&page=0&size=10`
- **请求方法**：`GET`
- **预期响应**：返回符合条件的客户信息列表的 JSON 数据

### 3. 更新客户信息

- **请求 URL**：`http://localhost:8080/clients/C001`
- **请求方法**：`PUT`
- **请求体**：

```json
{
    "totalAssets": 2000000.00,
    "riskLevel": 3,
    "incomeLevel": 4,
    "kycDueDate": "2026-12-31"
}
```



- **预期响应**：返回更新结果的 JSON 数据

## 九、核心业务代码展示

### 客户信息仓库接口（ClientRepository.java）

```java
package com.alxy.clientservice.repository;

import com.alxy.clientservice.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findByClientId(String clientId);

    @Query("SELECT c FROM Client c WHERE " +
            "(:name IS NULL OR c.name LIKE %:name%) AND " +
            "(:phoneNumber IS NULL OR c.phoneNumber LIKE %:phoneNumber%)")
    Page<Client> searchClientsByNameAndPhone(String name, String phoneNumber, Pageable pageable);

    @Modifying
    @Query("UPDATE Client c SET c.totalAssets = :totalAssets, c.riskLevel = :riskLevel, c.incomeLevel = :incomeLevel, c.kycDueDate = :kycDueDate WHERE c.clientId = :clientId")
    int updateClient(BigDecimal totalAssets, Integer riskLevel, Integer incomeLevel, LocalDate kycDueDate, String clientId);
}
```

### 数据传输对象（Result.java）

```java
package com.alxy.clientservice.dto;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private String token;
    private T data;

    private Result(Integer code, String msg, String token, T data) {
        this.code = code;
        this.msg = msg;
        this.token = token;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", null, data);
    }

    public static Result<String> success(String token) {
        return new Result<>(0, "操作成功", token, null);
    }

    public static <T> Result<T> success(String token, T data) {
        return new Result<>(0, "登录成功", token, data);
    }

    public static Result<Void> success() {
        return new Result<>(0, "操作成功", null, null);
    }

    public static Result<Void> error(String message) {
        return new Result<>(1, message, null, null);
    }

    public static Result<Void> error(Integer code, String message) {
        return new Result<>(code, message, null, null);
    }
}
```