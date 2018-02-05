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
package com.redhat.fabric8analytics.lsp.eclipse.ui.itests.tabs;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.ctab.DefaultCTabItem;
import org.eclipse.swt.custom.CTabItem;
import org.junit.Assert;


public class Fabric8AnalysisTab {

	public static final String LABEL = "Fabric8 Analysis";
	public static final String HEADER_TEXT_REGEX = ".*Click Generate Analyses Report to Proceed.*";
	public static final String GENERATE_STACK_REPORT_BUTTON_TEXT = "Generate Stack Report";

	private static final Logger log = Logger.getLogger(Fabric8AnalysisTab.class);

	Fabric8AnalysisTab() {
		log.info("Searching for label: " + LABEL);
		new DefaultCTabItem(LABEL).activate();
		Assert.assertTrue("Button '" + GENERATE_STACK_REPORT_BUTTON_TEXT + "' has to be visible", new PushButton(GENERATE_STACK_REPORT_BUTTON_TEXT).isVisible());//click();
	}

	public static Fabric8AnalysisTab openTab() {
		return new Fabric8AnalysisTab();
	}

}
