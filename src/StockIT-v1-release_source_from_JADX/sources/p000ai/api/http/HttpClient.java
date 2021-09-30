package p000ai.api.http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import org.apache.commons.p029io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/* renamed from: ai.api.http.HttpClient */
public class HttpClient {
    private static final int BUFFER_LENGTH = 4096;
    private static final int CHUNK_LENGTH = 2048;
    private static final Logger Log = LogManager.getLogger((Class<?>) HttpClient.class);
    private final String boundary = ("SwA" + Long.toString(System.currentTimeMillis()) + "SwA");
    private final HttpURLConnection connection;
    private final String delimiter = "--";

    /* renamed from: os */
    private OutputStream f0os;
    private boolean writeSoundLog;

    public HttpClient(HttpURLConnection httpURLConnection) {
        if (httpURLConnection != null) {
            this.connection = httpURLConnection;
            return;
        }
        throw new IllegalArgumentException("Connection cannot be null");
    }

    public void connectForMultipart() throws IOException {
        this.connection.setRequestProperty("Connection", "Keep-Alive");
        HttpURLConnection httpURLConnection = this.connection;
        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.boundary);
        this.connection.setChunkedStreamingMode(2048);
        this.connection.connect();
        this.f0os = this.connection.getOutputStream();
    }

    public void addFormPart(String str, String str2) throws IOException {
        OutputStream outputStream = this.f0os;
        outputStream.write(("--" + this.boundary + IOUtils.LINE_SEPARATOR_WINDOWS).getBytes());
        this.f0os.write("Content-Type: application/json\r\n".getBytes());
        OutputStream outputStream2 = this.f0os;
        outputStream2.write(("Content-Disposition: form-data; name=\"" + str + "\"\r\n").getBytes());
        OutputStream outputStream3 = this.f0os;
        outputStream3.write((IOUtils.LINE_SEPARATOR_WINDOWS + str2 + IOUtils.LINE_SEPARATOR_WINDOWS).getBytes());
    }

    public void addFilePart(String str, String str2, InputStream inputStream) throws IOException {
        FileOutputStream fileOutputStream;
        OutputStream outputStream = this.f0os;
        outputStream.write(("--" + this.boundary + IOUtils.LINE_SEPARATOR_WINDOWS).getBytes());
        OutputStream outputStream2 = this.f0os;
        outputStream2.write(("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + str2 + "\"\r\n").getBytes());
        this.f0os.write("Content-Type: audio/wav\r\n".getBytes());
        this.f0os.write(IOUtils.LINE_SEPARATOR_WINDOWS.getBytes());
        Log.debug("Sound write start");
        if (this.writeSoundLog) {
            File file = new File(System.getProperty("java.io.tmpdir"));
            if (!file.exists()) {
                file.mkdirs();
            }
            Log.debug(file.getAbsolutePath());
            fileOutputStream = new FileOutputStream(new File(file, "log.wav"), false);
        } else {
            fileOutputStream = null;
        }
        byte[] bArr = new byte[4096];
        int read = inputStream.read(bArr, 0, 4096);
        while (read >= 0) {
            if (read > 0) {
                this.f0os.write(bArr, 0, read);
                if (this.writeSoundLog) {
                    fileOutputStream.write(bArr, 0, read);
                }
            }
            read = inputStream.read(bArr, 0, 4096);
        }
        if (this.writeSoundLog) {
            fileOutputStream.close();
        }
        Log.debug("Sound write finished");
        this.f0os.write(IOUtils.LINE_SEPARATOR_WINDOWS.getBytes());
    }

    public void finishMultipart() throws IOException {
        OutputStream outputStream = this.f0os;
        outputStream.write(("--" + this.boundary + "--" + IOUtils.LINE_SEPARATOR_WINDOWS).getBytes());
        this.f0os.close();
    }

    public String getResponse() throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(this.connection.getInputStream());
        String readAll = p000ai.api.util.IOUtils.readAll((InputStream) bufferedInputStream);
        bufferedInputStream.close();
        return readAll;
    }

    public String getErrorString() {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(this.connection.getErrorStream());
            String readAll = p000ai.api.util.IOUtils.readAll((InputStream) bufferedInputStream);
            bufferedInputStream.close();
            return readAll;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setWriteSoundLog(boolean z) {
        this.writeSoundLog = z;
    }
}
