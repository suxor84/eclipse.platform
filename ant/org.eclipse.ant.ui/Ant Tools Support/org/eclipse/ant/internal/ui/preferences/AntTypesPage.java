/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ant.internal.ui.preferences;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.ant.core.AntCorePlugin;
import org.eclipse.ant.core.AntCorePreferences;
import org.eclipse.ant.core.Type;
import org.eclipse.ant.internal.ui.IAntUIHelpContextIds;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * Sub-page that allows the user to enter custom types
 * to be used when running Ant build files.
 */
public class AntTypesPage extends AntPage {
    
	/**
	 * Creates an instance.
	 */
	public AntTypesPage(AntRuntimePreferencePage preferencePage) {
		super(preferencePage);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ant.internal.ui.preferences.AntPage#addButtonsToButtonGroup(org.eclipse.swt.widgets.Composite)
	 */
	protected void addButtonsToButtonGroup(Composite parent) {
		createPushButton(parent, AntPreferencesMessages.getString("AntTypesPage.2"), ADD_BUTTON); //$NON-NLS-1$
		editButton = createPushButton(parent, AntPreferencesMessages.getString("AntTypesPage.3"), EDIT_BUTTON); //$NON-NLS-1$
		removeButton = createPushButton(parent, AntPreferencesMessages.getString("AntTypesPage.1"), REMOVE_BUTTON); //$NON-NLS-1$
	}
	
	/**
	 * Allows the user to enter a custom type.
	 */
	protected void add() {
		String title = AntPreferencesMessages.getString("AntTypesPage.addTypeDialogTitle"); //$NON-NLS-1$
		AddCustomDialog dialog = getCustomDialog(title, IAntUIHelpContextIds.ADD_TYPE_DIALOG);
		if (dialog.open() == Window.CANCEL) {
			return;
		}

		Type type = new Type();
		type.setTypeName(dialog.getName());
		type.setClassName(dialog.getClassName());
		type.setLibraryEntry(dialog.getLibraryEntry());
		addContent(type);
	}
	
	/**
	 * Creates the tab item that contains this sub-page.
	 */
	protected TabItem createTabItem(TabFolder folder) {
		TabItem item = new TabItem(folder, SWT.NONE);
		item.setText(AntPreferencesMessages.getString("AntTypesPage.typesPageTitle")); //$NON-NLS-1$
		item.setImage(AntObjectLabelProvider.getTypeImage());
		item.setData(this);
		Composite top = new Composite(folder, SWT.NONE);
		top.setFont(folder.getFont());			
		item.setControl(createContents(top));
		
		connectToFolder(item, folder);
				
		return item;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ant.internal.ui.preferences.AntPage#edit(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	protected void edit(IStructuredSelection selection) {
		Type type = (Type) selection.getFirstElement();
		String title = AntPreferencesMessages.getString("AntTypesPage.editTypeDialogTitle"); //$NON-NLS-1$
		AddCustomDialog dialog = getCustomDialog(title, IAntUIHelpContextIds.EDIT_TYPE_DIALOG);
		dialog.setClassName(type.getClassName());
		dialog.setName(type.getTypeName());
		dialog.setLibraryEntry(type.getLibraryEntry());
		if (dialog.open() == Window.CANCEL) {
			return;
		}

		type.setTypeName(dialog.getName());
		type.setClassName(dialog.getClassName());
		type.setLibraryEntry(dialog.getLibraryEntry());
		updateContent(type);
	}
	
	private AddCustomDialog getCustomDialog(String title, String helpContext) {
		Iterator types= getContents(true).iterator();
		List names= new ArrayList();
		while (types.hasNext()) {
			Type aTask = (Type) types.next();
			names.add(aTask.getTypeName());
		}
		
		AddCustomDialog dialog = new AddCustomDialog(getShell(), getPreferencePage().getLibraryEntries(), names, helpContext);
		dialog.setTitle(title);
		dialog.setAlreadyExistsErrorMsg(AntPreferencesMessages.getString("AntTypesPage.8")); //$NON-NLS-1$
		dialog.setNoNameErrorMsg(AntPreferencesMessages.getString("AntTypesPage.9")); //$NON-NLS-1$
		return dialog;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ant.internal.ui.preferences.AntPage#initialize()
	 */
	protected void initialize() {
		AntCorePreferences prefs= AntCorePlugin.getPlugin().getPreferences();
		setInput(prefs.getTypes());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ant.internal.ui.preferences.AntPage#getHelpContextId()
	 */
	protected String getHelpContextId() {
		return IAntUIHelpContextIds.ANT_TYPES_PAGE;
	}
}