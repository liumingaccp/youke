package youke.service.helper.provider;

import org.junit.Test;
import youke.facade.helper.provider.IFollowService;
import youke.facade.helper.provider.ITaokeService;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class FollowServiceTest extends BaseJunit4Test {

    @Resource
    private ITaokeService taokeService;

    @Test
    public void createPoter() {
        System.out.println(taokeService.createPoter(48,"oyxoc1ZVur_3RmQJocfcZuHEUFh4"));
    }
}