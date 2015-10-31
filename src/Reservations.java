import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the API for the Hotel Reservation System with the functionalities:
 * findRooms(), bookRoom(), cancelBooking()
 *
 * Enter the userName, passWord and the serverName to the local MySQL Instance
 */
public class Reservations {
    public static Connection connection = null;
    private static String userName = "root";
    private static String password = "DBProject1";

    private static String serverName = "localhost";
    private static String dbUrl = "jdbc:mysql://"+serverName+"/arroju_mounica_db";

    private Booking bookingObject;
    private Customer customerObject;
    private Bill billObject;

    public static void main(String[] args) throws SQLException {

        Date checkindate = new Date(2015, 01, 19);
        Date checkoutdate = new Date(2015, 01, 23);

//        Booking booking = new Booking(14, null, 4, 2, 1, checkindate, checkoutdate);
//        Bill bill = new Bill("NewBooking", "CreditCard");
//        Customer customer = new Customer("ab", "bc", "xyz@gmail.com", "xyz", "Redmond", "WA", "98052", "4222222222");
        Reservations reserve = new Reservations();
        reserve.initializeConnection();
        //reserve.bookRoom(booking, bill, customer);
        //reserve.cancelBooking("e48e937d-e");

        List<Room> listOfRooms;
        listOfRooms = reserve.findRooms();


        reserve.closeConnection();

    }

