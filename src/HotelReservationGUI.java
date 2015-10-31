import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Implements a simple GUI for the Hotel Reservation System
 *
 * Author: Mounica Arroju
 */
public class HotelReservationGUI extends JFrame implements ActionListener, ListSelectionListener {
    private JButton bookButton;
    private Reservations reserve;
    private List<Room> roomList;
    private String[] columnNames = {"Room Number", "Room Type", "Bed Size", "AirConditioned", "Balcony View",
            "Internet Availability", "Price per Night"};
    private Object[][] data;
    private JTable table;


    private JPanel customerDetailsPanel;
    private JLabel[] customerDetailLabel = new JLabel[8];
    private JTextField[] customerDetailField = new JTextField[8];
    private JLabel[] bookingDetailLabel = new JLabel[5];
    private JTextField[] bookingDetailField = new JTextField[5];
    private JButton submitBookingButton;
    private JPanel bookingDetailsPanel;
    private JButton submitCustomerButton;

    private Room selectedRoom;
    private Booking newBooking;
    private Customer newCustomer;
    private JPanel bookButtonPanel;
    private JPanel mainPanel;
    private JButton findRooms, cancelBooking;
    private JPanel cancelBookingPanel;
    private JLabel confirmationNumberLabel;
    private JTextField confirmationNumberField;
    private JButton submitConfirmationNumberButton;
    private JPanel paymentDetailsPanel;
    private JLabel[] paymentDetailLabel = new JLabel[2];
    private JTextField[] paymentDetailField = new JTextField[2];
    private JButton submitPaymentButton;
    private Bill newBill;


