package youke.service.wx.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.tools.corba.se.idl.PragmaEntry;
import org.springframework.stereotype.Service;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.*;
import youke.common.model.vo.page.PageModel;
import youke.common.model.vo.param.MassFansQueryVo;
import youke.common.model.vo.param.WxFansQueryVo;
import youke.common.model.vo.result.MassFansVo;
import youke.common.model.vo.result.MassRecordVo;
import youke.common.utils.DateUtil;
import youke.common.utils.StringUtil;
import youke.facade.wx.queue.message.SuperMassMessage;
import youke.facade.wx.util.Constants;
import youke.facade.wx.util.MediaType;
import youke.facade.wx.vo.mass.TaskParam;
import youke.facade.wx.vo.material.NewsTreeVo;
import youke.facade.wx.vo.material.SysNewsTreeVo;
import youke.service.wx.biz.IMaterialBiz;
import youke.service.wx.biz.IWxMassFansBiz;
import youke.service.wx.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.*;

import static org.apache.xmlbeans.XmlBeans.getTitle;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 19:51
 */
@Service
public class WxMassFansBiz implements IWxMassFansBiz {

    @Resource
    private IMassFansDao massFansDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IMassTaskDao massTaskDao;
    @Resource
    private IMassRecordDao massRecordDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private QueueSender queueSender;
    @Resource
    private IMaterialNewsDao materialNewsDao;
    @Resource
    private IMaterialImageDao materialImageDao;
    @Resource
    private IMaterialSysnewsDao materialSysnewsDao;
    @Resource
    private IMaterialBiz materialBiz;
    @Resource
    private IMassTaskOpenIdDao massTaskOpenIdDao;

    public PageInfo<MassFansVo> getSendlist(MassFansQueryVo params, int type) {
        List<TMassFans> fansPage = new ArrayList<TMassFans>();
        params.setTags(params.getTagIds());
        //筛选包含有 tags 的 openId
        //存在标签的筛选的时候进行这个查询
        if (params.getTagIds() != null && !"".equals(params.getTagIds())) {
            WxFansQueryVo idQuqery = new WxFansQueryVo(params.getTagIds(), params.getAppId());
            idQuqery.setTagFilter(params.getTagFilter());
            //得到所有满足的Id
            List<String> openIds = subscrFansDao.queryIdList(idQuqery);

            if (openIds != null && openIds.size() > 0) {
                params.setOpenIds(openIds);
            } else {
                return new PageInfo<>();
            }
        }
        if (params.getLimitSend() == null || params.getLimitSend() < -1 || params.getLimitSend() >= 4) {
            params.setLimitSend(-1);
        }

        PageHelper.startPage(params.getPage(), params.getLimit());
        //如果是类型3,则表示不受限制的查询
        if (type == 3) {
            //没有次数
            params.setWxTimeOut(new Date(new Date().getTime() - Constants.WX_TIME_OUT));
            fansPage = massFansDao.querySubList(params);
        } else if (type == 1) {
            fansPage = massFansDao.queryList(params);
        } else if (type == 2) {
            fansPage = massFansDao.queryList(params);
        }
        if (fansPage != null && fansPage.size() > 0) {
            for (TMassFans fans : fansPage) {
                Date lastsendtime = fans.getLastsendtime();
                if (lastsendtime == null || lastsendtime.getTime() < DateUtil.getMouthStartTimeForDate().getTime()) {
                    fans.setMonthtotal(0);
                }
            }
            PageModel<MassFansVo> massFansVos = new PageModel<MassFansVo>((Page) fansPage);
            addressPageMol((Page<TMassFans>) fansPage, massFansVos);
            return new PageInfo<>(massFansVos);
        }
        return new PageInfo<>();
    }

    public long getTotal(MassFansQueryVo params, int type) {
        long count = 0;
        //得到所有满足的openId,如果
        params.setTags(params.getTagIds());
        if (params.getTagIds() != null && !"".equals(params.getTagIds())) {
            WxFansQueryVo idQuqery = new WxFansQueryVo(params.getTagIds(), params.getAppId());
            idQuqery.setTagFilter(params.getTagFilter());
            //得到所有满足的Id
            List<String> openIds = subscrFansDao.queryIdList(idQuqery);
            if (openIds != null && openIds.size() > 0) {
                params.setOpenIds(openIds);
            } else {
                return 0;
            }
        }
        if (params.getLimitSend() == null) {
            params.setLimitSend(-1);
        }
        if (type == 2) {
            //没有次数
            params.setWxTimeOut(new Date(new Date().getTime() - Constants.WX_TIME_OUT));
            count = massFansDao.querySubCount(params);
        } else if (type == 1) {
            count = massFansDao.queryCount(params);
        }
        return count;
    }

