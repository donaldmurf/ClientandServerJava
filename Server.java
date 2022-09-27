/*
 * Server.java
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Server {

    public static final int SERVER_PORT = 5746;
    private static PrintStream os;

    //Create a file called "Directory" or notify if it exists
    public  static  void CreateDirFile(){
        try{
            File myObj = new File("Directory");
            if( myObj.createNewFile()){
               System.out.println("File created: " + myObj.getName());
            }else{
               System.out.println("File Already Exists.");
            }
        }catch (
                IOException e){
           System.out.println("Error Occurred");
           os.println("");
            e.printStackTrace();
        }
    }





    public static class Account {
        //Record ID(4 digits), First name (8 char), Last name (8 char), Phone Number (12 characters), Max accounts (20)
        int recordID;
        String firstName;
        String lastName;
        String phoneNumber;


        //SETTERS//
        public void setRecordID(int recordID) {
            this.recordID = recordID;
        }

        public void setFirstName(String firstName) {
            // pad string so LIST of addresses looks cleaner
            firstName = String.format("%-8s", firstName);
            this.firstName = firstName;

        }

        public void setLastName(String lastName) {
            // pad string so LIST of addresses looks cleaner
            lastName = String.format("%-8s", lastName);
            this.lastName = lastName;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        //GETTERS//
        public int getRecordID() {
            return recordID;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }


        //Create a file called "Directory" or notify if it exists
        public static void CreateDirFile() {
            try {
                File myObj = new File("Directory");
                if (myObj.createNewFile()) {
                   os.println("File created: " + myObj.getName());
                } else {
                   os.println("File Already Exists.");
                }
            } catch (
                    IOException e) {
               os.println("Error Occurred");
                e.printStackTrace();
                os.println("");
            }
        }

        // similar code was used more than once, so I put it in a function
        // PRINT 
        public String printRecord() {
            return this.getRecordID() + " " + 
                this.getFirstName() + " " + 
                    this.getLastName() + " " + 
                    this.getPhoneNumber();
        }
    }

        //Reads in full file, changes the record, and then rewrites the entire file
        public static void EditRecord(int recordNum, String newRecordInfo) throws IOException {

            //read in the whole file and separate all the lines into an array
            BufferedReader in = new BufferedReader(new FileReader("Directory"));
            String str;
            List<String> list = new ArrayList<String>();
            while ((str = in.readLine()) != null)
                list.add(str);
            String[] stringArr = list.toArray((new String[0]));
            //

            newRecordInfo = newRecordInfo.toUpperCase();
            String recordtoChange = new String();

            int recordIndex = 0;
            for (int i = 0; i < stringArr.length; i++, recordIndex++) {
                String compareID = stringArr[i].substring(0, 4);

                if (compareID.equals(String.valueOf(recordNum))) {// DO NOT USE == TO COMPARE BECAUSE IT ONLY CHECKS TO SEE IF VAR POINTS TO SAME MEMORY
                    recordtoChange = stringArr[i];

                    break;


                }


            }
           os.println("OLD RECORD: " + recordtoChange);
           os.println("NEW RECORD: " + recordNum + " " + newRecordInfo);
            stringArr[recordIndex] = String.valueOf(recordNum) + " " + newRecordInfo;

            //OVERWRITE COMPLETE FILE!
            FileWriter myObj = new FileWriter("Directory");
            //RE-INSERT ALL ACCOUNTS ONE AT A TIME + NEW LINE
            for (int i = 0; i < stringArr.length; i++) {
                myObj.write(stringArr[i] + "\n");
            }

            myObj.close();


        }
    public static void DeleteRecord(int recordNum, String deleteRecordInfo) throws IOException {

        //read in the whole file and separate all the lines into an array
        BufferedReader in = new BufferedReader(new FileReader("Directory"));
        String str;
        List<String> list = new ArrayList<String>();
        while ((str = in.readLine()) != null)
            list.add(str);  
        String[] stringArr = list.toArray((new String[0]));
        //

        deleteRecordInfo = deleteRecordInfo.toUpperCase();
        String recordtoChange = new String();

        int recordIndex = 0;
        for (int i = 0; i < stringArr.length; i++, recordIndex++) {
            String compareID = stringArr[i].substring(0, 4);

            if (compareID.equals(String.valueOf(recordNum))) {// DO NOT USE == TO COMPARE BECAUSE IT ONLY CHECKS TO SEE IF VAR POINTS TO SAME MEMORY
                recordtoChange = stringArr[i];

                break;


            }


        }

        stringArr[recordIndex] =  deleteRecordInfo;

        //OVERWRITE COMPLETE FILE!
        FileWriter myObj = new FileWriter("Directory");
        //RE-INSERT ALL ACCOUNTS ONE AT A TIME + NEW LINE
        for (int i = 0; i < stringArr.length; i++) {
            if(stringArr[i].isEmpty())
                continue;
            myObj.write(stringArr[i] + "\n");
        }

        myObj.close();


    }

        //Main menu for selecting different commands
        public static void Menu(String line) throws IOException {

            //Display Menu and show user how to use program
           os.println("//////////MAIN MENU//////////");

           os.println("Enter a command followed by the information.");
           os.println(" ");
           os.println("EXAMPLES:");
           os.println("ADD Jinhua Guo 313-123-4567");
           os.println("EDIT 1001 BIG BIRD 313-911-4411");
           os.println("DELETE 1001" );
           os.println("LIST");
           os.println("SHUTDOWN");
           os.println("QUIT");

           os.println("//////////////////////////");
           os.println(" ");
            os.println("Entry: ");

            //takes the user input and splits it into array

            String menuInput = line;

            String[] menuArray = menuInput.split(" ");


            //create instance of account
            Account account = new Account();

            //Set command to uppercase
            menuArray[0] = menuArray[0].toUpperCase();


            //switch to take first part of input and perform correct action
            switch (menuArray[0]) {
                // Marcus helped with testing and modifying ADD to ensure it works correctly
                case "ADD":

                    account.setRecordID(CalculateIDNumber()); //The number of lines in the document +1001 = ID number.
                  try {
                      if(menuArray[1].length() > 8)
                          throw  new IllegalArgumentException("first name cannot exceed length of 8");
                      if(menuArray[2].length() > 8)
                          throw  new IllegalArgumentException("last name cannot exceed length of 8");
                      if(menuArray[3].length() != 12)
                          throw  new IllegalArgumentException("phone number must be formatted correctly 111-111-1111");

                      account.setFirstName(menuArray[1].toUpperCase());
                      account.setLastName(menuArray[2].toUpperCase());
                      account.setPhoneNumber(menuArray[3]);
                  }
                  catch (IllegalArgumentException e){
                      os.println("301 message format error");
                      os.println("");
                      return;

                  }

                    //The standard way of writing into a file for Java WITHOUT OVERWRITE
                    try {
                        FileWriter myWriter = new FileWriter("Directory", true);
                        BufferedWriter out = new BufferedWriter(myWriter);
                        out.write(account.printRecord() + "\n");
                        out.close();
                        myWriter.close();
                       os.println("Account created : " + account.printRecord());
                    } catch (IOException e) {
                       os.println("An error occurred.");
                        e.printStackTrace();
                        os.println("");
                    }
                    break;

                // Marcus helped with testing and modifying EDIT to ensure it works correctly
                case "EDIT":
                    try {
                        if(menuArray[2].length() > 8)
                            throw  new IllegalArgumentException("first name cannot exceed length of 8");
                        if(menuArray[3].length() > 8)
                            throw  new IllegalArgumentException("last name cannot exceed length of 8");
                        if(menuArray[4].length() != 12)
                            throw  new IllegalArgumentException("phone number must be formatted correctly 111-111-1111");

                        account.setFirstName(menuArray[2].toUpperCase());
                        account.setLastName(menuArray[3].toUpperCase());
                        account.setPhoneNumber(menuArray[4]);
                    }
                    catch (IllegalArgumentException e) {
                        os.println("301 message format error");
                        os.println("");
                    return;
                    }

                    int editNumber = Integer.valueOf(menuArray[1]);

                    String editInfo = account.getFirstName() + " "
                                        + account.getLastName() + " "
                                        + account.getPhoneNumber();

                    EditRecord(editNumber, editInfo);
                    break;
                case "DELETE":
                    int deleteNumber = Integer.valueOf(menuArray[1]);
                    DeleteRecord(deleteNumber,"");
                    break;


                case "LIST":
                    try {
                        os.println("200 OK");
                        File myObj = new File("Directory");
                        Scanner myReader = new Scanner(myObj);
                        if (!myReader.hasNextLine())
                           os.println("The file is currently empty");

                        while (myReader.hasNextLine()) {
                            String data = myReader.nextLine();
                           os.println(data);
                        }
                        myReader.close();
                    } catch (FileNotFoundException e) {
                       os.println("Unknown Error occured when trying to list all the accounts.");
                        e.printStackTrace();
                        os.println("");
                    }


                    break;
                case "DISPLAYMENU" :
                    break;
                case "SHUTDOWN":

                   //handled in main due to scope

                    break;
                case "QUIT":
                    os.println("200 OK");

                    break;
                default:
                   os.println("300 invalid command");

                    break;
            }
            os.println("");
            return;
        }

        // Look through the Directory file and count how many lines are currently in the document. The number of lines in the document +1001 = ID number.
        static int CalculateIDNumber() {
            int count = 1001;

            try {
                File myObj = new File("Directory");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] dataToken = data.split(" ");
                    if(dataToken[0].equals(String.valueOf(count))){
                        count++;
                         myReader = new Scanner(myObj);
                        continue;
                    }
                    //System.out.println(data);

                }
                //System.out.println("The number the next account should be assigned is: " + count);
                myReader.close();
            } catch (FileNotFoundException e) {
               os.println("An error occurred.");
               os.println("");
                e.printStackTrace();
            }
            return count;
        }


        public static void main(String args[]) {
            ServerSocket myServerice = null;
            String line;
            BufferedReader is;
            
            Socket serviceSocket = null;

            // Try to open a server socket
            try {
                myServerice = new ServerSocket(SERVER_PORT);
            } catch (IOException e) {
               os.println(e);
            }

            // Create a socket object from the ServerSocket to listen and accept connections.
            // Open input and output streams
            CreateDirFile();

            while (true) {
                try {
                    serviceSocket = myServerice.accept();
                    is = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
                    os = new PrintStream(serviceSocket.getOutputStream());
                    Menu("DISPLAYMENU");

                    // As long as we receive data, echo that data back to the client.

                    while ((line = is.readLine()) != null ) {
                        if(line.toUpperCase().equals("SHUTDOWN")) {
                            is.close();
                            os.close();
                            serviceSocket.close();
                            return;

                        }

                        Menu(line);


                    }
                    //create the "Directory" file if it does not exist










                    //close input and output stream and socket
                    is.close();
                    os.close();
                    serviceSocket.close();
                } catch (IOException e) {
                   os.println(e);
                }
            }
        }
    }

