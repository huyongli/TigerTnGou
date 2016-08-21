package com.litesuits.android.async;

/**
 * <p>安全异步任务，可以捕获任意异常，并反馈给给开发者。
 * <p>从执行前，执行中，执行后，乃至更新时的异常都捕获。
 * <p>当{@link #doInBackgroundSafely(Object...)}有异常时，Exception将会被传递到
 * {@link #onPostExecuteSafely(Object, Exception)}。
 * <p/>
 * <p>如果用户取消了任务，那么会将回调{@link #onCancelled()}。
 *
 * @author MaTianyu
 *         2014-2-23下午9:22:34
 */
public abstract class SafeTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private Exception cause;
    private boolean printStackTrace = true;

    @Override
    protected final void onPreExecute() {
        try {
            onPreExecuteSafely();
        } catch (Exception e) {
            if (printStackTrace) e.printStackTrace();
        }
    }

    @Override
    protected final Result doInBackground(Params... params) {
        try {
            return doInBackgroundSafely(params);
        } catch (Exception e) {
            if (printStackTrace) e.printStackTrace();
            cause = e;
        }
        return null;
    }

    @Override
    protected final void onProgressUpdate(Progress... values) {
        try {
            onProgressUpdateSafely(values);
        } catch (Exception e) {
            if (printStackTrace) e.printStackTrace();
        }
    }

    @Override
    protected final void onPostExecute(Result result) {
        try {
            onPostExecuteSafely(result, cause);
        } catch (Exception e) {
            if (printStackTrace) e.printStackTrace();
        }
    }

    ;

    @Override
    protected final void onCancelled(Result result) {
        onCancelled();
    }

    ;

    /**
     * <p> 取代了{@link AsyncTask#onPreExecute()}, 这个方法的任意异常都能被捕获：它是安全的。
     * <p> 注意：本方法将在开发者启动任务的线程执行。
     */
    protected void onPreExecuteSafely() throws Exception {}

    ;

    /**
     * <p> Child Thread
     * <p> 取代了{@link AsyncTask#doInBackground(Object...)}, 这个方法的任意异常都能被捕获：它是安全的。
     * <p> 如果它发生了异常，Exception将会通过{@link #onPostExecuteSafely(Object, Exception)}
     * 传递。
     *
     * @param params 入参
     * @return
     */
    protected abstract Result doInBackgroundSafely(Params... params) throws Exception;

    /**
     * <p> Main UI Thread
     * <p> 用于取代{@link AsyncTask#onPostExecute(Object)}。
     * <p> 注意：本方法一定执行在主线程, 这个方法的任意异常都能被捕获：它是安全的。
     *
     * @param result
     */
    protected void onPostExecuteSafely(Result result, Exception e) throws Exception {}

    ;

    /**
     * <p> Main UI Thread
     * <p> 用于取代{@link AsyncTask#onProgressUpdate(Object...)},
     * <p> 这个方法的任意异常都能被捕获：它是安全的。
     *
     * @param values 更新传递的值
     */
    protected void onProgressUpdateSafely(Progress... values) throws Exception {}

}