    public void saveHighSendTask(TaskParam params) {
        TMassTask task = new TMassTask();

        task.setAppid(params.getAppId());
        task.setTagIds(params.getTagIds());
        task.setTagfilter(params.getTagFilter());
        task.setLimitsend(params.getLimitSend());
        task.setLasttimebeg(DateUtil.parseDate(params.getLastTimeBeg()));
        task.setLasttimeend(DateUtil.parseDate(params.getLastTimeEnd()));
        task.setSubtimebeg(DateUtil.parseDate(params.getSubTimeBeg()));
        task.setSubtimeend(DateUtil.parseDate(params.getSubTimeEnd()));
        task.setSex(params.getSex());
        task.setHasmobile(params.getHasMobile());
        task.setState(0);
        task.setProvince(params.getProvince());
        task.setCity(params.getCity());
        task.setMediatype(params.getMediaType());
        task.setMaterialid(params.getMaterialId());
        task.setContent(params.getContent());
        if (StringUtil.hasLength(params.getTaskTime())) {
            Date date = DateUtil.parseDate(params.getTaskTime());
            if (date.getTime() < new Date().getTime()) {
                task.setTasktime(new Date());
            } else {
                task.setTasktime(date);
            }
        } else {
            task.setTasktime(new Date());
        }
        task.setSendType(1);

        //2.粉丝发送记录更新
        //2.1筛选包含有 tags 的 openId
        MassFansQueryVo qo = new MassFansQueryVo();
        qo.setTagIds(task.getTagIds());
        qo.setTags(task.getTagIds());
        qo.setLimitSend(task.getLimitsend());
        qo.setTagFilter(params.getTagFilter());
        qo.setLastTimeBeg(params.getLastTimeBeg());
        qo.setLastTimeEnd(params.getLastTimeEnd());
        qo.setSubTimeBeg(params.getSubTimeBeg());
        qo.setSubTimeEnd(params.getSubTimeEnd());
        qo.setSex(task.getSex());
        qo.setProvince(task.getProvince());
        qo.setCity(task.getCity());
        qo.setAppId(task.getAppid());

        if (params.getTagIds() != null && !"".equals(params.getTagIds())) {
            //得到所有满足的Id
            WxFansQueryVo idQuqery = new WxFansQueryVo(params.getTagIds(), params.getAppId());
            idQuqery.setTagFilter(params.getTagFilter());
            //得到所有满足的Id
            List<String> openIds = subscrFansDao.queryIdList(idQuqery);
            if (openIds != null && openIds.size() > 0) {
                qo.setOpenIds(openIds);
            } else {
                throw new BusinessException("有效粉丝数量为0");
            }
        }
        //2.2查询全部
        PageHelper.startPage(1, 0);
        List<TMassFans> fans = massFansDao.queryList(qo);
        if (fans == null || fans.size() <= 0) {
            throw new BusinessException("有效粉丝数量为0");
        }


        //1.根据条件查询得到结果,插入任务
        massTaskDao.insertSelective(task);

        //2.记录发送结果
        TMassRecord record = new TMassRecord();
        record.setTaskid(task.getId());
        String content = task.getContent();
        String title = "";
        if (task.getMediatype().equals(MediaType.NEWS)) {
            TMaterialNews news = materialNewsDao.selectByPrimaryKey(task.getMaterialid());
            if (news != null) {
                title = news.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.IMG)) {
            TMaterialImage image = materialImageDao.selectByPrimaryKey(task.getMaterialid());
            if (image != null) {
                title = image.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.SYSNEWS)) {
            TMaterialSysnews sys = materialSysnewsDao.selectByPrimaryKey(task.getMaterialid());
            if (sys != null) {
                title = sys.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.TEXT)) {
            if (!StringUtil.hasLength(task.getContent())) {
                throw new BusinessException("发送内容不能为空");
            }
        }
        if (content != null) {
            if (content.length() > 50) {
                title = content.substring(0, 50);
            } else {
                title = content;
            }
        }
        record.setTitle(title);
        record.setSendtype(1);
        record.setTasktime(task.getTasktime());
        record.setSendnum(fans.size());
        record.setState(1);
        record.setFailnum(0);
        record.setSuccessnum(fans.size());
        record.setAppId(task.getAppid());
        massRecordDao.insertSelective(record);

        // 3.设置群发的次数和保存群发的openIds
        Date newDate = new Date();
        for (TMassFans massFans : fans) {
            //保存群发的openId
            TMassTaskOpenId taskOpenId = new TMassTaskOpenId();
            taskOpenId.setAppid(params.getAppId());
            taskOpenId.setOpenid(massFans.getOpenid());
            taskOpenId.setState(0);
            taskOpenId.setTaskid(task.getId());
            massTaskOpenIdDao.insertSelective(taskOpenId);

            //设置群发次数
            Date lastsendtime = massFans.getLastsendtime();
            if (lastsendtime != null && lastsendtime.getTime() >= DateUtil.getMouthStartTimeForDate().getTime()) {
                if (massFans.getMonthtotal() == null) {
                    massFans.setMonthtotal(1);
                } else {
                    massFans.setMonthtotal(massFans.getMonthtotal() + 1);
                }
            } else {
                massFans.setMonthtotal(1);
            }
            massFans.setLastsendtime(newDate);
            String openid = massFansDao.checkExite(massFans.getOpenid());
            if (openid != null) { //如果存在,更新内容
                massFansDao.updateByPrimaryKeySelective(massFans);
            } else {//如果不存在,增加内容
                massFans.setAppId(task.getAppid());
                massFansDao.insertSelective(massFans);
            }
        }

        queueSender.send("mass.queue", task);

    }

