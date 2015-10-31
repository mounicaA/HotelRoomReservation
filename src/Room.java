/**
 * Created by Mounica on 12/10/14.
 */
public class Room {
    private int roomID;
    private int roomTypeID;
    private String roomType;
    private String roomNumber;
    private String roomStatus;
    private String bedSize;
    private boolean isAirConditioned;

    public Room(int roomID, int roomTypeID, String roomType, String roomNumber, String roomStatus, String bedSize, boolean isAirConditioned, boolean hasBalconyView, boolean isInternetAvailable, float roomPrice) {
        this.roomID = roomID;
        this.roomTypeID = roomTypeID;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.roomStatus = roomStatus;
        this.bedSize = bedSize;
        this.isAirConditioned = isAirConditioned;
        this.hasBalconyView = hasBalconyView;
        this.isInternetAvailable = isInternetAvailable;
        this.roomPrice = roomPrice;
    }

    private boolean hasBalconyView;
    private boolean isInternetAvailable;
    private float roomPrice;

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getBedSize() {
        return bedSize;
    }

    public void setBedSize(String bedSize) {
        this.bedSize = bedSize;
    }

    public boolean isAirConditioned() {
        return isAirConditioned;
    }

    public void setAirConditioned(boolean isAirConditioned) {
        this.isAirConditioned = isAirConditioned;
    }

    public boolean getHasBalconyView() {
        return hasBalconyView;
    }

    public void setHasBalconyView(boolean hasBalconyView) {
        this.hasBalconyView = hasBalconyView;
    }

    public boolean isInternetAvailable() {
        return isInternetAvailable;
    }

    public void setInternetAvailable(boolean isInternetAvailable) {
        this.isInternetAvailable = isInternetAvailable;
    }

    public float getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }
}
