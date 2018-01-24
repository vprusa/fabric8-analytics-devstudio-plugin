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
package com.redhat.fabric8analytics.lsp.eclipse.ui.itests.view;

import org.eclipse.reddeer.eclipse.ui.browser.WebBrowserView;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

public class StackAnalysesView extends WorkbenchView {

	// basically clone of https://github.com/eclipse/reddeer/blob/02584bf1d10c8922bf616e670a8aeeca8a7b4f23/plugins/org.eclipse.reddeer.eclipse/src/org/eclipse/reddeer/eclipse/ui/browser/WebBrowserView.java
	// because constructors cannot be overriden
	StackAnalysesView() {
		super("Stack Analyses");
	}

}
