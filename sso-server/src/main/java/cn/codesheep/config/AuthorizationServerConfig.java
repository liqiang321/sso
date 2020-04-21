package cn.codesheep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    /*ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），客户端详情信息在 这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
    AuthorizationServerEndpointsConfigurer：用来配置令牌（token）的访问端点和令牌服务(token services)。
    AuthorizationServerSecurityConfigurer：用来配置令牌端点的安全约束*/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        // 定义了两个客户端应用的通行证
        //客户端详情，表情支持哪些客户端
        clients.inMemory()//暂时配置到内存中，也可以放到数据库
                .withClient("sheep1")//客户端id
                .secret(new BCryptPasswordEncoder().encode("123456"))//
                .authorizedGrantTypes("authorization_code", "refresh_token")//授权类型，五种授权类型，授权码，密码，简化模式，刷新令牌，。。
                .redirectUris("http://localhost:8086/login")
                .scopes("all")//授权范围，也可以指定具体的服务
                .autoApprove(false)//授权成功会跳转到授权页面
                .and()
                .withClient("sheep2")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://localhost:8087/login")
                .scopes("all")
                .autoApprove(false);
        System.out.println();
    }

    //令牌端点配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());//令牌存储策略
        tokenServices.setSupportRefreshToken(true);//是否产生刷新令牌
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());//客户端信息服务
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 一天有效期
        endpoints.tokenServices(tokenServices);
    }

    //令牌端点访安全策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");//
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("testKey");
        return converter;
    }

}