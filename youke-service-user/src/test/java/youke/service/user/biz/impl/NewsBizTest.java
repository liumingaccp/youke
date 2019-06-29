package youke.service.user.biz.impl;

import net.sf.json.JSONObject;
import org.junit.Test;
import youke.common.redis.RedisUtil;
import youke.service.user.biz.INewsBiz;
import youke.service.user.biz.IUserBiz;
import youke.service.user.provider.BaseJunit4Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class NewsBizTest extends BaseJunit4Test {

    @Resource
    private IUserBiz userBiz;

    @Test
    public void testUser(){
        System.out.println(userBiz.getRemainingAmount("dykb7Yhmay"));
    }

    @Test
    public void setRedis(){
        RedisUtil.hset("HEZUO-DIANYK-NUM","dykb7Yhmay",300);
    }
}