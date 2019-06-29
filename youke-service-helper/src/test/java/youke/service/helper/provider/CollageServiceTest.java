package youke.service.helper.provider;

import net.sf.json.JSONObject;
import org.junit.Test;
import youke.common.model.vo.result.helper.TuanDetailVo;
import youke.facade.helper.provider.ICollageService;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class CollageServiceTest extends BaseJunit4Test {

    @Resource
    private ICollageService collageService;

    @Test
    public void getTuanDetailByOpenId() {
      TuanDetailVo detailVo = collageService.getTuanDetailByOpenId("wxe3e582584ba16db1", "oyxoc1aGEn-Yfi5pDwtHTyUe8Lig", 89);
      System.out.println(JSONObject.fromObject(detailVo));
    }
}