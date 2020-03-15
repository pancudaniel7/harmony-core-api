package com.devxsquad.platform.partsselling.model.entity;

import com.devxsquad.platform.partsselling.util.FileType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static com.devxsquad.platform.partsselling.model.entity.DriveFileStructure.DRIVE_FILE_STRUCTURE_NAME;

@Entity(name = DRIVE_FILE_STRUCTURE_NAME)
@Data
@NoArgsConstructor
public class DriveFileStructure implements Serializable {

    public static final String DRIVE_FILE_STRUCTURE_NAME = "DriveFileStructure";

    @Id
    private String fileId;

    @NotNull
    @Column
    private String parentFolderId;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private FileType type;

    public DriveFileStructure(@NotNull String name, @NotNull String fileId, @NotNull String parentFolderId, @NotNull FileType type) {
        this.name = name;
        this.fileId = fileId;
        this.parentFolderId = parentFolderId;
        this.type = type;
    }
}