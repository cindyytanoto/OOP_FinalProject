package employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;
import restaurant.Restaurant;
import restaurant.RestaurantBranch;

public class EmployeeRepository {
	
	private Database database = Database.getInstance();
	
	private static EmployeeRepository employeeRepository;
	
	private EmployeeRepository() {
		
	}
	
	public static EmployeeRepository getInstance() {
		if(employeeRepository == null) {
			employeeRepository = new EmployeeRepository();
		}
		return employeeRepository;
	}
	
	private ArrayList<Object> toEmployee(ResultSet rs){
		ArrayList<Object> temp = new ArrayList<>();
	
		try {
			if(!rs.next()) {
				return null;
			}
			String employeeId = rs.getString(1);
			String employeeName = rs.getString(2);
			
			Employee newEmployee = new Employee(employeeId, employeeName);
			
			int restaurantId = rs.getInt(3);
			RestaurantBranch restaurantBranch = RestaurantBranch.valueOf(rs.getString(4));
			String restaurantName = rs.getString(5);
			
			Restaurant restaurant = new Restaurant(restaurantId, restaurantName, restaurantBranch);
			
			temp.add(newEmployee);
			temp.add(restaurant);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	
	public String createEmployee(int restaurantId, String employeeName) {
		String employeeId = "";
		
		try {
			PreparedStatement insertEmployee = database.prepareStatement(EmployeeQuery.ADD_EMPLOYEE);
			ResultSet getLastEmployee = database.executeQuery(EmployeeQuery.GET_LAST_EMPLOYEE);
			if(!getLastEmployee.next()) {
				employeeId = "EMP001";
			} else {
				employeeId = String.format("EMP%03d", Integer.parseInt(getLastEmployee.getString(1).substring(3)) + 1);
			}
			insertEmployee.setString(1, employeeId);
			insertEmployee.setString(2, employeeName);
			insertEmployee.setInt(3, restaurantId);
			insertEmployee.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return employeeId;
	}
	
	public ArrayList<Object> getEmployee (String employeeId){
		ArrayList<Object> temp = null;
		try {
			PreparedStatement selectEmployee = database.prepareStatement(EmployeeQuery.GET_EMPLOYEE);
			selectEmployee.setString(1, employeeId);
			ResultSet rs = selectEmployee.executeQuery();
			temp = toEmployee(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

}
