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

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
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
		ProjectItem pi = getProject(getProjectName()).getProjectItem("pom.xml");
		//TODO pi.open(); was not waiting long enough for Language server to initialize 
		pi.select();
		pi.getTreeItem().doubleClick();
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);

		Fabric8AnalysisTab.openTab();
	}

}