/**
 * $RCSfile: ,v $
 * $Revision: $
 * $Date: $
 * 
 * Copyright (C) 2004-2010 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jivesoftware.spellchecker;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.jivesoftware.spark.preference.Preference;

public class SpellcheckerPreference implements Preference
{
	public static String NAMESPACE = "spellchecking";
	private SpellcheckerPreferenceDialog dialog;
	private SpellcheckerPreferences preferences;

	public SpellcheckerPreference(ArrayList<String> languages) {
		dialog = new SpellcheckerPreferenceDialog(languages);
		preferences = new SpellcheckerPreferences();
	}
	
	public SpellcheckerPreferences getPreferences()
	{
		return preferences;
	}
	
	public void commit() {
		preferences.setAutoSpellCheckerEnabled(dialog.isAutoSpellCheckingEnabled());
		preferences.setSpellCheckerEnabled(dialog.isSpellCheckingEnabled());
		preferences.setSpellLanguage(dialog.getSelectedLanguage());
		SpellcheckManager.getInstance().loadDictionary(dialog.getSelectedLanguage());
		preferences.save();
	}

	public Object getData() {
		return preferences;
	}

	public String getErrorMessage() {
		return null;
	}

	public JComponent getGUI() {
		return dialog;
	}

	public Icon getIcon() {
		ClassLoader cl = getClass().getClassLoader();
		return new ImageIcon(cl.getResource("text_ok.png"));
	}

	public String getListName() {
		return SpellcheckerResource.getString("title.spellchecker");
	}

	public String getNamespace() {
		return NAMESPACE;
	}

	public String getTitle() {
		return SpellcheckerResource.getString("title.spellchecker");
	}

	public String getTooltip() {
		return SpellcheckerResource.getString("title.spellchecker");
	}

	public boolean isDataValid() {
		return true;
	}

	public void load() {	
		dialog.setAutoSpellCheckingEnabled(preferences.isAutoSpellCheckerEnabled());
		dialog.setSelectedLanguage(preferences.getSpellLanguage());
		dialog.setSpellCheckingEnabled(preferences.isSpellCheckerEnabled());
	}

	public void shutdown() {

	}
	
}
