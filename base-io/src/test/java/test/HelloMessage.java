package test;

import com.foolox.base.constant.message.Modules;
import com.foolox.base.constant.message.TestCmd;
import com.foolox.base.constant.annotation.MessageMeta;
import com.foolox.base.io.input.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * comment:
 *
 * @author: lipengfei
 * @date: 17/07/2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MessageMeta(module = Modules.LOGIN, cmd = TestCmd.TEST)
public class HelloMessage extends Message {
    private String info;
}
