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

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.exception.CoreLayerException;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenImportWizard;
import org.eclipse.reddeer.eclipse.m2e.core.ui.wizard.MavenImportWizardPage;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.handler.EditorHandler;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;

import com.redhat.fabric8analytics.lsp.eclipse.ui.itests.requirements.ImportProjectsRequirements.ImportProjects;

public class ImportProjectsRequirements extends AbstractRequirement<ImportProjects> {

	protected static final Logger log = Logger.getLogger(ImportProjectsRequirements.class);

	/**
	 * Marks test class, which requires clean workspace before test cases are
	 * executed.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@Documented
	public @interface ImportProjects {
		/**
		 * Project names.
		 *
		 * @return projects names
		 */
		String[] projectsNames() default { "maven-project-test" };
	}

	/**
	 * Save all editors and delete all projects from workspace.
	 */
	@Override
	public void fulfill() {
		for (String projectName : annotation.projectsNames()) {
			try {
				importProject(projectName);
			} catch (IOException e) {
				// e.printStackTrace();
				log.error(e.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {
		log.info("Cleaning projects");
		try {
			new ProjectExplorer().deleteAllProjects(false);
		} catch (EclipseLayerException | CoreLayerException e) {
			// idk why this error happens but deletion is successful
			// e.printStackTrace();
			log.info("Exception EclipseLayerException or CoreLayerException occured and ignored");
		}
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
		EditorHandler.getInstance().closeAll(false);
		try {
			new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
		} catch (Exception e) {
			String jobDescription = "Initialize Language Servers for pom.xml";
			log.info("Job '" + jobDescription + "' may be still running, lets try kill it");
			Job[] jobs = Job.getJobManager().find(null);
			for (Job job : jobs) {
				if (job.getName().matches(jobDescription)) {
					log.info("Stopping job " + jobDescription);
					job.cancel();
				}
			}
		}
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}

	public static void importProject(String projectName) throws IOException {
		log.info("Import " + projectName);
		String path = "target/classes/" + projectName;
		MavenImportWizard importDialog = new MavenImportWizard();
		importDialog.open();
		MavenImportWizardPage importPage = new MavenImportWizardPage(importDialog);
		String canonicalPath = new File(path).getCanonicalPath();
		log.info("Canonical path to resoruce project: " + canonicalPath);
		importPage.setRootDirectory(canonicalPath);
		importDialog.finish();
	}

}