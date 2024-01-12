package restaurant;

import menu.MenuCategory;

public enum RestaurantBranch {

	Bandung(MenuCategory.Special),
	Jakarta(MenuCategory.Special),
	Bali(MenuCategory.Special),
	Surabaya(MenuCategory.Local),
	Samarinda(MenuCategory.Local),
	Padang(MenuCategory.Local);
	
	private MenuCategory menuCategory;

	private RestaurantBranch(MenuCategory menuCategory) {
		this.menuCategory = menuCategory;
	}

	public MenuCategory getMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(MenuCategory menuCategory) {
		this.menuCategory = menuCategory;
	}
	
	
	
}

