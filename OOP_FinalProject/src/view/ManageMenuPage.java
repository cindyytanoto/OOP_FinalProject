package view;

import java.util.ArrayList;

import menu.LocalMenu;
import menu.Menu;
import menu.MenuCategory;
import menu.MenuRepository;
import menu.SpecialMenu;
import reservation.ReservationRepository;
import restaurant.Restaurant;

public class ManageMenuPage extends MasterPage {
	
	private MenuRepository menuRepository = MenuRepository.getInstance();
	private Restaurant restaurant;
	private ReservationRepository reservationRepository;
	
	public ManageMenuPage(Restaurant restaurant) {
		this.restaurant = restaurant;
		show();
	}

	@Override
	public void show() {
		
		int choice = -1;
		while(true) {
			System.out.println("Manage Menu");
			System.out.println("1. Add Menu");
			System.out.println("2. Update Menu");
			System.out.println("3. Delete Menu");
			System.out.println("4. Back");
			choice = helper.getInteger(">> ");
			if(choice == 1) {
				addMenu();
			} else if(choice == 2) {
				updateMenu();
			} else if(choice == 3) {
				deleteMenu();
			} else if(choice == 4) {
				break;
			}
		}
		
	}
	
	private void addMenu() {
		MenuCategory menuCategory = restaurant.getRestaurantBranch().getMenuCategory();
		
		int choice;
		
		while(true) {
			System.out.println("Choose the menu type below :");
			System.out.println("1. Regular");
			System.out.println("2. " + menuCategory);
			choice = helper.getInteger(">> ");
			
			if(choice == 1) {
				menuCategory = MenuCategory.Regular;
			} else if (choice == 2) {
				break;
			} else {
				break;
			}
			
		}
		
		String menuName;
		
		while(true) {
			menuName = helper.getString("Input menu name: ");
			
			if(!menuName.isEmpty()) {
				break;
			}
			
		}
		
		int price;
		while(true) {
			price = helper.getInteger("Input menu price: ");
			
			if(price > 0) {
				break;
			}
			
		}
		
		int result;
		
		if(menuCategory.equals("Special")) {
			String story;
			while(true) {
				story = helper.getString("Input story: ");
				if(!story.isEmpty()) {
					break;
				}
			}
			result = menuRepository.addSpecialMenu(restaurant.getRestaurantId(), menuCategory, menuName, price, story);
		}
		
		if(menuCategory.equals("Local")) {
			String uniqueNar;
			String origin;
			
			while(true) {
				uniqueNar = helper.getString("Input menu naration: ");
				if(!uniqueNar.isEmpty()) {
					break;
				}
			}
			
			while(true) {
				origin = helper.getString("Input origin: ");
				if(!origin.isEmpty()) {
					break;
				}
			}
			
			result = menuRepository.addLocalMenu(restaurant.getRestaurantId(), menuCategory, menuName, price, uniqueNar, origin);
		}
		
		else {
			result = menuRepository.addMenu(restaurant.getRestaurantId(), menuCategory, menuName, price);
		}
		
		if(result > 0) {
			System.out.println("Menu successfully inserted!");
			return;
		}
				
	}
	
	private void updateMenu() {
	    ArrayList<Menu> menuList = menuRepository.getMenuList(restaurant.getRestaurantId());
	    displayMenu(menuList);
	    
	    if (menuList.isEmpty()) {
	        System.out.println("No menuList available to update.");
	        return;
	    }
	    
	    int menuId = helper.getInteger("Input menu id to update: ");
	    
	    Menu menuToUpdate = findMenuById(menuList, menuId);
	    
	    if (menuToUpdate == null) {
	        System.out.println("Menu with the specified id not found.");
	        return;
	    }
	    
	    if (reservationRepository.isMenuOrdered(menuToUpdate.getMenuId())) {
	        System.out.println("Can't update a menu that is already ordered.");
	        return;
	    }
	    
	    updateMenuDetails(menuToUpdate);
	}

	private void deleteMenu() {
	    ArrayList<Menu> menuList = menuRepository.getMenuList(restaurant.getRestaurantId());
	    displayMenu(menuList);
	    
	    if (menuList.isEmpty()) {
	        System.out.println("No menuList available to delete.");
	        return;
	    }
	    
	    int menuId = helper.getInteger("Input menu id to delete: ");
	    
	    Menu menuToDelete = findMenuById(menuList, menuId);
	    
	    if (menuToDelete == null) {
	        System.out.println("Menu with the specified id not found.");
	        return;
	    }
	    
	    if (reservationRepository.isMenuOrdered(menuToDelete.getMenuId())) {
	        System.out.println("Can't delete a menu that is already ordered.");
	        return;
	    }
	    
	    int result = menuRepository.deleteMenu(menuToDelete.getMenuId());
	    
	    if (result == 0) {
	        System.out.println("Failed to delete the menu.");
	    } else {
	        System.out.println("Successfully deleted the menu: " + menuToDelete.getMenuName());
	    }
	}

