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

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.securestorage.SecureStorageRequirement.DisableSecureStorage;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.dialogs.OSIOLoginDialog;
import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.dialogs.OpenshiftServicesPreferenceDialog;
import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.requirements.OSIOLoginRequirement.OSIOLogin;

@RunWith(RedDeerSuite.class)
@OSIOLogin
public class EnableFabric8AnalyticsRequirementTest {

	private static final Logger log = Logger.getLogger(EnableFabric8AnalyticsRequirementTest.class);

	@Test
	public void enableFabric8AnalyticsOSIOButtonTest() {
		log.info("Just check if requirement is satisfied");
	}

}