    public void saveTagSendTask(TaskParam params) {
        TMassTask task = new TMassTask();

        task.setAppid(params.getAppId());
        task.setTagIds(params.getTagIds());
        task.setMediatype(params.getMediaType());
        task.setMaterialid(params.getMaterialId());
        task.setContent(params.getContent());
        if (StringUtil.hasLength(params.getTaskTime())) {
            Date date = DateUtil.parseDate(params.getTaskTime());
            if (date.getTime() < new Date().getTime()) {
                task.setTasktime(new Date());
            } else {
                task.setTasktime(date);
            }
        } else {
            task.setTasktime(new Date());
        }
        task.setSendType(0);
        task.setState(0);
        task.setAppid(params.getAppId());

        //2.粉丝发送记录更新
        //2.1筛选包含有 tags 的 openId
        MassFansQueryVo qo = new MassFansQueryVo();
        qo.setTags(params.getTagIds());
        qo.setNickname(params.getNickname());
        qo.setAppId(params.getAppId());

        //填充查询条件
        if (params.getTagIds() != null && !"".equals(params.getTagIds())) {
            //得到所有满足的Id
            WxFansQueryVo idQuqery = new WxFansQueryVo(params.getTagIds(), params.getAppId());
            idQuqery.setTagFilter(params.getTagFilter());
            //得到所有满足的Id
            List<String> openIds = subscrFansDao.queryIdList(idQuqery);
            if (openIds != null && openIds.size() > 0) {
                qo.setOpenIds(openIds);
            } else {
                throw new BusinessException("有效粉丝数量为0");
            }
        }
        qo.setLimitSend(-1);
        //2.2查询全部
        PageHelper.startPage(1, 0);
        List<TMassFans> fans = massFansDao.queryList(qo);
        if (fans == null || fans.size() <= 0) {
            throw new BusinessException("有效粉丝数量为0");
        }

        //3. 根据条件查询得到结果,插入任务记录
        massTaskDao.insertSelective(task);

        //4. 记录发送结果
        String content = task.getContent();
        String title = "";
        if (task.getMediatype().equals(MediaType.NEWS)) {
            TMaterialNews news = materialNewsDao.selectByPrimaryKey(task.getMaterialid());
            if (news != null) {
                title = news.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.IMG)) {
            TMaterialImage image = materialImageDao.selectByPrimaryKey(task.getMaterialid());
            if (image != null) {
                title = image.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.SYSNEWS)) {
            TMaterialSysnews sys = materialSysnewsDao.selectByPrimaryKey(task.getMaterialid());
            if (sys != null) {
                title = sys.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.TEXT)) {
            if (!StringUtil.hasLength(task.getContent())) {
                throw new BusinessException("发送内容不能为空");
            }
        }
        if (content != null) {
            if (content.length() > 50) {
                title = content.substring(0, 50);
            } else {
                title = content;
            }
        }
        TMassRecord record = new TMassRecord();
        record.setTaskid(task.getId());
        record.setTitle(title);
        record.setSendtype(0);
        record.setTasktime(task.getTasktime());
        record.setSendnum(fans.size());
        record.setFailnum(0);
        record.setSuccessnum(fans.size());
        record.setState(1);
        record.setAppId(task.getAppid());
        massRecordDao.insertSelective(record);

        Date newDate = new Date();
        for (TMassFans massFans : fans) {
            //保存群发的openId
            TMassTaskOpenId taskOpenId = new TMassTaskOpenId();
            taskOpenId.setAppid(params.getAppId());
            taskOpenId.setOpenid(massFans.getOpenid());
            taskOpenId.setState(0);
            taskOpenId.setTaskid(task.getId());
            massTaskOpenIdDao.insertSelective(taskOpenId);

            Date lastsendtime = massFans.getLastsendtime();
            if (lastsendtime != null && lastsendtime.getTime() >= DateUtil.getMouthStartTimeForDate().getTime()) {
                if (massFans.getMonthtotal() == null) {
                    massFans.setMonthtotal(1);
                } else {
                    massFans.setMonthtotal(massFans.getMonthtotal() + 1);
                }
            } else {
                massFans.setMonthtotal(1);
            }
            massFans.setLastsendtime(newDate);
            String openid = massFansDao.checkExite(massFans.getOpenid());
            if (openid != null) { //如果存在,更新内容
                massFansDao.updateByPrimaryKeySelective(massFans);
            } else {//如果不存在,增加内容
                massFans.setAppId(task.getAppid());
                massFansDao.insertSelective(massFans);
            }
        }
        queueSender.send("mass.queue", task);
    }

