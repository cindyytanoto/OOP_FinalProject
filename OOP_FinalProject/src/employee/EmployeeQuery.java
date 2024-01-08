package employee;

public class EmployeeQuery {
	
	public static String GET_EMPLOYEE = "SELECT EmployeeID, EmployeeName, e.RestaurantID, RestaurantBranch, RestaurantName FROM MsEmployee e JOIN MsRestaurant r ON e.RestaurantID = r.RestaurantID WHERE e.EmployeeID = ? LIMIT 1";
	public static String GET_LAST_EMPLOYEE = "SELECT EmployeeID FROM MsEmployee ORDER BY EmployeeID DESC LIMIT 1";
	public static String ADD_EMPLOYEE = "INSERT INTO MsEmployee VALUES (?, ?, ?)";
}
