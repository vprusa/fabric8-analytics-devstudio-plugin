package com.redhat.fabric8analytics.lsp.eclipse.ui.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class StackAnalysesContextMenuItemTests {

	protected static final String PROJECT_NAME = "maven-project-test";
	protected static final String CONTEXT_MENU_ITEM_TEXT = "Stack Analyses";

	private static final Logger log = Logger.getLogger(StackAnalysesContextMenuItemTests.class);

	@BeforeClass
	public static void prepare() {
		log.info("Import " + PROJECT_NAME);
		String path = "resources/" + PROJECT_NAME;
		ExternalProjectImportWizardDialog importDialog = new ExternalProjectImportWizardDialog();
		importDialog.open();
		WizardProjectsImportPage importPage = new WizardProjectsImportPage(importDialog);
		try {
			importPage.setRootDirectory((new File(path).getCanonicalPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		importPage.copyProjectsIntoWorkspace(true);
		importDialog.finish();
	}

	@AfterClass
	public static void clean() {
		new ProjectExplorer().deleteAllProjects();
		WorkbenchShellHandler.getInstance().closeAllNonWorbenchShells();
	}

	public DefaultProject getProject() {
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		return explorer.getProject(PROJECT_NAME);
	}

	public ContextMenu getContextMenuForProject() {
		getProject().select();
		return new ContextMenu();
	}

	public ContextMenu getContextMenuForProjectsPom() {
		getProject().getProjectItem("pom.xml").select();
		return new ContextMenu();
	}

	@Test
	public void existsForMavenProjectTest() {
		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is present for project '" + PROJECT_NAME + "'");
		ContextMenu contextMenu = getContextMenuForProject();
		assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing", contextMenu.getItems().stream()
				.filter(p -> p.getText().matches(CONTEXT_MENU_ITEM_TEXT)).findAny().isPresent());
	}

	@Test
	public void enabledForMavenProjectTest() {
		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is enabled for project '" + PROJECT_NAME + "'");
		ContextMenu contextMenu = getContextMenuForProject();
		assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing",
				contextMenu.getItem(CONTEXT_MENU_ITEM_TEXT).isEnabled());
	}

	@Test
	public void existsForMavenProjectPomTest() {
		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is present for root pom.xml file in project '"
				+ PROJECT_NAME + "'");
		ContextMenu contextMenu = getContextMenuForProjectsPom();
		assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing", contextMenu.getItems().stream()
				.filter(p -> p.getText().matches(CONTEXT_MENU_ITEM_TEXT)).findAny().isPresent());
	}

	@Test
	public void enabledForMavenProjectPomTest() {
		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is enabled for root pom.xml file in project '"
				+ PROJECT_NAME + "'");
		ContextMenu contextMenu = getContextMenuForProjectsPom();
		assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing",
				contextMenu.getItem(CONTEXT_MENU_ITEM_TEXT).isEnabled());
	}

}