    public void saveKefuSendTask(TaskParam params) {
        TMassTask task = new TMassTask();
        task.setAppid(params.getAppId());
        task.setTagIds(params.getTagIds());
        task.setTagfilter(params.getTagFilter());
        task.setLimitsend(params.getLimitSend());
        task.setLasttimebeg(DateUtil.parseDate(params.getLastTimeBeg()));
        task.setLasttimeend(DateUtil.parseDate(params.getLastTimeEnd()));
        task.setState(0);
        task.setSubtimebeg(DateUtil.parseDate(params.getSubTimeBeg()));
        task.setSubtimeend(DateUtil.parseDate(params.getSubTimeEnd()));
        task.setSex(params.getSex());
        task.setProvince(params.getProvince());
        task.setCity(params.getCity());
        task.setHasmobile(params.getHasMobile());
        task.setMediatype(params.getMediaType());
        task.setMaterialid(params.getMaterialId());
        task.setContent(params.getContent());
        if (StringUtil.hasLength(params.getTaskTime())) {
            Date date = DateUtil.parseDate(params.getTaskTime());
            if (date.getTime() < new Date().getTime()) {
                task.setTasktime(new Date());
            } else {
                task.setTasktime(date);
            }
        } else {
            task.setTasktime(new Date());
        }
        task.setSendType(2);
        task.setAppid(params.getAppId());
        //2.粉丝发送记录更新
        //2.1筛选包含有 tags 的 openId
        MassFansQueryVo qo = new MassFansQueryVo();
        qo.setTagIds(params.getTagIds());
        qo.setTags(params.getTagIds());
        qo.setTagFilter(params.getTagFilter());
        qo.setLastTimeBeg(params.getLastTimeBeg());
        qo.setLastTimeEnd(params.getLastTimeEnd());
        qo.setSubTimeBeg(params.getSubTimeBeg());
        qo.setSubTimeEnd(params.getSubTimeEnd());
        qo.setSex(task.getSex());
        qo.setProvince(task.getProvince());
        qo.setCity(task.getCity());
        qo.setAppId(task.getAppid());

        //填充查询条件
        if (params.getTagIds() != null && !"".equals(params.getTagIds())) {
            //得到所有满足的Id
            WxFansQueryVo idQuqery = new WxFansQueryVo(params.getTagIds(), params.getAppId());
            idQuqery.setTagFilter(params.getTagFilter());
            //得到所有满足的Id
            List<String> openIds = subscrFansDao.queryIdList(idQuqery);
            if (openIds != null && openIds.size() > 0) {
                qo.setOpenIds(openIds);
            } else {
                throw new BusinessException("有效粉丝数量为0");
            }
        }
        //没有次数
        qo.setWxTimeOut(new Date(new Date().getTime() - Constants.WX_TIME_OUT));
        //2.2查询全部
        List<TMassFans> fans = massFansDao.querySubList(qo);
        if (fans == null || fans.size() <= 0) {
            throw new BusinessException("有效粉丝数量为0");
        }

        //根据条件查询得到结果,插入任务记录
        massTaskDao.insertSelective(task);

        //3.记录发送结果
        String content = task.getContent();
        String title = "";
        if (task.getMediatype().equals(MediaType.NEWS)) {
            TMaterialNews news = materialNewsDao.selectByPrimaryKey(task.getMaterialid());
            if (news != null) {
                title = news.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.IMG)) {
            TMaterialImage image = materialImageDao.selectByPrimaryKey(task.getMaterialid());
            if (image != null) {
                title = image.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.SYSNEWS)) {
            TMaterialSysnews sys = materialSysnewsDao.selectByPrimaryKey(task.getMaterialid());
            if (sys != null) {
                title = sys.getTitle();
            }
        }
        if (task.getMediatype().equals(MediaType.TEXT)) {
            if (!StringUtil.hasLength(task.getContent())) {
                throw new BusinessException("发送内容不能为空");
            }
        }

        if (content != null) {
            if (content.length() > 50) {
                title = content.substring(0, 50);
            } else {
                title = content;
            }
        }
        TMassRecord record = new TMassRecord();
        record.setTaskid(task.getId());
        record.setTitle(title);
        record.setSendtype(2);
        record.setTasktime(task.getTasktime());
        record.setSendnum(fans.size());
        record.setState(1);
        record.setFailnum(0);
        record.setSuccessnum(fans.size());
        record.setAppId(task.getAppid());
        massRecordDao.insertSelective(record);

        for (TMassFans massFans : fans) {
            //保存群发的openId
            TMassTaskOpenId taskOpenId = new TMassTaskOpenId();
            taskOpenId.setAppid(params.getAppId());
            taskOpenId.setOpenid(massFans.getOpenid());
            taskOpenId.setState(0);
            taskOpenId.setTaskid(task.getId());
            massTaskOpenIdDao.insertSelective(taskOpenId);
        }

        queueSender.send("mass.queue", task);
    }

