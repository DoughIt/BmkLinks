package cn.fdsd.bmk.domain.po;

import cn.fdsd.bmk.domain.enums.CommandEnum;
import lombok.Builder;
import lombok.Data;

/**
 * @author Jerry Zhang
 * create: 2022-11-02 12:40
 */
@Data
@Builder
public class CommandPo {
    private String fullCmd;
    private CommandEnum name;
    private String[] args;
    private String atOption;
}
