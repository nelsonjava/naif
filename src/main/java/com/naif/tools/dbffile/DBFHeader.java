package com.naif.tools.dbffile;

import java.util.Date;

/**
 * Header segment of the .DBF file.
 * 
 * @author Nelson Ivan Fernandez Suarez
 */
public final class DBFHeader {
	
	/** File type identification. */
	private int 	fileID;
	
	/** File's last modification date. */
	private Date 	lastModification;
	
	/** Number of table entries in the file. */
	private int 	recordNum;
	
	/** Number of bytes in the header. */
	private int 	whereRecordsBegin;
	
	/** Number of bytes in the record. */
	private int 	oneRecordLenght;
	
	/** It shows if there are index components. */
	private int 	hasIndexComp;
	
	/** Page number of the table. */
	private int 	tablePageNum;
	
	
	// CONSTRUCTORS //

	/** Default constructor. */
	public DBFHeader() {} // end : constructor

	/**
	 * Constructor with parameters, to ease
	 * the creation process.
	 * 
	 * @param fileID File type ID.
	 * @param lastModification Last modification date.
	 * @param recordNum Number of table entries.
	 * @param whereRecordsBegin Number of bytes in the header.
	 * @param oneRecordLenght Number of bytes in the record.
	 * @param hasIndexComp Presence of index components.
	 * @param tablePageNum Table's page number.
	 */
	public DBFHeader(int fileID, 		   Date lastModification, 
			  		 int recordNum,	   	   int whereRecordsBegin, 
			  		 int oneRecordLenght,  int hasIndexComp,
			  		 int tablePageNum) {
		
		if (fileID < 0) {
			throw new NullPointerException("File type ID is negative: " + fileID);
		}
		if (lastModification == null) {
			throw new NullPointerException("Last modification date is null.");
		}
		if (recordNum <= 0) {
			throw new NullPointerException("Number of table entries is zero/negative: " + recordNum);
		}
		if (whereRecordsBegin < 0) {
			throw new NullPointerException("Number of bytes in the header is negative: " + whereRecordsBegin);
		}
		if (oneRecordLenght < 0) {
			throw new NullPointerException("Number of bytes in the record is negative: " + oneRecordLenght);
		}
		if (hasIndexComp < 0) {
			throw new NullPointerException("Presence of index components is negative: " + hasIndexComp);
		}
		if (tablePageNum < 0) {
			throw new NullPointerException("Table's page number is negative: " + tablePageNum);
		}
		
		this.fileID 			= fileID;
		this.lastModification 	= lastModification;
		this.recordNum 			= recordNum;
		this.whereRecordsBegin 	= whereRecordsBegin;
		this.oneRecordLenght 	= oneRecordLenght;
		this.hasIndexComp 		= hasIndexComp;
		this.tablePageNum 		= tablePageNum;
	} // end : constructor

	
	// GETTERS AND SETTERS //
	
	/** @return The file type ID. */
	public int getFileID() {
		return fileID;
	}

	/** @param fileID The file type ID. */
	public void setFileID(int fileID) {
		if (fileID < 0) {
			throw new NullPointerException("File type ID is negative: " + fileID);
		}
		this.fileID = fileID;
	}

	/** @return The file's last modification date. */
	public Date getLastModification() {
		return lastModification;
	}

	/** @param lastModification File's last modification date. */
	public void setLastModification(Date lastModification) {
		if (lastModification == null) {
			throw new NullPointerException("Last modification date is null.");
		}
		this.lastModification = lastModification;
	}

	/** @return The number of table entries. */
	public int getRecordNum() {
		return recordNum;
	}

	/** @param recordNum Number of table entries. */
	public void setRecordNum(int recordNum) {
		if (recordNum <= 0) {
			throw new NullPointerException("Number of table entries is zero/negative: " + recordNum);
		}
		this.recordNum = recordNum;
	}

	/** @return The number of bytes in the header. */
	public int getWhereRecordsBegin() {
		return whereRecordsBegin;
	}

	/** @param whereRecordsBegin Number of bytes in the header. */
	public void setWhereRecordsBegin(int whereRecordsBegin) {
		if (whereRecordsBegin < 0) {
			throw new NullPointerException("Number of bytes in the header is negative: " + whereRecordsBegin);
		}
		this.whereRecordsBegin = whereRecordsBegin;
	}

	/** @return The number of bytes in the record. */
	public int getOneRecordLenght() {
		return oneRecordLenght;
	}

	/** @param oneRecordLenght Number of bytes in the record. */
	public void setOneRecordLenght(int oneRecordLenght) {
		if (oneRecordLenght < 0) {
			throw new NullPointerException("Number of bytes in the record is negative: " + oneRecordLenght);
		}
		this.oneRecordLenght = oneRecordLenght;
	}

	/** @return Presence of index components. */
	public int getHasIndexComp() {
		return hasIndexComp;
	}

	/** @param hasIndexComp Presence of index components. */
	public void setHasIndexComp(int hasIndexComp) {
		if (hasIndexComp < 0) {
			throw new NullPointerException("Presence of index components is negative: " + hasIndexComp);
		}
		this.hasIndexComp = hasIndexComp;
	}

	/** @return The table's page number. */
	public int getTablePageNum() {
		return tablePageNum;
	}

	/** @param tablePageNum Table's page number. */
	public void setTablePageNum(int tablePageNum) {
		if (tablePageNum < 0) {
			throw new NullPointerException("Table's page number is negative: " + tablePageNum);
		}
		this.tablePageNum = tablePageNum;
	}	

}