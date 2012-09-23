/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.formatter;

import com.dubture.pdt.formatter.internal.core.formatter.CodeFormatterConstants;
import com.dubture.pdt.formatter.internal.core.formatter.CodeFormatterOptions;

/**
 * 
 *
 * @see http://symfony.com/doc/2.0/contributing/code/standards.html
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyFormatterOptions extends CodeFormatterOptions {

	
	public static CodeFormatterOptions getDefaultSettings() {
		
		SymfonyFormatterOptions options = new SymfonyFormatterOptions();		
		options.setDefaultSettings();		
		return options;		
		
	}
	
	@Override
	public void setDefaultSettings()
	{

		super.setDefaultSettings();
		
		this.tab_char = SPACE;
		this.tab_size = 4;		
		
		this.brace_position_for_type_declaration = CodeFormatterConstants.NEXT_LINE;
		this.brace_position_for_method_declaration = CodeFormatterConstants.NEXT_LINE;		
		this.brace_position_for_constructor_declaration = CodeFormatterConstants.NEXT_LINE;
		this.insert_new_line_after_namespace_declaration = true;
		this.blank_lines_before_first_class_body_declaration = 1;
		
		
	}

}
