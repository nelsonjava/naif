package com.naif.tools.dbf;

import com.naif.tools.dbffile.DBFFieldDescriptor;
import com.naif.tools.dbffile.DBFFile;
import com.naif.tools.dbffile.DBFReader;
import com.naif.tools.dbffile.DBFRecord;

import com.naif.tools.dbf.BeanUtils;

public class DbfUtils {

    public static void beanDbf(String sfile,String sClass) {

        DBFFile dbf = new DBFReader().readDBFFile(sfile);

        if (dbf == null){
           System.out.println( "No existen Registros" );
           return;
        }

        DBFRecord registro = dbf.getRecord("CCODIGOEST","214185");
        if (registro == null){
           System.out.println( "Registro no existe" );
           return;
        }

        if (!registro.isActive()){
           System.out.println( "Registro Eliminado" );
           return;
        }


        System.out.println("package com.naif.sima.dbfs;");
        System.out.println("");
        System.out.println("import java.util.*;");
        System.out.println("");
        System.out.println("import com.naif.tools.dbffile.DBFFieldDescriptor;");
        System.out.println("import com.naif.tools.dbffile.DBFFile;");
        System.out.println("import com.naif.tools.dbffile.DBFReader;");
        System.out.println("import com.naif.tools.dbffile.DBFRecord;");
        System.out.println("");
        System.out.println("public class "+sClass+" implements java.io.Serializable {");
        System.out.println("");

        for (DBFFieldDescriptor fd : dbf.getFieldDescs()) {

            switch(fd.getFieldType()) {
                case 'C':
                   System.out.println("    private String " + BeanUtils.attribute(fd.getField())); break;
                case 'N':
                   if (fd.getDecfieldLenght() == 0) {
                      System.out.println("    private int " + BeanUtils.attribute(fd.getField()));
                   } else {
                      System.out.println("    private float " + BeanUtils.attribute(fd.getField()));
                   }
                   break;
                case 'D':
                  System.out.println("    private Date " + BeanUtils.attribute(fd.getField())); break;
                case 'L':
                  System.out.println("    private boolean " + BeanUtils.attribute(fd.getField())); break;
                default: break;
            } // switch
        } // for

        System.out.println("");

        System.out.println("    private DBFFile dbf;");
        System.out.println("    private ArrayList<DBFRecord> registros;");
        System.out.println("    private DBFRecord registro;");

        System.out.println("");

        System.out.println("    public "+sClass+"(String sFile) {");
        System.out.println("        dbf = new DBFReader().readDBFFile(sFile);");
        System.out.println("        registros = dbf.getRecords();");
        System.out.println("    }");

        System.out.println("");

        for (DBFFieldDescriptor fd : dbf.getFieldDescs()) {
        switch(fd.getFieldType()) {
            case 'C':
                 System.out.println("public String get" + fd.getField() + "() {");
                 System.out.println("    return "+BeanUtils.attribute(fd.getField())+";");
                 System.out.println("}");
                 break;

            case 'N':
                if (fd.getDecfieldLenght() == 0) {

                   System.out.println("public int get" + fd.getField() + "() {");
                   System.out.println("    return "+BeanUtils.attribute(fd.getField())+";");
                   System.out.println("}");

                } else {

                   System.out.println("public float get" + fd.getField() + "() {");
                   System.out.println("    return "+BeanUtils.attribute(fd.getField())+";");
                   System.out.println("}");
                }
                break;

            case 'D':

                   System.out.println("public Date get" + fd.getField() + "() {");
                   System.out.println("    return "+BeanUtils.attribute(fd.getField())+";");
                   System.out.println("}");
                   break;

            case 'L':

                   System.out.println("public boolean get" + fd.getField() + "() {");
                   System.out.println("    return "+BeanUtils.attribute(fd.getField())+";");
                   System.out.println("}");
                   break;


            default: break;
        } // switch
        System.out.println("");
        } // for

        System.out.println("public ArrayList<DBFRecord> getRegistros() {");
        System.out.println("    return registros;");
        System.out.println("}");
        System.out.println("");

        System.out.println("public ArrayList<String> getIds(String id) {");
        System.out.println("");
        System.out.println("    ArrayList<String> ids = new ArrayList<String>();");
        System.out.println("");
        System.out.println("    for (DBFRecord registro : registros) {");
        System.out.println("        ids.add((String)registro.getField(id));");
        System.out.println("    }");
        System.out.println("");
        System.out.println("    return ids;");
        System.out.println("}");
        System.out.println("");

        System.out.println("public void seek(String cCodigoEst) {");
        System.out.println("    registro = dbf.getRecord(\"CCODIGOEST\",cCodigoEst);");
        System.out.println("");

        for (DBFFieldDescriptor fd : dbf.getFieldDescs()) {
        switch(fd.getFieldType()) {
            case 'C':
                 System.out.println("    this."+BeanUtils.attribute(fd.getField()));
                 System.out.println("= (String)registro.getField(\" "+fd.getField()+"\"");
                 break;

            case 'N':
                if (fd.getDecfieldLenght() == 0) {

                   System.out.println("    this."+BeanUtils.attribute(fd.getField()));
                   System.out.println("= ((Integer)registro.getField(\" "+fd.getField());
                   System.out.println("\")).intValue();");

                } else {

                   System.out.println("    this."+BeanUtils.attribute(fd.getField()));
                   System.out.println("= ((Float)registro.getField(\" "+fd.getField());
                   System.out.println("\")).floatValue();");
                }
                break;

            case 'D':

                   System.out.println("    this."+BeanUtils.attribute(fd.getField()));
                   System.out.println("= (Date)registro.getField(\" "+fd.getField()+"\"");

                   break;

            case 'L':

                   System.out.println("    this."+BeanUtils.attribute(fd.getField()));
                   System.out.println("= ((Boolean)registro.getField(\" "+fd.getField());
                   System.out.println("\")).booleanValue();");

                   break;

            default: break;
        } // switch
        System.out.println("");
        } // for

    System.out.println("}");

    } // beanDbf

}