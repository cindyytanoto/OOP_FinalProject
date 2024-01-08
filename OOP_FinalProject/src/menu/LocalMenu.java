package menu;

public class LocalMenu extends Menu {

	private String uniqueNar, origin;
	
	public LocalMenu(int menuId, String menuName, int price, String uniqueNar, String origin) {		
		super(menuId, menuName, price);
		this.uniqueNar = uniqueNar;
		this.origin = origin;
	}

	public String getUniqueNar() {
		return uniqueNar;
	}

	public void setUniqueNar(String uniqueNar) {
		this.uniqueNar = uniqueNar;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	

}
