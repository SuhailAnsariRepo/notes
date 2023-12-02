package com.suhail.notes.controller;

import com.suhail.notes.requests.CreateNoteRequest;
import com.suhail.notes.responses.CreateNoteResponse;
import com.suhail.notes.service.FolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/notes-api/note")
@RequiredArgsConstructor
public class NotesController {

    private final FolderService folderService;

    @PostMapping("/create-note")
    public ResponseEntity<CreateNoteResponse> createNote(@RequestBody @Valid CreateNoteRequest request) {
        return folderService.createNotes(request);
    }

    @PostMapping(value = "/add-image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CreateNoteResponse> addImageToFolder(@RequestParam("image") MultipartFile request, @RequestParam("folder-name") String folderName) throws IOException {
        return folderService.addImageToFolder(request, folderName);
    }
}