package com.suhail.notes.controller;

import com.suhail.notes.dto.FolderDTO;
import com.suhail.notes.requests.CreateFolderRequest;
import com.suhail.notes.responses.CreateFolderResponse;
import com.suhail.notes.responses.GetFolderResponse;
import com.suhail.notes.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notes-api/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/create")
    public ResponseEntity<CreateFolderResponse> createFolder(@RequestBody @Valid CreateFolderRequest request) {
        return folderService.createFolder(request);
    }

    @GetMapping("/folders")
    public ResponseEntity<GetFolderResponse> getFolders() {
        return ResponseEntity.ok(folderService.getFolders());
    }

    @GetMapping("/folders/{folder-name}")
    public ResponseEntity<FolderDTO> getFolder(@PathVariable("folder-name") String folderName) {
        return folderService.getFolderByName(folderName);
    }
}