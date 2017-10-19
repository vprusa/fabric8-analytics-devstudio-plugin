package com.redhat.fabric8analytics.lsp.eclipse.reddeer;

import java.util.Collection;

import org.eclipse.reddeer.junit.internal.runner.ParameterizedRequirementsRunnerFactory;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.server.ServerRequirementState;
import org.jboss.ide.eclipse.as.reddeer.server.requirement.ServerRequirement.JBossServer;
import org.jboss.tools.examples.ui.bot.test.integration.AbstractImportQuickstartsTest;
import org.jboss.tools.examples.ui.bot.test.integration.Quickstart;
import org.jboss.tools.maven.reddeer.requirement.NewRepositoryRequirement.DefineMavenRepository;
import org.jboss.tools.maven.reddeer.requirement.NewRepositoryRequirement.MavenRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;

@RunWith(RedDeerSuite.class)
@UseParametersRunnerFactory(ParameterizedRequirementsRunnerFactory.class)
@DefineMavenRepository(newRepositories = {@MavenRepository(url="https://maven.repository.redhat.com/ga/",ID="ga",snapshots=true)})
@JBossServer(state=ServerRequirementState.RUNNING)
public class EAPQuickstartsPomTest extends AbstractImportQuickstartsTest {
	public static final String SERVER_NAME ="Enterprise Application Platform";
	public static final String BLACKLIST_FILE = "resources/servers/eap-blacklist";

	@Parameters(name = "{0}")
	public static Collection<Quickstart> data() {
		return createQuickstartsList();
	}

	@Parameter
	public Quickstart qstart;

	/*
	 * Main tests. Imports quickstart as maven project, performs checks and
	 * deploy on server.
	 */
	@Test
	public void quickstartTest() {
		runQuickstarts(qstart, SERVER_NAME, BLACKLIST_FILE);
	}


	
}