package com.codeWithProject.ecom.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codeWithProject.ecom.serviceImpl.UserDetailsServiceImpl;
import com.codeWithProject.ecom.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{

	
	private final UserDetailsServiceImpl userDetailService;
	private final JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getRequestURI();
		
		 // ✅ Skip token validation for public endpoints
        if (path.startsWith("/authenticate") || path.startsWith("/sign-up") || path.startsWith("/order")) {
            filterChain.doFilter(request, response);
            return;
        }
	
		
		// TODO Auto-generated method stub
		String authHeader =  request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtUtil.extractUsername(token);
		}
		
		  if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			  UserDetails userDetails = userDetailService.loadUserByUsername(username);
			  if (jwtUtil.validateToken(token, userDetails)) {
	            	
	                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities()
	                );
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
			  }
		}
		  
		  filterChain.doFilter(request, response);
		
	}

}
