package com.newsbag.server.core;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Main server class
 * 
 * @author adrianmihaichesnoiu
 *
 */
public class MainFramework
{
	/**
	 * Public server configuration TODO: make it configurable via properties
	 */
	// Package name for public handlers
	private static final String PUBLIC_HANDLERS_PACKAGE_NAME = "com.newsbag.server.handlers.pub";

	// Port
	private static final int PUBLIC_PORT = 8080;

	/**
	 * Administration server configuration TODO: make it configurable via properties
	 */
	// Package name for private handlers
	private static final String ADMIN_HANDLERS_PACKAGE_NAME = "com.newsbag.server.handlers.admin";

	// Port
	private static final int ADMIN_PORT = 8081;

	// Singleton instance
	private static MainFramework instance = null;

	// Embedded Jetty server in charge of serving public requests
	private Server publicServer = null;

	// Embedded Jetty server in charge of serving administration requests
	private Server adminServer = null;

	// Overall server status
	// Default is dead :)
	private String overallStatus = "I am dead";

	/**
	 * Double checked singleton
	 * 
	 * @return MainFramework
	 */
	public static MainFramework getInstance()
	{
		if (instance == null)
		{
			synchronized (MainFramework.class)
			{
				if (instance == null)
				{
					instance = new MainFramework();
				}
			}
		}

		return instance;
	}

	/**
	 * Starts the public server
	 * 
	 * @param port
	 * @param handlersPackageName
	 */
	private void startPublicServer(final int port, final String handlersPackageName)
	{
		System.out.println("Starting public server...");
		Thread serverThread = new Thread(() ->
		{
			ResourceConfig config = new ResourceConfig();
			config.packages(handlersPackageName);
			ServletHolder servlet = new ServletHolder(new ServletContainer(config));

			publicServer = new Server(port);
			ServletContextHandler context = new ServletContextHandler(publicServer, "/*");
			context.addServlet(servlet, "/*");

			try
			{
				publicServer.start();
				publicServer.join();
			} catch (Exception e)
			{
				System.err.println(
						"Error occured when starting the private server on port " + port + ": " + e.getMessage());
			} finally
			{
				publicServer.destroy();
			}

		});

		serverThread.start();
		System.out.println("Admin server started!");
	}

	/**
	 * Start the administration server
	 * 
	 * @param port
	 * @param handlersPackageName
	 */
	private void startAdminServer(final int port, final String handlersPackageName)
	{
		System.out.println("Starting admin server...");
		Thread serverThread = new Thread(() ->
		{
			ResourceConfig config = new ResourceConfig();
			config.packages(handlersPackageName);
			ServletHolder servlet = new ServletHolder(new ServletContainer(config));

			adminServer = new Server(port);
			ServletContextHandler context = new ServletContextHandler(adminServer, "/*");
			context.addServlet(servlet, "/*");

			try
			{
				adminServer.start();
				adminServer.join();
			} catch (Exception e)
			{
				System.err.println(
						"Error occured when starting the private server on port " + port + ": " + e.getMessage());
			} finally
			{
				adminServer.destroy();
			}

		});

		serverThread.start();
		System.out.println("Admin server started!");
	}

	/**
	 * The main server method
	 * 
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException
	{
		// Start public and administration servers
		getInstance().startPublicServer(PUBLIC_PORT, PUBLIC_HANDLERS_PACKAGE_NAME);
		getInstance().startAdminServer(ADMIN_PORT, ADMIN_HANDLERS_PACKAGE_NAME);

		// Wait 5 second for servers to start
		waitSometime(5);
		
		if (getInstance().getPublicServer().isStarted() && getInstance().getAdminServer().isStarted())
		{
			// Set status to alive
			// THIS MUST ALWAYS BE THE LAST INSTRUCTION
			getInstance().setOverallStatus("I am alive");
		}
	}
	
	/**
	 * Helper method for interrupting the thread for given amount of seconds
	 * 
	 * @param seconds
	 * @throws InterruptedException
	 */
	public static void waitSometime(int seconds) throws InterruptedException
	{
		Thread.sleep(seconds * 1000);
	}

	/**
	 * Returns the overall status
	 * 
	 * @return String
	 */
	public String getOverallStatus()
	{
		return overallStatus;
	}

	/**
	 * Sets the overall status Intentionally private, to be called only within
	 * MainFramework
	 * 
	 * @param overallStatus
	 */
	private void setOverallStatus(String overallStatus)
	{
		this.overallStatus = overallStatus;
	}

	/**
	 * Returns the public server Intentionally private
	 * 
	 * @return Server
	 */
	private Server getPublicServer()
	{
		return publicServer;
	}

	/**
	 * Returns the admin server Intentionally private
	 * 
	 * @return Server
	 */
	private Server getAdminServer()
	{
		return adminServer;
	}

}
