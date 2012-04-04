import java.util.ArrayList;
import java.util.Collection;


public class User {
	String user_name;
	Collection<Group> user_groups = new ArrayList<Group>();
	Group default_group;
	boolean hasgroup= false;
	int crtgrp =0;
	User(String name){
		user_name = name;
		hasgroup = false;
		
	}
	User(String name,Group grp){
		default_group = grp;
		user_name= name;
		hasgroup = true;
		user_groups.add(grp);
	}
	 @Override public String toString() {
		 return user_name + " ";
	 }
}