    public PageInfo<MassRecordVo> getRecordList(MassFansQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit(), "taskTime desc");
        List<MassRecordVo> list = massRecordDao.queryList(params);
        if (list != null && list.size() > 0) {
            for (MassRecordVo vo : list) {
                if (MediaType.NEWS.equals(vo.getMediatype())) {
                    NewsTreeVo news = materialBiz.getNews(vo.getMaterialid(), params.getAppId());
                    if (news != null) {
                        vo.setMediaurl(news.getNews().getUrl());
                    }
                }
                if (MediaType.SYSNEWS.equals(vo.getMediatype())) {
                    SysNewsTreeVo sysnews = materialBiz.getSysnews(vo.getMaterialid(), params.getAppId());
                    if (sysnews != null) {
                        vo.setMediaurl(sysnews.getNews().getUrl());
                    }
                }
            }
        }
        return new PageInfo<>(list);
    }

    private void addressPageMol(Page<TMassFans> fansPage, PageModel<MassFansVo> massFansVos) {
        for (TMassFans fans : fansPage) {
            MassFansVo vo = new MassFansVo();
            vo.setOpenId(fans.getOpenid());
            vo.setNickname(fans.getNickname());
            if (fans.getLastsendtime() == null) {
                vo.setLastsendtime(DateUtil.formatDateTime(new Date()));
                vo.setMonthTotal(0);
            } else {
                vo.setLastsendtime(DateUtil.formatDateTime(fans.getLastsendtime()));
                vo.setMonthTotal(fans.getMonthtotal());
            }
            massFansVos.add(vo);
        }
    }

    public int getSubScrtype(String appId) {
        return subscrDao.getSubScrtype(appId);
    }

    @Override
    public PageInfo<MassFansVo> getRecordFansList(Integer id, String appId, Integer page, Integer limit) {
        TMassRecord record = massRecordDao.selectByPrimaryKey(id);
        if (record == null) {
            throw new BusinessException("不存在此记录,请检查参数");
        }
        TMassTask task = massTaskDao.selectByPrimaryKey(record.getTaskid());
        if (task == null) {
            throw new BusinessException("不存在此群发任务,情检查参数");
        }
        PageHelper.startPage(page, limit);
        List<MassFansVo> list = massTaskOpenIdDao.selectFansByTaskId(task.getId(), task.getAppid());

        return new PageInfo<>(list);
    }
}
