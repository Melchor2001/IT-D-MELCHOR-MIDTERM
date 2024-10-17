
package pkg2d.sugaroljr.midterm;

import pkg2d.sugaroljr.midterm.config;
import com.sun.corba.se.pept.transport.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import static i_id,name,email.dipartment,p_numberr,h_date;


public class sugaroljr.midterm {

public static void main(String[] args) {


Scanner sc = new Scanner (System.in);
String response;
do{
System.out.println("Welcome to Project:");
System.out.println("1. ADD");
System.out.println("2. VIEW");
System.out.println("3. UPDATE");
System.out.println("4. DELETE");
System.out.println("5. EXIT");

System.out.print("Enter Action: ");
int action = sc.nextInt();
sugarol= new Candilanza();
switch(action){
case 1:
Candilanza.addproject();
break;
case 2:
Candilanza.viewproject();
break;
case 3:Candilanza.viewproject();
Candilanza.updateproject();
break;
case 4:
Candilanza.viewproject();
Candilanza.deleteproject();
Candilanza.viewproject();
break;
}
System.out.println("Do you want to continue? (yes/no): ");
response = sc.next();

}while(response.equalsIgnoreCase("yes"));
System.out.println("Thank you!");
}



public void addproject(){
Scanner sc = new Scanner(System.in);
config conf = new config();

System.out.print(" Project ID: ");
String pid = sc.next();
System.out.print("Project Title: ");
String ptitle =sc.next();
System.out.print("Project Description: ");
String pdesc = sc.next();
System.out.print("Submission Date:");
String sdate = sc.next();
System.out.println("Grade:");
String pgrade = sc.next();



String sql = "INSERT INTO tbl_project (p_id,p_title,p_desc,s_date,p_grade) VALUES (?,?,?,? ,?)";

conf.addRecord(sql, pid ,ptitle ,pdesc,sdate,pgrade);


}

private void viewproject() {


String qry = "SELECT * FROM tbl_project";
String[] hdrs = {"pid","ptitle", "pdesc ","sdate", " pgrade"};
String[] clmns = {"p_id","p_title","p_desc","s_date","p_grade"};


config conf = new config();
conf.viewRecords(qry,hdrs,clmns);

}

private void updateproject(){

Scanner sc= new Scanner(System.in);
System.out.print("Enter the ID to Update: ");
int npid = sc.nextInt();

System.out.print("Enter new Project Title: ");
String nptitle = sc.next();
System.out.print("Enter new Project Description : ");
String npdesc = sc.next();
System.out.print("Enter new Submission Date : ");
String nsdate = sc.next();
System.out.print("Enter new Grade: ");
String npgrade = sc.next();

String qry = "UPDATE tbl_project SET p_title = ?,p_desc = ?, s_date = ?,p_grade WHERE p_id = ?";

config conf = new config();
conf.updateRecord(qry, nptitle,npdesc,nsdate,npgrade, id );
}

private void deleteproject(){

Scanner sc= new Scanner(System.in);
System.out.print("Enter the ID to Delete: ");
int id = sc.nextInt();

String qry = "DELETE FROM tbl_project WHERE p_id = ?";

config conf = new config();
conf.deleteRecord(qry, id);

}

Add class comment…


Announcement: "package omaan; import…"
Melchor Sugarol
Created YesterdayYesterday
package omaan;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class config {


//Connection Method to SQLITE
public static Connection connectDB() {
Connection con = null;
try {
Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
con = DriverManager.getConnection("jdbc:sqlite:Omaan.db"); // Establish connection
System.out.println("Connection Successful");
} catch (Exception e) {
System.out.println("Connection Failed: " + e);
}
return con;
}


public void addRecord(String sql, Object... values) {
try (Connection conn = this.connectDB(); // Use the connectDB method
PreparedStatement pstmt = conn.prepareStatement(sql)) {

// Loop through the values and set them in the prepared statement dynamically
for (int i = 0; i < values.length; i++) {
if (values[i] instanceof Integer) {
pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
} else if (values[i] instanceof Double) {
pstmt.setDouble(i + 1, (Double) values[i]); // If the value is Double
} else if (values[i] instanceof Float) {
pstmt.setFloat(i + 1, (Float) values[i]); // If the value is Float
} else if (values[i] instanceof Long) {
pstmt.setLong(i + 1, (Long) values[i]); // If the value is Long
} else if (values[i] instanceof Boolean) {
pstmt.setBoolean(i + 1, (Boolean) values[i]); // If the value is Boolean
} else if (values[i] instanceof java.util.Date) {
pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); // If the value is Date
} else if (values[i] instanceof java.sql.Date) {
pstmt.setDate(i + 1, (java.sql.Date) values[i]); // If it's already a SQL Date
} else if (values[i] instanceof java.sql.Timestamp) {
pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); // If the value is Timestamp
} else {
pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
}
}

pstmt.executeUpdate();
System.out.println("Record added successfully!");
} catch (SQLException e) {
System.out.println("Error adding record: " + e.getMessage());
}
}

