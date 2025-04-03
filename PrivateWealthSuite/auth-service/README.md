# Auth Service 服务模块文档

## 一、项目需求

本项目旨在构建一个认证服务模块，为系统提供用户认证、注册、用户信息获取等功能。同时，需要对用户的 KYC（Know Your Customer）状态进行定期检查和更新，确保系统的安全性和合规性。

## 二、项目核心功能

1. **用户认证**：支持手机号和密码登录。
2. **用户注册**：用户可以使用手机号和密码进行注册。
3. **用户信息获取**：根据用户 ID 获取用户信息。
4. **KYC 状态检查**：定期检查用户的 KYC 状态，对过期的用户进行冻结处理。
5. **用户等级更新**：更新用户的身份等级和状态。

## 三、项目环境

- **操作系统**：Windows
- **开发语言**：Java 17
- **开发框架**：Spring Boot
- **数据库**：MySQL 8.x
- **注册中心**：Eureka
- **消息队列**：Kafka
- **API 调用**：OpenFeign
- **测试框架**：JUnit 5
- **构建工具**：Maven

## 四、项目依赖

- Spring Boot Starter Web
- Spring Cloud Starter Config
- Spring Cloud Starter Bus Kafka
- Spring Boot Starter Actuator
- Spring Boot Starter Data JPA
- MySQL Connector/J
- Spring Boot Starter Data Redis
- JJWT
- Lombok
- Spring Cloud Starter Netflix Eureka Client
- Spring Boot Starter Validation
- Spring Cloud OpenFeign Core
- Spring Cloud Starter OpenFeign
- Java-JWT
- Spring Kafka

## 五、项目结构

```plaintext
auth-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/alxy/authservice/
│   │   │       ├── controller/       # 控制器层
│   │   │       ├── dto/              # 数据传输对象
│   │   │       ├── entity/           # 实体类
│   │   │       ├── repository/       # 数据访问层
│   │   │       ├── service/          # 服务层
│   │   │       │   └── impl/         # 服务实现类
│   │   │       └── utils/            # 工具类
│   │   └── resources/
│   │       └── application.yml       # 配置文件
│   └── test/
│       └── java/
│           └── com/alxy/authservice/ # 测试类
└── pom.xml                           # Maven 配置文件
```

## 六、数据库设计

### 1. 用户表（users）

| 字段名         | 类型         | 描述             |
| -------------- | ------------ | ---------------- |
| user_id        | VARCHAR(12)  | 用户 ID，主键    |
| phone_number   | VARCHAR(20)  | 手机号，唯一索引 |
| password_hash  | VARCHAR(255) | 密码哈希值       |
| name           | VARCHAR(50)  | 用户姓名         |
| email          | VARCHAR(100) | 用户邮箱         |
| identity_level | INT          | 用户身份等级     |
| status         | VARCHAR(20)  | 用户状态         |

### 2. 客户表（clients）

| 字段名        | 类型           | 描述             |
| ------------- | -------------- | ---------------- |
| client_id     | VARCHAR(12)    | 客户 ID，主键    |
| name          | VARCHAR(50)    | 客户姓名         |
| gender        | VARCHAR(1)     | 客户性别         |
| birthday      | DATE           | 客户生日         |
| phone_number  | VARCHAR(20)    | 手机号，唯一索引 |
| email         | VARCHAR(100)   | 客户邮箱         |
| nationality   | VARCHAR(50)    | 国籍             |
| id_type       | VARCHAR(20)    | 证件类型         |
| id_number     | VARCHAR(50)    | 证件号码         |
| income_level  | INT            | 收入等级         |
| risk_level    | INT            | 风险等级         |
| total_assets  | DECIMAL(15, 2) | 总资产           |
| register_date | DATETIME       | 注册日期         |
| kyc_due_date  | DATE           | KYC 过期日期     |
| status        | VARCHAR(20)    | 客户状态         |
| remarks       | VARCHAR(255)   | 备注             |
| last_updated  | DATETIME       | 最后更新日期     |
| user_id       | VARCHAR(12)    | 用户 ID，外键    |
| advisor_id    | VARCHAR(12)    | 顾问 ID          |

## 七、接口调用 API

### 1. 手机号和密码登录

- **请求 URL**：`POST /v1/auth/pwdLogin`
- **请求体**：

```json
{
    "phoneNumber": "13800138000",
    "password": "123456"
}
```

- **响应数据**：

```json
{
    "code": 0,
    "message": "成功",
    "token": "xxxxxxxxxxxxxx",
    "data": {
        "phoneNumber": "13800138000",
        "userId": "123456789012",
        "clientId": "abcdef123456",
        "identityLevel": 1
    }
}
```

### 2. 获取用户信息

- **请求 URL**：`GET /v1/auth/getUser?userId=123456789012`
- **响应数据**：

```json
{
    "code": 0,
    "message": "成功",
    "data": {
        "userId": "123456789012",
        "phoneNumber": "13800138000",
        "name": "张三",
        "email": "zhangsan@example.com",
        "identityLevel": 1,
        "status": "正常"
    }
}
```

### 3. 更新用户等级

- **请求 URL**：`PUT /v1/auth/updateLevel?clientId=abcdef123456&identityLevel=2&Status=正常`
- **响应数据**：

```json
{
    "code": 0,
    "message": "成功",
    "data": null
}
```

## 八、测试用例（使用 Postman）

### 1. 手机号和密码登录测试

- **请求 URL**：`POST http://localhost:8080/v1/auth/pwdLogin`
- **请求体**：

```json
{
    "phoneNumber": "13800138000",
    "password": "123456"
}
```

- **预期响应**：返回包含 token 和用户信息的 JSON 数据。

### 2. 用户注册测试

- **请求 URL**：`POST http://localhost:8080/v1/auth/register`
- **请求体**：

```json
{
    "advisorId": "abcdef123456",
    "name": "张三",
    "phoneNumber": "13800138000",
    "password": "123456",
    "nationality": "中国",
    "idNumber": "123456789012345678",
    "gender": "男",
    "email": "zhangsan@example.com",
    "birthday": "2000-01-01",
    "totalAssets": 100000.00
}
```

- **预期响应**：返回注册成功的消息。

## 九、核心业务代码展示

### 1. 用户认证服务实现类

```java
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserRepository userRepository;

    @Override
    public Result<?> loginWithPassword(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        boolean validPassword = Md5Util.checkPassword(password, user.getPasswordHash());
        return validPassword ? getToken(user) : Result.error("密码错误");
    }

    private Result<?> getToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("identityLevel", user.getIdentityLevel());
        String token = JwtUtil.genToken(claims);
        return Result.success(token);
    }
}
```

### 2. KYC 状态检查服务类

```java
@Service
@RequiredArgsConstructor
public class KycCheckService {

    private final ClientServiceFeign clientServiceFeign;
    private final UserRepository userRepository;

    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void checkAndUpdateKycStatus() {
        List<Client> expiredClients = clientServiceFeign.findByKycDueDateBefore(LocalDate.now()).getData();
        for (Client client : expiredClients) {
            if (client.getUserId() != null) {
                userRepository.updateIdentityIdByUserId(client.getUserId(), 0, "冻结");
                clientServiceFeign.updateStatusById(client.getClientId(), "冻结");
            }
        }
    }
}
```
