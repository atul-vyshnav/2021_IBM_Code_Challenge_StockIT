package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.upstream.DataSource;

public final class FileDataSourceFactory implements DataSource.Factory {
    private final TransferListener listener;

    public FileDataSourceFactory() {
        this((TransferListener) null);
    }

    public FileDataSourceFactory(TransferListener transferListener) {
        this.listener = transferListener;
    }

    public DataSource createDataSource() {
        FileDataSource fileDataSource = new FileDataSource();
        TransferListener transferListener = this.listener;
        if (transferListener != null) {
            fileDataSource.addTransferListener(transferListener);
        }
        return fileDataSource;
    }
}
