package menu;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Database;

public class MenuRepository {
	
	private static MenuRepository menuRepository = null;
	private Database database = Database.getInstance();
	
	private MenuRepository() {
		
	}
	
	public static MenuRepository getInstance() {
		if(menuRepository == null) {
			menuRepository = new MenuRepository();
		}
		
		return menuRepository;
	}
	
	private Menu toMenu(ResultSet rs) {
		Menu menu = null;
		try {
			int menuId = rs.getInt(1);
			String menuName = rs.getString(2);
			int price = rs.getInt(3);
			String uniqueNar = rs.getString(4);
			String origin = rs.getString(5);
			
			if(uniqueNar != null && origin != null) {
				return new LocalMenu(menuId, menuName, price, uniqueNar, origin);
			}
			
			String menuBackStory = rs.getString(6);
			
			if(menuBackStory != null) {
				return new SpecialMenu(menuId, menuName, price, menuBackStory);
			}
			
			return new Menu(menuId, menuName, price);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return menu;
		
	}
	
	public int updateMenu(Menu menu) {
		try {
			PreparedStatement updateMenu = database.prepareStatement("UPDATE MsMenu SET MenuName = ?, Price = ? WHERE MenuID = ?");
			updateMenu.setString(1, menu.getMenuName());
			updateMenu.setInt(2, menu.getPrice());
			updateMenu.setInt(3, menu.getMenuId());
			return updateMenu.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateLocalMenu(LocalMenu menu) {
		try {
			int result = updateMenu(menu);
			if(result == 0) return result;
			PreparedStatement updateLocalMenu = database.prepareStatement("UPDATE LocalMenuDetail SET UniqueNar = ?, Origin = ? WHERE MenuID = ?");
			updateLocalMenu.setString(1, menu.getUniqueNar());
			updateLocalMenu.setString(2, menu.getOrigin());
			updateLocalMenu.setInt(3, menu.getMenuId());
			return updateLocalMenu.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateSpecialMenu(SpecialMenu menu) {
		try {
			int result = updateMenu(menu);
			if(result == 0) return result;
			PreparedStatement updateSpecialMenu = database.prepareStatement("UPDATE SpecialMenuDetail SET MenuStory = ? WHERE MenuID = ?");
			updateSpecialMenu.setString(1, menu.getStory());
			updateSpecialMenu.setInt(2, menu.getMenuId());
			return updateSpecialMenu.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deleteMenu(int id) {
		try {
			PreparedStatement deleteMenu = database.prepareStatement("DELETE FROM MsMenu WHERE MenuID = ?");
			deleteMenu.setInt(1, id);
			return deleteMenu.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
