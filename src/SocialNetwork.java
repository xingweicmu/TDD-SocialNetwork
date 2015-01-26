import java.util.Collection;
import java.util.HashSet;

public class SocialNetwork {

	private Collection<Account> accounts = new HashSet<Account>();

	public Account join(String userName) {
		if (userName != null && !userName.equals("")
				&& !this.listMembers().contains(userName)) {
			Account newAccount = new Account(userName);
			accounts.add(newAccount);
			return newAccount;
		} else {
			return null;
		}
	}

	private Account findAccountForUserName(String userName) {
		for (Account each : accounts) {
			if (each.getUserName().equals(userName))
				return each;
		}
		return null;
	}

	public Collection<String> listMembers() {
		Collection<String> members = new HashSet<String>();
		for (Account each : accounts) {
			members.add(each.getUserName());
		}
		return members;
	}

	public void sendFriendRequestTo(String userName, Account me) {
		if (me != null && findAccountForUserName(userName) != null && !me.hasFriend(userName)) {

			findAccountForUserName(userName).befriend(me);
//			if (!me.whoDidIAskToBefriend().contains(userName))
//				//me.addToAskToBeFriendLists(findAccountForUserName(userName));
//				me.whoDidIAskToBefriend().add(userName);

//			if (findAccountForUserName(userName).getAutoFlag()) {
//				me.accepted(findAccountForUserName(userName));
//				// me.whoDidIAskToBefriend().remove(userName);
//			} else {}
		}
	}

	public void acceptFriendshipFrom(String userName, Account me) {
		if (me != null) {
			if (me.whoWantsToBeFriends().contains(userName)
					&& findAccountForUserName(userName).whoDidIAskToBefriend()
							.contains(me.getUserName()))
				findAccountForUserName(userName).accepted(me);
		}
	}

	public void acceptAllFriendRequestsTo(Account me) {

		// Collection<String> pendings= me.whoWantsToBeFriends();
		// for(String name : pendings){
		// findAccountForUserName(name).accepted(me);
		// }
		Collection<String> pendingFriends = me.whoWantsToBeFriends();
		for (String friend : pendingFriends) {
			findAccountForUserName(friend).whoDidIAskToBefriend().remove(
					me.getUserName());
		}
		me.acceptAll();
	}

	public void rejectFriendRequestFrom(String userName, Account me) {
		findAccountForUserName(userName).rejected(me);

	}

	public void rejectAllFriendRequestsTo(Account me) {
		Collection<String> pendingFriends = me.whoWantsToBeFriends();
		for (String friend : pendingFriends) {
			findAccountForUserName(friend).whoDidIAskToBefriend().remove(
					me.getUserName());
		}
		me.rejectALL();
	}

	public void autoAcceptFriendRequestsTo(Account me) {
		me.autoAccept();
		// Collection<String> pendingFriends = me.whoWantsToBeFriends();
		// for (String friend : pendingFriends) {
		// findAccountForUserName(friend).whoDidIAskToBefriend().remove(
		// me.getUserName());
		// }
		// me.acceptAll();
	}

	public void sendUnfriendRequestTo(String userName, Account me) {
		findAccountForUserName(userName).unfriend(me);

	}

	public void leave(Account me) {
		// Remove pending requests
		Collection<String> pendingFriends = me.whoWantsToBeFriends();
		for (String friend : pendingFriends) {
			findAccountForUserName(friend).whoDidIAskToBefriend().remove(
					me.getUserName());
		}
		// Remove who I asked to be friend
		Collection<String> whoDidIAskToBefriend = me.whoDidIAskToBefriend();
		for (String person : whoDidIAskToBefriend) {
			findAccountForUserName(person).whoWantsToBeFriends().remove(
					me.getUserName());
		}

		// Remove friends
		Collection<String> friends = me.getFriends();
		for (String each : friends) {
			findAccountForUserName(each).unfriend(me);
		}
		accounts.remove(me);

	}
}
