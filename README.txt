-----INTRODUCTION-----
-This program is designed to run with Java. 
To use this program on Unix you must compile it.

-A MakeFile is included inside of the root folder.
Inorder to compile the program just type "make" and hit enter.

-If for some reason the MakeFile does not work correctly
do the following:

javac Server.java
javac Client.java

-After doing one of the above methods to compile the program
the program is ready to run.
To run the Server enter:
java Server

To run the Client enter:
java Client 127.0.0.1


----COMMANDS----

ADD:
-The add command will let you add a new account into
a file name Directory. Note that when the Server is 
started a file name Directory will be created if 
it does not exist. An example of using add:
ADD Bill Murray 313-222-1234

EDIT:
To edit an account you must enter edit, followed by
the ID number you wish to edit, followed by the new
information you wish to edit. An example of using edit:
EDIT 1001 Will Smith 734-345-7890

DELETE:
To delete an account you must enter delete, followed
the ID number you wish to delete. An example
of using delete:
DELETE 1001

LIST:
To see all the accounts just enter the word list.
An example of using list:
LIST

SHUTDOWN:
To shutdown enter "SHUTDOWN".
This will shut down the server

QUIT:
To shutdown enter "QUIT".
This will shut down the client only


----SAMPLE OUTPUT----

//////////MAIN MENU//////////
Enter a command followed by the information.
 
EXAMPLES:
ADD Jinhua Guo 313-123-4567
EDIT 1001 BIG BIRD 313-911-4411
DELETE 1001
LIST
SHUTDOWN
QUIT
//////////////////////////
 
Entry: 
list
//////////MAIN MENU//////////
Enter a command followed by the information.
 
EXAMPLES:
ADD Jinhua Guo 313-123-4567
EDIT 1001 BIG BIRD 313-911-4411
DELETE 1001
LIST
SHUTDOWN
QUIT
//////////////////////////
 
Entry: 
200 OK
1001 MARTY MCFLY 222-444-6788
1002 TACO BELL 333-444-5555
1003 ROBO COP 444-555-6666
1004 MOONMAN DUDE 333-999-0000
1005 PATRICK STARR 333-444-8888
1006 SAILOR MOON 313-444-6666
edit 1002 bill murray 313-233-4456
//////////MAIN MENU//////////
Enter a command followed by the information.
 
EXAMPLES:
ADD Jinhua Guo 313-123-4567
EDIT 1001 BIG BIRD 313-911-4411
DELETE 1001
LIST
SHUTDOWN
QUIT
//////////////////////////
 
Entry: 
OLD RECORD: 1002 TACO BELL 333-444-5555
NEW RECORD: 1002 BILL MURRAY 313-233-4456
list
//////////MAIN MENU//////////
Enter a command followed by the information.
 
EXAMPLES:
ADD Jinhua Guo 313-123-4567
EDIT 1001 BIG BIRD 313-911-4411
DELETE 1001
LIST
SHUTDOWN
QUIT
//////////////////////////
 
Entry: 
200 OK
1001 MARTY MCFLY 222-444-6788
1002 BILL MURRAY 313-233-4456
1003 ROBO COP 444-555-6666
1004 MOONMAN DUDE 333-999-0000
1005 PATRICK STARR 333-444-8888
1006 SAILOR MOON 313-444-6666
delete 1005
//////////MAIN MENU//////////
Enter a command followed by the information.
 
EXAMPLES:
ADD Jinhua Guo 313-123-4567
EDIT 1001 BIG BIRD 313-911-4411
DELETE 1001
LIST
SHUTDOWN
QUIT
//////////////////////////
 
Entry: 
list
//////////MAIN MENU//////////
Enter a command followed by the information.
 
EXAMPLES:
ADD Jinhua Guo 313-123-4567
EDIT 1001 BIG BIRD 313-911-4411
DELETE 1001
LIST
SHUTDOWN
QUIT
//////////////////////////
 
Entry: 
200 OK
1001 MARTY MCFLY 222-444-6788
1002 BILL MURRAY 313-233-4456
1003 ROBO COP 444-555-6666
1004 MOONMAN DUDE 333-999-0000
1006 SAILOR MOON 313-444-6666
add bill gates 313-321-4780
//////////MAIN MENU//////////
Enter a command followed by the information.
 
EXAMPLES:
ADD Jinhua Guo 313-123-4567
EDIT 1001 BIG BIRD 313-911-4411
DELETE 1001
LIST
SHUTDOWN
QUIT
//////////////////////////
 
Entry: 
Account created : 1005 BILL GATES 313-321-4780
list
//////////MAIN MENU//////////
Enter a command followed by the information.
 
EXAMPLES:
ADD Jinhua Guo 313-123-4567
EDIT 1001 BIG BIRD 313-911-4411
DELETE 1001
LIST
SHUTDOWN
QUIT
//////////////////////////
 
Entry: 
200 OK
1001 MARTY MCFLY 222-444-6788
1002 BILL MURRAY 313-233-4456
1003 ROBO COP 444-555-6666
1004 MOONMAN DUDE 333-999-0000
1006 SAILOR MOON 313-444-6666
1005 BILL GATES 313-321-4780


