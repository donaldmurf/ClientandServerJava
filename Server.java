/*
 * Server.java
 */

import javax.management.relation.RoleUnresolved;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Server implements Runnable {
    public static ArrayList<Socket> clientHandlers = new ArrayList<>();
    public static ArrayList<User> userArray = new ArrayList<>();
    public static ArrayList<PrintStream> OSArray = new ArrayList<>();
    public static ArrayList<BufferedReader> ISArray = new ArrayList<>();
    public static int socketCount;

    public static final int SERVER_PORT = 5746;



    //Create a file called "Directory" or notify if it exists
    public static void CreateDirFile() {
        try {
            File myObj = new File("Directory");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File Already Exists.");
            }
        } catch (
                IOException e) {
            System.out.println("Error Occurred");
            //os.println("");
            e.printStackTrace();
        }
    }
    public static void CreateUserFile() {
        try {
            File myObj = new File("Users");
            if (myObj.createNewFile()) {
                FileWriter fileWriter = new FileWriter("Users");
                fileWriter.write("root root01 root\n");
                fileWriter.write("john john01 generic\n");
                fileWriter.write("david david01 generic\n");
                fileWriter.write("mary mary01 generic\n");
                System.out.println("File created: " + myObj.getName());
                fileWriter.close();
            } else {
                System.out.println("File Already Exists.");
            }
        } catch (
                IOException e) {
            System.out.println("Error Occurred");
            //os.println("");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }

    public static void run(int socketPosition, User user) throws IOException {

        BufferedReader is;

        String line;
        Socket serviceSocket = clientHandlers.get(socketPosition);
        PrintStream os;

        is = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
        os = new PrintStream(serviceSocket.getOutputStream());
        ISArray.add(is);
        OSArray.add(os);


        Menu("DISPLAYMENU", os, user);

        // As long as we receive data, echo that data back to the client.

        while ((line = is.readLine()) != null) {
            if (line.toUpperCase().equals("SHUTDOWN")) {
                if(!user.userRole.equals(AccessRoles.root)) {
                    Menu(line, os, user);
                    continue;

                }
                os.println("200 OK");

                for(PrintStream itOS : OSArray){
                    itOS.println("210 the server is about to shutdown .....");
                    itOS.print("");
                    itOS.close();
                }
                for(BufferedReader ISBuffReader: ISArray){

                    ISBuffReader.close();
                }
                for(Socket ITsocket : clientHandlers){
                    ITsocket.close();
                }
                System.exit(0);
                return;

            }

            Menu(line, os, user);


        }
        //create the "Directory" file if it does not exist


        //close input and output stream and socket
        is.close();
        os.close();
        serviceSocket.close();
        ISArray.remove(is);
        OSArray.remove(os);
        clientHandlers.remove(serviceSocket);

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
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
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

    }
    enum AccessRoles{root, genericUser, none}

    public static AccessRoles stringToAccessRole(String roleString){
        if(roleString.equals("root"))
            return AccessRoles.root;

        return AccessRoles.genericUser;

    }

    public static class User{
        String userID;
        String userPW;
       AccessRoles userRole;
       String IPAddress;

    }

    //Reads in full file, changes the record, and then rewrites the entire file
    public static void EditRecord(int recordNum, String newRecordInfo, PrintStream os) throws IOException {

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

        stringArr[recordIndex] = deleteRecordInfo;

        //OVERWRITE COMPLETE FILE!
        FileWriter myObj = new FileWriter("Directory");
        //RE-INSERT ALL ACCOUNTS ONE AT A TIME + NEW LINE
        for (int i = 0; i < stringArr.length; i++) {
            if (stringArr[i].isEmpty())
                continue;
            myObj.write(stringArr[i] + "\n");
        }

        myObj.close();


    }


    //Main menu for selecting different commands
    public static void Menu(String line, PrintStream os, User user) throws IOException {

        //Display Menu and show user how to use program
        os.println("//////////MAIN MENU//////////");

        os.println("Enter a command followed by the information.");
        os.println(" ");
        os.println("EXAMPLES:");
        os.println("LOGIN james !007");
        os.println("LOGOUT");
        os.println("WHO");
        os.println("LOOK 2 MILLER");
        os.println("ADD 1001 Keanu Reeves 313-123-4567");
        os.println("EDIT 1001 BIG BIRD 313-911-4411");
        os.println("DELETE 1001");
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
            case "ADD":
                if(user.userRole.equals(AccessRoles.none)) {
                    os.println("401 You are not currently logged in, login first");
                    break;
                }



                // if(menuArray[1].length() >=)


                account.setRecordID(CalculateIDNumber(os)); //The number of lines in the document +1001 = ID number.
                try {
                    if (menuArray[1].length() >= 8)
                        throw new IllegalArgumentException("first name cannot exceed length of 8");
                    if (menuArray[2].length() >= 8)
                        throw new IllegalArgumentException("last name cannot exceed length of 8");
                    if (menuArray[3].length() != 12)
                        throw new IllegalArgumentException("phone number must be formatted correctly 111-111-1111");

                    account.setFirstName(menuArray[1].toUpperCase());
                    account.setLastName(menuArray[2].toUpperCase());
                    account.setPhoneNumber(menuArray[3]);
                } catch (IllegalArgumentException e) {
                    os.println("301 message format error");
                    os.println("");
                    return;

                }


                //The standard way of writing into a file for Java WITHOUT OVERWRITE
                try {
                    FileWriter myWriter = new FileWriter("Directory", true);
                    BufferedWriter out = new BufferedWriter(myWriter);
                    out.write(account.getRecordID() + " " + account.getFirstName() + " " + account.getLastName() + " " + account.getPhoneNumber() + "\n");
                    out.close();
                    myWriter.close();
                    os.println("Account created : " + account.getRecordID() + " " + account.getFirstName() + " " + account.getLastName() + " " + account.getPhoneNumber());
                } catch (IOException e) {
                    os.println("An error occurred.");
                    e.printStackTrace();
                    os.println("");
                }
                break;

            case "EDIT":
                try {
                    if (menuArray[1].length() >= 8)
                        throw new IllegalArgumentException("first name cannot exceed length of 8");
                    if (menuArray[2].length() >= 8)
                        throw new IllegalArgumentException("last name cannot exceed length of 8");
                    if (menuArray[4].length() != 12)
                        throw new IllegalArgumentException("phone number must be formatted correctly 111-111-1111");

                    account.setFirstName(menuArray[1].toUpperCase());
                    account.setLastName(menuArray[2].toUpperCase());
                    account.setPhoneNumber(menuArray[3]);
                } catch (IllegalArgumentException e) {
                    os.println("301 message format error");
                    os.println("");
                    return;
                }


                String[] newRecordArray = menuInput.split(" ");

                int editNumber = Integer.valueOf(newRecordArray[1]);
                String editInfo = newRecordArray[2] + " " + newRecordArray[3] + " " + newRecordArray[4];

                EditRecord(editNumber, editInfo, os);
                break;
            case "DELETE":
                if(user.userRole.equals(AccessRoles.none)) {
                    os.println("401 You are not currently logged in, login first");
                    break;
                }

                int deleteNumber = Integer.valueOf(menuArray[1]);
                DeleteRecord(deleteNumber, "");
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
                    os.println("Unknown Error occurred when trying to list all the accounts.");
                    e.printStackTrace();
                    os.println("");
                }


                break;
            case "DISPLAYMENU":
                break;
            case "LOGIN":
                try{
                    String userID = menuArray[1];
                    String userPW = menuArray[2];


                    File myObj = new File("Users");
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String[] dataArray = data.split(" ");
                        if(userID.equals(dataArray[0]) && userPW.equals(dataArray[1])){
                            user.userID =  dataArray[0];
                            user.userPW = dataArray[1];

                            user.userRole = stringToAccessRole(dataArray[2]);
                            break;
                        }

                    }
                    myReader.close();
                    if(!user.userID.isEmpty()) {
                        os.println("200 OK");
                        userArray.add(user);
                    }
                    else {
                        os.println("410 Wrong UserID or Password");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "LOGOUT":
                userArray.remove(user);
                user.userRole = AccessRoles.none;
                user.userID = "";
                user.userPW = "";
                os.println("200 OK");
                break;
            case "WHO":
                os.println("200 OK");
                os.println("The list of the active users:");
                for(User itUser: userArray){
                    if(itUser.userID.isEmpty()) {
                        continue;
                    }
                    os.println(itUser.userID+"\t" + itUser.IPAddress);
                }
                break;
            case "LOOK":
                //1 command
                //2 lookUpString
                String command = menuArray[1];
                String lookUpString = menuArray[2];




                File myObj = new File("Directory");
                Scanner myReader = new Scanner(myObj);
                int numFound = 0;
                StringBuilder lookList = new StringBuilder();
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] dataArray = data.split(" ");

                    switch (command) {

                        case "1":

                            if(dataArray[1].equals(lookUpString)){
                                numFound++;
                                lookList.append(data+ "\n");
                            }

                            break;
                        case "2":
                            if(dataArray[2].equals(lookUpString)){
                                numFound++;
                                lookList.append(data+ "\n");
                            }
                            break;
                        case "3":
                            if(dataArray[3].equals(lookUpString)){
                                numFound++;
                                lookList.append(data+ "\n");
                            }
                            break;
                    }

                }

                myReader.close();
                if(numFound == 0)
                {
                    os.println("404 Your search did not match any records");
                }
                else{
                    os.println("200 OK");
                    os.println("Found "+ numFound + "match");
                    os.print(lookList);
                }

                break;
            case "SHUTDOWN":
                os.println("402 User not allowed to execute this command");
                //handled in run
                break;
            case "QUIT":

                userArray.remove(user);
                user.userRole = AccessRoles.none;
                user.userID = "";
                user.userPW = "";
                os.println("200  OK");
                break;
            default:
                os.println("300 invalid command");

                break;
        }
        os.println("");
        return;
    }

    // Look through the Directory file and count how many lines are currently in the document. The number of lines in the document +1001 = ID number.
    static int CalculateIDNumber(PrintStream os) {
        int count = 1001;

        try {
            File myObj = new File("Directory");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataToken = data.split(" ");
                if (dataToken[0].equals(String.valueOf(count))) {
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


    public static void main(String args[]) throws IOException {
        ServerSocket myServerice = null;
        String line;
        BufferedReader is;

        Socket serviceSocket = null;

        // Try to open a server socket
        try {
            myServerice = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            //os.println(e);
        }

        // Create a socket object from the ServerSocket to listen and accept connections.
        // Open input and output streams
        CreateDirFile();
        CreateUserFile();



        while (!myServerice.isClosed()) {
            try {




                //serviceSocket = myServerice.accept();
                 final Socket curSocket = myServerice.accept();
                int curSocketPosition = socketCount;
                clientHandlers.add(socketCount++,curSocket);
                User user = new User();
                user.userID = "";
                user.userPW = "";
                user.userRole = AccessRoles.none;
                user.IPAddress = curSocket.getRemoteSocketAddress().toString();

                ServerSocket finalMyServerice = myServerice;
                Thread t = new Thread(() -> {
                    try {
                        run(curSocketPosition, user);
                    } catch (IOException e) {
                        try {
                            finalMyServerice.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
                myServerice.close();
            }
        }
    }
}
