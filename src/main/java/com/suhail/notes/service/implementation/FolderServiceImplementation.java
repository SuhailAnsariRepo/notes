package com.suhail.notes.service.implementation;

import com.suhail.notes.dto.FolderDTO;
import com.suhail.notes.model.Folder;
import com.suhail.notes.repository.FolderRepository;
import com.suhail.notes.requests.CreateFolderRequest;
import com.suhail.notes.requests.CreateNoteRequest;
import com.suhail.notes.responses.CreateFolderResponse;
import com.suhail.notes.responses.CreateNoteResponse;
import com.suhail.notes.responses.GetFolderResponse;
import com.suhail.notes.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FolderServiceImplementation implements FolderService {

    private final FolderRepository folderRepository;

    @Transactional
    @Override
    public ResponseEntity<FolderDTO> getFolderByName(String folderName) {
        FolderDTO response = new FolderDTO();
        if (Objects.nonNull(folderName) && StringUtils.hasLength(folderName)) {
            var folder = folderRepository.findByName(folderName);
            if (folder.isPresent()) {
                response.setName(folder.get().getName());
                response.setText(folder.get().getText());
                response.setImage(folder.get().getImage());
                response.setParent(Objects.nonNull(folder.get().getParent()) ? folder.get().getParent().getName() : null);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setName(String.format("No folder exists with %s name!", folderName));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            response.setName("Please provide the name of the folder!");
            return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
        }
    }

    @Override
    public ResponseEntity<CreateFolderResponse> createFolder(CreateFolderRequest request) {
        CreateFolderResponse response = new CreateFolderResponse();
        if (Objects.nonNull(request) && Objects.nonNull(request.getName()) && Objects.nonNull(request.getParent())) {
            if (StringUtils.hasLength(request.getParent())) {
                var parent = folderRepository.findByName(request.getParent());
                if (parent.isPresent()) {
                    var folder = Folder.builder()
                            .name(request.getName())
                            .isPrivate(request.isPrivate())
                            .parent(parent.get())
                            .build();
                    folderRepository.save(folder);
                    response.setMessage(String.format("Successfully created sub-folder '%s' under parent folder '%s'. Privacy: %b.", request.getName(), request.getParent(), request.isPrivate()));
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                } else {
                    response.setMessage(String.format("Parent folder '%s' not found.", request.getParent()));
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                }
            } else {
                var dbFolder = folderRepository.findByName(request.getName());
                if (dbFolder.isPresent()) {
                    response.setMessage(String.format("Folder already exists with %s name, and it's %b.", request.getName(), request.isPrivate()));
                    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
                } else {
                    var folder = Folder.builder()
                            .name(request.getName())
                            .isPrivate(request.isPrivate())
                            .build();
                    folderRepository.save(folder);
                    response.setMessage(String.format("Successfully created new folder '%s', and it's %b.", request.getName(), request.isPrivate()));
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                }
            }
        } else {
            response.setMessage("Please provide proper request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<CreateNoteResponse> createNotes(CreateNoteRequest request) {
        CreateNoteResponse response = new CreateNoteResponse();
            if (
                Objects.nonNull(request)
                        && Objects.nonNull(request.getNotes())
                        && !request.getNotes().isEmpty()
                        && StringUtils.hasLength(request.getFolderName())
            ) {
            var name = folderRepository.findByName(request.getFolderName());
            if (name.isPresent()) {
                name.get().setText(request.getNotes());
                folderRepository.save(name.get());
                response.setMessage(String.format("Notes successfully saved in folder '%s'.", request.getFolderName()));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage(String.format("No folder found with the name '%s'.", request.getFolderName()));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
                response.setMessage("Please provide the request in the correct format.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
    }

    @Override
    public GetFolderResponse getFolders() {
        List<Folder> all = folderRepository.findAll();
        List<FolderDTO> collect = all.stream().
                map(
                        folder -> new FolderDTO(
                                folder.getName(),
                                folder.getText(),
                                folder.getImage(),
                                Objects.nonNull(folder.getParent()) ? folder.getParent().getName() : null)
                ).collect(Collectors.toList());
        GetFolderResponse response = new GetFolderResponse();
        if (!collect.isEmpty()) {
            response.setList(collect);
        } else {
            response.setList(Collections.emptyList());
        }
        return response;
    }

    @Override
    public ResponseEntity<CreateNoteResponse> addImageToFolder(MultipartFile request, String folderName) throws IOException {
        CreateNoteResponse response = new CreateNoteResponse();
        if (!request.isEmpty() && StringUtils.hasLength(folderName)) {
            var folder = folderRepository.findByName(folderName);
            if (folder.isPresent()) {
                folder.get().setImage(request.getBytes());
                folderRepository.save(folder.get());
                response.setMessage(String.format("Image successfully saved to folder '%s'.", folderName));
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.setMessage(String.format("No folder found with the name '%s'.", folderName));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            response.setMessage("Please provide the request in the correct format.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
