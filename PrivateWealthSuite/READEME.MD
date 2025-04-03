# PrivateWealthSuite 项目文档

## 一、项目概述

PrivateWealthSuite 是一个综合性的项目，旨在提供一系列与私人财富管理相关的服务。该项目采用了微服务架构，包含多个子服务，每个服务负责特定的功能，如网关服务、认证服务、咨询服务等。项目使用 Spring Boot 和 Spring Cloud 构建，具备良好的可扩展性和高可用性。

## 二、项目结构

项目采用 Maven 多模块结构，主要模块如下：

- `gateway-service`：网关服务，负责请求的路由和过滤。
- `discovery-service`：服务发现服务，使用 Eureka 实现服务的注册与发现。
- `auth-service`：认证服务，处理用户的认证和授权。
- `advisory-service`：咨询服务，提供投资建议和推荐。
- `approval-service`：审批服务，处理业务流程中的审批操作。
- `client-service`：客户服务，管理客户信息。
- `config-service`：配置服务，集中管理项目的配置信息。
- `notification-service`：通知服务，负责发送各种通知消息。
- `portfolio-service`：投资组合服务，管理投资组合相关信息。
- `risk-service`：风险服务，进行风险评估和管理。
- `trade-service`：交易服务，处理交易相关业务。

## 三、技术栈

- **Java 版本**：17
- **Spring Boot 版本**：3.4.4
- **Spring Cloud 版本**：2024.0.1
- **MySQL 驱动版本**：8.0.33
- **Spring Data JPA 版本**：3.2.4

## 四、环境配置

### 1. Java 环境

- **版本要求**：Java 17 或更高版本。
- **配置步骤**：安装完成后，需要配置相应的环境变量。通常需要设置 `JAVA_HOME` 指向 Java 安装目录，同时将 `%JAVA_HOME%\bin` 添加到系统的 `PATH` 环境变量中，以确保在命令行中可以直接使用 `java` 和 `javac` 命令。

### 2. MySQL 数据库

- **版本要求**：MySQL 8.0.33 或兼容版本。
- **安装步骤**：从 MySQL 官方网站下载适合你操作系统的安装包，按照安装向导进行安装。安装过程中，需要设置 root 用户的密码等信息。
- **数据库和表结构创建**：安装完成后，需要创建项目所需的数据库。可以使用 MySQL 自带的命令行工具（如 `mysql -u root -p`）或者可视化工具（如 MySQL Workbench）来创建数据库。表结构信息可以参考 `.idea/dataSources` 目录下的 XML 文件，根据这些文件中的信息在数据库中创建相应的表。

### 3. Maven 配置

- **版本要求**：建议使用较新的稳定版本。
- **安装步骤**：从 Maven 官方网站下载 Maven 压缩包，解压到指定目录。
- **配置步骤**：配置 `MAVEN_HOME` 环境变量指向 Maven 的安装目录，并将 `%MAVEN_HOME%\bin` 添加到系统的 `PATH` 环境变量中。这样在命令行中就可以使用 `mvn` 命令。项目使用 Maven 进行依赖管理和构建，你可以在项目根目录下的 `pom.xml` 文件以及各子模块的 `pom.xml` 文件中查看和修改依赖信息。

### 4. Kafka 消息队列

- **版本要求**：项目中 Kafka 相关依赖使用默认适配版本，建议使用较新的稳定版本。
- **安装步骤**：从 Kafka 官方网站下载二进制包，解压到指定目录。
- **配置步骤**：配置 Kafka 的 `server.properties` 文件，设置监听的端口、日志存储路径等信息。同时，需要启动 ZooKeeper 服务（Kafka 依赖 ZooKeeper 进行集群管理），然后启动 Kafka 服务。项目中的 `application.yml` 文件中配置了 Kafka 的 `bootstrap-servers` 地址为 `localhost:9092`，如果 Kafka 服务的地址和端口有变化，需要相应修改配置文件。

### 5. 开发工具

- **IDE 要求**：建议使用 IntelliJ IDEA 等 Java 开发工具，并且需要安装 Lombok 插件，以支持项目中使用的 Lombok 注解。
- **配置步骤**：在 IDE 中打开项目后，需要配置项目的 SDK 为 Java 17 或更高版本。同时，在 IDE 的设置中配置 Maven 的路径，确保 IDE 可以使用本地安装的 Maven 进行项目构建。另外，需要在 IDE 中安装 Lombok 插件，以避免 Lombok 注解带来的编译错误。

