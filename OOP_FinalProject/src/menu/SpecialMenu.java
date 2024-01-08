package menu;

public class SpecialMenu extends Menu {

	private String story;

	public SpecialMenu(int menuId, String menuName, int price, String story) {
		super(menuId, menuName, price);
		this.story = story;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

}
