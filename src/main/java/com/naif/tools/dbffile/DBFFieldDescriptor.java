package com.naif.tools.dbffile;

/**
 * Structure to store a field description
 * in the .DBF file.
 * 
 * @author Nelson Ivan Fernandez Suarez
 */
public final class DBFFieldDescriptor {
	
	/** Field name. */
	private String 	field;
	
	/** Field type. */
	private char 	fieldType;
	
	/** Index where field begin(From the beginning) */
	private int 	whereFieldBegin;
	
	/** Field value length. */
	private int 	fieldLenght;
	
	/** In case of decimal number, the decimal digit number. */
	private int 	decfieldLenght;	
	
	
	// CONSTRUCTORS //
	
	/** Default constructor. */
	public DBFFieldDescriptor() {} // end : constructor

	/**
	 * Constructor with parameters, to ease
	 * the creation process.
	 * 
	 * @param field Field name.
	 * @param fieldType Field type.
	 * @param whereFieldBegin Index where field begin.
	 * @param fieldLenght Field value length.
	 * @param decfieldLenght Number of decimals used.
	 */
	public DBFFieldDescriptor(String field, char fieldType,
					   		  int whereFieldBegin, int fieldLenght, int decfieldLenght) {
		if (field == null || field.isEmpty()) {
			throw new NullPointerException("Field name is null/empty.");
		}
		if (fieldType == '\u0000') {
			throw new IllegalArgumentException("Field type is not valid: " + fieldType);
		}
		if (whereFieldBegin <= 0) {
			throw new IllegalArgumentException("Index where field begin is zero/negative: " + whereFieldBegin);
		}
		if (fieldLenght <= 0) {
			throw new IllegalArgumentException("Field value length is zero/negative: " + fieldLenght);
		}
		if (decfieldLenght < 0) {
			throw new IllegalArgumentException("Number of decimals used are negative: " + decfieldLenght);
		}
		
		this.field 				= field;
		this.fieldType 			= fieldType;
		this.whereFieldBegin 	= whereFieldBegin;
		this.fieldLenght 		= fieldLenght;
		this.decfieldLenght 	= decfieldLenght;
	} // end : constructor

	
	// GETTERS AND SETTERS //
	
	/** @return The field name. */
	public String getField() {
		return field;
	}

	/** @param field The field name. */
	public void setField(String field) {
		if (field == null || field.isEmpty()) {
			throw new NullPointerException("Field name is null/empty.");
		}
		this.field = field;
	}

	/** @return The field type. */
	public char getFieldType() {
		return fieldType;
	}

	/** @param fieldType The field type. */
	public void setFieldType(char fieldType) {
		if (fieldType == '\u0000') {
			throw new IllegalArgumentException("Field type is not valid: " + fieldType);
		}
		this.fieldType = fieldType;
	}

	/** @return The index where field begin. */
	public int getWhereFieldBegin() {
		return whereFieldBegin;
	}

	/** @param whereFieldBegin Index where field begin. */
	public void setWhereFieldBegin(int whereFieldBegin) {
		if (whereFieldBegin <= 0) {
			throw new IllegalArgumentException("Index where field begin is zero/negative: " + whereFieldBegin);
		}
		this.whereFieldBegin = whereFieldBegin;
	}

	/** @return The field value length. */
	public int getFieldLenght() {
		return fieldLenght;
	}

	/** @param fieldLenght Field value length. */
	public void setFieldLenght(int fieldLenght) {
		if (fieldLenght <= 0) {
			throw new IllegalArgumentException("Field value length is zero/negative: " + fieldLenght);
		}
		this.fieldLenght = fieldLenght;
	}

	/** @return The number of decimal digits. */
	public int getDecfieldLenght() {
		return decfieldLenght;
	}

	/** @param decfieldLenght Number of decimal digits. */
	public void setDecfieldLenght(int decfieldLenght) {
		if (decfieldLenght < 0) {
			throw new IllegalArgumentException("Number of decimals used are negative: " + decfieldLenght);
		}
		this.decfieldLenght = decfieldLenght;
	}

}