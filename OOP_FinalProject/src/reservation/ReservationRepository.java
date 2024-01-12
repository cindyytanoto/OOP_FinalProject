package reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.Database;
import table.TableType;

public class ReservationRepository {
	
	private static ReservationRepository reservationRepository;
	private Database database = Database.getInstance();
	
	private ReservationRepository() {
		
	}
	
	public static ReservationRepository getInstance() {
		if(reservationRepository == null) {
			reservationRepository = new ReservationRepository();
		}
		
		return reservationRepository;
	}
	
	public boolean isMenuOrdered(int menuId) {
		try {
			PreparedStatement getMenuCount = database.prepareStatement("SELECT COUNT(*) AS count FROM ReservationDetail WHERE MenuID = ?");
			getMenuCount.setInt(1, menuId);
			ResultSet rs = getMenuCount.executeQuery();
			if(!rs.next()) return false;
			return rs.getInt(1) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Reservation toReservation(ResultSet rs) {
		try {
			int reservationId = rs.getInt(1);
			int restaurantId = rs.getInt(2);
			String employeeId = rs.getString(3);
			String customerName = rs.getString(4); 
			int numberOfTable = rs.getInt(5);
			TableType tableType = TableType.valueOf(rs.getString(6));
			int numberOfPeople = rs.getInt(7);
			ReservationStatus reservationStatus = ReservationStatus.valueOf(rs.getString(8)) ;
			return new Reservation(reservationId, restaurantId, employeeId, customerName, numberOfTable, tableType, numberOfPeople, reservationStatus);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Reservation> getReservationByStatus(ReservationStatus reservationStatus){
		ArrayList<Reservation> Reservations = new ArrayList<>();
		try {
			PreparedStatement getReservationByStatus = database.prepareStatement("SELECT ReservationID, RestaurantID, EmployeeID, CustomerName, NumberOfTable, TableType, NumberOfPeople, ReservationStatus FROM reservationheader WHERE ReservationStatus = ?");
			getReservationByStatus.setString(1, reservationStatus.toString());
			ResultSet rs = getReservationByStatus.executeQuery();
			while(rs.next()) {
				Reservations.add(toReservation(rs));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Reservations;
	}
	
	public int addReservation(int restaurantId, String employeeId, String customerName, ReservationStatus reservationStatus, int numberOfTable, TableType tableType, int numberOfPeople, ArrayList<ReservationDetail> reservationDetails) {
		int temp = 0;
		try {
			PreparedStatement insertReservation = database.prepareStatement("INSERT INTO ReservationHeader(RestaurantID, EmployeeID, CustomerName, NumberOfTable, TableType, NumberOfPeople, ReservationStatus) VALUES (?, ?, ?, ?, ?, ?, ?)");
			insertReservation.setInt(1, restaurantId);
			insertReservation.setString(2, employeeId);
			insertReservation.setString(3, customerName);
			insertReservation.setInt(4, numberOfTable);
			insertReservation.setString(5, tableType.toString());
			insertReservation.setInt(6, numberOfPeople);
			insertReservation.setString(7, reservationStatus.toString());
			int result = insertReservation.executeUpdate();
			if(result == 0) return result;
			ResultSet rs = database.executeQuery("SELECT ReservationID FROM ReservationHeader ORDER BY ReservationID DESC LIMIT 1");
			if(!rs.next()) {
				return 0;
			}
			int reservationId = rs.getInt(1);
			temp = handleReservationDetails(reservationId, reservationDetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public int updateToInOrder(int reservationId, ArrayList<ReservationDetail> reservationDetails) {
		int result = handleReservationDetails(reservationId, reservationDetails);
		if(result == 0) return result;
		try {
			result = updateReservationStatus(ReservationStatus.InOrder, reservationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateReservationStatus(ReservationStatus reservationStatus, int reservationId) {
		try {
			PreparedStatement updateReservationStatus = database.prepareStatement("UPDATE ReservationHeader SET ReservationStatus = ? WHERE ReservationID = ?");
			updateReservationStatus.setString(1, reservationStatus.toString());
			updateReservationStatus.setInt(2, reservationId);
			return updateReservationStatus.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private ReservationDetail toReservationDetail(ResultSet rs) {
		try {
			int reservationId = rs.getInt(1);
			String menuName = rs.getString(2);
			int price = rs.getInt(3);
			int quantity = rs.getInt(4);
			ReservationDetail rd = new ReservationDetail(reservationId, quantity);
			rd.setPrice(price);
			rd.setMenuName(menuName);
			return rd;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int handleReservationDetails(int reservationId, ArrayList<ReservationDetail> reservationDetails) {
		for (ReservationDetail reservationDetail : reservationDetails) {
			int result = addReservationDetail(reservationId, reservationDetail);
			if(result == 0) return 0;
		}
		return 1;
	}
	
	public int addReservationDetail(int reservationId, ReservationDetail reservationDetail) {
		try {
			PreparedStatement insertReservationDetail = database.prepareStatement("INSERT INTO ReservationDetail(ReservationID, MenuID, Quantity) VALUES(?, ?, ?)");
			insertReservationDetail.setInt(1, reservationId);
			insertReservationDetail.setInt(2, reservationDetail.getMenuID());
			insertReservationDetail.setInt(3, reservationDetail.getQuantity());
			return insertReservationDetail.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ArrayList<ReservationDetail> getReservationDetail(int reservationId) {
		ArrayList<ReservationDetail> reservationDetails = new ArrayList<>();
		try {
			PreparedStatement selectReservationDetail = database.prepareStatement("SELECT ReservationID, RestaurantID, EmployeeID, CustomerName, NumberOfTable, TableType, NumberOfPeople, ReservationStatus FROM reservationheader WHERE ReservationStatus = ?");
			selectReservationDetail.setInt(1, reservationId);
			ResultSet rs = selectReservationDetail.executeQuery();
			while(rs.next()) {
				reservationDetails.add(toReservationDetail(rs));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return reservationDetails;
	}

}
