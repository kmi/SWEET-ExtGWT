package uk.ac.kmi.microwsmo.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


public class RemoteServiceHelper {
    private final static RemoteServiceHelper remoteServiceHelper = new RemoteServiceHelper();
    public static final String PROXY_SUFFIX = "_Proxy";
    public static final String DEFAULT_ACTION_SUFFIX = ".do";

    public final static RemoteServiceHelper getInstance() {
	return remoteServiceHelper;
    }

    /**
     * Returns default servlet name for service class.
     * 
     * @param serviceClass
     * @return
     */
    public String getServletName(Class serviceClass) {
	// String servletName = serviceClass.getSimpleName(); GWT complains
	// about this
	String className = serviceClass.getName();
	int lastDot = className.lastIndexOf('.');
	String servletName = className.substring(lastDot != -1 ? lastDot + 1 : 0, className.length());
	int proxySuffixIndex = servletName.lastIndexOf(PROXY_SUFFIX);
	String trimmedServletName = (proxySuffixIndex == -1) ? servletName : servletName.substring(0, proxySuffixIndex);
	return trimmedServletName + DEFAULT_ACTION_SUFFIX;
    }

    /**
     * Sets correct endpoint into service reference
     * 
     * @param serviceReference
     * @param servletName
     * @return
     */
    public ServiceDefTarget setupRemoteService(ServiceDefTarget serviceReference) {
	String servletName = getServletName(serviceReference.getClass());
	return setupRemoteService(serviceReference, servletName);
    }

    /**
     * 
     * @param remoteService
     * @param serviceClass
     * @return
     */
    //public ServiceDefTarget setupRemoteService(ServiceDefTarget remoteService, Class serviceClass) {
	//return setupRemoteService(remoteService, getServletName(serviceClass));
   // }

    /**
     * Sets correct endpoint into service reference
     * 
     * @param serviceReference
     * @param servletName
     * @return
     */
    public ServiceDefTarget setupRemoteService(ServiceDefTarget serviceReference, String servletName) {
	// RemoteService remoteService = GWT.create(serviceClass); GWT compiler
	// does not like This also
	String moduleRelativeURL = GWT.getModuleBaseURL() + servletName;
	serviceReference.setServiceEntryPoint(moduleRelativeURL);
	
	return serviceReference;
    }

}
