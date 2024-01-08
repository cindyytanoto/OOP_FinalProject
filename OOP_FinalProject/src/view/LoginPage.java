package view;

import java.util.ArrayList;

import employee.Employee;
import employee.EmployeeRepository;
import restaurant.Restaurant;

public class LoginPage extends MasterPage {
	
	private EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
	private Employee employee;
	private Restaurant restaurant;
	
	public LoginPage() {
		show();
	}

	@Override
	public void show() {
		String employeeId;
		
		employeeId = helper.getString("Input your employee ID: ");
		
		if(!employeeId.matches("^EMP[0-9][0-9][0-9]$")) {
			System.out.println("Your ID is invalid");
		}
		
		ArrayList<Object> temp = employeeRepository.getEmployee(employeeId);
		
		if(temp.isEmpty()) {
			System.out.println("Employee ID not found");
		}
		
		employee = (Employee) temp.get(0);
		restaurant = (Restaurant) temp.get(1);
		
		new EmployeeHomePage(employee, restaurant);
		
	}


}
