package app.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Данный класс реализует перенаправление на соответствующие страницы в зависимости от роли пользователя
 *
 * TODO    уточнить эндпоинты для маршрутизации
 *
 * @author Minibaeva Elvira
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else if (roles.contains("ROLE_MANAGER")) {
            httpServletResponse.sendRedirect("/manager");
        } else if (roles.contains("ROLE_PASSENGER")){
            httpServletResponse.sendRedirect("/index");
        } else {
            httpServletResponse.sendRedirect("/index");
        }
    }
}

