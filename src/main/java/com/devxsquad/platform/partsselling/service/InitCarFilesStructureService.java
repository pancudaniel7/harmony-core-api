package com.devxsquad.platform.partsselling.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import com.devxsquad.platform.partsselling.error.DriveFileDoesntExistException;
import com.devxsquad.platform.partsselling.util.DriveFileType;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.devxsquad.platform.partsselling.util.DocsManager.readXlsxColumnData;
import static com.devxsquad.platform.partsselling.util.ResourcesUtil.getFileFromResources;
import static java.lang.String.format;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitCarFilesStructureService {

    @Value("${drive.root.name:scrapping_cars}")
    private String rootFolderName;

    @Value("${drive.folder.data.name:data}")
    private String dataFolderName;

    @Value("${drive.file.cars:batch.xlsx}")
    private String carsFileName;

    @Value("${drive.file.description.name:description.docx}")
    private String descriptionFolderName;

    @Value("${driver.file.car-parts:products.xlsx}")
    private String carPartsFileName;

    @Value("#{'${app.user.emails:pancudaniel7@gmail.com}'.split(',')}")
    private List<String> usersEmails;

    @Value("${drive.action.startup.clean:false}")
    private boolean wantToClean;

    @Value("${drive.action.startup.share-root-again:false}")
    private boolean whatToShareRootFolderAgain;

    private final DriveManagerService driveManagerService;

    public void initStructure() {
        File rootFolder;
        if (wantToClean) {
            driveManagerService.cleanDrive(rootFolderName);
            rootFolder = driveManagerService.createRootFolder(rootFolderName);
            shareRootFolder(usersEmails, rootFolder);
        } else {
            rootFolder = getRootFolder(rootFolderName);
            if (whatToShareRootFolderAgain) {
                shareRootFolder(usersEmails, rootFolder);
            }
        }
        createCarFolders(carsFileName, rootFolder.getId());
    }

    private File getRootFolder(String rootFolderName) {
        List<File> folderList = driveManagerService.searchFiles(format("name='%s'", rootFolderName));
        if (isEmpty(folderList)) {
            throw new DriveFileDoesntExistException(format("Root folder %s not exists on the drive!", rootFolderName));
        }
        return folderList.get(0);
    }

    private void shareRootFolder(List<String> emails, File rootFolder) {
        final String rootFolderId = rootFolder.getId();
        emails.forEach(userEmail -> driveManagerService.shareFile(rootFolderId, userEmail));
    }

    private void createCarFolders(String carsFileName, String mainFolderId) {
        File carsFile = uploadCarsFile(carsFileName, mainFolderId);
        InputStream carsFileInputStream = driveManagerService.downloadFileAsStream(carsFile);
        List<String> carNames = readXlsxColumnData(carsFileInputStream, 0);
        List<String> missingCarFolderNames = driveManagerService.listMissingFolder(mainFolderId, carNames);
        missingCarFolderNames.forEach(missingCarFolder -> createCarFolderStructure(mainFolderId, missingCarFolder));
        driveManagerService.syncDrive(carNames, mainFolderId);
        driveManagerService.listFilesIdByName(dataFolderName)
            .forEach(this::createDateFolderStructure);
    }

    private File uploadCarsFile(String carsFileName, String parentFolderId) {
        List<File> carsUploadedFile = driveManagerService.searchFiles(format("name='%s' and parents in '%s'", carsFileName, parentFolderId));
        return isEmpty(carsUploadedFile) ?
            driveManagerService.createFile(parentFolderId, getFileFromResources(carsFileName), DriveFileType.XLSX) :
            carsUploadedFile.get(0);
    }

    private File createCarFolderStructure(String mainFolderId, String carName) {
        File carFolder = driveManagerService.createFolder(mainFolderId, carName);
        driveManagerService.createFile(carFolder.getId(), getFileFromResources(descriptionFolderName), DriveFileType.DOCX);
        return driveManagerService.createFolder(carFolder.getId(), dataFolderName);
    }

    private void createDateFolderStructure(String dataFolderId) {
        String currentDay = LocalDate.now().toString();
        File dateFolder = driveManagerService.createFolderIfNotExist(dataFolderId, currentDay);
        driveManagerService.createFileIfNotExist(dateFolder.getId(), getFileFromResources(carPartsFileName), DriveFileType.XLSX, currentDay);
    }
}
