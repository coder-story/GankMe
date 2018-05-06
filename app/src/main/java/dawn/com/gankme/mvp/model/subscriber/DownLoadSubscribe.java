package dawn.com.gankme.mvp.model.subscriber;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.jess.arms.utils.ArmsUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


/**
 * Created by _SOLID
 * Date:2016/8/1
 * Time:16:40
 */
public abstract class DownLoadSubscribe implements Observer<ResponseBody> {
    private String mSaveFilePath;
    private File mFile;
    private Handler handler = new Handler(Looper.getMainLooper());
    private long fileSizeDownloaded = 0;
    private long fileSize = 0;




    public DownLoadSubscribe(@NonNull String filePath, @NonNull String fileName) {
        mSaveFilePath = filePath;
        mFile = new File(mSaveFilePath + File.separator + fileName);
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    /**
     * 下载进度回调
     *
     * @param progress     下载进度 0.0~1.0
     * @param downloadByte 当前已经下载的字节
     * @param totalByte    文件的总字节数
     */
    public abstract void onProgress(double progress, long downloadByte, long totalByte);

    /**
     * 下载完成回调
     *
     * @param file 下载的文件
     */
    public abstract void onCompleted(File file);



    @Override
    public void onNext(ResponseBody o) {
        writeResponseBodyToDisk(o);
        onCompleted(mFile);
    }

    @Override
    public void onError(Throwable e) {
        ArmsUtils.snackbarText("图片保存失败" );
    }



    public boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                fileSize = body.contentLength();


                inputStream = body.byteStream();
                outputStream = new FileOutputStream(mFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onProgress(fileSizeDownloaded * 1.0f / fileSize, fileSizeDownloaded, fileSize);

                        }
                    });
                }

                outputStream.flush();

                return true;
            } catch (final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onError(e);
                    }
                });

                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (final IOException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onError(e);
                }
            });
            return false;
        }
    }


}
