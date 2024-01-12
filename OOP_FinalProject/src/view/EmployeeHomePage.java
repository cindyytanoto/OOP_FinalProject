package view;

import employee.Employee;
import restaurant.Restaurant;

public class EmployeeHomePage extends MasterPage{
	
	private Employee employee;
	private Restaurant restaurant;
	
	public EmployeeHomePage(Employee employee, Restaurant restaurant) {
		this.employee = employee;
		this.restaurant = restaurant;
		show();
	}

	@Override
	public void show() {
		int choice;
		while(true) {
			System.out.println("Welcome " + employee.getEmployeeName()+ ", " +restaurant.getRestaurantName());
			System.out.println("1. Manage Menu");
			System.out.println("2. Manage Reservation");
			System.out.println("3. Log Out");
			choice = helper.getInteger(">> ");
			
			if(choice == 1) {
				helper.clear();
				new ManageMenuPage(restaurant);
			} else if(choice == 2) {
				
			} else if(choice == 3) {
				break;
			}
			
		}
	}

}
