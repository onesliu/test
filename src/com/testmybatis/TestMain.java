package com.testmybatis;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TestMain {

	private static SqlSessionFactory factory;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Reader res = Resources.getResourceAsReader("com/testmybatis/data/config.xml");
		Properties props = Resources.getResourceAsProperties("config.properties");
		factory = new SqlSessionFactoryBuilder().build(res, props);
		
		TestMain myself = new TestMain();
		Random random = new Random();
		
		User user = new User();
		user.setUsername("张三" + random.nextInt());
		user.setSex(false);
		myself.insertUser(user);
		user.setUsername("李四" + random.nextInt());
		user.setSex(true);
		myself.insertUser(user);
		
		myself.selectUsers();
	}
	
	public void selectUsers() {
		SqlSession session = factory.openSession();
		try {
			List<User> userList = session.selectList("com.user.selectAllUser");
			for(User user: userList) {
				System.out.println("user: " + user.getId() + " name:" + user.getUsername() + " sex:" + user.getSex());
			}
		}finally {
			session.close();
		}
	}
	
	public void insertUser(User user) {
		SqlSession session = factory.openSession();
		try {
			session.insert("com.user.insertUser", user);
		}finally {
			session.close();
		}
	}

}
