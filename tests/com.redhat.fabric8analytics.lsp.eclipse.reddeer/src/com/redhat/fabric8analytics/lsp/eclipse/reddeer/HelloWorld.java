package com.redhat.fabric8analytics.lsp.eclipse.reddeer;

import static org.junit.Assert.*;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class HelloWorld {

	private static final Logger log = Logger.getLogger(HelloWorld.class);

	@Test
	public void test() {
		//fail("Not yet implemented");
		log.info("Hello World test!");
	}
	
}