package youke.service.fans.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.*;
import youke.common.model.*;
import youke.common.model.vo.page.PageModel;
import youke.common.model.vo.param.ShopFansQueryVo;
import youke.common.model.vo.result.*;
import youke.common.utils.DateUtil;
import youke.common.utils.StringUtil;
import youke.service.fans.biz.IFansShopBiz;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Service
public class FansShopBiz implements IFansShopBiz {
    @Resource
    private IShopFansDao shopFansDao;
    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private IShopFansTagDao shopFansTagDao;
    @Resource
    private IShopFansImportDao shopFansImportDao;
    @Resource
    private IFriendShopfansDao friendShopfansDao;

    public PageInfo<ShopFansVo> getList(ShopFansQueryVo qo) {
        //存在标签的筛选的时候进行这个查询
        if (qo.getTagIds() != null && !"".equals(qo.getTagIds())) {
            qo.setTags(qo.getTagIds());
            if (qo.getTagCount() > 0) {
                List<Integer> ids = shopFansDao.getFansIds(qo);
                if (ids == null || ids.size() == 0 || ids.get(0) == 0) {
                    return null;
                }
                qo.setIds(ids);
            }
        }

        PageHelper.startPage(qo.getPage(), qo.getLimit(), "fans.lastTime desc");
        //得到的是一个Page
        List<TShopFans> list = shopFansDao.queryForList(qo);
        if (list == null || list.size() <= 0) {
            return new PageInfo<>();
        }
        //拼接自己的...
        PageModel<ShopFansVo> fansVos = new PageModel<ShopFansVo>((Page) list);
        for (TShopFans item : list) {
            ShopFansVo fansVo = new ShopFansVo();
            fansVo.setAvgDealTotal(item.getAvgdealtotal());
            fansVo.setComeFrom(item.getComefrom());
            fansVo.setDealNum(item.getDealnum());
            fansVo.setDealTotal(item.getDealtotal());
            fansVo.setId(item.getId());
            Date lasttime = item.getLasttime();
            if(lasttime != null){
                String dateTime = DateUtil.formatDateTime(lasttime);
                fansVo.setLastTime(dateTime);
            }
            fansVo.setShopId(item.getShopid());
            fansVo.setMobile(item.getMobile());
            if (item.getShop() != null) {
                fansVo.setShopName(item.getShop().getTitle());
            }
            fansVo.setTrueName(item.getTruename());

            //这里再去查一次吧
            List<TTag> tTags = shopFansDao.selectTTagById(item.getId().intValue());
            if (tTags != null) {
                for (TTag tag : tTags) {
                    TagVo tagVo = new TagVo();
                    tagVo.setId(tag.getId());
                    tagVo.setTitle(tag.getTitle());
                    fansVo.getTags().add(tagVo);
                }
            }
            fansVos.add(fansVo);
        }
        PageInfo<ShopFansVo> pageTest = new PageInfo<>(fansVos);

        return pageTest;
    }

    public void deleteTags(long fansId, String tags) {
        String[] split = tags.split(",");
        //删除中间表
        shopFansTagDao.delTags(fansId, split);
    }

