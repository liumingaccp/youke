package youke.facade.wx.provider;

import youke.common.model.TMassTask;
import youke.facade.wx.queue.message.SuperMassMessage;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/19
 * Time: 10:49
 */
public interface IWeixinMassService {
    void mass(TMassTask task);
}
