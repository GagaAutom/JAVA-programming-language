package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ImageDaoTest {

    @Before
    public void before(){
        System.out.println("----------before--------------");
    }

    @After
    public void after(){
        System.out.println("----------after--------------");
    }
    @Ignore
    @Test
    public void insert() {
        //准备测试数据
        ImageDao imageDao = new ImageDao();
        Image image = new Image();

        image.setImageName("test.png");
        image.setUploadTime("20200629");
        image.setPath("./image/fbab242a577efdf0c530a27a92e0c255");
        image.setMd5("fbab242a577efdf0c530a27a92e0c255");
        image.setContentType("image/png");
        image.setSize(45197);

        //测试,在数据库中可查看是否插入成功
        imageDao.insert(image);
    }

    @Test
    public void selectAll() {
        ImageDao imageDao = new ImageDao();
        List<Image> lists = imageDao.selectAll();
        System.out.println(lists.size());
        System.out.println();
        for (Image image:lists) {
            System.out.println("imageId = " + image.getImageId()+" "+"imageName = "+image.getImageName());
        }
    }

    @Test
    public void selectOne() {
        ImageDao imageDao = new ImageDao();
        try {
            Image image = imageDao.selectOne(2);

            System.out.println(image.getImageName());
            System.out.println(image.getImageId());
            System.out.println(image.getSize());
            System.out.println(image.getMd5());
        }catch (Exception e){
            System.out.println("查询失败，imageId 不存在！！！");
        }
    }

    @Test
    public void delete() {
        ImageDao imageDao = new ImageDao();
        List<Image> lists1 = imageDao.selectAll();
        System.out.println("删除前图片容量" + lists1.size());
        imageDao.delete(54);
        List<Image> lists2 = imageDao.selectAll();
        System.out.println("删除后图片容量" + lists2.size());
        if(lists1.size()>lists2.size()) {
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败，imageId 不存在！！！");
        }
    }

    @Test
    public void selectByMd5() {
        ImageDao imageDao = new ImageDao();
        try {
            Image image = imageDao.selectByMd5("fbab242a577efdf0c530a27a92e0c255");
            System.out.println(image.getImageId());
            System.out.println(image.getImageName());
            System.out.println(image.getPath());
        } catch (Exception e) {
            System.out.println("查询失败，Md5 不存在！！！");
        }
    }
}