    public void addTags(long fansId, String tags, String youkeId) {
        List<Integer> tagList = shopFansTagDao.selectByFansId(fansId);
        String[] split = tags.split(",");
        if (tagList == null) {
            tagList = new ArrayList<Integer>();
        }
        List<String> list = Arrays.asList(split);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String tag = it.next();
            int next = Integer.parseInt(tag);
            if (!tagList.contains(next)) {
                TShopFansTag fansTag = new TShopFansTag();
                fansTag.setFansid(fansId);
                fansTag.setTagid(next);
                fansTag.setYoukeid(youkeId);
                shopFansTagDao.insertSelective(fansTag);
            }
        }
    }

    public int saveImportFansFromFile(int shopId, String url, String fileName, String youkeId) {

        //增加记录
        TShopFansImport info = new TShopFansImport();
        info.setCreatetime(new Date());
        info.setFilename(fileName);
        info.setShopid(shopId);
        info.setUrl(url);
        info.setState(0);
        info.setYoukeid(youkeId);
        shopFansImportDao.insertSelective(info);

        return info.getId();
    }

    public void saveRemark(long fansId, String remark) {
        shopFansDao.sveRemark(fansId, remark);
    }

    public PageInfo<ShopFansImportVo> getImportList(int page, int limit, String dykId) {

        //以时间降序
        PageHelper.startPage(page, limit, "createTime DESC");
        List<TShopFansImport> importList = shopFansImportDao.selectAll(dykId);

        PageModel<ShopFansImportVo> fansVos = new PageModel<ShopFansImportVo>((Page) importList);
        for (TShopFansImport model : importList) {
            ShopFansImportVo vo = new ShopFansImportVo();
            vo.setId(model.getId());
            vo.setCompletetime(model.getCompletetime());
            vo.setFailcsvurl(model.getFailcsvurl());
            vo.setFailnum(model.getFailnum());
            vo.setFilename(model.getFilename());
            vo.setState(model.getState());
            vo.setCreateTime(model.getCreatetime());
            vo.setSuccessnum(model.getSuccessnum());
            fansVos.getResult().add(vo);
        }

        return new PageInfo<>(fansVos);
    }

    public int add(TShopFans fans) {
        return shopFansDao.insertSelective(fans);
    }

    public void updateImmport(TShopFansImport tShopFansImport) {
        shopFansImportDao.updateByPrimaryKeySelective(tShopFansImport);
    }

    @Override
    public BuyerVo getFriendBuyerInfo(String friendId,String weixinId,String youkeId) {
        TFriendShopfans friendShopfans = friendShopfansDao.selectByFriendId(friendId,weixinId);
        if(friendShopfans==null||StringUtil.isEmpty(friendShopfans.getMobile()))
            return null;
        List<Map> orderMaps = friendShopfansDao.selectFriendOrdersByMobile(friendShopfans.getMobile(),youkeId);
        if(orderMaps==null||orderMaps.size()==0)
            return null;
        String buyerName = (String)orderMaps.get(0).get("buyerName");
        String truename = (String)orderMaps.get(0).get("receiveName");
        String province = (String)orderMaps.get(0).get("receiveState");
        String city = (String)orderMaps.get(0).get("city");
        Integer latelyPrice = (Integer) orderMaps.get(0).get("totalPrice");
        Date latelyTime = (Date) orderMaps.get(0).get("payTime");
        BuyerVo buyerVo = new BuyerVo();
        buyerVo.setTruename(truename);
        buyerVo.setMobile(friendShopfans.getMobile());
        buyerVo.setBuyerName(buyerName);
        buyerVo.setProvince(province);
        buyerVo.setCity(city);
        if (latelyPrice == null)
            buyerVo.setLatelyDeal("无");
        else
            buyerVo.setLatelyDeal("￥" + StringUtil.FenToYuan(latelyPrice));
        buyerVo.setLatelyTime(DateUtil.formatDate(latelyTime, "yyyy-MM-dd HH:mm"));
        Integer dealTotal = 0;
        Integer dealNum = orderMaps.size();
        for (Map map:orderMaps) {
            dealTotal+=(Integer)map.get("totalPrice");
        }
        Integer avgDealTotal = dealTotal/dealNum;
        buyerVo.setTotalDeal("￥" + StringUtil.FenToYuan(dealTotal) + " 共成交" + dealNum + "笔");
        buyerVo.setUnitDeal("￥" + StringUtil.FenToYuan(avgDealTotal));
        return buyerVo;
    }

    @Override
    public List<BuyerOrderVo> getShopOrderList(String friendId,String weixinId,String youkeId) {
        List<BuyerOrderVo> orderVos = new ArrayList<>();
        TFriendShopfans friendShopfans = friendShopfansDao.selectByFriendId(friendId,weixinId);
        if(friendShopfans==null||StringUtil.isEmpty(friendShopfans.getMobile()))
            return orderVos;
        List<Map> maps = shopOrderDao.selectBuyerOrderList(friendShopfans.getMobile(),youkeId);
        if (maps != null) {
            for (Map map : maps) {
                BuyerOrderVo orderVo = new BuyerOrderVo();
                orderVo.setGoodsId((String) map.get("goodId"));
                orderVo.setId((Long) map.get("id"));
                orderVo.setShopType((Integer) map.get("shopType"));
                orderVo.setShopTypeIcon(ApiCodeConstant.DOMAIN_PCAPI+"file/icon/"+orderVo.getShopType()+".png");
                orderVo.setShopTitle((String) map.get("shopTitle"));
                orderVo.setTitle((String) map.get("title"));
                orderVo.setPicPath((String) map.get("picPath"));
                orderVo.setPrice("￥" + StringUtil.FenToYuan((Integer) map.get("price")));
                orderVo.setNum((Integer) map.get("num"));
                orderVo.setTotalPrice("￥" + StringUtil.FenToYuan((Integer) map.get("totalPrice")));
                orderVo.setState(getOrderState((Integer) map.get("state")));
                orderVos.add(orderVo);
            }
        }
        return orderVos;
    }

    @Override
    public void saveBuyerName(String friendId, String buyerName, String weixinId,String youkeId) {
        TFriendShopfans friendShopfans = friendShopfansDao.selectByFriendId(friendId,weixinId);
        if (friendShopfans == null) {
            friendShopfans = new TFriendShopfans();
            friendShopfans.setBuyername(buyerName);
            friendShopfans.setMobile(friendShopfansDao.selectMobileByBuyerName(buyerName,youkeId));
            friendShopfans.setWeixinid(weixinId);
            friendShopfans.setCreatetime(new Date());
            friendShopfans.setFriendid(friendId);
            friendShopfans.setOpenid("");
            friendShopfansDao.insertSelective(friendShopfans);
        } else {
            friendShopfans.setBuyername(buyerName);
            friendShopfans.setMobile(friendShopfansDao.selectMobileByBuyerName(buyerName,youkeId));
            friendShopfansDao.updateByPrimaryKeySelective(friendShopfans);
        }
    }

    @Override
    public Integer selectByNickName(String nickName, String youkeId) {
        return shopFansDao.selectByNickName(nickName, youkeId);
    }

    @Override
    public void saveFriendMobile(String friendId, String mobile, String weixinId) {
        TFriendShopfans friendShopfans = friendShopfansDao.selectByFriendId(friendId,weixinId);
        if (friendShopfans == null) {
            friendShopfans = new TFriendShopfans();
            friendShopfans.setMobile(mobile);
            friendShopfans.setWeixinid(weixinId);
            friendShopfans.setCreatetime(new Date());
            friendShopfans.setFriendid(friendId);
            friendShopfans.setOpenid("");
            friendShopfansDao.insertSelective(friendShopfans);
        } else {
            friendShopfans.setMobile(mobile);
            friendShopfansDao.updateByPrimaryKeySelective(friendShopfans);
        }
    }

    private String getOrderState(int state) {
        String smark = "";
        switch (state) {
            case 0:
                smark = "待付款";
                break;
            case 1:
                smark = "待发货";
                break;
            case 2:
                smark = "待收货";
                break;
            case 3:
                smark = "待评价";
                break;
            case 4:
                smark = "交易成功";
                break;
            case 5:
                smark = "已退款";
                break;
            case 6:
                smark = "交易关闭";
                break;
        }
        return smark;
    }

    @Override
    public void saveFans(ShopFansVo fans) {
        TShopFans fans1 = shopFansDao.selectByPrimaryKey(fans.getId());
        if(fans1 == null){
            return;
        }
        fans1.setTruename(fans.getTrueName());
        fans1.setShopid(fans.getShopId());
        fans1.setMobile(fans.getMobile());
        fans1.setLasttime(DateUtil.parseDate(fans.getLastTime()));
        if(fans.getDealNum()>0) {
            fans1.setAvgdealtotal(fans.getDealTotal() / fans.getDealNum());
            fans1.setDealtotal(fans.getDealTotal());
            fans1.setDealnum(fans.getDealNum());
        }else{
            fans1.setAvgdealtotal(0.0);
            fans1.setDealtotal(0.0);
            fans1.setDealnum(0);
        }
        shopFansDao.updateByPrimaryKeySelective(fans1);
    }


    @Override
    public void updateFansState(List<String> ids, int state) {
        shopFansDao.bacthupdateState(ids, state);
    }

}
