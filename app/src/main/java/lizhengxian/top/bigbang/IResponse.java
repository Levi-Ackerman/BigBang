package lizhengxian.top.bigbang;

/**
 * Created by lizhengxian on 2016/10/23.
 */

public interface IResponse {
    void finish(String[] words);
    void failure(String errorMsg);
}
