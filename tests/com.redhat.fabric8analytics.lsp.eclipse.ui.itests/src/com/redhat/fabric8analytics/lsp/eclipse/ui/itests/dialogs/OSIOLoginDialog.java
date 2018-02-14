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
package com.redhat.fabric8analytics.lsp.eclipse.ui.itests.dialogs;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.jface.window.AbstractWindow;
import org.eclipse.reddeer.jface.window.Openable;
import org.eclipse.reddeer.swt.impl.browser.InternalBrowser;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.tools.openshift.reddeer.condition.BrowserContainsText;

import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.pages.OpenshiftServicesPreferencePage;

public class OSIOLoginDialog extends AbstractWindow {
	// https://github.com/jbosstools/jbosstools-openshift/blob/master/itests/org.jboss.tools.openshift.ui.bot.test/src/org/jboss/tools/openshift/ui/bot/test/integration/openshift/io/GetOpenShiftIOTokenTest.java

	protected DefaultShell browser;

	private static final Logger log = Logger.getLogger(OSIOLoginDialog.class);

	private int attempts = 0;
	public static int MAX_ATTEMPTS = 5;

	public OSIOLoginDialog() {
	}

	public void clearAttempts() {
		attempts = 0;
	}

	public int getAttempts() {
		return attempts;
	}

	
	public static OSIOLoginDialog openAndLogin() {
		OSIOLoginDialog old = new OSIOLoginDialog();
		old.open();
		old.login();
		return old;
	}

	public void waitWhileLoading(String text) {
		// new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		// ^^ commented because recent Central lags
		new WaitUntil(new BrowserContainsText(text, false), TimePeriod.VERY_LONG);
		// new WaitUntil(new BrowserContainsText(text), TimePeriod.VERY_LONG);
	}
	
	public void close() {
		if(browser == null)
			return;
		//browser.close();
		new CancelButton().click();
		try {
			// deprecated
			new DefaultShell("OpenShift.io");
			new OkButton().click();
			new OkButton().click();
			new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		} catch (Exception e) {
			// skip
		}
	}

	public boolean catch404() {
		try {
			//DefaultShell browserTmp = new DefaultShell();
			InternalBrowser internalBrowser = new InternalBrowser(browser);
			new WaitUntil(new BrowserContainsText("404 page not found"), TimePeriod.SHORT);
			log.info("Something went wrong at URL: " + internalBrowser.getURL());
			// internalBrowser.getSWTWidget().getShell().close();
			close();
			//return login();
			return true;
		} catch (CoreLayerException e) {
			// skip
		} catch (Exception e) {
			// skip
		}
		return false;
	}

	public boolean login() {
		attempts++;
		log.info("Openshift login attempt: " + attempts);
		assertTrue("Maximum number of OS Login attempts occured, failing ", attempts != MAX_ATTEMPTS);
		AbstractWait.sleep(TimePeriod.SHORT);
		browser = new DefaultShell();

		InternalBrowser internalBrowser = new InternalBrowser(browser);
		waitWhileLoading("OpenShift.io Developer Preview");

		// by account provider
		String provider = System.getProperty("OSLoginProvider") == null ? "" : System.getProperty("OSLoginProvider");
		switch (provider) {
		case "JBossDeveloper":
			internalBrowser.execute("document.getElementById(\"social-jbossdeveloper\").click()");
			// did not find out any better solution then sleep because of superfast
			// reloading of page before JS click() redirect to new url
			AbstractWait.sleep(TimePeriod.SHORT);
			log.info("Waiting for JBossDeveloper portal");
			waitWhileLoading("JBoss<strong>Developer</strong>");

			internalBrowser.execute(String.format("document.getElementById(\"username\").value=\"%s\"",
					System.getProperty("OSusername")));
			internalBrowser.execute(String.format("document.getElementById(\"password\").value=\"%s\"",
					System.getProperty("OSpassword")));

			internalBrowser.execute("document.getElementById(\"fm1\").submit.click()");

			break;
		default:
			internalBrowser.execute(String.format("document.getElementById(\"username\").value=\"%s\"",
					System.getProperty("OSusername")));
			internalBrowser.execute(String.format("document.getElementById(\"password\").value=\"%s\"",
					System.getProperty("OSpassword")));
			internalBrowser.execute(
					"document.getElementById(\"password\").parentElement.parentElement.parentElement.submit()");

		}
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		//if (catch404())
		//	return false;
		
		try {
			// deprecated
			new DefaultShell("OpenShift.io");
			new OkButton().click();
			new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		} catch (Exception e) {
			// skip
		}
		WorkbenchPreferenceDialog preferences = new WorkbenchPreferenceDialog();
		OpenshiftServicesPreferencePage osserivcesPreferences = new OpenshiftServicesPreferencePage(preferences);
		OpenshiftServicesPreferenceDialog osserivces = osserivcesPreferences.getOpenshiftServicesPreferenceDialog();
		log.info("Fabric8Analytics checkbox is: " + osserivces.isFabric8AnalyticsLSPServerEnabled());
		assertTrue("Fabric8Analytics should have been enabled in Openshift Services preferences by now but it is not",
				osserivces.isFabric8AnalyticsLSPServerEnabled());
		osserivces.ok();
		return true;
	}

	@Override
	public Openable getDefaultOpenAction() {
		OSIOLoginDialogOpenable openable = new OSIOLoginDialogOpenable();
		return openable;
	}

}
