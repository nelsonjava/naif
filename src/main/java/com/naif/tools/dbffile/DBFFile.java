package com.naif.tools.dbffile;

import java.util.ArrayList;

/**
 * This class will be the container for a
 * .DBF file, designed for legacy system
 * database.
 * 
 * @author Nelson Ivan Fernandez Suarez
 */
public final class DBFFile {
	
	/** DBF File header. */
	private DBFHeader header;
	
	/** DBF File fields descriptions. */
	private ArrayList<DBFFieldDescriptor> 	fieldDescs;
	
	/** All the table records in the DBF File. */
	private ArrayList<DBFRecord> 			records;
	
	
	// CONSTRUCTORS //

	/** Default constructor. */
	public DBFFile() {
		this.header 		= new DBFHeader();
		this.fieldDescs 	= new ArrayList<DBFFieldDescriptor>();
		this.records 		= new ArrayList<DBFRecord>();
	} // end : constructor
	
	
	// ______________________________________________________________//
	// *********************** PUBLIC METHODS ***********************//	

	/** @see DBFFile#getRecordProcess(String, T) */
	public DBFRecord getRecord(String fieldColumn, String value) { 
		return getRecordProcess(fieldColumn, value);
	} // end : getRecord(String,String) Method
	
	/** @see DBFFile#getRecordProcess(String, T) */
	public DBFRecord getRecord(String fieldColumn, Integer value) {
		return getRecordProcess(fieldColumn, value);
	} // end : getRecord(String,Integer) Method	
	
	/** @see DBFFile#getRecordProcess(String, T) */
	public DBFRecord getRecord(String fieldColumn, Float value) {
		return getRecordProcess(fieldColumn, value);
	} // end : getRecord(String,Float) Method	
	
	/**
	 * It will retrieve a record(row) from the .DBF file
	 * according to the first occurrence found from the
	 * specified parameters.
	 * 
	 * @param fieldColumn The column property related with the data.
	 * @param value The value to be contained in the returned record.
	 * @return A registry from the record table from the .DBF file; if nothing found, null.
	 */	
	private <T> DBFRecord getRecordProcess(String fieldColumn, T value) {
		
		DBFRecord recordFound = null;
		
		if (fieldColumn == null || fieldColumn.isEmpty()) {
			throw new IllegalArgumentException("Field column for the search is null/empty.");
		}
		if (value == null) {
			throw new IllegalArgumentException("Value for the search is null.");
		}
		if (records.isEmpty()) {
			throw new IllegalArgumentException("Search not possible, there are not records.");
		}

		// Search the record inside the file
		for (DBFRecord record : records) {
			
			T fieldValueReaded = (T)record.getField(fieldColumn);
			
			if (fieldValueReaded != null 
			 && fieldValueReaded.equals(value)) {
				recordFound = record;
				break;
			}
		}
		
		return recordFound;
	} // end : getRecordProcess(String,T) Method
	
	
	// GETTERS AND SETTERS //

	/** @return The header representation of the .DBF file. */
	public DBFHeader getHeader() {
		return header;
	}

	/** @return Information of the field descriptions in the .DBF file. */
	public ArrayList<DBFFieldDescriptor> getFieldDescs() {
		return fieldDescs;
	}	
	
	/** @return Information rows of the records in the .DBF file. */
	public ArrayList<DBFRecord> getRecords() {
		return records;
	}	

}