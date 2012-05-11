/* {{{ header
 * :tabSize=4:indentSize=4:noTabs=false:folding=explicit:collapseFolds=1:
 *
 * SwitchBufferOptionPane.java - Option Pane for the SwitchBuffer plugin
 * Copyright (C) 2003 Lee Turner
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
* }}}
 */
package switchbuffer;

//{{{ imports
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import org.gjt.sp.jedit.AbstractOptionPane;
import org.gjt.sp.jedit.jEdit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
//}}}

/**
 * Defines what to display in the <a href="http://www.jedit.org/">jEdit</a>
 * Global options dialog
 *
 * @author    <a href="mailto:lee@leeturner.org">Lee Turner</a>
 * @version   $Revision: 1.11 $ $Date: 2003/10/28 09:39:38 $
 */
public class SwitchBufferOptionPane extends AbstractOptionPane
{
	//{{{ instance variables
	private JCheckBox onLostFocus;
	private ButtonGroup filenameMatching;
	private JRadioButton filenameMatchingBeginning;
	private JRadioButton filenameMatchingAnywhere;
	private JRadioButton filenameMatchingSubSequence;
	private JCheckBox showBufferIcons;
	private JCheckBox showBufferColours;
	private JCheckBox showDirectories;
	private JCheckBox showDirectoriesIntelligently;
	private JCheckBox ignoreCase;
	private JCheckBox removeActiveFromList;
	private JCheckBox rememberPrevious;
	private JCheckBox rememberPreviousFromAnywhere;
	private JCheckBox rememberTextEntered;
	//}}}

	//{{{ +SwitchBufferOptionPane() : <init>
	/**
	 * Constructor for the SwitchBufferOptionPane object
	 */
	public SwitchBufferOptionPane()
	{
		super("switchbuffer");
	} //}}}

