package com.newsbag.server.handlers.admin;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.newsbag.server.core.DatabaseConnector;
import com.newsbag.server.core.MainFramework;
import com.newsbag.server.util.DatabaseConnectorSource;

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
	 * @throws Exception
	 */
	@GET
	@Path("/overall")
	public Response getOverallStatus() throws Exception
	{
		final String overallStatus = mainFramework.getOverallStatus();

		final DatabaseConnector dbConnector = mainFramework.getConnector(DatabaseConnectorSource.CORE);
		final ResultSet resultSet = dbConnector.executeQuery("SELECT version FROM db_version");

		int version = 0;
		while (resultSet.next())
		{
			version = resultSet.getInt("version");
		}

		return Response.ok(overallStatus + "! Database version: " + version).build();
	}
}
