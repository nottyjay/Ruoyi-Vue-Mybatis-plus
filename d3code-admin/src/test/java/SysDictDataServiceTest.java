import com.alphay.boot.D3codeApplication;
import com.alphay.boot.common.core.domain.entity.SysDictData;
import com.alphay.boot.common.utils.DictUtils;
import com.alphay.boot.common.utils.JsonUtil;
import com.alphay.boot.system.common.service.ISysDictDataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nottyjay
 */
@SpringBootTest(classes = D3codeApplication.class)
public class SysDictDataServiceTest {

  @Resource private ISysDictDataService sysDictDataService;

  @Test
  public void testRefreshDataByChildren() {
    sysDictDataService.refreshDictData("province");
    List<SysDictData> result = DictUtils.getDictCache("province");
    System.out.println(JsonUtil.toJsonHex(result));
    Assertions.assertEquals(1, 1);
  }
}
