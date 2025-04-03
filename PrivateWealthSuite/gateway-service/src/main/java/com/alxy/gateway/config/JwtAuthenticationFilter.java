package com.alxy.gateway.config;

import com.alxy.gateway.utils.JwtUtil;
import com.alxy.gateway.utils.ThreadLocalUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    // 精确放行路径列表
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/v1/auth/pwdLogin",
            "/v1/auth/register",
            "/v1/client/export"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        HttpMethod method = exchange.getRequest().getMethod();

        // 放行OPTIONS请求和公开端点
        if (method == HttpMethod.OPTIONS || isExcludedPath(path)) {
            return chain.filter(exchange);
        }

        // 获取并验证Token
        String token = extractToken(exchange.getRequest());
        System.out.println("##########################"+token);
        if (token == null) {
            return unauthorizedResponse(exchange, "Missing authorization token");
        }
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 使用响应式方式处理用户信息
            return chain.filter(exchange)
                    .contextWrite(ctx -> ctx.put("userClaims", claims))
                    .doFinally(signal -> ThreadLocalUtil.remove());
        } catch (Exception e) {
            return unauthorizedResponse(exchange, "Invalid token: " + e.getMessage());
        }
    }

    private String extractToken(ServerHttpRequest request) {
        String header = request.getHeaders().getFirst("Authorization");
        if (header.startsWith("Bearer ")) {
            return header.substring(7);
        } else {
            return header;
        }
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        byte[] bytes = ("{\"code\":401,\"message\":\"" + message + "\"}").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private boolean isExcludedPath(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1; // 在负载均衡之后执行
    }
}