package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，这样junit在启动时就会加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;
    @Test
    public void queryById() {
        long seckillId=1000;
        Seckill seckill=seckillDao.queryById(seckillId);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }
    /*Caused by: org.apache.ibatis.binding.BindingException: Parameter 'offset' not found.
    Available parameters are [1, 0, param1, param2]
    意思就是无法完成offset参数的绑定，这也是我们java编程语言的一个问题，也就是java没有保存行参的记录.
    java在运行的时候会把List<Seckill> queryAll(int offset,int limit)中的参数变成这样:queryAll(int arg0,int arg1),这样我们就没有办法去传递多个参数。
    需要在SeckillDao接口中修改方法:
    List<Seckill> queryAll(@Param("offset") int offset,@Param("limit") int limit);
     */
    @Test
    public void queryAll() {
        List<Seckill> seckills=seckillDao.queryAll(0,100);
        for (Seckill seckill : seckills)
        {
            System.out.println(seckill);
        }
    }
    /*
    同上一样的问题，更改SeckillDao接口的reduceNumber()方法:
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
     */
    @Test
    public void reduceNumber() {
        long seckillId=1000;
        Date date=new Date();
        int updateCount=seckillDao.reduceNumber(seckillId,date);
        System.out.println(updateCount);
    }

}