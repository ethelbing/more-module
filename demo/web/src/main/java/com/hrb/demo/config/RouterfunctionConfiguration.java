package com.hrb.demo.config;

import com.hrb.demo.domain.User;
import com.hrb.demo.repository.UserRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * 路由器函数 配置
 */
//configurable 标志这个类所所属对象这个是一个配置文件，
// 是spring3以后提出来的，这个对象是逐一替代我们的xml文件
@Configuration
public class RouterfunctionConfiguration {
    /**
     * Servlet
     * 请求接口 ：ServletRequest 或者 HttpServletRequest
     * 响应接口：ServletResponse 或者 HttpServletResponse
     * Spring 5.0 重新定义了服务请求和响应接口：
     * 请求接口：ServerRequest
     * 响应接口：ServerResponse
     * 既可以支持 Servlet 规范，也可以支持自定义，比如 Netty ( Web Server)
     * 以本例：
     * 定义 Get 请求，并且返回所有的用户对象 URI：/person/find/all
     * Flux 是 0-N 个对象集合
     * Mono 是 0-1 个对象集合
     * Reactive 中的 Flux 或者 Mono它是异步处理（非阻塞）
     * 集合对象基本是同步处理 （阻塞）
     * Flux 或者 Mono 都是 Publisher
     */

    //通过方法参数来注入
    @Bean
    @Autowired
    public RouterFunction<ServerResponse> personFindAll(UserRepostiory userRepostiory){

        /**
         * 查看 routerfunction 源码可知：Mono<HandlerFunction<T>> route(ServerRequest request);
         * route 需要两个参数 ServerRequest request
         * 返回 HandlerFunction ：public interface HandlerFunction<T extends ServerResponse>
         *     HandlerFunciton 继承于 ServerResponse
         * 学习参考：https://docs.spring.io/spring/docs/5.2.2.RELEASE/spring-framework-reference/web-reactive.html#webflux-fn-router-functions
         * 官方文档: 1.5.3 RouterFunction 学习参考文档 隶属于springframework框架下的使用
         * */
        return RouterFunctions.route(RequestPredicates.GET("/person/find/all"),
                request -> {
//            返回所有用户
                    Collection<User> users = userRepostiory.findAll();
                    Flux<User> userFlux = Flux.fromIterable(users); // 调用的方法表示可以迭代
//            Flux<User>是一个发布器，userFlux是推的方法发布数据，需要一个模型，所以把User.class类传进去
                    return ServerResponse.ok().body(userFlux, User.class);
                });
    }
}
