package br.com.smartcoders.components.swing.mask;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class MaskFactory {
	
	public static MaskFormatter createMask(String maskPattern) {
		try {
			return new MaskFormatter(maskPattern);
		} catch (ParseException e) {
			throw new MaskParseError(e);
		}
	}
	
}
