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
package com.redhat.fabric8analytics.lsp.eclipse.ui.itests.requirements;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;

import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.dialogs.OSIOLoginDialog;
import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.requirements.OSIOLoginRequirement.OSIOLogin;

public class OSIOLoginRequirement extends AbstractRequirement<OSIOLogin> {
	
	private static final Logger log = Logger.getLogger(OSIOLoginRequirement.class);
	
	/**
	 * Marks test class, which requires clean workspace before test cases are executed.
	 */
	@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
	@Documented
	public @interface OSIOLogin {
		
	}
	
	/**
	 * Save all editors and delete all projects from workspace.
	 */
	@Override
	public void fulfill() {	
		log.info("Fulfilling OSIOLoginRequirement");
		OSIOLoginDialog osiologindialog =  OSIOLoginDialog.openLoginDialog();
		osiologindialog.login();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {

	}
}