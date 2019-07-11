package com.zhoujiulong.baselib.http.other;

import android.support.annotation.NonNull;
import com.zhoujiulong.baselib.http.listener.UploadListener;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.*;

import java.io.File;
import java.io.IOException;

/**
 * @author zhoujiulong
 * @createtime 2019/2/26 16:49
 * 上传文件监听上传进度
 */
public class UploadRequestBody extends RequestBody {

    private String mFilePath;
    private RequestBody delegate;
    private UploadListener listener;

    public static UploadRequestBody create(UploadListener listener, File uploadFile) {
        //application/octet-stream: 任意二进制数据流
        RequestBody requestBodyOuter = RequestBody.create(MediaType.parse("application/octet-stream"), uploadFile);
        return new UploadRequestBody(requestBodyOuter, listener, uploadFile.getAbsolutePath());
    }

    private UploadRequestBody(RequestBody delegate, UploadListener listener, String filePath) {
        this.mFilePath = filePath;
        this.delegate = delegate;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(@NonNull BufferedSink sink) throws IOException {
        BufferedSink bufferedSink;

        CountingSink countingSink = new CountingSink(sink);
        bufferedSink = Okio.buffer(countingSink);

        delegate.writeTo(bufferedSink);

        bufferedSink.flush();
    }

    private final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(@NonNull Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            bytesWritten += byteCount;
            int progress = contentLength() > 0 ? (int) (bytesWritten * 100 / contentLength()) : 0;
            listener.onUploadProgress(progress, bytesWritten, contentLength(), mFilePath);
        }
    }

}







