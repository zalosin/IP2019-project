package com.newsbag.server.handlers.pub;

import java.io.IOException;

import javax.ws.rs.Priorities;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

import com.newsbag.server.cache.UserCache;
import com.newsbag.server.core.MainFramework;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter
{
	private final MainFramework mainFramework;
	private final UserCache userCache;
	
	public AuthenticationFilter()
	{
		this.mainFramework = MainFramework.getInstance();
		this.userCache = mainFramework.getUserCache();
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException
	{
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (!userCache.validateToken(authorizationHeader)) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
    }
}
