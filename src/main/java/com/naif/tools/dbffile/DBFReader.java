package com.naif.tools.dbffile;

import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.GregorianCalendar;

import javax.imageio.stream.FileImageOutputStream;

/**
 * Utility to read a DBF File, and produce
 * an object representation of all the
 * content.
 * 
 * @author Nelson Ivan Fernandez Suarez
 */
public final class DBFReader {
	
	/** DBF File object to build. */
	private DBFFile 				dbfFile;
	
	/** Tool for reading/writing the file. */
	private FileImageOutputStream 	fiis;
	
	/**
	 * Method to organize the reading file process.
	 * 
	 * @throws IOException When some exception occur in the process.
	 */
	private DBFFile read() throws IOException {
		
		boolean moreFieldDesc;
		dbfFile = new DBFFile();
		
		readDBFHeader();
		
		do {
			moreFieldDesc = readDBFFieldDesc();
		} while(moreFieldDesc);
		
		fiis.readByte(); // FIELD DESCRIPTOR END
		fiis.readByte(); // EMPTY BYTE
		
		int recordsNumber = dbfFile.getHeader().getRecordNum();
		
		for (int nRecord = 0; nRecord < recordsNumber; nRecord++) {
			readDBFFieldsRecords();
		}
		
		return dbfFile;
	} // end : read Method
	
	/**
	 * Method for reading the DBF Header.
	 * 
	 * @throws IOException When some exception occur in the process.
	 */
	private void readDBFHeader() throws IOException {

		DBFHeader header = dbfFile.getHeader();
		
		header.setFileID(fiis.readUnsignedByte());

		// A , M , D
		int year 	= 2000 + fiis.readUnsignedByte(); // ?? Year 
		int month 	= fiis.readUnsignedByte();
		int day 	= fiis.readUnsignedByte(); 
											// In Calendar, January is month zero //
		header.setLastModification(new GregorianCalendar(year, month - 1, day).getTime());
		header.setRecordNum(fiis.readInt());
		header.setWhereRecordsBegin(fiis.readShort());  
		header.setOneRecordLenght(fiis.readShort());
		fiis.readFully(new byte[16]); // Not used 13:28 : 16 bytes 
		header.setHasIndexComp(fiis.readByte()); 
		header.setTablePageNum(fiis.readByte()); 
        fiis.readFully(new byte[2]);  // Not used 31:32 : 2 bytes 
	} // end : readDBFHeader Method	
	
	/**
	 * Method for reading a field descriptor contained
	 * in the .DBF file.
	 * 
	 * @throws IOException When some exception occur in the process.
	 */
	private boolean readDBFFieldDesc() throws IOException {
		
		DBFFieldDescriptor fieldDesc = new DBFFieldDescriptor();
		
		// fieldName
		byte[] fieldName = new byte[11];
		fiis.readFully(fieldName); 
		fieldDesc.setField(new String(fieldName)); // FIXME .trim()

		// 13 = FIELD DESCRIPTOR END
		if (fieldName[0] == 13) { 
			fiis.seek(fiis.getStreamPosition() - 11); 
			return false; // Come back to field values index
		} 

		int fieldTypeCode = fiis.readUnsignedByte();
		fieldDesc.setWhereFieldBegin(fiis.readShort());
        fiis.readFully(new byte[2]); // Not used 15:16 : 2 bytes 
        
		switch (fieldTypeCode) {
            case 67: // CHARACTER
            	fieldDesc.setFieldType('C');
            	fieldDesc.setFieldLenght(fiis.readUnsignedShort());
                break;
            case 68: // DATE
            	fieldDesc.setFieldType('D');
            	fieldDesc.setFieldLenght(fiis.readUnsignedByte());
            	fiis.readByte(); // 18 : Not needed 
                break;
            case 76: // LOGICAL
            	fieldDesc.setFieldType('L');
            	fieldDesc.setFieldLenght(fiis.readUnsignedByte());
            	fiis.readByte(); // 18 : Not needed 
                break;                
            case 78: // NUMERIC
            	fieldDesc.setFieldType('N');
            	fieldDesc.setFieldLenght(fiis.readUnsignedByte());
            	
            	int _n18 = fiis.readUnsignedByte();
            	if (_n18 > 0) {
            		fieldDesc.setDecfieldLenght(_n18);
            	}

                break;
            default: throw new IllegalArgumentException("Field type code(dec) " + fieldTypeCode + " not supported yet.");
        }		
		
        fiis.readFully(new byte[14]); // Not used 19:32 : 14 bytes
        
        dbfFile.getFieldDescs().add(fieldDesc);
        
        return true;
	} // end : readDBFFieldDesc Method
	