// Dynamic view method to display records from any table
public void viewRecords(String sqlQuery, String[] columnHeaders, String[] columnNames) {
// Check that columnHeaders and columnNames arrays are the same length
if (columnHeaders.length != columnNames.length) {
System.out.println("Error: Mismatch between column headers and column names.");
return;
}

try (Connection conn = this.connectDB();
PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
ResultSet rs = pstmt.executeQuery()) {

// Print the headers dynamically
StringBuilder headerLine = new StringBuilder();
headerLine.append("--------------------------------------------------------------------------------\n| ");
for (String header : columnHeaders) {
headerLine.append(String.format("%-20s | ", header)); // Adjust formatting as needed
}
headerLine.append("\n--------------------------------------------------------------------------------");

System.out.println(headerLine.toString());

// Print the rows dynamically based on the provided column names
while (rs.next()) {
StringBuilder row = new StringBuilder("| ");
for (String colName : columnNames) {
String value = rs.getString(colName);
row.append(String.format("%-20s | ", value != null ? value : "")); // Adjust formatting
}
System.out.println(row.toString());
}
System.out.println("--------------------------------------------------------------------------------");

} catch (SQLException e) {
System.out.println("Error retrieving records: " + e.getMessage());
}
}


//-----------------------------------------------
// UPDATE METHOD
//-----------------------------------------------

public void updateRecord(String sql, Object... values) {
try (Connection conn = this.connectDB(); // Use the connectDB method
PreparedStatement pstmt = conn.prepareStatement(sql)) {

// Loop through the values and set them in the prepared statement dynamically
for (int i = 0; i < values.length; i++) {
if (values[i] instanceof Integer) {
pstmt.setInt(i + 1, (Intege

Add class comment…


Announcement: "package omaan; import…"
Melchor Sugarol
Created YesterdayYesterday
package omaan;

import java.util.Scanner;


public class Omaan {


public static void main(String[] args) {


Scanner sc = new Scanner (System.in);
String response;
do{
System.out.println("1. ADD");
System.out.println("2. VIEW");
System.out.println("3. UPDATE");
System.out.println("4. DELETE");
System.out.println("5. EXIT");

System.out.print("Enter Action: ");
int action = sc.nextInt();
Omaan omaan = new Omaan();
switch(action){
case 1:
omaan.add4ps();
break;
case 2:
omaan.view4ps();
break;
case 3:omaan.view4ps();
omaan.update4ps();
break;
case 4:
omaan.view4ps();
omaan.delete4ps();
omaan.view4ps();
break;
}
System.out.println("Do you want to continue? (yes/no): ");
response = sc.next();

}while(response.equalsIgnoreCase("yes"));
System.out.println("Thank you!");
}



public void add4ps(){
Scanner sc = new Scanner(System.in);
config conf = new config();

System.out.print(" Beneficiary ID: ");
String bID = sc.next();
System.out.print("Full Name: ");
String funame = sc.next();
System.out.print("Age: ");
String age = sc.next();
System.out.print("Gender: ");
String gender = sc.next();
System.out.print("Address: ");
String address = sc.next();
System.out.print("Family Members: ");
String fam = sc.next();


String sql = "INSERT INTO tbl_4ps (b_id,full_name,Age,Gender,Address,fam_m) VALUES (?,?,?,? ,?,?)";

conf.addRecord(sql, bID ,funame ,age,gender,address,fam);


}

private void view4ps() {


String qry = "SELECT * FROM tbl_4ps";
String[] hdrs = {"bID","funame", "age ","gender", " address","fam"};
String[] clmns = {"b_id","full_name","Age","Gender","Address","fam_m"};


config conf = new config();
conf.viewRecords(qry,hdrs,clmns);

}

private void update4ps(){

Scanner sc= new Scanner(System.in);
System.out.print("Enter the ID to Update: ");
int id = sc.nextInt();

System.out.print("Enter new Full Name: ");
String nfuname = sc.next();
System.out.print("Enter new Age : ");
String nage = sc.next();
System.out.print("Enter new Gender : ");
String ngender = sc.next();
System.out.print("Enter new Address: ");
String naddress = sc.next();
System.out.print("Enter new Family Members : ");
String nfam = sc.next();

String qry = "UPDATE tbl_4ps SET full_name = ?,Age = ?, Gender = ?,address =?, fam_m =? WHERE b_id = ?";

config conf = new config();
conf.updateRecord(qry, nfuname,nage,ngender,naddress,nfam, id);
}

private void delete4ps(){

Scanner sc= new Scanner(System.in);
System.out.print("Enter the ID to Delete: ");
int id = sc.nextInt();

String qry = "DELETE FROM tbl_4ps WHERE b_id = ?";

config conf = new config();
conf.deleteRecord(qry, id);

}

}