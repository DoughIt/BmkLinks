package cn.fdsd.bmk.core.cmd;


import lombok.AllArgsConstructor;

/**
 *
 * @author Jerry Zhang
 * create: 2022-11-01 20:11
 */
@AllArgsConstructor
public class AddCommand extends GeneralCommand{
    @Override
    public void execute() {
        switch (po.getName()) {
            case ADD_TITLE:
                // todo
                break;
            case ADD_BOOKMARK:
                // todo

                break;
            default:
                break;
        }
    }
}
