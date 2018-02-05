/*******************************************************************************
 * Copyright (c) 2007-2017 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v 1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package com.redhat.fabric8analytics.lsp.eclipse.ui.itests.dialogs;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferenceDialog;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuPreferencesDialog;

public class OpenshiftServicesPreferenceDialog extends WorkbenchMenuPreferencesDialog {

	public static String CHECKBOX_LABEL = "Fabric8 Analytics LSP Server";
	public RegexMatcher CHECKBOX_LABEL_REGEX = new RegexMatcher(".*" + CHECKBOX_LABEL + ".*");

	public OpenshiftServicesPreferenceDialog() {
		super(new WithTextMatcher(new RegexMatcher("Preferences.*")));
	}

	public OSIOLoginDialog enableFabric8AnalyticsLSPServer() {
		CheckBox enablef8analytics = new CheckBox(CHECKBOX_LABEL);
		enablef8analytics.click();
		OSIOLoginDialog loginDialog = new OSIOLoginDialog();
		try {
			loginDialog.login();
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
		return loginDialog;
	}

	public void disableFabric8AnalyticsLSPServer() {
		CheckBox enablef8analytics = new CheckBox(CHECKBOX_LABEL);
		enablef8analytics.click();
	}

	public boolean isFabric8AnalyticsLSPServerEnabled() {
		return new CheckBox(CHECKBOX_LABEL).isChecked();
	}

}
