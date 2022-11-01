package cn.fdsd.bmk.core.cmd.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 命令集中处理
 * @author Jerry Zhang
 * create: 2022-11-01 20:04
 */
@Data
@Builder
@AllArgsConstructor
public class CommandInvoker {

    public void lsTree() {

    }

    public void undo() {

    }

    public void redo() {

    }

    public void addTitle() {

    }

    public void deleteTitle() {

    }

    public void addLink() {

    }
}
