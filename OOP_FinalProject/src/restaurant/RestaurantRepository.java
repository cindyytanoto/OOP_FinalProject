package restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Database;

public class RestaurantRepository {
	
	private Database database = Database.getInstance();
	
	private static RestaurantRepository restaurantRepository;
	
	private RestaurantRepository() {
		
	}
	
	public static RestaurantRepository getInstance() {
		if(restaurantRepository == null) {
			restaurantRepository = new RestaurantRepository();
		}
		return restaurantRepository;
	}
	
	public Restaurant toRestaurant(ResultSet rs) {
		Restaurant newRestaurant = null;
		
		try {
			int restaurantId = rs.getInt(1);
			String restaurantName = rs.getString(2);
			RestaurantBranch restaurantBranch = RestaurantBranch.valueOf(rs.getString(3));
			newRestaurant = new Restaurant(restaurantId, restaurantName, restaurantBranch);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newRestaurant;
	}
	
	public ArrayList<Restaurant> getRestaurantList(){
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		
		PreparedStatement selectRestaurant = database.prepareStatement("SELECT * FROM MsRestaurant");
	
		try {
			ResultSet rs = selectRestaurant.executeQuery();
			while(rs.next()) {
				Restaurant newRestaurant = toRestaurant(rs);
				restaurants.add(newRestaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}
	

}
