package com.naif.tools.dbffile;

import java.util.HashMap;

/**
 * Structure to store a table record from
 * the .DBF file.
 * 
 * @author Nelson Ivan Fernandez Suarez
 */
public final class DBFRecord {
	
	/** It indicates if the DBF table record is active or not. */
	private boolean active;
	
	/** All the values stored in the record. */
	private HashMap<String, Object> values;
	
	
	// CONSTRUCTORS //
	
	/** Default constructor. */
	public DBFRecord() {
		this.active = true;
		this.values = new HashMap<String, Object>();
	} // end : constructor
	
	
	// ______________________________________________________________//
	// *********************** PUBLIC METHODS ***********************//	

	/**
	 * This method will obtain the value contained in
	 * a specified property, later, it is necessary to
	 * apply a cast among one of the following types:
	 * String, Integer, Float, Boolean and Date.
	 * 
	 * @param fieldColumn The column property to retrieve the data value.
	 * @return An object with the field value.
	 */
	public Object getField(String fieldColumn) {
		return values.get(fieldColumn);
	} // end : getField Method
	
	
	// GETTERS AND SETTERS //
	
	/** @return If the record is active(true) or not(false). */
	public boolean isActive() {
		return active;
	}

	/** @param active The field name. */
	public void setActive(boolean active) {
		this.active = active;
	}

	/** @return A list with the values stored in the record. */
	public HashMap<String, Object> getValues() {
		return values;
	}
	
}