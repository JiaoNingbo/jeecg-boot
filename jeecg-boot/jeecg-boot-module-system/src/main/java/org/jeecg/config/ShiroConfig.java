package org.jeecg.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.shiro.authc.ShiroRealm;
import org.jeecg.modules.shiro.authc.aop.JwtFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: Scott
 * @date: 2018/2/7
 * @description: shiro 配置类
 */

@Slf4j
@Configuration
public class ShiroConfig {

    @Value("${jeecg.shiro.excludeUrls}")
    private String excludeUrls;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String redisPassword;

    /**
     * Filter Chain定义说明
     * <p>
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        if (oConvertUtils.isNotEmpty(excludeUrls)) {
            String[] permissionUrl = excludeUrls.split(",");
            for (String url : permissionUrl) {
                filterChainDefinitionMap.put(url, "anon");
            }
        }
		// 配置不会被拦截的链接 顺序判断
		//cas验证登录
		filterChainDefinitionMap.put("/cas/client/validateLogin", "anon");
        //登录验证码接口排除
        filterChainDefinitionMap.put("/sys/getCheckCode", "anon");
        //登录接口排除
        filterChainDefinitionMap.put("/sys/login", "anon");
        //登录接口排除
        filterChainDefinitionMap.put("/sys/mLogin", "anon");
        //登出接口排除
        filterChainDefinitionMap.put("/sys/logout", "anon");
        //获取加密串
        filterChainDefinitionMap.put("/sys/getEncryptedString", "anon");
        //短信验证码
        filterChainDefinitionMap.put("/sys/sms", "anon");
        //手机登录
        filterChainDefinitionMap.put("/sys/phoneLogin", "anon");
        //校验用户是否存在
        filterChainDefinitionMap.put("/sys/user/checkOnlyUser", "anon");
        //用户注册
        filterChainDefinitionMap.put("/sys/user/register", "anon");
        //根据手机号获取用户信息
        filterChainDefinitionMap.put("/sys/user/querySysUser", "anon");
        //用户忘记密码验证手机号
        filterChainDefinitionMap.put("/sys/user/phoneVerification", "anon");
        //用户更改密码
        filterChainDefinitionMap.put("/sys/user/passwordChange", "anon");
        //登录验证码
        filterChainDefinitionMap.put("/auth/2step-code", "anon");
        //图片预览不限制token
        filterChainDefinitionMap.put("/sys/common/view/**", "anon");
        //文件下载不限制token
        filterChainDefinitionMap.put("/sys/common/download/**", "anon");
        //pdf预览
        filterChainDefinitionMap.put("/sys/common/pdf/**", "anon");
        filterChainDefinitionMap.put("/generic/**", "anon");
        //pdf预览需要文件
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/**/*.js", "anon");
        filterChainDefinitionMap.put("/**/*.css", "anon");
        filterChainDefinitionMap.put("/**/*.html", "anon");
        filterChainDefinitionMap.put("/**/*.svg", "anon");
        filterChainDefinitionMap.put("/**/*.pdf", "anon");
        filterChainDefinitionMap.put("/**/*.jpg", "anon");
        filterChainDefinitionMap.put("/**/*.png", "anon");
        filterChainDefinitionMap.put("/**/*.ico", "anon");

        // update-begin--Author:sunjianlei Date:20190813 for：排除字体格式的后缀
        filterChainDefinitionMap.put("/**/*.ttf", "anon");
        filterChainDefinitionMap.put("/**/*.woff", "anon");
        // update-begin--Author:sunjianlei Date:20190813 for：排除字体格式的后缀

        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        //性能监控
        filterChainDefinitionMap.put("/actuator/metrics/**", "anon");
        filterChainDefinitionMap.put("/actuator/httptrace/**", "anon");
        filterChainDefinitionMap.put("/actuator/redis/**", "anon");

        //测试示例
        //模板页面
        filterChainDefinitionMap.put("/test/jeecgDemo/html", "anon");
        //redis测试
        filterChainDefinitionMap.put("/test/jeecgDemo/redis/**", "anon");

        //websocket排除
        filterChainDefinitionMap.put("/websocket/**", "anon");

        //dingtalk排除
        filterChainDefinitionMap.put("/dingtalk/**", "anon");

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "jwt");

        // 未授权界面返回JSON
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/common/403");
        shiroFilterFactoryBean.setLoginUrl("/sys/common/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(ShiroRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
         * StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //自定义缓存实现,使用redis
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     */
    public RedisCacheManager redisCacheManager() {
        log.info("===============(1)创建缓存管理器RedisCacheManager");
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        log.info("===============(2)创建RedisManager,连接Redis..URL= " + host + ":" + port);
        RedisManager redisManager = new RedisManager();
        //老版本是分别setHost和setPort,新版本只需要setHost就可以了
        redisManager.setHost(host + ":" + port);
        if (!StringUtils.isEmpty(redisPassword)) {
            redisManager.setPassword(redisPassword);
        }
        return redisManager;
    }

}
