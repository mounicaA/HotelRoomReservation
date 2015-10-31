HOTEL RESERVATION SYSTEM

SOFTWARE REQUIREMENTS: 
1. JAVA JDK 7 
2. IDE like ECLIPSE or INTELLIJ IDEA
3. MySQL WorkBench
4. JDBC Connnector for MYSQL

Steps for Execution:
1. Open the script "Arroju_Mounica_backend.sql" in MySQL workbench and run it.
2. Import the project HotelProject into the IDE (Eclipse)
3. Add the JDBC dowloaded file to the classpath/build path of the project.
4. In the file "Reservations.java" set the user name, password and server name to those for your local MySQL instance
5. Compile the project and run "HotelReservationGUI.java"

Using the HotelReservation System:

After running the GUI file, a dialogue box opens with buttons "Find rooms" and "Cancel Booking"

TEST CASE 1 (Booking a Room):
1. Click on Find Rooms button
2. The system searches for all the available rooms and displays them.
3. Select one of the rows and click on "Book Selected Room"
4. Enter the following details (in exact format):
	CheckInDate: 2015-02-12
	CheckOutDate: 2015-02-15
	NoOfNights: 3
	NoOfAdults: 1
	NoOfKids: 1
5. Click on the "Next" button
6. Enter the following customer details:
	FirstName: John
	LastName: Williams
	Emailid: john@comcast.com
	Street Address: 1900 Commerce St
	City: Tacoma	
	State: WA
	Zip: 98052
	Phone Number: 4256788754
7. Click on "Next"
8. Maximize the window and Enter the following details
	Payment Method: Credit Card
	Booking Description: New Booking
9. Note the room number and the uniquely generated confirmation number. Close the windows.

Verification: The booking transaction can be checked in the tables "bookings", "customerdata", "totalbill". Also the booked room number will be shown as "Booked" in the table room.

TEST CASE 2(Cancelling a Room):
1. Run the GUi File again
2. Click on Cancel Booking button
3. Maximize the window and Enter the confirmation number from the previous test case.
4. Click on "Cancel this Booking" button

Verification: The booking will be show the booking status as "Cancelled" in the table "bookings". The room number will show as "Available" in the table "room". The bill will show as "Cancelled and Refunded" in the table "bill".

Note: In this implementation, the customer login and checking previous customer records is omitted. Also payment procedure is simplified and not as a practical scenario. The search functionality is also limited to the available rooms. 



	
