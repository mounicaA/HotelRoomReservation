import java.sql.Date;

/**
 * Created by Mounica on 12/9/14.
 */
public class Bill {
    private String BillDescription, PaymentMethod;
    private float BillAmount;
    private Date BillDate;
    private int BillID;

    public Bill(String BillDescription, String PaymentMethod){
        this.BillDescription = BillDescription;
        this.PaymentMethod = PaymentMethod;
    }



    public String getBillDescription() {
        return BillDescription;
    }

    public void setBillDescription(String billDescription) {
        BillDescription = billDescription;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public Date getBillDate() {
        return BillDate;
    }

    public void setBillDate() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        java.util.Date utilDate = cal.getTime();
        java.sql.Date sqlDate = new Date(utilDate.getTime());
        BillDate = sqlDate;
    }

    public float getBillAmount() {
        return BillAmount;
    }

    public void setBillAmount(float billAmount) {
        BillAmount = billAmount;
    }

    public int getBillID() {
        return BillID;
    }

    public void setBillID(int billID) {
        BillID = billID;
    }

//    public static void main(String[] args) {
//        Bill bill = new Bill();
//        bill.setBillDate();
//        System.out.println(bill.getBillDate());
//    }
}
