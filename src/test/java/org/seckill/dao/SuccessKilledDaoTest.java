package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;
    /*
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
    运行第一次返回更新数为1，
    第二次运行该测试方法，程序没有报主键异常的错，是因为我们在编写我们的明细表的时候添加了一个联合主键的字段，它保证我们明细表中的seckillId和userPhone不能重复插入。
    另外在SuccessDao.xml中写的插入语句的ignore关键字保证控制台输出0，表示没有对明细表做插入操作。
     */
    @Test
    public void insertSuccessKilled() {
        long seckillId=1000;
        long userPhone=13476191888L;
        int insertCount=successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long seckillId=1000L;
        long userPhone=13476191888L;
        SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}