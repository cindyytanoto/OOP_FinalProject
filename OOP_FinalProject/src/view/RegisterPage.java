package view;

import java.util.ArrayList;

import employee.EmployeeRepository;
import restaurant.Restaurant;
import restaurant.RestaurantRepository;

public class RegisterPage extends MasterPage {
	
	private RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
	private EmployeeRepository employeeRepository = EmployeeRepository.getInstance();
	
	public RegisterPage() {
		show();
	}

	@Override
	public void show() {
		String employeeName = "";
		int restaurantId = -1;
		
		do {
			employeeName = helper.getString("Input name: ");
			if(employeeName.isEmpty()) {
				System.out.println("Name must be filled!");
				continue;
			}
		} while(employeeName.isEmpty());
		
		ArrayList<Restaurant> restaurants = restaurantRepository.getRestaurantList();
		
		if(restaurants.isEmpty()) {
			System.out.println("There are currently no restaurant available!");
			return;
		} 
		
		while(true) {
			System.out.println("Restaurant List:");
			for (Restaurant restaurant : restaurants) {
				System.out.printf("%d, %s, %s\n", restaurant.getRestaurantId(), restaurant.getRestaurantName(), restaurant.getRestaurantBranch());
			}
			restaurantId = helper.getInteger("Input restaurant ID: ");
			
			if(restaurantId < 1 || restaurantId > restaurants.size()) {
				System.out.println("Restaurant ID must be between 1 - " + restaurants.size());
			}
			
			break;
		}
		
		String employeeId = employeeRepository.createEmployee(restaurantId, employeeName);	
		if(employeeId.isEmpty()) {
			System.out.println("Employee insertion failed");
			helper.enter();
		}
		
	}

}