	private Menu findMenuById(ArrayList<Menu> menuList, int menuId) {
	    for (Menu menu : menuList) {
	        if (menu.getMenuId() == menuId) {
	            return menu;
	        }
	    }
	    return null;
	}

	private void updateMenuDetails(Menu menuToUpdate) {
	    String name = helper.getString("Input updated name: ");
	    
	    if (name.isEmpty()) {
	        System.out.println("Updated name must not be empty.");
	        return;
	    }
	    
	    int price = helper.getInteger("Input updated price: ");
	    
	    if (price <= 0) {
	        System.out.println("Updated price must be more than 0.");
	        return;
	    }
	    
	    if (menuToUpdate instanceof LocalMenu) {
	        updateLocalMenuDetails((LocalMenu) menuToUpdate);
	    } else if (menuToUpdate instanceof SpecialMenu) {
	        updateSpecialMenuDetails((SpecialMenu) menuToUpdate);
	    }
	    
	    int result = menuRepository.updateMenu(menuToUpdate);
	    
	    if (result != 0) {
	        System.out.println("Successfully updated menu:");
	        displayMenuDetails(menuToUpdate);
	    } else {
	        System.out.println("An unknown error occurred when updating menu.");
	    }
	}

	private void updateLocalMenuDetails(LocalMenu localMenu) {
	    String uniqueNar = helper.getString("Input updated unique trait: ");
	    
	    if (uniqueNar.isEmpty()) {
	        System.out.println("Updated unique trait must not be empty.");
	        return;
	    }
	    
	    String origins = helper.getString("Input updated origins: ");
	    
	    if (origins.isEmpty()) {
	        System.out.println("Updated origins must not be empty.");
	        return;
	    }
	    
	    localMenu.setUniqueNar(uniqueNar);
	    localMenu.setOrigin(origins);
	}

	private void updateSpecialMenuDetails(SpecialMenu specialMenu) {
	    String story = helper.getString("Input updated story: ");
	    
	    if (story.isEmpty()) {
	        System.out.println("Updated story must not be empty.");
	        return;
	    }
	    
	    specialMenu.setStory(story);
	}
	
	private void displayMenu(ArrayList<Menu> menus) {
	    if (menus.isEmpty()) {
	        System.out.println("There are currently no menus available.");
	        return;
	    }

	    System.out.println("Menu List:");

	    for (Menu menu : menus) {
	        System.out.println("---------------");
	        System.out.println("ID: " + menu.getMenuId());
	        System.out.println("Name: " + menu.getMenuName());
	        System.out.println("Price: Rp " + menu.getPrice());

	        if (menu instanceof LocalMenu) {
	            LocalMenu localMenu = (LocalMenu) menu;
	            System.out.println("Category: Local");
	            System.out.println("Unique Narration: " + localMenu.getUniqueNar());
	            System.out.println("Origin: " + localMenu.getOrigin());
	        } else if (menu instanceof SpecialMenu) {
	            SpecialMenu specialMenu = (SpecialMenu) menu;
	            System.out.println("Category: Special");
	            System.out.println("Story: " + specialMenu.getStory());
	        } else {
	            System.out.println("Category: Regular");
	        }
	    }
	}


	private void displayMenuDetails(Menu menu) {
	    System.out.println("Menu Details:");
	    System.out.println("ID: " + menu.getMenuId());
	    System.out.println("Name: " + menu.getMenuName());
	    System.out.println("Price: Rp " + menu.getPrice());

	    if (menu instanceof LocalMenu) {
	        LocalMenu localMenu = (LocalMenu) menu;
	        System.out.println("Category: Local");
	        System.out.println("Unique Trait: " + localMenu.getUniqueNar());
	        System.out.println("Origin: " + localMenu.getOrigin());
	    } else if (menu instanceof SpecialMenu) {
	        SpecialMenu specialMenu = (SpecialMenu) menu;
	        System.out.println("Category: Special");
	        System.out.println("Story: " + specialMenu.getStory());
	    } else {
	        System.out.println("Category: Regular");
	    }

	    System.out.println("---------------");
	}


	
}
