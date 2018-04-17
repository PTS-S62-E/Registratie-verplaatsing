package util;

import io.sentry.Sentry;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class SentrySetup {

	@PostConstruct
	public void setup(){
		Sentry.init();
	}
}
