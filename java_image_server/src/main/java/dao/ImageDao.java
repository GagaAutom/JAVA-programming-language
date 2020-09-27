package dao;

import common.JavaImageServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//这个类是image对象的管理类，借助这个类完成image对象的增删改查操作
public class ImageDao {
    /**
     * 把 image 对象插入到数据库中
     *
     * @param image
     */
    public void insert(Image image) {
        // 1. 获取数据库连接
        Connection connection = DBUtil.getConnection();
        // 2. 创建并拼装 SQL 语句
        //？表示占位符，从下标1开始，imageId 是自增主键，不需要设置
        String sql = "insert into image_table values(null, ?, ?, ?, ?, ?, ?)";
        //Statement 对象用于将sql语句发送到数据库，PreparedStatement 是Statement对象的一种，最常用
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            //从Image类中获得image对象的属性信息，将拼装好的sql语句发送到数据库，
            // 数据库中的表中就插入了一张照片属性信息
            statement.setString(1, image.getImageName());
            statement.setInt(2, image.getSize());
            statement.setString(3, image.getUploadTime());
            statement.setString(4, image.getContentType());
            statement.setString(5, image.getPath());
            statement.setString(6, image.getMd5());

            // 3. 执行 SQL 语句
            //executeUpdate()返回一个整数，常用于insert,delect,update语句
            int ret = statement.executeUpdate();
            //说明将sql语句发送给数据库失败
            if (ret != 1) {
                // 程序出现问题, 抛出一个异常
                throw new JavaImageServerException("插入数据库出错!");
            }
        } catch (SQLException | JavaImageServerException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭连接和statement对象
            //插入没有结果集
            //无论插入成功还是失败都要关闭连接
            DBUtil.close(connection, statement, null);
        }
    }

    /**
     * 查找数据库中的所有图片的信息
     * 所有图片是一个集合，集合里边放的是一张张图片，每张图片是一个Image对象
     *
     * @return
     */
    public List<Image> selectAll() {
        List<Image> images = new ArrayList<>();
        // 1. 获取数据库连接
        Connection connection = DBUtil.getConnection();
        // 2. 构造 SQL 语句
        String sql = "select * from image_table";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 3. 执行 SQL 语句
            statement = connection.prepareStatement(sql);
            //executeQuery() 返回单个结果集
            resultSet = statement.executeQuery();
            // 4. 处理结果集
            while (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("imageId"));
                image.setImageName(resultSet.getString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getString("uploadTime"));
                image.setContentType(resultSet.getString("contentType"));
                image.setPath(resultSet.getString("path"));
                image.setMd5(resultSet.getString("md5"));
                images.add(image);
            }
            return images;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭连接
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    /**
     * 根据 imageId 查找指定的图片信息
     *
     * @param imageId
     * @return
     */
    public Image selectOne(int imageId) {
        // 1. 获取数据库连接
        Connection connection = DBUtil.getConnection();
        // 2. 构造 SQL 语句
        String sql = "select * from image_table where imageId = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 3. 执行 SQL 语句
            statement = connection.prepareStatement(sql);
            statement.setInt(1, imageId);
            resultSet = statement.executeQuery();
            // 4. 处理结果集
            if (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("imageId"));
                image.setImageName(resultSet.getString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getString("uploadTime"));
                image.setContentType(resultSet.getString("contentType"));
                image.setPath(resultSet.getString("path"));
                image.setMd5(resultSet.getString("md5"));
                return image;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. 关闭链接
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    /**
     * 根据 imageId 删除指定的图片
     *
     * @param imageId
     */
    public void delete(int imageId) {
        // 1. 获取数据库连接
        Connection connection = DBUtil.getConnection();
        // 2. 拼装 SQL 语句
        String sql = "delete from image_table where imageId = ?";
        PreparedStatement statement = null;
        // 3. 执行 SQL 语句
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, imageId);
            int ret = statement.executeUpdate();
            if (ret != 1) {
                throw new JavaImageServerException("删除数据库操作失败");
            }
        } catch (SQLException | JavaImageServerException e) {
            e.printStackTrace();
        } finally {
            // 4. 关闭连接
            DBUtil.close(connection, statement, null);
        }
    }


    public static void main(String[] args) {
        // 用于进行简单的测试
        //1. 测试插入数据
        Image image = new Image();
        image.setImageName("1.png");
        image.setSize(100);
        image.setUploadTime("20200216");
        image.setContentType("image/png");
        image.setPath("./data/1.png");
        image.setMd5("11223344");
        ImageDao imageDao = new ImageDao();
        imageDao.insert(image);
        // 2. 测试查找所有图片信息
//        ImageDao imageDao = new ImageDao();
//        List<Image> images = imageDao.selectAll();
//        System.out.println(images);

        // 3. 测试查找指定图片信息
//        ImageDao imageDao = new ImageDao();
//        Image image = imageDao.selectOne(1);
//        System.out.println(image);

        // 4. 测试删除图片
//        ImageDao imageDao = new ImageDao();
//        imageDao.delete(1);
    }

    public Image selectByMd5(String md5){
        //1. 获取数据库连接
        Connection connection = DBUtil.getConnection();
        // 2. 构造 SQL 语句
        String sql = "select * from image_table where md5 = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 3. 执行 SQL 语句
            statement = connection.prepareStatement(sql);
            statement.setString(1, md5);
            resultSet = statement.executeQuery();

            // 4. 处理结果集
            if (resultSet.next()) {
                Image image = new Image();
                image.setImageId(resultSet.getInt("imageId"));
                image.setImageName(resultSet.getString("imageName"));
                image.setSize(resultSet.getInt("size"));
                image.setUploadTime(resultSet.getString("uploadTime"));
                image.setContentType(resultSet.getString("contentType"));
                image.setPath(resultSet.getString("path"));
                image.setMd5(resultSet.getString("md5"));
                return image;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            } finally {
            // 5. 关闭链接
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }
}

