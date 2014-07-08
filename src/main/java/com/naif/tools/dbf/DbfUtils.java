package com.naif.tools.dbf;

import com.naif.tools.dbffile.DBFFieldDescriptor;
import com.naif.tools.dbffile.DBFFile;
import com.naif.tools.dbffile.DBFReader;
import com.naif.tools.dbffile.DBFRecord;

import com.naif.tools.filetxt.FileTxt;

import com.naif.tools.dbf.BeanUtils;

public class DbfUtils {

    private static FileTxt filetxt;

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

    public static void beanDbfile(String sfile,String paquete,String sClass,String visibilidad,String destino ) {

        DBFFile dbf = new DBFReader().readDBFFile(sfile);

        StringBuilder javaSource = new StringBuilder();

        if (dbf == null){
           System.out.println( "No existen Registros" );
           return;
        }

        javaSource.append("package "+paquete+";");
        javaSource.append("\n");
        javaSource.append("\nimport java.util.*;");
        javaSource.append("\n");
        javaSource.append("\nimport com.naif.tools.dbffile.DBFFieldDescriptor;");
        javaSource.append("\nimport com.naif.tools.dbffile.DBFFile;");
        javaSource.append("\nimport com.naif.tools.dbffile.DBFReader;");
        javaSource.append("\nimport com.naif.tools.dbffile.DBFRecord;");
        javaSource.append("\n");

        javaSource.append("\npublic class "+sClass+" implements java.io.Serializable {");
        javaSource.append("\n");

        String campo = "";
        String space = "\n   ";


        for (DBFFieldDescriptor fd : dbf.getFieldDescs()) {

            campo = BeanUtils.attribute(fd.getField());

            switch(fd.getFieldType()) {
                case 'C':
                    javaSource.append(space+visibilidad+" String " + campo + ";"); break;
                case 'N':
                    if (fd.getDecfieldLenght() == 0) {
                        javaSource.append(space+visibilidad+" int " + campo + ";");
                    } else {
                        javaSource.append(space+visibilidad+" float " + campo + ";");
                    }
                    break;
                case 'D':
                    javaSource.append(space+visibilidad+" Date " + campo + ";"); break;
                case 'L':
                    javaSource.append(space+visibilidad+" boolean " + campo + ";"); break;
                default: break;
            } // switch

        } // for

        javaSource.append("\n");

        javaSource.append(space+"private DBFFile dbf;");
        javaSource.append(space+"private ArrayList<DBFRecord> registros;");
        javaSource.append(space+"private DBFRecord registro;");

        javaSource.append("\n");

        javaSource.append(space+"public "+sClass+"(String sFile) {");
        javaSource.append(space+"  dbf = new DBFReader().readDBFFile(sFile);");
        javaSource.append(space+"  registros = dbf.getRecords();");
        javaSource.append(space+"}");

        javaSource.append("\n");

        for (DBFFieldDescriptor fd : dbf.getFieldDescs()) {

            campo = fd.getField();

            switch(fd.getFieldType()) {
                case 'C':
                     javaSource.append(space+"public String get" + campo + "() {");
                     javaSource.append(space+"    return "+BeanUtils.attribute(campo)+";");
                     javaSource.append(space+"}");
                     break;

                case 'N':
                     if (fd.getDecfieldLenght() == 0) {

                         javaSource.append(space+"public int get" + campo + "() {");
                         javaSource.append(space+"    return "+BeanUtils.attribute(campo)+";");
                         javaSource.append(space+"}");

                     } else {

                         javaSource.append(space+"public float get" + campo + "() {");
                         javaSource.append(space+"    return "+BeanUtils.attribute(campo)+";");
                         javaSource.append(space+"}");
                     }
                     break;

                case 'D':

                     javaSource.append(space+"public Date get" + campo + "() {");
                     javaSource.append(space+"    return "+BeanUtils.attribute(campo)+";");
                     javaSource.append(space+"}");
                     break;

                case 'L':

                     javaSource.append(space+"public boolean get" + campo + "() {");
                     javaSource.append(space+"    return "+BeanUtils.attribute(campo)+";");
                     javaSource.append(space+"}");
                     break;


                default: break;
            } // switch

            javaSource.append("\n");

        } // for

        javaSource.append(space+"public ArrayList<DBFRecord> getRegistros() {");
        javaSource.append(space+"    return registros;");
        javaSource.append(space+"}");
        javaSource.append("\n");

        javaSource.append(space+"public ArrayList<String> getIds(String id) {");
        javaSource.append("\n");
        javaSource.append(space+"    ArrayList<String> ids = new ArrayList<String>();");
        javaSource.append("\n");
        javaSource.append(space+"    for (DBFRecord registro : registros) {");
        javaSource.append(space+"        ids.add((String)registro.getField(id));");
        javaSource.append(space+"    }");
        javaSource.append("\n");
        javaSource.append(space+"    return ids;");
        javaSource.append(space+"}");
        javaSource.append("\n");

        javaSource.append(space+"public void seek(String xCodigo,String cCodigo) {");
        javaSource.append(space+"    registro = dbf.getRecord(xCodigo,cCodigo);");
        javaSource.append(space+"    setRegistro();");
        javaSource.append(space+"}");
        javaSource.append("\n");

        javaSource.append(space+"public void setRegistro() {");
        for (DBFFieldDescriptor fd : dbf.getFieldDescs()) {

            campo = fd.getField();

            switch(fd.getFieldType()) {

                case 'C':
                     javaSource.append(space+"    this."+BeanUtils.attribute(campo));
                     javaSource.append(" = (String)registro.getField(\""+campo+"\");");
                     break;

                case 'N':
                     if (fd.getDecfieldLenght() == 0) {

                        javaSource.append(space+"    this."+BeanUtils.attribute(campo));
                        javaSource.append(" = ((Integer)registro.getField(\""+campo);
                        javaSource.append("\")).intValue();");

                     } else {

                        javaSource.append(space+"    this."+BeanUtils.attribute(campo));
                        javaSource.append(" = ((Float)registro.getField(\""+campo);
                        javaSource.append("\")).floatValue();");
                     }
                     break;

                case 'D':

                     javaSource.append(space+"    this."+BeanUtils.attribute(campo));
                     javaSource.append(" = (Date)registro.getField(\""+campo+"\");");

                     break;

                case 'L':

                     javaSource.append(space+"    this."+BeanUtils.attribute(campo));
                     javaSource.append(" = ((Boolean)registro.getField(\""+campo);
                     javaSource.append("\")).booleanValue();");

                     break;

                default: break;
            } // switch

        } // for

        javaSource.append(space+"}");

        javaSource.append("\n");

        javaSource.append(space+"public void viewRegistro(String xCodigo,String cCodigo) {");

        javaSource.append("\n");
        javaSource.append(space+"    seek(xCodigo,cCodigo);");
        javaSource.append("\n");

        javaSource.append(space+"    System.out.println(\"-----------------------Detalle-de Registro-----------------------\");");
        for (DBFFieldDescriptor fd : dbf.getFieldDescs()) {

            campo = fd.getField();

            javaSource.append(space+"    System.out.println(");
            javaSource.append("\""+campo+":\"+");
            javaSource.append("get"+campo+"());");


        } // for

        javaSource.append("\n");
        javaSource.append(space+"    System.out.println();");

        javaSource.append(space+"}");

        javaSource.append("\n");
        javaSource.append(space+"public void viewRegistros(String xCodigo) {");

        javaSource.append("\n");
        javaSource.append(space+"    ArrayList<String> codigos = null;");
        javaSource.append(space+"    codigos = getIds(xCodigo);");
        javaSource.append("\n");

        javaSource.append(space+"    for (String codigo : codigos) {");
        javaSource.append(space+"         viewRegistro(xCodigo,codigo);");
        javaSource.append(space+"    } // for");

        javaSource.append(space+"} // viewRegistros");

        javaSource.append("\n");
        javaSource.append("\n} // class ");

        filetxt = new FileTxt(destino, sClass+".java", javaSource.toString());
        filetxt = new FileTxt("c:/models.com.naif.sima.dbf", sClass+".java", javaSource.toString());

    } // beanDbfile


}