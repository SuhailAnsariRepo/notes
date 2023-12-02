package com.suhail.notes.service;

import com.suhail.notes.dto.FolderDTO;
import com.suhail.notes.requests.CreateFolderRequest;
import com.suhail.notes.responses.CreateFolderResponse;
import com.suhail.notes.requests.CreateNoteRequest;
import com.suhail.notes.responses.CreateNoteResponse;
import com.suhail.notes.responses.GetFolderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FolderService {
    ResponseEntity<CreateFolderResponse> createFolder(CreateFolderRequest request);
    ResponseEntity<CreateNoteResponse> createNotes(CreateNoteRequest request);
    GetFolderResponse getFolders();
    ResponseEntity<CreateNoteResponse> addImageToFolder(MultipartFile request, String folderName) throws IOException;
    ResponseEntity<FolderDTO> getFolderByName(String folderName);
}
