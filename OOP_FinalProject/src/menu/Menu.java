package menu;

public class Menu {

	private int menuId;
	private String menuName;
	private int price;

	public Menu(int menuId, String menuName, int price) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
		this.price = price;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
