package com.zhiyou100.myRealm;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.zhiyou100.dao.UserDao;
import com.zhiyou100.model.User;

public class MyRealm extends AuthorizingRealm{

	@Override//用于授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection p) {
		SqlSessionFactory sf = null;
		try {
			sf = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("MybatisConf.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SqlSession sql = sf.openSession();
	    UserDao mapper = sql.getMapper(UserDao.class);
	    int roleId = mapper.selectByRole((String)p.getPrimaryPrincipal());
	    List<String> roles = mapper.selectByRoleId(roleId);
	    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
	    for (String string : roles) {
			info.addStringPermission(string);
		}
	    //info.addRole(role);封装角色
		return info;
	}

	@Override//用于认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String principal = (String) token.getPrincipal();
		System.out.println("MyRealm"+principal);
		SqlSessionFactory sf = null;
		try {
			sf = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("MybatisConf.xml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SqlSession sql = sf.openSession();
	    UserDao mapper = sql.getMapper(UserDao.class);
	    User user = mapper.selectByName(principal);
	    sql.close();
		if(user==null) {
			return null;
		}
		//如果执行到这里，证明用户存在
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),"myRealm");
		return info;
	}

}
