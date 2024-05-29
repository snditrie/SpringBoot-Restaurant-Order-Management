package com.enigma.wmb_sb.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    final String AUTH_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(AUTH_HEADER);

            if(bearerToken != null && jwtService.verifyJwtToken(bearerToken)){

                // claims token/ decode token

                JwtClaims decodeJwt = jwtService.getClaimsByToken(bearerToken);

                // find UserAccount by id form sub in token

                UserAccount userAccountBySub = userService.getByUserId(decodeJwt.getUserAccountId());

                // verify Authentication use UserPassAuthToken

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(

                        userAccountBySub.getUsername(),

                        null,

                        userAccountBySub.getAuthorities()

                );

                // kita masukkan detail detail lain seperti ip addres, siapa yg ngehit

                authentication.setDetails(new WebAuthenticationDetails(request));

                // Set ini Security Context

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (Exception e){

            log.error("Cannot set user authentication: {}", e.getMessage());

        }

        // ibarat finally

        // Lempar ke controller

        filterChain.doFilter(request,response);
    }
}
