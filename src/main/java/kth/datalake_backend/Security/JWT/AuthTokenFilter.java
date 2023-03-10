package kth.datalake_backend.Security.JWT;

import kth.datalake_backend.Security.Services.UserDetailsServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class represents a filter that intercepts incoming requests and checks if they contain a valid JWT token
 * in the Authorization header. If a valid token is found, it sets the user authentication information in the
 * SecurityContextHolder. If the request is for an endpoint that requires admin privileges, it checks if the
 * user has admin privileges before allowing access.
 */
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImp userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    /**
     * This method extracts the JWT token from the Authorization header of an incoming request.
     *
     * @param request The incoming request object
     * @return The JWT token as a String, or null if the header is missing or invalid
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    /**
     * This method intercepts incoming requests and checks if they contain a valid JWT token in the Authorization header.
     * If a valid token is found, it sets the user authentication information in the SecurityContextHolder.
     * If the request is for an endpoint that requires admin privileges, it checks if the user has admin privileges before allowing access.
     *
     * @param request     The incoming request object
     * @param response    The outgoing response object
     * @param filterChain The filter chain object
     * @throws ServletException If there is a problem with the servlet handling the request
     * @throws IOException      If there is an I/O error while handling the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                System.out.println(request.getRequestURI().toString());
                if (request.getRequestURI().toString().equals("/api/admin/getAllUser") || request.getRequestURI().toString().equals("/api/admin/removeUser") || request.getRequestURI().toString().equals("/api/admin/nrOfRelations")
                        || request.getRequestURI().toString().equals("/api/admin/nrOfNodes") || request.getRequestURI().toString().equals("/api/patient/getAllDatasets")
                        || request.getRequestURI().toString().equals("/api/admin/signUp") || request.getRequestURI().toString().equals("/api/admin/updateUser")
                        || request.getRequestURI().toString().equals("/api/patient/input/symptoms") || request.getRequestURI().toString().equals("/api/patient/input")) {
                    if (!userDetailsService.ifUserIsAdmin(username)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        filterChain.doFilter(request, response);
                    }
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }
}
