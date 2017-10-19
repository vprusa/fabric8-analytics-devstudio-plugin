package com.redhat.fabric8analytics.lsp.eclipse.reddeer;

import static org.junit.Assert.*;

import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.problems.Problem;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView;
import org.eclipse.reddeer.eclipse.ui.views.markers.ProblemsView.ProblemType;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
//import org.jboss.tools.batch.reddeer.wizard.NewJobXMLFileWizardDialog;
//import org.jboss.tools.batch.reddeer.wizard.NewJobXMLFileWizardPage;


@RunWith(RedDeerSuite.class)
public class StackAnalysisTest {
	
	
	
	private static final String PROJECT_NAME = "batch-test-project";

	protected static final String JAVA_RESOURCES = "Java Resources";

	protected static final String JAVA_FOLDER = "src/main/java";

	protected static final String RESOURCES_FOLDER = "src/main/resources";

	protected static final String META_INF_FOLDER = "META-INF";

	protected static final String BATCH_XML_FILE = "batch.xml";

	protected static final String[] BATCH_XML_FILE_FULL_PATH = new String[] { JAVA_RESOURCES, RESOURCES_FOLDER,
			META_INF_FOLDER, BATCH_XML_FILE };

	protected static final String JOB_FILES_FOLDER = "batch-jobs";

	protected static final String JOB_XML_FILE = "batch-test-job.xml";

	protected static final String JOB_ID = "batch-test";

	protected static final String[] JOB_XML_FILE_FULL_PATH = new String[] { JAVA_RESOURCES, RESOURCES_FOLDER,
			META_INF_FOLDER, JOB_FILES_FOLDER, JOB_XML_FILE };

	private static final Logger log = Logger.getLogger(StackAnalysisTest.class);


	/**
	 * Returns actual project object base on PROJECT_NAME constant
	 * 
	 * @return Project object of actual project
	 */
	protected static DefaultProject getProject() {
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		return explorer.getProject(getProjectName());
	}

	protected static String getProjectName() {
		return PROJECT_NAME;
	}

	/**
	 * Test environment initialization, imports batch test project and creates its
	 * job XML configuration file.
	 * 
	 * @param log
	 *            class logger from which method was called
	 */
	protected static void initTestResources(Logger log, String projectPath) {
		log.info("Import archive project " + projectPath);
		log.info("Name of the project " + PROJECT_NAME);
		importProject(projectPath);
		new WaitWhile(new JobIsRunning());
		getProject().select();
		log.info("Create empty batch-job xml file");
		createJobXMLFile(JOB_ID);
	}

	/**
	 * Creates job XML in actual selected project
	 * 
	 * @param jobID
	 *            id of the job XML file
	 */
	protected static void createJobXMLFile(String jobID) {
		NewJobXMLFileWizardDialog dialog = new NewJobXMLFileWizardDialog();
		dialog.open();

		NewJobXMLFileWizardPage page = new NewJobXMLFileWizardPage(dialog);
		page.setFileName(JOB_XML_FILE);
		page.setJobID(jobID);

		dialog.finish();
	}

	/**
	 * Imports zip test project
	 */
	private static void importProject(String projectPath) {
		ExternalProjectImportWizardDialog dialog = new ExternalProjectImportWizardDialog();
		dialog.open();

		WizardProjectsImportPage page = new WizardProjectsImportPage(dialog);
		page.setArchiveFile(Activator.getPathToFileWithinPlugin(projectPath));
		page.selectProjects(getProjectName());

		dialog.finish(TimePeriod.VERY_LONG);
	}

	/**
	 * Removes project from Project Explorer
	 * 
	 * @param log
	 *            class logger from which method was called
	 */
	protected static void removeProject(Logger log) {
		log.info("Removing " + getProjectName());
		// temporary workaround until upstream patch is applied (eclipse bug 478634)
		try {
			org.eclipse.reddeer.direct.project.Project.delete(getProjectName(), true, true);
		} catch (RuntimeException exc) {
			log.error("RuntimeException occured during deleting project");
			exc.printStackTrace();
			log.info("Deleting project second time ...");
			org.eclipse.reddeer.direct.project.Project.delete(getProjectName(), true, true);
		}
		new WaitWhile(new JobIsRunning());
	}

	@Before
	public void before() {
		initTestResources(log, "resources/" + getProjectName() + ".zip");
	}

	@Test
	public void openStackAnalysis() {
		log.info("Validating ContextMenu item StackAnalysis");

		DefaultProject project = getProject();
		project.select();
		
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.getItem("Stack Analysis").select();
		log.info("Validating that shell opened");
		//DefaultShell stackAnalysisShell = new DefaultShell(new RegexMatcher(".*OpenShift\\.io.*")); 
		DefaultShell stackAnalysisShell = new DefaultShell();
		stackAnalysisShell.maximize();
		log.info("stackAnalysisShell.getText()");
		log.info(stackAnalysisShell.getText());
		
		 
		new WaitUntil(new JobIsRunning(), TimePeriod.LONG);
	}

}