	/**
	 * Method for reading a record table contained
	 * in the .DBF file.
	 * 
	 * @throws IOException When some exception occur in the process.
	 */
	private void readDBFFieldsRecords() throws IOException {
		
		DBFRecord recordFromTable = new DBFRecord();
		
		if(fiis.readUnsignedByte() == 32) {
			recordFromTable.setActive(true);
		} else { // NOT ACTIVE
			recordFromTable.setActive(false);
		}
		
		for (DBFFieldDescriptor column : dbfFile.getFieldDescs()) {
			
			byte[] cell = new byte[column.getFieldLenght()];
			fiis.readFully(cell);
			
			String key 		= column.getField().trim();
			String value 	= new String(cell).trim();

			switch (column.getFieldType()) {
            	case 'C': // CHARACTER
            		recordFromTable.getValues().put(key, value);
            		break;
            	case 'D': // DATE
            		if (value.isEmpty()) {
            			recordFromTable.getValues().put(key, value);
            			break;
            		} 
            		
            		int year 	= Integer.parseInt(value.substring(0,4)); 
            		int month 	= Integer.parseInt(value.substring(4,6));
            		int day 	= Integer.parseInt(value.substring(6,8));

            		recordFromTable.getValues().put(key, new GregorianCalendar(year, month - 1, day).getTime());
            		break;
            	case 'L': // LOGICAL
            		if (value.equalsIgnoreCase("T")) {
            			recordFromTable.getValues().put(key, new Boolean(true));
            		}
            		if (value.equalsIgnoreCase("F")) {
            			recordFromTable.getValues().put(key, new Boolean(false));
            		}
            		
            		break;                
            	case 'N': // NUMERIC
            		if (column.getDecfieldLenght() == 0) {
            			recordFromTable.getValues().put(key, Integer.parseInt(value));
            		} else {
            			recordFromTable.getValues().put(key, Float.parseFloat(value));
            		}
            		
            		break;
            	default: throw new IllegalArgumentException("Field type stored " + column.getFieldType() + " is not supported yet.");
			} // end : switch
			 
		} 
		
		dbfFile.getRecords().add(recordFromTable);
	} // end : readDBFFieldRecord Method
	
	/**
	 * It will read the DBF file from the specified
	 * path in the system.
	 * 
	 * @param pathToFile Path to file.
	 * @return DBFFile object with all the data.
	 */
	public DBFFile readDBFFile(String pathToFile) {
		
		dbfFile = null;
		
		// String basic validation
		if (pathToFile == null || pathToFile.isEmpty()) {
			throw new NullPointerException("Path to file is null/empty.");
		}
		
		// Format(DAT/DBF) validation
        int 	dot = pathToFile.lastIndexOf(".");
        String 	ext = pathToFile.substring(dot + 1 , pathToFile.length());
        
        if( !(ext.equalsIgnoreCase("dat") || ext.equalsIgnoreCase("dbf")) ) {
        	throw new IllegalArgumentException("The file extension is not .dat or .dbf.");
        }
       
		try {       
            fiis = new FileImageOutputStream(new File(pathToFile));
            fiis.setByteOrder(ByteOrder.LITTLE_ENDIAN); // LSB

            dbfFile = read();

            fiis.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
		
		return dbfFile;
	} // end : readDBFFile Method

}