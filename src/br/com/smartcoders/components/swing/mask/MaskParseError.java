package br.com.smartcoders.components.swing.mask;

import java.text.ParseException;

@SuppressWarnings("serial")
public class MaskParseError extends RuntimeException {

	public MaskParseError(ParseException e) {
		super(e);
	}

}
