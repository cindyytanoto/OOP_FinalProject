package view;

import utility.Helper;

public abstract class MasterPage {
	
	Helper helper = new Helper().getInstance();
	
	public abstract void show();

}
