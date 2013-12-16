package edu.buaa.scse.niu.wechat.engine.repository;

public interface RepositoryTest {

	public static enum TestType{
		Insert,Delete,Update
	}
	public void testInsert();
	
	public void deleteAll();
	
	public void testUpdate();
	
	public void testFind();
	
	public void testUser();
	
	public void testUserGroup();
	
	public void testUserRelationship(TestType type);
	
	public void testChatGroup(TestType type);
	
	public void testSingleMessage();
	
	public void testGroupMessage();
	
	public void testOfflineMessage();
	
}
