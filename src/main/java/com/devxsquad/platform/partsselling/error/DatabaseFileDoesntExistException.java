package com.devxsquad.platform.partsselling.error;

import static java.lang.String.format;

public class DatabaseFileDoesntExistException extends RuntimeException {
    public DatabaseFileDoesntExistException(String parentFileId) {
        super(format("File with parent_folder_id: %s was not found in database", parentFileId));
    }
}