    public HotelReservationGUI() {

        super("Hotel Room Reservation");

        reserve = new Reservations();
        reserve.initializeConnection();

        try
        {
            roomList = reserve.findRooms();

            data = new Object[roomList.size()][columnNames.length];
            for(int i=0; i < roomList.size(); i++){
                data[i][0] = roomList.get(i).getRoomNumber();
                data[i][1] = roomList.get(i).getRoomType();
                data[i][2] = roomList.get(i).getBedSize();
                data[i][3] = roomList.get(i).isAirConditioned();
                data[i][4] = roomList.get(i).getHasBalconyView();
                data[i][5] = roomList.get(i).isInternetAvailable();
                data[i][6] = roomList.get(i).getRoomPrice();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        createComponents();
        setVisible(true);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createComponents() {
        mainPanel = new JPanel();
        findRooms = new JButton("Find Rooms");
        cancelBooking = new JButton("Cancel a Booking");
        mainPanel.add(findRooms);
        mainPanel.add(cancelBooking);
        add(mainPanel, BorderLayout.CENTER);
        findRooms.addActionListener(this);
        cancelBooking.addActionListener(this);


        bookButtonPanel = new JPanel();
        bookButton = new JButton("Book Selected Room");
        bookButton.addActionListener(this);
        bookButtonPanel.add(bookButton);

        //Booking Details Panel
        bookingDetailsPanel = new JPanel();
        bookingDetailsPanel.setLayout(new GridLayout(6, 0));
        String labelBookingNames[] = {"Check in Date", "Check out Date", "Number of Nights",
                "Number of Adults", "Number of Kids"};
        for(int i=0; i<labelBookingNames.length; i++){
            JPanel tempPanel = new JPanel();
            bookingDetailLabel[i] = new JLabel(labelBookingNames[i]);
            bookingDetailField[i] = new JTextField(25);
            tempPanel.add(bookingDetailLabel[i]);
            tempPanel.add(bookingDetailField[i]);
            bookingDetailsPanel.add(tempPanel);
        }

        JPanel submitBookingDetailsPanel = new JPanel();
        submitBookingButton = new JButton("Next");
        submitBookingButton.addActionListener(this);
        submitBookingDetailsPanel.add(submitBookingButton);
        bookingDetailsPanel.add(submitBookingDetailsPanel);

        //Customer Details Panel
        customerDetailsPanel = new JPanel();
        customerDetailsPanel.setLayout(new GridLayout(9, 0));
        String labelCustomerNames[] = {"First Name: ", "Last Name: ", "Email Id: ", "Street Address: ", "City: ",
                "State: ", "Zip: ", "Phone Number: "};
        for(int i=0; i< labelCustomerNames.length; i++){
            JPanel tempPanel = new JPanel();
            customerDetailLabel[i] = new JLabel(labelCustomerNames[i]);
            customerDetailField[i] = new JTextField(30);
            tempPanel.add(customerDetailLabel[i]);
            tempPanel.add(customerDetailField[i]);
            customerDetailsPanel.add(tempPanel);
        }

        JPanel submitCustomerDetailsPanel = new JPanel();
        submitCustomerButton = new JButton("Next");
        submitCustomerButton.addActionListener(this);
        submitCustomerDetailsPanel.add(submitCustomerButton);
        customerDetailsPanel.add(submitCustomerDetailsPanel);

        //Payment Panel
        paymentDetailsPanel = new JPanel();
        paymentDetailsPanel.setLayout(new GridLayout(2, 0));
        String labelPaymentNames[] = {"Payment Method: ", "Booking Description: "};
        for(int i=0; i< labelPaymentNames.length; i++){
            JPanel tempPanel = new JPanel();
            paymentDetailLabel[i] = new JLabel(labelPaymentNames[i]);
            paymentDetailField[i] = new JTextField(30);
            tempPanel.add(paymentDetailLabel[i]);
            tempPanel.add(paymentDetailField[i]);
            paymentDetailsPanel.add(tempPanel);
        }

        JPanel submitPaymentDetailsPanel = new JPanel();
        submitPaymentButton = new JButton("Confirm Booking");
        submitPaymentButton.addActionListener(this);
        submitPaymentDetailsPanel.add(submitPaymentButton);
        paymentDetailsPanel.add(submitPaymentDetailsPanel);

        //Cancel Booking Panel
        cancelBookingPanel = new JPanel();
        cancelBookingPanel.setLayout(new GridLayout(1,0));
        String labelConfirmationNumber = "Enter your booking confirmation number ";
        confirmationNumberLabel = new JLabel(labelConfirmationNumber);
        confirmationNumberField = new JTextField(20);
        cancelBookingPanel.add(confirmationNumberLabel);
        cancelBookingPanel.add(confirmationNumberField);
        submitConfirmationNumberButton = new JButton("Cancel this Booking");
        cancelBookingPanel.add(submitConfirmationNumberButton, BorderLayout.PAGE_END);
        submitConfirmationNumberButton.addActionListener(this);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                HotelReservationGUI reservationInstance = new HotelReservationGUI();
                reservationInstance.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == findRooms){
            try{
                roomList = reserve.findRooms();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            mainPanel.removeAll();
            table = new JTable(data, columnNames);
            table.setRowSelectionAllowed(true);
            ListSelectionModel rowSelection = table.getSelectionModel();
            rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            rowSelection.addListSelectionListener(this);

            JScrollPane scrollPane = new JScrollPane(table);
            mainPanel.add(scrollPane);
            mainPanel.add(bookButtonPanel, BorderLayout.PAGE_END);
            mainPanel.revalidate();
            this.repaint();
        }

        else if(e.getSource() == cancelBooking){
            mainPanel.removeAll();
            mainPanel.add(cancelBookingPanel);
            mainPanel.revalidate();
            this.repaint();
        }

        else if(e.getSource() == submitConfirmationNumberButton){
            String confirmationNumber = confirmationNumberField.getText();
            try {
                reserve.cancelBooking(confirmationNumber);
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                JOptionPane.showMessageDialog(null, "Booking Cancelled");
            }
        }

        else if(e.getSource() == bookButton){
            mainPanel.removeAll();
            mainPanel.add(bookingDetailsPanel);
            mainPanel.revalidate();
            this.repaint();
        }

        else if(e.getSource() == submitBookingButton){
            newBooking = new Booking(selectedRoom.getRoomID(), null, Integer.parseInt(bookingDetailField[2].getText()),
                    Integer.parseInt(bookingDetailField[3].getText()), Integer.parseInt(bookingDetailField[4].getText()),
                    java.sql.Date.valueOf(bookingDetailField[0].getText()), java.sql.Date.valueOf(bookingDetailField[1].getText()));
            reserve.setBookingObject(newBooking);

            mainPanel.removeAll();
            mainPanel.add(customerDetailsPanel);
            mainPanel.revalidate();
            this.repaint();
        }

        else if ( e.getSource() == submitCustomerButton){
            newCustomer = new Customer(customerDetailField[0].getText(), customerDetailField[1].getText(),
                    customerDetailField[2].getText(), customerDetailField[3].getText(), customerDetailField[4].getText(), customerDetailField[5].getText(),
                    customerDetailField[6].getText(), customerDetailField[7].getText());

            reserve.setCustomerObject(newCustomer);

            mainPanel.removeAll();
            mainPanel.add(paymentDetailsPanel);
            mainPanel.revalidate();
            this.repaint();
        }

        else if(e.getSource() == submitPaymentButton){
            newBill = new Bill(paymentDetailField[1].getText(), paymentDetailField[0].getText());

            reserve.setBillObject(newBill);

            try {
                reserve.bookRoom();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                JOptionPane.showMessageDialog(mainPanel, "Booking Successful for \n" +
                        "Room Number " + selectedRoom.getRoomNumber()
                        +"Your booking confirmation number is " + newBooking.getConfirmationNumber());
            }
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selection = table.getSelectedRow();
        selectedRoom = roomList.get(table.convertRowIndexToModel(selection));
    }
}
