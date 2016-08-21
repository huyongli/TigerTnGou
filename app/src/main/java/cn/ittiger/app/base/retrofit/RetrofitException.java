package cn.ittiger.app.base.retrofit;

/**
 * 使用retrofit请求过程中的异常
 * @author laohu
 */
public class RetrofitException extends Exception {

    public RetrofitException() {

        super();
    }

    public RetrofitException(String detailMessage) {

        super(detailMessage);
    }

    public RetrofitException(String detailMessage, Throwable throwable) {

        super(detailMessage, throwable);
    }

    public RetrofitException(Throwable throwable) {

        super(throwable);
    }
}
