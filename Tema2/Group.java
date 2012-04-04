import java.util.*;


public class Group {
	
	public String group_name;
	public int nr_users;
	User group_owner;
	
	Collection<User> users_group = new ArrayList<User>();
	Group(String name,User user){
		this.group_name= name;
		group_owner = user;
		nr_users ++;
		users_group.add(user);
	}
	String toString(Group g){
		return g.group_name + "  " + g.group_owner.user_name;
	}
}
