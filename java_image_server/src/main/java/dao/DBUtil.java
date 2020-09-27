
package dao;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//DBUtil这个类来封装获取数据库连接的过程
public class DBUtil {
    private static final String URL ="jdbc:mysql://127.0.0.1:3306/java_image_server?characterEncoding=utf8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    //获取 Connection 对象，通过 Datasource（数据源） 来获取
    //volatile保证可见性，使数据及时刷新到主内存，保证可见性
    private static volatile DataSource dataSource = null;

    // 通过这个方法来创建 DataSource 的实例
    public static DataSource getDataSource() {
        if (dataSource == null) {
            //加锁是比较耗时的操作，所以加上一层判断（if(dataSource==null)）可以减少损耗时间
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    //这句是线程不安全的，执行到这里时，另外一个线程可能会抢占CPU
                    //在另一个线程看来，dataSource ==null ，另外一个线程也会调用 get 方法，导致 dataSource
                    //这个对象被创建了两次，所以线程不安全，给这个类加锁，另外一个线程就不会进来了
                    dataSource = new MysqlDataSource();
                    MysqlDataSource tmpDataSource = (MysqlDataSource) dataSource;
                    tmpDataSource.setURL(URL);
                    tmpDataSource.setUser(USERNAME);
                    tmpDataSource.setPassword(PASSWORD);
                }
            }
        }
        return dataSource;
    }

    //获取连接，每次收到请求获取连接，DataSource内置连接池
    public static Connection getConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //释放资源，关闭连接
    //关闭连接不是真的销毁了这条连接，只是回到池子里
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
