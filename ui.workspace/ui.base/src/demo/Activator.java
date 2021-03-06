package demo;

import java.util.Properties;

import javax.servlet.Filter;

import org.amdatu.security.tokenprovider.TokenProvider;
import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

public class Activator extends DependencyActivatorBase {

	@Override
	public void init(BundleContext arg0, DependencyManager dm) throws Exception {

		Properties properties = new Properties();
		// TODO: This should be configured somewhere or there should be configuration somewhere else the filter reads to figure out what to filter not to
		properties.put("pattern", "/demo/*.*");
		dm.add(createComponent()
				.setInterface(Filter.class.getName(), properties)
				.setImplementation(SecurityFilter.class)
				.add(createServiceDependency().setService(TokenProvider.class)
						.setRequired(false))
				.add(createServiceDependency().setService(LogService.class)
						.setRequired(false)));

	}

	@Override
	public void destroy(BundleContext arg0, DependencyManager arg1)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
