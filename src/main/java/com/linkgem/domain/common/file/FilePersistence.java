package com.linkgem.domain.common.file;

public interface FilePersistence {

    File store(FileCommand.UploadFile uploadCommand);

    File store(FileCommand.UploadUrlFile uploadCommand);

    void delete(FileCommand.DeleteFile deleteCommand);
}
