
import java.util.HashMap;


public class QueryCreator {

    //without primary key
    public static String queryCreate(String tableName , HashMap<String, String> hashMap){
        String colData="";
        String beforeCreate = "CREATE table "+tableName+"(";
        for(String columnName : hashMap.keySet()){
            colData += columnName+" "+hashMap.get(columnName) + ",";
        }
        colData = colData.substring(0, colData.length() -1);
        String query = beforeCreate+colData+");";
        return query;
    }

    //with primary key
    public static String queryCreate(String tableName , HashMap<String, String> hashMap, String pimarykey){
        String colData="";
        String beforeCreate = "CREATE table "+tableName+"("+pimarykey+" PRIMARY KEY,";
        for(String columnName : hashMap.keySet()){
            colData += columnName+" "+hashMap.get(columnName) + ",";
        }
        colData = colData.substring(0, colData.length() -1);
        String query = beforeCreate+colData+");";
        return query;
    }
    //Insert values
    public static String queryInsert(String tableName , HashMap<String,String> recordData, String[] colDataType){
        String colData="";
        String beforeInsert = "INSERT INTO "+tableName+"(";
        for(String columnName : recordData.keySet()){
            colData += columnName+" ,";
        }
        //remove last comma
        colData = colData.substring(0, colData.length() -1);
        colData = beforeInsert + colData+ ") values(";
        String handledData = dataTypeHandler(colDataType,recordData);
        colData +=handledData;
        colData = colData.substring(0, colData.length() -1);
        String query = colData+");";
        return query;
    }

    //update table
    public static String queryUpdate(String tableName, HashMap<String, String> colValue, HashMap<String, String> condition, String[] colDataType, String[] colDataTypeBWhere){
        String colData="";
        String beforeCreate = "update "+tableName+" ";
        for(String columnName : colValue.keySet()){
            colData += columnName+"="+dataTypeHandler(colDataTypeBWhere,colValue) + ",";
        }
        colData = colData.substring(0, colData.length() -1)+" where ";
        //
        for(String columnName : condition.keySet()){
            colData += columnName+"="+dataTypeHandler(colDataType,condition);
        }
        colData = colData.substring(0, colData.length() -1);
        String query = beforeCreate+colData+";";
        return query;
    }
    //delete query
    public static String queryDelete(String tableName, HashMap<String, String> condition, String[] colDataType){
        String query = "DELETE FROM "+ tableName + " where ";
        String colData ="";
        for(String columnName : condition.keySet()){
            colData += columnName+"="+dataTypeHandler(colDataType,condition);
        }
        //delete extra comma
        colData = colData.substring(0, colData.length() -1);

        query+=colData+";";

        return query;
    }
    //run program
    public static void main(String[] args) {
        //creating table with student name roll number and address with s_id primary key
        HashMap<String,String> studentTable = new HashMap<>();
        studentTable.put("student_name", "varchar(255)");
        studentTable.put("rollNumber", "int");
        studentTable.put("Address", "varchar(255)");
        //create table
        System.out.println(queryCreate("StudentRecord",studentTable,"s_id int"));
        //insert student name roll number and address in created table
        HashMap<String, String> studentData = new HashMap<>();
        String[] colDataType = {"i","s","i"};
        studentData.put("student_name","Ram Oza");
        studentData.put("rollNumber", "1");
        studentData.put("Address","Maitidevi");
        System.out.println(queryInsert("StudentRecord",studentData,colDataType));
        //update table--> Change name of Ram Oza to Ram Ojhas
        HashMap<String,String> updatedStudentData = new HashMap<>();
        updatedStudentData.put("student_name", "Ram Ojhas");
        HashMap<String, String> condition = new HashMap<>();
        condition.put("rollNumber", "1");
        String[] dataTypeRoll = {"i"};
        String[] updateDataType = {"s"};
        System.out.println(queryUpdate("StudentRecord",updatedStudentData,condition,dataTypeRoll,updateDataType));
        //delete table -> deleting record for s_id 4210
        HashMap<String, String> conditionToDelete = new HashMap<>();
        conditionToDelete.put("s_id","4210");
        String[] delColDataType = {"i"};
        System.out.println(queryDelete("StudentRecord",conditionToDelete,delColDataType));
    }
    //data type handler
    public static String dataTypeHandler(String[] colDataType, HashMap<String, String> hashMap) {
        int i = 0;
        String colData = "";
        for (String columnName : hashMap.keySet()) {
            //assuming values to insert and coldata type are in order
            //if int then no ""
            if (colDataType[i] == "i") {
                colData += hashMap.get(columnName) + ",";
            }
            //if string then ""
            else if (colDataType[i] == "s") {
                colData += '"' + hashMap.get(columnName) + '"' + ",";
            }
            //if char then ''
            else if (colDataType[i] == "c") {
                colData += "'" + hashMap.get(columnName) + "',";
            }
            //if boolean
            else if (colDataType[i] == "b") {
                if (hashMap.get(columnName) == "true" || hashMap.get(columnName) == "false") {
                    colData += hashMap.get(columnName) + ",";
                } else {
                    colData += "null,";
                }
            } else {
                colData += "null,";
            }
            i++;
        }
        return colData;
    }
}
