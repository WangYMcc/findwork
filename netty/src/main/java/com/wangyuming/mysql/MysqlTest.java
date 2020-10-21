package com.wangyuming.mysql;

import com.github.pagehelper.PageHelper;
import com.mchange.v1.db.sql.ConnectionUtils;
import com.wangyuming.mysql.dao.UserDao;
import com.wangyuming.mysql.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MysqlTest {
    public static void main(String[] args) {
        try {
            //加载核心配置文件
            InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
            //获得sqlSession工厂对象
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
            //获得sqlSession对象
            SqlSession sqlSession = sqlSessionFactory.openSession();
            //执行sql语句
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            //List<User> userList = sqlSession.selectList("userMapper.findAll");
            PageHelper.startPage(1, 2);
            List<User> userList = userDao.findAll();
            //打印结果
            System.out.println(userList);
            //释放资源
            sqlSession.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}