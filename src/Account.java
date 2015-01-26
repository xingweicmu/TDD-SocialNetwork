import java.util.Collection;
import java.util.HashSet;

public class Account {

	private String userName;
	private boolean autoAcceptFlag = false;
	private Collection<String> pendingResponses = new HashSet<String>();
	private Collection<String> friends = new HashSet<String>();
	private Collection<String> pendingRequests = new HashSet<String>();

	public Account(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public Collection<String> whoWantsToBeFriends() {
		return pendingResponses;
	}

	/* a friend request from another account */
	public void befriend(Account fromAccount) {
		if (fromAccount != null) {
			if (!friends.contains(fromAccount.getUserName())) {
				pendingResponses.add(fromAccount.getUserName());
				fromAccount.whoDidIAskToBefriend().add(this.getUserName());
			}
		}
	}

	/*
	 * an acceptance notification from an account that a friend request sent
	 * from this account has been accepted
	 */
	public void accepted(Account toAccount) {
		if (toAccount != null) {

			if (this.whoDidIAskToBefriend().contains(toAccount.getUserName())
					&& toAccount.whoWantsToBeFriends().contains(userName)) {
				friends.add(toAccount.getUserName());
				// this.whoDidIAskToBefriend().remove(toAccount.getUserName());
				toAccount.friends.add(this.getUserName());
				toAccount.pendingResponses.remove(this.getUserName());
				this.whoDidIAskToBefriend().remove(toAccount.getUserName());
			}
		}
	}

//	public void addToAskToBeFriendLists(Account theAccount) {
//		if (theAccount != null) {
//			pendingRequests.add(theAccount.getUserName());
//		}
//	}

	public boolean hasFriend(String userName) {
		return friends.contains(userName);
	}

	public Collection<String> whoDidIAskToBefriend() {
		return pendingRequests;
	}

	public void acceptAll() {
		friends.addAll(pendingResponses);
		pendingResponses.clear();
	}

	public void rejected(Account theOther) {
		if (theOther != null) {
			theOther.whoWantsToBeFriends().remove(this.getUserName());
			this.whoDidIAskToBefriend().remove(theOther.getUserName());
		}
	}

	public void rejectALL() {
		this.whoWantsToBeFriends().clear();
	}

	public void autoAccept() {
		this.autoAcceptFlag = true;
	}

	public boolean getAutoFlag() {
		return this.autoAcceptFlag;
	}

	public void unfriend(Account theOther) {
		if (theOther != null) {
			this.friends.remove(theOther.getUserName());
			theOther.friends.remove(this.getUserName());
		}
	}

	public Collection<String> getFriends() {
		return friends;
	}

}