### 6. Spring Boot 

- **Spring Boot 版本**：`3.4.4`，从 `PrivateWealthSuite/pom.xml` 中的父项目 `<version>3.4.4</version>` 可知。
- **Spring Cloud 版本**：`2024.0.1`，适配 Spring Boot 3.4.4。
- **Actuator 配置**：多个服务的 `application.yml` 文件中配置了 Actuator 端点暴露，可通过 `/actuator` 访问应用程序的监控和管理端点。
- **Kafka 配置**：多个服务的 `application.yml` 文件中配置了 Kafka 服务器地址为 `localhost:9092`，用于消息队列的使用。
- **Eureka 配置**：部分服务的 `application.yml` 文件中配置了 Eureka 服务注册中心地址为 `http://localhost:8761/eureka`，用于服务的注册和发现。
- **Config Server 配置**：多个服务的 `application.yml` 文件中配置了 Config Server 的地址为 `http://localhost:8888`，用于集中管理配置信息。示例配置如下：
- **OpenFeign**：在项目中，会创建 OpenFeign 客户端接口来定义远程服务的调用。虽然无需关注具体接口代码，但要明白这些接口通过特定的注解来指定远程服务的名称、地址以及要调用的 API 方法等信息。通过这些接口，项目中的其他组件就可以方便地发起对远程服务的调用，实现不同服务之间的通信和协作。

### 7. WebSocket

- **WebSocket 配置**：`notification-service` 模块中的 `WebSocketConfig.java` 文件配置了 WebSocket 和 STOMP 消息代理，同时处理跨域资源共享（CORS）问题。

## 五、项目启动

### 1. 服务发现服务

启动 `discovery-service`，确保其他服务能够注册到 Eureka 服务器。

### 2. 配置服务

启动 `config-service`，为其他服务提供配置信息。

### 3. 其他服务

依次启动其他服务，如 `gateway-service`、`auth-service` 等。你可以在 `.idea/workspace.xml` 文件中查看服务的启动配置。

## 六、服务功能介绍

### 1. 投资组合服务（`portfolio-service`）

- **计算持仓市值**：通过 `/portfolio/{portfolioId}/market-value` 接口计算指定投资组合的持仓市值。
- **计算投资组合的风险率**：通过 `/client/{clientId}/top-five-risk-ratios` 接口计算指定客户的投资组合风险率，并返回前五的组合。

### 2. 组合推荐服务（`advisory-service`）

- **获取推荐列表**：通过 `/v1/advisory/getRecommendList` 接口根据客户 ID 获取推荐列表。
- **根据推荐组合生成订单**：通过 `/v1/advisory/createTrades` 接口根据推荐组合 ID 生成订单。

### 3. 风险服务（`risk-service`）

- **风险评估列表查询**：通过 `/v1/risk/list` 接口查询风险评估列表。
- **查看风险评估信息**：通过 `/v1/risk/viewById/{riskId}` 和 `/v1/risk/viewByClientId/{clientId}` 接口查看指定风险评估信息。

### 4. 通知服务（`notification-service`）

- **标记通知为已读**：通过 `/notification/{notificationId}/read` 接口将指定通知标记为已读。

## 七、测试

项目中包含了多个服务的测试用例，你可以在各服务的 `target/surefire-reports` 目录下查看测试报告。例如，`auth-service` 的测试报告在 `auth-service/target/surefire-reports/com.alxy.authservice.AuthServiceApplicationTests.txt` 中。

## 八、异常处理

在 `auth-service` 中，使用 `GlobalHandler` 类进行全局异常处理，包括校验异常和其他异常。校验异常返回 400 状态码，其他异常返回 500 状态码。

## 九、注意事项

- 项目中使用了 Lombok 注解，确保你的开发环境支持 Lombok 插件。
- 各服务的配置信息可以在 `config-service` 中进行集中管理和修改。
- 数据库的表结构和外键关系可以参考 `.idea/dataSources` 目录下的 XML 文件。
