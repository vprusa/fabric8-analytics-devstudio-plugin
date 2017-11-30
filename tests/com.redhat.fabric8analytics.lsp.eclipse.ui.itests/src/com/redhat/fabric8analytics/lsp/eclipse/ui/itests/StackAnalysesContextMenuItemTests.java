package com.redhat.fabric8analytics.lsp.eclipse.ui.itests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizardDialog;
import org.eclipse.reddeer.eclipse.ui.wizards.datatransfer.WizardProjectsImportPage;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;
import org.eclipse.reddeer.workbench.handler.WorkbenchShellHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;
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
			String canonicalPath = new File(path).getCanonicalPath();
			log.info("Canonical path to resoruce project: " + canonicalPath);
			importPage.setRootDirectory(canonicalPath);
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

	public ContextMenu getContextMenuFor(String path) {
		if (path.equals("/")) {
			getProject().select();

		} else {
			getProject().getProjectItem(path).select();
		}
		return new ContextMenu();
	}

	@Test
	public void existsForMavenProjectPomTest() {
		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is present for project '" + PROJECT_NAME + "'");
		ContextMenu contextMenu = getContextMenuFor("/");
		assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing", contextMenu.getItems().stream()
				.filter(p -> p.getText().matches(CONTEXT_MENU_ITEM_TEXT)).findAny().isPresent());
		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is enabled for project '" + PROJECT_NAME + "'");
		assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing",
				contextMenu.getItem(CONTEXT_MENU_ITEM_TEXT).isEnabled());

		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is present for root pom.xml file in project '"
				+ PROJECT_NAME + "'");
		contextMenu = getContextMenuFor("pom.xml");
		assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing", contextMenu.getItems().stream()
				.filter(p -> p.getText().matches(CONTEXT_MENU_ITEM_TEXT)).findAny().isPresent());
		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is enabled for root pom.xml file in project '"
				+ PROJECT_NAME + "'");
		assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing",
				contextMenu.getItem(CONTEXT_MENU_ITEM_TEXT).isEnabled());
	}

	@Test
	public void notExistsForMavenProjectSrcTest() {
		String[] paths = { "src" };
		log.info("Validating that " + CONTEXT_MENU_ITEM_TEXT + " is not present for " + paths.toString()
				+ " file in project '" + PROJECT_NAME + "'");
		Arrays.stream(paths).forEach(path -> {
			assertTrue("ContextMenu item '" + CONTEXT_MENU_ITEM_TEXT + "' is missing",
					!getContextMenuFor(path).getItems().stream()
							.filter(p -> p.getText().matches(CONTEXT_MENU_ITEM_TEXT)).findAny().isPresent());
		});
	}

}