/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.update.internal.configurator;

import java.text.MessageFormat;
import java.util.*;

public class Messages {
	private static String bundleName = "org.eclipse.update.internal.configurator.messages"; //$NON-NLS-1$
	private static ResourceBundle bundle = ResourceBundle.getBundle(bundleName, Locale.getDefault());

	/**
	 * Lookup the message with the given ID in this catalog 
	 */
	public static String getString(String id) {
		return getString(id, (String[]) null);
	}
	/**
	 * Lookup the message with the given ID in this catalog and bind its
	 * substitution locations with the given string.
	 */
	public static String getString(String id, String binding) {
		return getString(id, new String[] { binding });
	}
	/**
	 * Lookup the message with the given ID in this catalog and bind its
	 * substitution locations with the given strings.
	 */
	public static String getString(String id, String binding1, String binding2) {
		return getString(id, new String[] { binding1, binding2 });
	}

	/**
	 * Lookup the message with the given ID in this catalog and bind its
	 * substitution locations with the given string values.
	 */
	public static String getString(String id, String[] bindings) {
		if (id == null)
			return "No message available"; //$NON-NLS-1$
		String message = null;
		try {
			message = bundle.getString(id);
		} catch (MissingResourceException e) {
			// If we got an exception looking for the message, fail gracefully by just returning
			// the id we were looking for.  In most cases this is semi-informative so is not too bad.
			return "Missing message: " + id + " in: " + bundleName; //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (bindings == null)
			return message;
		return MessageFormat.format(message, bindings);
	}
	/**
	 * Print a debug message to the console. If the given boolean is <code>true</code> then
	 * pre-pend the message with the current date.
	 */
	public static void debug(boolean includeDate, String message) {
		if (includeDate)
			message = new Date(System.currentTimeMillis()).toString() + " - " + message; //$NON-NLS-1$
		System.out.println(message);
	}
}