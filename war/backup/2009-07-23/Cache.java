package uk.ac.kmi.microwsmo.server;

import java.util.HashMap;
import java.util.Map;

/**
 * This class istantiates a data structure wich caches the semantic results for
 * a setted time. Is possible store, find, clear the cache and set the time.
 * 
 * @author Simone Spaccarotella
 */
public final class Cache {

	/** the duration time of the cache */
	private Long expireTime;
	/** it stores the semantic documents retrieved with the keyword */
	private Map<String, String[]> semanticDocuments;
	/** it stores the service properties string */
	private Map<String, String> servicePropertiesStorage;
	/** it stores the domain ontologies string */
	private Map<String, String> domainOntologiesStorage;

	/**
	 * Create a new cache without expiring time. It means that the cache will
	 * store the results indefinitely.
	 */
	public Cache() {
		semanticDocuments = new HashMap<String, String[]>();
		servicePropertiesStorage = new HashMap<String, String>();
		domainOntologiesStorage = new HashMap<String, String>();
		expireTime = new Long(0);
	}

	/**
	 * Sets the expire time of the cache, in seconds.
	 * 
	 * @param seconds
	 *            the time of life in seconds of the results into the cache.
	 */
	public void setExpireTimeInSeconds(int seconds) {
		setExpireTime(new Long(seconds * 1000));
	}

	/**
	 * Sets the expire time of the cache, in minutes.
	 * 
	 * @param minutes
	 *            the time of life in minutes of the results into the cache.
	 */
	public void setExpireTimeInMinutes(int minutes) {
		setExpireTime(new Long(minutes * 60000));
	}

	/**
	 * Sets the expire time of the cache in hours.
	 * 
	 * @param hours
	 */
	public void setExpireTimeInHours(int hours) {
		setExpireTime(new Long(hours * 3600000));
	}

	/**
	 * Sets the expire time. This method is called by all the "setExpireTimeXXX"
	 * methods. It check if the time value is an integer. If not, set the value
	 * to indefinite.
	 * 
	 * @param time
	 */
	private synchronized void setExpireTime(Long time) {
		if (time >= 0) {
			expireTime = time.longValue();
		} else {
			expireTime = new Long(0);
		}
	}

	/**
	 * Returns the expire time in milliseconds.
	 * 
	 * @return a long value which represent the cache's expire time.
	 */
	private synchronized Long getExpireTime() {
		return expireTime;
	}

	/**
	 * Clears the cache.
	 */
	public synchronized void clearCache() {
		semanticDocuments.clear();
		servicePropertiesStorage.clear();
		domainOntologiesStorage.clear();
	}

	/**
	 * Remove the service properties result eventually stored inside the cache.
	 * 
	 * @param key
	 *            the keyword of the service properties.
	 */
	public synchronized void removeServicePropertiesOf(String key) {
		servicePropertiesStorage.remove(key);
	}

	/**
	 * Remove the domain ontologies result eventually stored inside the cache.
	 * 
	 * @param key
	 *            the keyword of the domain ontologies.
	 */
	public synchronized void removeDomainOntologiesOf(String key) {
		domainOntologiesStorage.remove(key);
	}

	/**
	 * Remove the semantic documents result eventually stored inside the cache.
	 * 
	 * @param key
	 *            the keyword of the semantic document.
	 */
	public synchronized void removeSemanticDocumentsOf(String key) {
		semanticDocuments.remove(key);
	}

	/**
	 * Store the result of the service properties.
	 * 
	 * @param key
	 *            the keyword.
	 * @param value
	 *            the string result of the service properties.
	 */
	public synchronized void storeServiceProperties(String key, String value) {
		if (expireTime > 0) {
			servicePropertiesStorage.put(key, value);
			new CacheTimer(key).start();
		}
	}

	/**
	 * Store the result of the domain ontologies.
	 * 
	 * @param key
	 *            the keyword.
	 * @param value
	 *            the string result of the domain ontologies.
	 */
	public synchronized void storeDomainOntologies(String key, String value) {
		if (expireTime > 0) {
			domainOntologiesStorage.put(key, value);
		}
	}

	/**
	 * Store the result of the semantic documents.
	 * 
	 * @param keyword
	 *            the keyword.
	 * @param ontoURI
	 *            the array which contains the semantic documents.
	 */
	public synchronized void storeSemanticDocuments(String keyword,
			String[] ontoURI) {
		semanticDocuments.put(keyword, ontoURI);
	}

	/**
	 * Returns the service properties result related to the keyword.
	 * 
	 * @param key
	 *            the keyword.
	 * @return the service properties result string.
	 */
	public synchronized String getServicePropertiesOf(String key) {
		return servicePropertiesStorage.get(key);
	}

	/**
	 * Returns the domain ontologies result, related to the keyword.
	 * 
	 * @param key
	 *            the keyword.
	 * @return the service properties result string.
	 */
	public synchronized String getDomainOntologiesOf(String key) {
		return domainOntologiesStorage.get(key);
	}

	/**
	 * Returns the semantic documents result, related to the keyword.
	 * 
	 * @param key
	 *            the keyword.
	 * @return the array which contains the semantic documents.
	 */
	public synchronized String[] getSemanticDocumentsOf(String key) {
		return semanticDocuments.get(key);
	}

	/**
	 * Checks if the service properties result, related to the given keyword, is
	 * cached.
	 * 
	 * @param key
	 *            the keyword.
	 * @return true if the result is cached.
	 */
	public synchronized boolean isServicePropertiesCached(String key) {
		return servicePropertiesStorage.containsKey(key);
	}

	/**
	 * Checks if the domain ontologies result, related to the given keyword, is
	 * cached.
	 * 
	 * @param key
	 * @return
	 */
	public synchronized boolean isDomainOntologiesCached(String key) {
		return domainOntologiesStorage.containsKey(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public synchronized boolean isAlreadySearched(String key) {
		return semanticDocuments.containsKey(key);
	}

	/**
	 * The timer of the cache. This timer remove the result from the cache when
	 * the time expire.
	 * 
	 * @author Simone Spaccarotella
	 */
	private class CacheTimer extends Thread {

		private String keyword;
		private long sleepingTime;

		/**
		 * Creates the cache of the timer.
		 * 
		 * @param keyString
		 *            the keyword which this timer is associated.
		 */
		public CacheTimer(String keyString) {
			this.keyword = keyString;
			sleepingTime = getExpireTime();
		}

		@Override
		public void run() {
			try {
				Thread.sleep(sleepingTime);
				removeResultOf(keyword);
			} catch (InterruptedException e) {
				removeResultOf(keyword);
			}
		}

		/**
		 * Remove the result associated to this keyword, from the cache.
		 * 
		 * @param keyword
		 *            the keyword of the result, that will be removed from the
		 *            cache.
		 */
		private void removeResultOf(String keyword) {
			removeServicePropertiesOf(keyword);
			removeDomainOntologiesOf(keyword);
			removeSemanticDocumentsOf(keyword);
		}

	}

}