	//{{{ +_init() : void
	/**
	 * Initialises the option pane.
	 */
	public void _init()
	{
		// set up the general options.
		onLostFocus = new JCheckBox(jEdit.getProperty("switchbuffer.options.general.lost-focus.title"), jEdit.getBooleanProperty("switchbuffer.options.general.lost-focus"));

		// add all the filename matching options.
		addSeparator("switchbuffer.options.general.title");
		addComponent(onLostFocus);

		// set up the filename matching options.
		filenameMatchingBeginning = new JRadioButton(jEdit.getProperty("switchbuffer.options.filenameMatchingBeginning.title"));
		filenameMatchingBeginning.setSelected("BEGINNING".equals(jEdit.getProperty("switchbuffer.options.filenameMatching")));
		filenameMatchingAnywhere = new JRadioButton(jEdit.getProperty("switchbuffer.options.filenameMatchingAnywhere.title"));
		filenameMatchingAnywhere.setSelected("ANYWHERE".equals(jEdit.getProperty("switchbuffer.options.filenameMatching")));
		filenameMatchingSubSequence = new JRadioButton(jEdit.getProperty("switchbuffer.options.filenameMatchingSubSequence.title"));
		filenameMatchingSubSequence.setSelected("SUBSEQUENCE".equals(jEdit.getProperty("switchbuffer.options.filenameMatching")));
		ignoreCase = new JCheckBox(jEdit.getProperty("switchbuffer.options.ignore-case.title"), jEdit.getBooleanProperty("switchbuffer.options.ignore-case"));

		// add all the filename matching options.
		addSeparator("switchbuffer.options.filenameMatching.title");
		addComponent(filenameMatchingBeginning);
		addComponent(filenameMatchingAnywhere);
		addComponent(filenameMatchingSubSequence);
		addComponent(ignoreCase);

		// group the filename matching radios
		filenameMatching = new ButtonGroup();
		filenameMatching.add(filenameMatchingBeginning);
		filenameMatching.add(filenameMatchingAnywhere);
		filenameMatching.add(filenameMatchingSubSequence);

		// set up all the file list options.
		showBufferIcons = new JCheckBox(jEdit.getProperty("switchbuffer.options.show-buffer-icons.title"), jEdit.getBooleanProperty("switchbuffer.options.show-buffer-icons"));
		showBufferColours = new JCheckBox(jEdit.getProperty("switchbuffer.options.show-buffer-colours.title"), jEdit.getBooleanProperty("switchbuffer.options.show-buffer-colours"));
		showDirectories = new JCheckBox(jEdit.getProperty("switchbuffer.options.show-buffer-directories.title"), jEdit.getBooleanProperty("switchbuffer.options.show-buffer-directories"));
		showDirectories.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent event)
			{
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					showDirectoriesIntelligently.setEnabled(true);
					showDirectoriesIntelligently.setSelected(false);
				}
				else if(event.getStateChange() == ItemEvent.DESELECTED)
				{
					showDirectoriesIntelligently.setSelected(false);
					showDirectoriesIntelligently.setEnabled(false);
				}
			}
		});
		showDirectoriesIntelligently = new JCheckBox(jEdit.getProperty("switchbuffer.options.show-intelligent-buffer-directories.title"), jEdit.getBooleanProperty("switchbuffer.options.show-intelligent-buffer-directories"));
		removeActiveFromList =  new JCheckBox(jEdit.getProperty("switchbuffer.options.remove-active-buffer.title"), jEdit.getBooleanProperty("switchbuffer.options.remove-active-buffer"));
		// add all the file list options.
		addSeparator("switchbuffer.options.fileList.title");
		addComponent(showBufferIcons);
		addComponent(showBufferColours);
		addComponent(showDirectories);
		addComponent(showDirectoriesIntelligently);
		addComponent(removeActiveFromList);

		// set up all the previous file options
		rememberPrevious = new JCheckBox(jEdit.getProperty("switchbuffer.options.remember-previous-buffer.title"), jEdit.getBooleanProperty("switchbuffer.options.remember-previous-buffer"));
		rememberPrevious.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent event)
			{
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					rememberPreviousFromAnywhere.setEnabled(true);
					rememberTextEntered.setSelected(false);
				}
				else if(event.getStateChange() == ItemEvent.DESELECTED)
				{
					rememberPreviousFromAnywhere.setSelected(false);
					rememberPreviousFromAnywhere.setEnabled(false);
					rememberTextEntered.setEnabled(true);
				}
			}
		});
		rememberPreviousFromAnywhere = new JCheckBox(jEdit.getProperty("switchbuffer.options.remember-previous-buffer-from-anywhere.title"), jEdit.getBooleanProperty("switchbuffer.options.remember-previous-buffer-from-anywhere"));
		rememberPreviousFromAnywhere.setEnabled(jEdit.getBooleanProperty("switchbuffer.options.remember-previous-buffer"));
		rememberTextEntered = new JCheckBox(jEdit.getProperty("switchbuffer.options.remember-text-entered.title"), jEdit.getBooleanProperty("switchbuffer.options.remember-text-entered"));
		rememberTextEntered.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent event)
			{
				if(event.getStateChange() == ItemEvent.SELECTED)
				{
					rememberPrevious.setSelected(false);
				}
				else if(event.getStateChange() == ItemEvent.DESELECTED)
				{

				}
			}
		});

		// add all the previously open file options.
		addSeparator("switchbuffer.options.previousbuffers.title");
		addComponent(rememberPrevious);
		addComponent(rememberPreviousFromAnywhere);
		addComponent(rememberTextEntered);
	} //}}}

	//{{{ +_save() : void
	/**
	 * Saves the settings on the option pane.
	 */
	public void _save()
	{
		jEdit.setBooleanProperty("switchbuffer.options.general.lost-focus", onLostFocus.isSelected());
		if(filenameMatchingBeginning.isSelected())
		{
			jEdit.setProperty("switchbuffer.options.filenameMatching", "BEGINNING");
		}
		else if(filenameMatchingAnywhere.isSelected())
		{
			jEdit.setProperty("switchbuffer.options.filenameMatching", "ANYWHERE");
		}
		else if(filenameMatchingSubSequence.isSelected())
		{
			jEdit.setProperty("switchbuffer.options.filenameMatching", "SUBSEQUENCE");
		}
		else
		{
			jEdit.unsetProperty("switchbuffer.options.filenameMatching");
		}
		jEdit.setBooleanProperty("switchbuffer.options.ignore-case", ignoreCase.isSelected());
		jEdit.setBooleanProperty("switchbuffer.options.show-buffer-icons", showBufferIcons.isSelected());
		jEdit.setBooleanProperty("switchbuffer.options.show-buffer-colours", showBufferColours.isSelected());
		jEdit.setBooleanProperty("switchbuffer.options.show-buffer-directories", showDirectories.isSelected());
		jEdit.setBooleanProperty("switchbuffer.options.show-intelligent-buffer-directories", showDirectoriesIntelligently.isSelected());
		jEdit.setBooleanProperty("switchbuffer.options.remove-active-buffer", removeActiveFromList.isSelected());
		jEdit.setBooleanProperty("switchbuffer.options.remember-previous-buffer", rememberPrevious.isSelected());
		jEdit.setBooleanProperty("switchbuffer.options.remember-previous-buffer-from-anywhere", rememberPreviousFromAnywhere.isSelected());
		jEdit.setBooleanProperty("switchbuffer.options.remember-text-entered", rememberTextEntered.isSelected());
	} //}}}
}

