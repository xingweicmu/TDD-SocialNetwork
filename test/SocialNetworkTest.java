import static org.junit.Assert.*;

import java.util.Collection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SocialNetworkTest {

	private SocialNetwork sn;
	private Account me;
	private Account her;
	
	@Before
	public void setUp() throws Exception {
		sn = new SocialNetwork();
	}

	@After
	public void tearDown() throws Exception {
	
	}

	@Test 
	public void canJoinSocialNetwork() {		
		me = sn.join("Hakan");
		assertEquals("Hakan", me.getUserName());
	}
	
	@Test 
	public void canJoinSocialNetworkOnlyWithUsername() {		
		Account noNamePerson = sn.join("");
		assertNull(noNamePerson);
	}
	
	@Test
	public void cannotJoinSocialNetworkWithNullUsername() {
		Account nullPerson = sn.join(null);
		assertNull(nullPerson);
	}
	
	@Test
	public void cannotJoinSocialNetworkWithExsitingUsername() {
		Account me = sn.join("Hakan");
		Account nullPerson = sn.join("Hakan");
		assertNull(nullPerson);
	}
	
	@Test 
	public void canListSingleMemberOfSocialNetwork() {
		sn.join("Hakan");
		Collection<String> members = sn.listMembers();
		assertEquals(1, members.size());
		assertTrue(members.contains("Hakan"));
	}
	
	@Test 
	public void twoPeopleCanJoinSocialNetwork() {
		sn.join("Hakan");
		sn.join("Cecile");
		Collection<String> members = sn.listMembers();
		assertEquals(2, members.size());
		assertTrue(members.contains("Hakan"));
		assertTrue(members.contains("Cecile"));
	}
	
	@Test 
	public void aMemberCanSendAFriendRequestToAnother() {
		
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		sn.sendFriendRequestTo("Cecile", me);
		//sn.sendFriendRequestTo("Hakan", her);
		assertTrue(her.whoWantsToBeFriends().contains("Hakan"));
		assertTrue(me.whoDidIAskToBefriend().contains("Cecile"));
//		assertTrue(her.whoDidIAskToBefriend().contains("Hakan"));
//		assertTrue(me.whoWantsToBeFriends().contains("Cecile"));
	}
	
	@Test
	public void aMemberCanSendRepeatedFriendRequestsToAnother() {
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		sn.sendFriendRequestTo("Cecile", me);
		sn.sendFriendRequestTo("Cecile", me);
		her.whoWantsToBeFriends().remove("Hakan");
		assertFalse(her.whoWantsToBeFriends().contains("Hakan"));
		assertTrue(me.whoDidIAskToBefriend().contains("Cecile"));
		me.whoDidIAskToBefriend().remove("Cecile");
		assertFalse(me.whoDidIAskToBefriend().contains("Cecile"));
	}
	
	@Test
	public void sendingFriendRequestsToNonExsitingAccount() {
		me = sn.join("Hakan");
		sn.sendFriendRequestTo("Cecile", me);
		assertFalse(me.whoDidIAskToBefriend().contains("Cecile"));
	}
	
	@Test 
	public void aMemberCanAcceptAFriendRequestFromAnother() {
		
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		sn.sendFriendRequestTo("Cecile", me);
		sn.acceptFriendshipFrom("Hakan", her);
		assertTrue(me.hasFriend("Cecile"));
		assertTrue(her.hasFriend("Hakan"));
	}
	
	@Test
	public void canOnlyAcceptFriendsWithFriendRequest() {
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		sn.acceptFriendshipFrom("Hakan", her);
		assertFalse(her.hasFriend("Hakan"));
		assertFalse(me.hasFriend("Cecile"));
	}
	
	@Test
	public void canTest(){
		me = sn.join("Hakan");
		her = sn.join("Serra");
		me.accepted(her);
		//sn.acceptFriendshipFrom("Hakan", her);
		assertFalse(her.hasFriend("Hakan"));
		assertFalse(me.hasFriend("Serra"));
	}
	
	@Test
	public void canListAllFriendsRequests(){
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		Account anotherHer = sn.join("Lily");
		sn.sendFriendRequestTo("Cecile", me);
		sn.sendFriendRequestTo("Lily", me);
		
		assertTrue(me.whoDidIAskToBefriend().contains("Cecile"));
		assertTrue(me.whoDidIAskToBefriend().contains("Lily"));
	}
	
	@Test
	public void canAcceptAllFriendRequestsTo() {
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		Account anotherHer = sn.join("Lily");
		sn.sendFriendRequestTo("Hakan", her);
		sn.sendFriendRequestTo("Hakan", anotherHer);
		sn.acceptAllFriendRequestsTo(me);
		assertTrue(me.hasFriend("Cecile"));
		assertTrue(me.hasFriend("Lily"));
	}
	
	@Test
	public void canReject() {
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		sn.sendFriendRequestTo("Cecile", me);
		sn.rejectFriendRequestFrom("Hakan",her);
		assertFalse(me.hasFriend("Cecile"));
		assertFalse(her.whoWantsToBeFriends().contains("Hakan"));
		assertFalse(me.whoDidIAskToBefriend().contains("Cecile"));
	}
	
	@Test
	public void cannotBeInPedingReqeustsIfAlreadyFriends(){
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		sn.sendFriendRequestTo("Hakan", her);
		sn.acceptFriendshipFrom("Cecile", me);
		sn.sendFriendRequestTo("Hakan", her);
		assertFalse(her.whoDidIAskToBefriend().contains("Hakan"));
		assertFalse(me.whoWantsToBeFriends().contains("Cecile"));
	}
	
	@Test
	public void canRejectAllFriendRequests() {
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		Account anotherHer = sn.join("Lily");
		sn.sendFriendRequestTo("Hakan", her);
		sn.sendFriendRequestTo("Hakan", anotherHer);
		sn.rejectAllFriendRequestsTo(me);
		assertFalse(me.whoWantsToBeFriends().contains("Cecile"));
		assertFalse(me.whoWantsToBeFriends().contains("Lily"));		
		assertFalse(her.whoDidIAskToBefriend().contains("Hakan"));
		assertFalse(anotherHer.whoDidIAskToBefriend().contains("Hakan"));
	}
	
	@Test
	public void canAutoAcceptFriendRequests() {
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		Account anotherHer = sn.join("Serra");
		sn.sendFriendRequestTo("Hakan", anotherHer); // anotherHer want to be friend with Hakan(me)
		sn.autoAcceptFriendRequestsTo(me);
		sn.sendFriendRequestTo("Hakan", her);	// her wants to be friend with Hakan(me)
		assertTrue(me.hasFriend("Cecile"));
		assertTrue(her.hasFriend("Hakan"));
		assertFalse(me.hasFriend("Serra"));
		assertFalse(anotherHer.hasFriend("Hakan"));
		assertFalse(her.whoDidIAskToBefriend().contains("Hakan"));
		assertTrue(anotherHer.whoDidIAskToBefriend().contains("Hakan"));
		assertFalse(me.whoWantsToBeFriends().contains("Cecile"));
		assertTrue(me.whoWantsToBeFriends().contains("Serra"));
	}
		
	@Test
	public void canSendUnfriendRequest() {
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		sn.sendFriendRequestTo("Cecile", me);
		sn.acceptFriendshipFrom("Hakan", her);
		sn.sendUnfriendRequestTo("Cecile",me);
		assertFalse(her.hasFriend("Hakan"));
		assertFalse(me.hasFriend("Cecile"));		
	}
	
	@Test
	public void canLeave() {
		me = sn.join("Hakan");
		her = sn.join("Cecile");
		Account anotherPerson = sn.join("Lily");
		Account anotherAnotherPerson = sn.join("Jan");
		sn.sendFriendRequestTo("Jan", me);
		sn.sendFriendRequestTo("Hakan",anotherPerson);
		sn.sendFriendRequestTo("Cecile", me);
		sn.acceptFriendshipFrom("Hakan", her);
		sn.leave(me);
		assertFalse(sn.listMembers().contains(me));
		assertFalse(her.hasFriend("Hakan"));
		assertFalse(anotherPerson.whoDidIAskToBefriend().contains("Hakan"));
		assertFalse(anotherAnotherPerson.whoWantsToBeFriends().contains("Hakan"));
	}
	
	@Test
	public void receivingAFriendRequestFromNullAccount() {
		me = sn.join("Hakan");
		sn.sendFriendRequestTo("Hakna", null);
		assertFalse(me.whoWantsToBeFriends().contains(null));
	}
	

	
}
