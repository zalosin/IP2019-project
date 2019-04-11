package com.newsbag.server.core;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.newsbag.server.cache.ArticleCache;
import com.newsbag.server.cache.CategoryCache;
import com.newsbag.server.dao.ArticleDao;
import com.newsbag.server.dao.CategoryDao;
import com.newsbag.server.util.DatabaseConnectorSource;

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

	/**
	 * Database settings
	 * 
	 * TODO:make it configurable via properties
	 */
	// DB User
	private static final String DB_USER = "sqluser";

	// DB Pass
	private static final String DB_USER_PASS = "sqluserpw";

	// DB Path
	private static final String DB_PATH = "localhost";

	// Singleton instance
	private static MainFramework instance = null;

	// Embedded Jetty server in charge of serving public requests
	private Server publicServer = null;

	// Embedded Jetty server in charge of serving administration requests
	private Server adminServer = null;

	// Overall server status
	// Default is dead :)
	private String overallStatus = "I am dead";

	// Connectors map
	private Map<DatabaseConnectorSource, DatabaseConnector> dbConnectors;

	// TODO make it configurable
	public boolean isLocal = false;

	/**
	 * DAOs
	 */
	private ArticleDao articleDao;
	private CategoryDao categoryDao;

	/**
	 * CACHEs
	 */
	private ArticleCache articleCache;
	private CategoryCache categoryCache;

	/**
	 * Private singleton constructor
	 */
	private MainFramework()
	{
		// intentionally empty
	}

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
	 * Initializes all the DB Connectors
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void initializeDatabaseConnectors() throws ClassNotFoundException, SQLException
	{
		// initialize connectors map
		dbConnectors = new HashMap<DatabaseConnectorSource, DatabaseConnector>();

		// initialize all defined connectors
		for (DatabaseConnectorSource connectorSource : DatabaseConnectorSource.values())
		{
			final DatabaseConnector connector = new DatabaseConnector(connectorSource, DB_USER, DB_USER_PASS, DB_PATH,
					connectorSource.getSchemaName(), isLocal);

			dbConnectors.put(connectorSource, connector);
		}
	}

	/**
	 * The main server method
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		// Start public and administration servers
		if (!getInstance().isLocal)
		{
			final String herokuPort = System.getenv("PORT");
			getInstance().startPublicServer(Integer.valueOf(herokuPort), PUBLIC_HANDLERS_PACKAGE_NAME);
		} else
		{
			getInstance().startPublicServer(PUBLIC_PORT, PUBLIC_HANDLERS_PACKAGE_NAME);
		}
		getInstance().startAdminServer(ADMIN_PORT, ADMIN_HANDLERS_PACKAGE_NAME);

		// initialize database connectors
		getInstance().initializeDatabaseConnectors();

		// initialize daos
		getInstance().initializeDaos();

		// initialize caches before daos
		getInstance().initializeCaches();

		// reload caches
		getInstance().reloadCaches();

		// Wait 5 second for servers to start
		waitSometime(5);

		if (getInstance().getPublicServer().isStarted() && getInstance().getAdminServer().isStarted())
		{
			// Set status to alive
			// THIS MUST ALWAYS BE THE LAST INSTRUCTION
			getInstance().setOverallStatus("I am alive");
		}
		
		if(!getInstance().isLocal)
		{
			getInstance().keepDBConnectionAlive();
		}
	}

	private void keepDBConnectionAlive()
	{
		System.out.println("Starting connection keeper...");
		Thread serverThread = new Thread(() ->
		{
			while (true)
			{
				try
				{
					MainFramework.getInstance().getConnector(DatabaseConnectorSource.CORE).executeQuery("SELECT 1");
				} catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try
				{
					waitSometime(50);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		serverThread.start();
	}

	/**
	 * Method used to init daos
	 * 
	 * @throws Exception
	 */
	private void initializeDaos() throws Exception
	{
		articleDao = new ArticleDao();
		categoryDao = new CategoryDao();
	}

	/**
	 * Method used to init caches
	 * 
	 * @throws Exception
	 */
	private void initializeCaches() throws Exception
	{
		articleCache = new ArticleCache(articleDao);
		categoryCache = new CategoryCache(categoryDao);
	}

	/**
	 * Method used for caches load
	 * 
	 * @throws Exception
	 */
	private void reloadCaches() throws Exception
	{
		articleCache.reloadCache();
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

	/**
	 * Return the DB Connector given source
	 * 
	 * @param source
	 * @return DatabaseConnector
	 * @throws Exception in case connector was not found
	 */
	public DatabaseConnector getConnector(DatabaseConnectorSource source) throws Exception
	{
		final DatabaseConnector connector = dbConnectors.get(source);

		if (connector == null)
		{
			throw new Exception("Database connector was not found for " + source.getSchemaName());
		}

		return connector;
	}

	/**
	 * Returns the Article dao
	 * 
	 * @return ArticleDao
	 */
	public ArticleDao getArticleDao()
	{
		return this.articleDao;
	}

	/**
	 * Returns the Article cache
	 * 
	 * @return ArticleCache
	 */
	public ArticleCache getArticleCache()
	{
		return this.articleCache;
	}

	public CategoryDao getCategoryDao()
	{
		return this.categoryDao;
	}

	public CategoryCache getCategoryCache()
	{
		return this.categoryCache;
	}
}
