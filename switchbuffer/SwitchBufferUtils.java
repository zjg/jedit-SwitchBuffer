/* {{{ header
 * :tabSize=4:indentSize=4:noTabs=false:folding=explicit:collapseFolds=1:
 *
 * SwitchBufferUtils.java - Utility methods for SwitchBuffer
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
import gnu.regexp.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.gjt.sp.jedit.*;
import org.gjt.sp.util.*;
import org.gjt.sp.jedit.io.*;
//}}}

/**
 * Utility methods for the SwitchBuffer plugin
 *
 * @author    <a href="mailto:lee@leeturner.org">Lee Turner</a>
 * @version   $Revision: 1.5 $ $Date: 2003/10/28 09:39:38 $
 */
public class SwitchBufferUtils
{
	//{{{ static fields
	//private static Vector colors;
	public static Hashtable name2color;
	//private static Object LOCK = new Object();
	private static Color colNormal = UIManager.getColor("List.textForeground");
	//}}}

	//{{{ +findCommonRoot(Vector) : String
	/**
	 * Takes a Vector of jEdit buffers and finds the common root
	 * ie common directory of all of them.
	 *
	 * @param buffers  The vector of jEdit Buffers
	 * @return         The common root or null if there is no common root.
	 */
	public static String findCommonRoot(Vector buffers)
	{
		String separator = File.separator;
		String commonRoot = null;
		StringBuffer tmpPath = null;

		for(int i = 0; i < buffers.size(); i++)
		{
			if(i == 0)
			{//first buffer in vector...

				commonRoot = ((Buffer)buffers.get(i)).getDirectory();
			}
			else
			{
				if(!(commonRoot == null))
				{// if null and not first then no match and we don't need to continue

					String currentPath = ((Buffer)buffers.get(i)).getDirectory();

					int commonBaseIndex = 0;
					int commonSeparatorIndex = 0;
					int thisBaseIndex = 0;
					int thisSeparatorIndex = 0;
					boolean match = true;
					tmpPath = new StringBuffer();
					do
					{
						commonSeparatorIndex = commonRoot.indexOf(separator, commonBaseIndex);
						thisSeparatorIndex = currentPath.indexOf(separator, thisBaseIndex);
						if((commonSeparatorIndex != -1) && (thisSeparatorIndex != -1))
						{
							if(commonRoot.substring(commonBaseIndex, commonSeparatorIndex).equals(currentPath.substring(thisBaseIndex, thisSeparatorIndex)))
							{
								tmpPath.append(currentPath.substring(thisBaseIndex, thisSeparatorIndex) + separator);
								commonBaseIndex = commonSeparatorIndex + 1;
								thisBaseIndex = thisSeparatorIndex + 1;
							}
							else
							{
								match = false;
							}
						}
						else
						{
							match = false;
						}
					}while (match == true);
				}

				if(tmpPath.length() == 0)
				{
					commonRoot = null;
				}
				else
				{
					commonRoot = tmpPath.toString();
				}
			}
		}
		return commonRoot;
	}
	//}}}

	//{{{ +subSequenceMatch(String, String) : boolean
	/**
	 * Provides an implementation of a subsequence match:
	 * The empty string is a subsequence match of any string.
	 * The string aX where a is a character and X is a string is a subsequence
	 * match of Y if a occurs in Y, and the substring of Y after the first
	 * occurrence of a is a subsequence match of X.
	 *
	 * @param toMatch  the string we are checking.
	 * @param text     the text we are checking against.
	 * @return         true if toMatch is a subsequence match of text, false if not.
	 */
	public static boolean subSequenceMatch(String toMatch, String text)
	{
		boolean match = false;

		if(toMatch.length() == 0)
		{
			match = true;
		}
		else
		{
			String firstChar = toMatch.substring(0, 1);// get the first char.
			String rest = toMatch.substring(1);// get the rest...

			int index = text.indexOf(firstChar);
			if(index == -1)
			{
				match = false;
			}
			else
			{
				match = subSequenceMatch(text.substring(index + 1), rest);
			}
		}
		return match;
	}//}}}

	//{{{ +getColour(String) : Color
	/**
	 * Gets the colour for a given buffer name as defined in the
	 * jEdit file system browser properties
	 *
	 * @param name  The buffer name.
	 * @return      The colour associated to the buffer name.
	 */
	public static Color getColour(String name)
	{
		Color col = null;
		if(name2color == null)
		{
			name2color = new Hashtable();
		}

		col = (Color)name2color.get(name);
		if(col != null)
		{
			return col;
		}
		else
		{
			col = VFS.getDefaultColorFor(name);
			if(col != null)
			{
				name2color.put(name, col);
				return col;
			}
			else
			{
				return colNormal;
			}
		}
	}//}}}

	//{{{ +clearColourCache() : void
	public static void clearColourCache()
	{
		name2color = null;
	} //}}}

}

