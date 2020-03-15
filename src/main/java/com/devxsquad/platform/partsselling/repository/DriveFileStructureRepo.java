package com.devxsquad.platform.partsselling.repository;

import com.devxsquad.platform.partsselling.model.entity.DriveFileStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devxsquad.platform.partsselling.model.entity.DriveFileStructure.DRIVE_FILE_STRUCTURE_NAME;

@Repository
public interface DriveFileStructureRepo extends JpaRepository<DriveFileStructure, String> {

    @Query("select name from " + DRIVE_FILE_STRUCTURE_NAME + " where parentFolderId=?1 and type='FOLDER'")
    List<String> findFoldersNames(String parentFolderId);

    @Query("select fileId from " + DRIVE_FILE_STRUCTURE_NAME + " where parentFolderId=?1 and name=?2")
    List<String> getFileId(String parentFolderId, String fileName);

    @Query(nativeQuery = true,
            value = "with recursive subfiles as " +
                    "(select * " +
                    "from drive_file_structure " +
                    "where file_id=?1 " +
                    "union all " +
                    "select dfs.* " +
                    "from drive_file_structure dfs " +
                    "join subfiles s " +
                    "on s.file_id = dfs.parent_folder_id) " +
                    "select file_id from subfiles")
    List<String> listAllByParentId(String folderId);

    @Query("select fileId from " + DRIVE_FILE_STRUCTURE_NAME + " where  name=?1" )
    List<String> findFilesIdByName(String folderName);

    @Transactional
    @Modifying
    void deleteByFileId(String fileId);
}
