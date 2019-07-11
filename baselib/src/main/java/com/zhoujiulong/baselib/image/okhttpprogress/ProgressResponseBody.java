package com.zhoujiulong.baselib.image.okhttpprogress;

import android.support.annotation.NonNull;
import com.zhoujiulong.baselib.image.listener.OnProgressListener;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.*;

import java.io.IOException;


public class ProgressResponseBody extends ResponseBody {

    private String imageUrl;
    private ResponseBody responseBody;
    private OnProgressListener progressListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(String url, ResponseBody responseBody, OnProgressListener progressListener) {
        this.imageUrl = url;
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += (bytesRead == -1) ? 0 : bytesRead;
                if (progressListener != null) {
                    int percent = contentLength() > 0 ? (int) (100 * totalBytesRead / contentLength()) : 0;
                    progressListener.onProgress(imageUrl, percent, totalBytesRead, contentLength(), (bytesRead == -1), null);
                }
                return bytesRead;
            }
        };
    }
}
