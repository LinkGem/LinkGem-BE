package com.linkgem.domain.common.file;

public interface FileStore {

    FileInfo store(FileCommand.UploadFile uploadCommand);

    FileInfo store(FileCommand.UploadUrlFile uploadCommand);

    void delete(FileCommand.DeleteFile deleteCommand);
}
