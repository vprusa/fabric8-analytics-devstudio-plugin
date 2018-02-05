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
package com.redhat.fabric8analytics.lsp.eclipse.ui.itests.pages;

import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuPreferencesDialog;

import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.dialogs.OSIOLoginDialog;
import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.dialogs.OpenshiftServicesPreferenceDialog;

public class OpenshiftServicesPreferencePage extends PreferencePage {
	
	protected OpenshiftServicesPreferenceDialog osServices;
	
	public OpenshiftServicesPreferencePage(ReferencedComposite composite) {
		super(composite, "Openshift Services");
		osServices = new OpenshiftServicesPreferenceDialog();
	}
	
	public OpenshiftServicesPreferenceDialog getOpenshiftServicesPreferenceDialog() {
		return osServices;
	}

}
