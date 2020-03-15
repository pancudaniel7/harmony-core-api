package com.devxsquad.platform.partsselling.service;

import com.devxsquad.platform.partsselling.error.DatabaseFileDoesntExistException;
import com.devxsquad.platform.partsselling.model.entity.DriveFileStructure;
import com.devxsquad.platform.partsselling.repository.DriveFileStructureRepo;
import com.devxsquad.platform.partsselling.service.drive.DriveManager;
import com.devxsquad.platform.partsselling.util.FileType;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static com.devxsquad.platform.partsselling.util.DriveFileType.FOLDER_MIME_TYPE;
import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.ListUtils.subtract;

@Service
@RequiredArgsConstructor
public class DriveManagerService extends DriveManager {

    private final DriveFileStructureRepo driveFileStructureRepo;

    public File createRootFolder(String rootFolderName) {
        File rootFolder = super.createFolder(null, rootFolderName);
        driveFileStructureRepo.save(
            new DriveFileStructure(
                rootFolderName,
                rootFolder.getId(),
                format("root_%s", rootFolder.getId()), FileType.FOLDER));
        return rootFolder;
    }

    public File createFolder(String parentFolderId, String folderName) {
        File createdFolder = super.createFolder(singletonList(parentFolderId), folderName);
        driveFileStructureRepo.save(new DriveFileStructure(createdFolder.getName(), createdFolder.getId(), parentFolderId, FileType.FOLDER));
        return createdFolder;
    }

    public File createFolderIfNotExist(String parentFolderId, String folderName) {
        List<String> folderId = driveFileStructureRepo.getFileId(parentFolderId, folderName);
        return isEmpty(folderId) ? createFolder(parentFolderId, folderName) : searchFileById(folderId.get(0));
    }

    public List<String> listMissingFolder(String parentFolderId, List<String> folderNames) {
        List<String> databaseCurrentFileName = driveFileStructureRepo.findFoldersNames(parentFolderId);
        return subtract(folderNames, databaseCurrentFileName);
    }

    public List<String> listFilesIdByName(String folderName){
        return driveFileStructureRepo.findFilesIdByName(folderName);
    }

    public File createFile(String parentFolderId, java.io.File file, String fileType) {
        return createFile(parentFolderId, file, fileType, null);
    }

    public File createFile(String parentFolderId, java.io.File file, String fileType, String fileName) {
        File uploadedFile = super.uploadFile(singletonList(parentFolderId), file, fileType, fileName);
        driveFileStructureRepo.save(new DriveFileStructure(uploadedFile.getName(), uploadedFile.getId(), parentFolderId, FileType.FILE));
        return uploadedFile;
    }

    public File createFileIfNotExist(String parentFolderId, java.io.File file, String fileType, String fileName) {
        fileName = isNull(fileName) ? file.getName() : fileName;
        List<String> fileId = driveFileStructureRepo.getFileId(parentFolderId, fileName);
        return isEmpty(fileId) ? createFile(parentFolderId, file, fileType, fileName) : searchFileById(fileId.get(0));
    }

    public void cleanDrive(String parentFolderName) {
        List<File> parentFolder = searchFiles(format("name='%s'", parentFolderName));
        if (!isEmpty(parentFolder)) {
            super.cleanDrive(parentFolder.get(0).getId());
        }
        driveFileStructureRepo.deleteAll();
    }

    public InputStream downloadFileAsStream(File carsFile) {
        byte[] carsFileBytes = downloadFile(carsFile.getId());
        return new ByteArrayInputStream(carsFileBytes);
    }

    public void syncDrive(List<String> syncFolderNames, String parentFolderId) {
        syncDriveFoldersWithDatabase(syncFolderNames, parentFolderId);
        syncDatabaseWithDriveFileStructure(parentFolderId);
    }

    private void syncDriveFoldersWithDatabase(List<String> syncFolderNames, String parentFolderId) {
        List<String> databaseFolderNames = driveFileStructureRepo.findFoldersNames(parentFolderId);
        subtract(databaseFolderNames, syncFolderNames).forEach(databaseFolderName -> recursiveDelete(parentFolderId, databaseFolderName));
    }

    private void syncDatabaseWithDriveFileStructure(String parentFolderId) {
        List<String> databaseFolderNames = driveFileStructureRepo.findFoldersNames(parentFolderId);
        searchFiles(format("mimeType = '%s' and parents in '%s'", FOLDER_MIME_TYPE, parentFolderId))
            .stream()
            .filter(folder -> !databaseFolderNames.contains(folder.getName()))
            .forEach(folder -> deleteFile(folder.getId()));
    }

    private void recursiveDelete(String parentFolderId, String fileName) {
        List<String> fileId = driveFileStructureRepo.getFileId(parentFolderId, fileName);
        if (isEmpty(fileId)) {
            throw new DatabaseFileDoesntExistException(parentFolderId);
        }
        driveFileStructureRepo.listAllByParentId(fileId.get(0)).forEach(driveFileStructureRepo::deleteByFileId);
    }
}
