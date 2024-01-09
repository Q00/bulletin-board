package com.example.jpa.common.guard;

import com.example.jpa.common.exception.AuthFailException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String token = resolveToken((HttpServletRequest) request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (AuthFailException e) {
            log.info(e.getMessage());
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write(createErrorResponse("인증실패", e.getMessage()));
        } catch (Exception e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 status
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write(createErrorResponse("서비스 처리 중 에러가 발생하였습니다.", e.getMessage()));
        }
    }

    private String createErrorResponse(String message, String detail) {
        // Assuming your ResponseResult.fail method returns an object that can be converted to JSON
        // Replace this with actual implementation
        return "{\"error\": \"" + message + "\", \"detail\": \"" + detail + "\"}";
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
