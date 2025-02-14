package com.byd.emg.config;

import com.byd.emg.filter.MyAccessDeniedHandler;
import com.byd.emg.service.UserService;
import com.byd.emg.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


@Configuration
@EnableWebSecurity
@ComponentScan("com.byd.emg")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    //根据一个url请求，获得访问它所需要的roles权限
    @Autowired
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    //接收一个用户的信息和访问一个url所需要的权限，判断该用户是否可以访问
    @Autowired
    private AccessDecisionManager accessDecisionManager;

    //403页面
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    /**定义认证用户信息获取来源，密码校验规则等*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**有以下几种形式，使用第3种*/
        //inMemoryAuthentication 从内存中获取
        //auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user1").password(new BCryptPasswordEncoder().encode("123123")).roles("USER");

        //jdbcAuthentication从数据库中获取，但是默认是以security提供的表结构
        //usersByUsernameQuery 指定查询用户SQL
        //authoritiesByUsernameQuery 指定查询权限SQL
        //auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query).authoritiesByUsernameQuery(query);

        //注入userDetailsService，需要实现userDetailsService接口
        auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.MD5EncodeUtf8(rawPassword.toString());
            }

            /**
             * @param rawPassword 明文
             * @param encodedPassword 密文
             * @return
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.MD5EncodeUtf8(decode(rawPassword.toString())));
            }



            private  byte[] base64DecodeChars = new byte[]{
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                    -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60,
                    61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
                    14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27,
                    28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47,
                    48, 49, 50, 51, -1, -1, -1, -1, -1};
            /**
             *  * 解密 * @param str * @return
             */
            public  String decode(String str) {
                byte[] data = str.getBytes();
                int len = data.length;
                ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
                int i = 0;
                int b1, b2, b3, b4;
                while (i < len) {
                    do {
                        b1 = base64DecodeChars[data[i++]];
                    } while (i < len && b1 == -1);
                    if (b1 == -1) {
                        break;
                    }
                    do {
                        b2 = base64DecodeChars[data[i++]];
                    } while (i < len && b2 == -1);
                    if (b2 == -1) {
                        break;
                    }
                    buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));
                    do {
                        b3 = data[i++];
                        if (b3 == 61) {
                            return buf.toString();
                        }
                        b3 = base64DecodeChars[b3];
                    } while (i < len && b3 == -1);
                    if (b3 == -1) {
                        break;
                    }
                    buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
                    do {
                        b4 = data[i++];
                        if (b4 == 61) {
                            return buf.toString();
                        }
                        b4 = base64DecodeChars[b4];
                    } while (i < len && b4 == -1);
                    if (b4 == -1) {
                        break;
                    }
                    buf.write((int) (((b3 & 0x03) << 6) | b4));
                }
                return buf.toString();
            }
        });  //user Details Service验证
    }

    //在这里配置哪些页面不需要认证
    @Override
    public void configure(WebSecurity web) throws Exception {
       web.ignoring().antMatchers("/js/**","/css/**","/images/**");
//       web.ignoring().antMatchers("/**");
    }

    /**定义安全策略*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()       //配置安全策略
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public FilterSecurityInterceptor postProcess(FilterSecurityInterceptor o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(accessDecisionManager);
                        return o;
                    }
                })

//                .antMatchers("/user/login").permitAll()
                .and()
                .formLogin()
                .loginPage("/user/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        StringBuffer sb = new StringBuffer();
                        System.out.println(e);
                        if ( e instanceof BadCredentialsException) {
                            sb.append("用户名或密码输入错误，登录失败!");
                        } else if (e instanceof DisabledException) {
                            sb.append("账户被禁用，登录失败，请联系管理员!");
                        } else {
                            sb.append("登录失败!");
                        }
                        request.getSession().setAttribute("errorMsg",sb.toString());
                        request.getRequestDispatcher("/user/loginError").forward(request, response);
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        request.getRequestDispatcher("/user/loginSuccessful").forward(request, response);
                     /*   PrintWriter out = response.getWriter();
                         ObjectMapper objectMapper = new ObjectMapper();
                        String s = "{\"status\":\"success\",\"msg\":" + "}";
                        //String s= JsonUtil.obj2String(authentication);
                        out.write(s);
                        out.flush();
                        out.close();*/
                    }
                })

                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);

    }

}
