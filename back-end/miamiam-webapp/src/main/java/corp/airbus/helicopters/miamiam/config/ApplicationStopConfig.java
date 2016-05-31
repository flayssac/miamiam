package corp.airbus.helicopters.miamiam.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import com.sqli.commons.core.config.configuration.ReloadablePlaceholderConfig;

/**
 * The Class ApplicationStopConfig.
 */
@Component
public class ApplicationStopConfig implements ApplicationListener<ContextClosedEvent> {
	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/** The reloadable placeholder config. */
	@Autowired
	private ReloadablePlaceholderConfig reloadablePlaceholderConfig;

	/**
	 * On application event.
	 *
	 * @param event
	 *            the event
	 */
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		logger.info("Application stopping...");
		reloadablePlaceholderConfig.stopWatching();
	}

}
