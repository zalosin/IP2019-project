package com.newsbag.server.handlers.admin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.newsbag.server.core.MainFramework;

/**
 * Admin handler in charge of health checks
 * 
 * @author adrianmihaichesnoiu
 *
 */
@Path("/admin/healthcheck")
public class AdminHealthCheckHandler
{
	private MainFramework mainFramework;

	/**
	 * Constructor
	 */
	public AdminHealthCheckHandler()
	{
		mainFramework = MainFramework.getInstance();
	}

	/**
	 * Returns the overall status
	 * 
	 * @return Response
	 */
	@GET
	@Path("/overall")
	public Response getOverallStatus()
	{
		final String overallStatus = mainFramework.getOverallStatus();
		return Response.ok(overallStatus).build();
	}
}