    private void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Unable to load Driver");
        }
    }

    public void initializeConnection() {
        try {
            loadDriver();
            connection = DriverManager.getConnection(dbUrl, userName, password);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bookRoom() throws SQLException {

        PreparedStatement prepareStatement = null;
        int lastInsertedBookingID = 0, lastInsertedBillID = 0, lastInsertedCustomerID = 0;
        int roomID = bookingObject.getRoomID();
        String updateToBooked = "Booked";

        try {
            connection.setAutoCommit(false);
            prepareStatement = prepareNewBooking(bookingObject);
            prepareStatement.executeUpdate();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            if (resultSet.next()) {
                lastInsertedBookingID = resultSet.getInt(1);
                bookingObject.setBookingID(lastInsertedBookingID);
            }


            prepareStatement = prepareRoomStatusUpdate(roomID, updateToBooked);
            prepareStatement.executeUpdate();

            prepareStatement = preparePrice(roomID, lastInsertedBookingID, bookingObject);
            prepareStatement.executeUpdate();

            prepareStatement = prepareBill(billObject, bookingObject);
            prepareStatement.executeUpdate();
            ResultSet resultSet1 = prepareStatement.getGeneratedKeys();
            if (resultSet1.next()) {
                lastInsertedBillID = resultSet1.getInt(1);
                billObject.setBillID(lastInsertedBillID);
            }

            prepareStatement = updateBillBooking(billObject, bookingObject);
            prepareStatement.executeUpdate();

            prepareStatement = insertCustomerDetails(customerObject);
            prepareStatement.executeUpdate();
            ResultSet resultSet2 = prepareStatement.getGeneratedKeys();
            if (resultSet2.next()) {
                lastInsertedCustomerID = resultSet2.getInt(1);
                customerObject.setCustomerID(lastInsertedCustomerID);
            }

            prepareStatement = updateCustomerBooking(customerObject, bookingObject);
            prepareStatement.executeUpdate();

            prepareStatement = updateCustomerBillss(customerObject, billObject);
            prepareStatement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();

            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (prepareStatement != null) {
                prepareStatement.close();
            }
            connection.setAutoCommit(true);
        }
    }

    private PreparedStatement updateCustomerBillss(Customer customerDetails, Bill billObject) throws SQLException {
        PreparedStatement insertCustomerBills;

        String query = "Insert into arroju_mounica_db.customerbills(CustomerID, BillID) values (?, ?);";
        insertCustomerBills = connection.prepareStatement(query);
        insertCustomerBills.setInt(1, customerDetails.getCustomerID());
        insertCustomerBills.setInt(2, billObject.getBillID());

        return insertCustomerBills;
    }

    private PreparedStatement updateCustomerBooking(Customer customerDetails, Booking bookingObject) throws SQLException {
        PreparedStatement insertCustomerBooking;

        String query = "Insert into arroju_mounica_db.customerbookings(CustomerID, BookingID) values (?, ?);";
        insertCustomerBooking = connection.prepareStatement(query);
        insertCustomerBooking.setInt(1, customerDetails.getCustomerID());
        insertCustomerBooking.setInt(2, bookingObject.getBookingID());

        return insertCustomerBooking;
    }

    private PreparedStatement insertCustomerDetails(Customer customerDetails) throws SQLException {
        PreparedStatement insertCustomerData;

        String query = "Insert into arroju_mounica_db.customerdata(FirstName, LastName, EmailID, Street_Address, " +
                "City, State, zip, PhoneNumber) values (?, ?, ?, ?, ?, ?, ?, ?);";
        insertCustomerData = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        insertCustomerData.setString(1, customerDetails.getFirstName());
        insertCustomerData.setString(2, customerDetails.getLastName());
        insertCustomerData.setString(3, customerDetails.getEmailID());
        insertCustomerData.setString(4, customerDetails.getStreetAddress());
        insertCustomerData.setString(5, customerDetails.getCity());
        insertCustomerData.setString(6, customerDetails.getState());
        insertCustomerData.setString(7, customerDetails.getZip());
        insertCustomerData.setString(8, customerDetails.getPhoneNumber());

        return insertCustomerData;
    }

    private PreparedStatement updateBillBooking(Bill bill, Booking booking) throws SQLException {
        PreparedStatement updateBillBooking = null;
        String query = "Insert into arroju_mounica_db.bill(BillID, BookingID) values (?,?);";
        updateBillBooking = connection.prepareStatement(query);
        updateBillBooking.setInt(1, bill.getBillID());
        updateBillBooking.setInt(2, booking.getBookingID());

        return updateBillBooking;
    }

    private PreparedStatement prepareBill(Bill bill, Booking booking) throws SQLException {
        bill.setBillAmount(booking.getPaymentTotal());
        bill.setBillDate();

        String insertBillDetailsQuery = "Insert into arroju_mounica_db.totalbill(BillDescription, BillDate, " +
                "PaymentMethod, BillAmount) values (?, ?, ?, ?);";
        PreparedStatement generateBill = connection.prepareStatement(insertBillDetailsQuery, Statement.RETURN_GENERATED_KEYS);
        generateBill.setString(1, bill.getBillDescription());
        generateBill.setDate(2, bill.getBillDate());
        generateBill.setString(3, bill.getPaymentMethod());
        bill.setBillAmount(booking.getPaymentTotal());
        generateBill.setFloat(4, bill.getBillAmount());

        return generateBill;
    }

    private PreparedStatement preparePrice(int roomID, int bookingID, Booking bookingObject) throws SQLException {
        float bookingPrice = 0;
        float taxAmount = 0;
        float finalPrice = 0;


        String getPriceQuery = "select RoomExtendedPrice from room where roomID=?";
        PreparedStatement getPrice = connection.prepareStatement(getPriceQuery);
        getPrice.setInt(1, roomID);
        ResultSet resultPrice = getPrice.executeQuery();


        if (resultPrice.next()) {
            bookingPrice = resultPrice.getFloat(1);
            taxAmount = Booking.taxRatePercent * bookingPrice / 100;
            finalPrice = bookingPrice + taxAmount;
        }

        bookingObject.setFinalBookingPrice(bookingPrice);
        bookingObject.setTaxAmount(taxAmount);
        bookingObject.setPaymentTotal(finalPrice);

        String updatePriceQuery = "update arroju_mounica_db.bookings set FinalBookingPrice = ?, TaxAmount = ?, " +
                "PaymentTotal = ? where bookingID = ?";
        PreparedStatement updatePrice = null;
        updatePrice = connection.prepareStatement(updatePriceQuery);
        updatePrice.setFloat(1, bookingPrice);
        updatePrice.setFloat(2, taxAmount);
        updatePrice.setFloat(3, finalPrice);
        updatePrice.setInt(4, bookingID);

        return updatePrice;
    }

    private PreparedStatement prepareRoomStatusUpdate(int roomID, String update) throws SQLException {
        PreparedStatement updateRoomStatus = null;

        String query = "UPDATE arroju_mounica_db.room SET RoomStatus=" + "?" + "WHERE RoomID=" + "?";


        updateRoomStatus = connection.prepareStatement(query);
        updateRoomStatus.setString(1, update);
        updateRoomStatus.setInt(2, roomID);

        return updateRoomStatus;
    }

    private PreparedStatement prepareNewBooking(Booking booking) throws SQLException {

        String query = "INSERT INTO arroju_mounica_db.bookings (RoomID, PromotionID, CheckInDate, " +
                "CheckOutDate, NoOfNights, BookingStatus, NoOfAdults, NoOfKids, ConfirmationNumber)" +
                "VALUES (?, ?, ?, ?, ?, 'booked', ?, ?, ?)";

        PreparedStatement newBooking = null;

        newBooking = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        newBooking.setInt(1, booking.getRoomID());
        if (booking.getPromotionID() == null) {
            newBooking.setNull(2, Types.INTEGER);
        } else {
            newBooking.setInt(2, booking.getPromotionID());
        }
        newBooking.setDate(3, booking.getCheckInDate());
        newBooking.setDate(4, booking.getCheckOutDate());
        newBooking.setInt(5, booking.getNoOfNights());
        newBooking.setInt(6, booking.getNoOfAdults());
        newBooking.setInt(7, booking.getNoOfKids());
        booking.setConfirmationNumber();
        newBooking.setString(8, booking.getConfirmationNumber());

        return newBooking;
    }

    //TODO
    public List<Room> findRooms() throws SQLException {
        List<Room> availableRooms = new ArrayList<Room>();

        PreparedStatement getRooms = null;
        String query = "Select * from arroju_mounica_db.room where room.RoomStatus LIKE ?;";
        ResultSet result;
        String status = "Available";

        try {
            getRooms = connection.prepareStatement(query);
            getRooms.setString(1, status);
            getRooms.execute();
            result = getRooms.getResultSet();
            while (result.next()) {
                int roomID = result.getInt("RoomID");
                String roomNumber = result.getString("RoomNumber");
                String roomStatus = result.getString("RoomStatus");
                String bedSize = result.getString("BedSize");
                boolean hasAc = result.getBoolean("AirConditionFlag");
                boolean hasBalcony = result.getBoolean("BalconyViewFlag");
                boolean hasInternet = result.getBoolean("InternetAvailabilityFlag");
                float price = result.getFloat("RoomExtendedPrice");
                int roomTypeID = result.getInt("RoomTypeID");
                String roomType = getRoomType(roomTypeID);
              Room room = new Room(roomID, roomTypeID, roomType, roomNumber, roomStatus, bedSize, hasAc, hasBalcony,
                        hasInternet, price);
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (getRooms != null) {
                getRooms.close();
            }
        }

        return availableRooms;
    }

    private String getRoomType(int roomTypeID) throws SQLException {
        PreparedStatement statement;
        String roomtype = " ";

        String query = "Select RoomType from arroju_mounica_db.roomtype where RoomTypeID = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, roomTypeID);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            roomtype = rs.getString(1);
        }
        statement.close();
        return roomtype;
    }

    public void cancelBooking(String confirmationNumber) throws SQLException {
        PreparedStatement prepareStatement = null;

        try {
            connection.setAutoCommit(false);

            prepareStatement = changeBookingStatus(confirmationNumber);
            prepareStatement.executeUpdate();

            int roomID = getRoomID(confirmationNumber);
            int bookingID = getbookingID(confirmationNumber);
            int billID = getBillID(bookingID);

            prepareStatement = prepareRoomStatusUpdate(roomID, "Available");
            prepareStatement.executeUpdate();

            prepareStatement = updateBillStatus(billID);
            prepareStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();

            if (connection != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (prepareStatement != null) {
                prepareStatement.close();
            }
            connection.setAutoCommit(true);
        }


    }

    private PreparedStatement updateBillStatus(int billID) throws SQLException {
        PreparedStatement statement;
        String newStatus = "Cancelled and Refunded";
        String query = "Update arroju_mounica_db.totalbill set BillDescription = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, newStatus);

        return statement;
    }

    private int getBillID(int bookingID) throws SQLException {
        PreparedStatement statement;
        int billid = 0;
        String query = "Select BillID from bill where BookingID = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, bookingID);

        ResultSet result = statement.executeQuery();
        if (result.next()) {
            billid = result.getInt(1);
        }

        statement.close();
        return billid;
    }

    private int getbookingID(String confirmationNumber) throws SQLException {
        PreparedStatement statement;
        int bookingid = 0;
        String query = "Select BookingID from bookings where confirmationNumber = ? ;";
        statement = connection.prepareStatement(query);
        statement.setString(1, confirmationNumber);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            bookingid = result.getInt(1);
        }

        statement.close();
        return bookingid;

    }

    private int getRoomID(String confirmationNumber) throws SQLException {
        PreparedStatement statement;
        int roomid = 0;
        String query = "Select RoomID from bookings where confirmationNumber = ? ;";
        statement = connection.prepareStatement(query);
        statement.setString(1, confirmationNumber);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            roomid = result.getInt(1);
        }

        statement.close();
        return roomid;
    }

    private PreparedStatement changeBookingStatus(String confirmationNumber) throws SQLException {
        PreparedStatement changeStatusStatement;
        String bookingStatus = "Cancelled";
        String query = "Update arroju_mounica_db.bookings set BookingStatus = ? where ConfirmationNumber = ? ;";

        changeStatusStatement = connection.prepareStatement(query);
        changeStatusStatement.setString(1, bookingStatus);
        changeStatusStatement.setString(2, confirmationNumber);

        return changeStatusStatement;
    }


    public void setCustomerObject(Customer customerObject) {
        this.customerObject = customerObject;
    }

    public void setBookingObject(Booking bookingObject) {
        this.bookingObject = bookingObject;
    }

    public void setBillObject(Bill billObject) {
        this.billObject = billObject;
    }
}
