/*******************************************************************************
 * Copyright (c) 2017 Red Hat Inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Incorporated - initial API and implementation
 *******************************************************************************/
package com.redhat.fabric8analytics.lsp.eclipse.ui.itests;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.requirements.ImportProjectsRequirements.ImportProjects;
import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.requirements.OSIOLoginRequirement.OSIOLogin;
import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.tabs.Fabric8AnalysisTab;

@RunWith(RedDeerSuite.class)
@OSIOLogin
@ImportProjects
public class Fabric8AnalysisTabTests extends StackAnalysesTestProjectBase {

	@Test
	public void validateFabric8AnalysisTabTest() {
		log.info("Validating that tab can be opened for project " + getProjectName());
		getProject(getProjectName()).getProjectItem("pom.xml").open();
		Fabric8AnalysisTab.openTab();
	}

}