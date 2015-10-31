import java.sql.Date;
import java.util.UUID;

/**
 *
 */
public class Booking {
    private Integer bookingID, roomID, promotionID, noOfNights, noOfAdults, noOfKids;
    private Date checkInDate, checkOutDate;
    private String bookingStatus;
    private String confirmationNumber;
    private float finalBookingPrice, taxAmount, paymentTotal;
    public static float taxRatePercent = 10;

    public Booking(int roomID, Integer promotionID, int noOfNights, int noOfAdults, int noOfKids, Date checkInDate,
                   Date checkOutDate) {
        this.roomID = roomID;
        this.promotionID = promotionID;
        this.noOfAdults = noOfAdults;
        this.noOfNights = noOfNights;
        this.noOfKids = noOfKids;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }


    public int getBookingID() {
        return bookingID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Integer getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber() {
        confirmationNumber = UUID.randomUUID().toString();
        confirmationNumber = confirmationNumber.substring(0, Math.min(confirmationNumber.length(), 10));
    }

    public static void main(String[] args) {
        for(int i = 0; i < 100; i++) {
            String confirmationNumber = UUID.randomUUID().toString();
            confirmationNumber = confirmationNumber.substring(0, Math.min(confirmationNumber.length(), 10));
            System.out.println(confirmationNumber);
        }
    }


    public int getNoOfNights() {
        return noOfNights;
    }

    public void setNoOfNights(int noOfNights) {
        this.noOfNights = noOfNights;
    }

    public int getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public int getNoOfKids() {
        return noOfKids;
    }

    public void setNoOfKids(int noOfKids) {
        this.noOfKids = noOfKids;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public float getFinalBookingPrice() {
        return finalBookingPrice;
    }

    public void setFinalBookingPrice(float finalBookingPrice) {
        this.finalBookingPrice = finalBookingPrice;
    }

    public float getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(float paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setBookingID(Integer bookingID) {
        this.bookingID = bookingID;
    }
}
