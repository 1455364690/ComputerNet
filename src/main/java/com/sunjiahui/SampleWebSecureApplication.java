package com.sunjiahui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
@Controller
public class SampleWebSecureApplication implements WebMvcConfigurer {

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/paytest").setViewName("pay");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("pay_success").setViewName("pay_success");
        // registry.addViewController("/Access_Denied").setViewName("access_denied");
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(SampleWebSecureApplication.class).run(args);
    }

    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        PasswordEncoder myPasswordEncoder;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(myPasswordEncoder);
            super.configure(auth);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.authorizeRequests()
                    .antMatchers("/", "/home").permitAll()
                    .antMatchers("/admin/**").access("hasRole('ADMIN')")
                    .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                    .antMatchers("/user/**").access("hasRole('USER')")
                    .antMatchers("/activity/getActivityState").permitAll()
                    .antMatchers("/template/getHomeTemplateList").permitAll()
                    .antMatchers("/activity/**").access("hasRole('USER')")
                    .antMatchers("/template/**").access("hasRole('USER')")
                    .antMatchers("/userTemplate/**").access("hasRole('USER')")
                    .antMatchers("/littleGame/**").permitAll()
                    .antMatchers("/pay/alipay/redirect/**").permitAll()
                    .and().formLogin().loginPage("/login")
                    .successForwardUrl("/loginSuccess")
                    .failureForwardUrl("/loginFail")
                    .and().logout().logoutSuccessHandler(logoutSuccessHandler())
                    .and().exceptionHandling().accessDeniedPage("/access_denied")
                    .defaultAuthenticationEntryPointFor(loginUrlAuthenticationEntryPoint(),
                            new AntPathRequestMatcher("/**"));

            // @formatter:on
            http.csrf().disable();
        }


        @Bean
        public AuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
            return new LoginUrlAuthenticationEntryPoint("/?isLogin=false");
        }

        public LogoutSuccessHandler logoutSuccessHandler() {
            return new MyLogoutSuccessHandler();
        }

        class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                request.getRequestDispatcher("/logoutSuccess").forward(request, response);
            }
        }

    }

}
