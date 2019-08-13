package com.zhiyou100;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class MainClass1 {
	
	@SuppressWarnings("deprecation")
	@Test
	public void shiro() {
		String username = "zhangsan";  //假装接收用户名
	    String password = "123";    //假装接收密码
		//1:加载realm配置文件初始化SecurityManagerFactory
	    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:realm2.ini");
	    //2:通过工厂获取对象
	    SecurityManager sm = factory.getInstance();
	    //3:通过SecurityUtils把SecurityManager对象放到运行环境中
	    SecurityUtils.setSecurityManager(sm);
	    //4:通过SecurityUtils获取Subject对象
	    Subject subject = SecurityUtils.getSubject();
	    //5:根据用户登录信息创建token
	    UsernamePasswordToken token = new UsernamePasswordToken(username,password);
	    try {
	    	subject.login(token);
	    }catch(IncorrectCredentialsException e) {
	    	System.out.println("认证失败");
	    }

	    //用户是否认证成功
	    boolean b = subject.isAuthenticated();
	    System.out.println("用户是否认证成功"+b);
	    
	    //判断用户是否有某一个角色
	    /*boolean hasRole = subject.hasRole("role1");
	    System.out.println(hasRole);*/
	    
	   //判断用户是否有多个角色
	    /*boolean rs = subject.hasAllRoles(Arrays.asList("role1","role2"));
	    System.out.println(rs);*/
	    //判断用户是否有某个权限
	    boolean permitted = subject.isPermitted("select");
	    System.out.println(permitted);
	    //判断用户是否有多个权限
	    boolean permittedAll = subject.isPermittedAll("select","create");
	    System.out.println(permittedAll);
	    
	    //退出
	    subject.logout();
	    boolean b2 = subject.isAuthenticated();
	    System.out.println("用户是否认证成功"+b2);
	}

}
