package com.academy.services;

import com.academy.model.AttachmentDTO;
import com.academy.persistence.entity.Attachment;
import com.academy.persistence.repositories.AttachmentRepository;
import com.academy.util.AttachmentUtils;
import com.academy.util.ContentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentUtils attachmentUtils;
    private final ContentUtils contentUtils;

    public AttachmentDTO findById(Long id) {
        Attachment attachment = attachmentRepository.findById(id).orElse(null);
        return getAttachmentDto(attachment);
    }

    public void deleteById(Long id) {
        attachmentRepository.deleteById(id);
    }

    public List<AttachmentDTO> findAllByTaskId(Long id) {
        List<Attachment> attachmentList = attachmentRepository.findAllByTaskId(id);
        return getAttachmentDtoList(attachmentList);
    }

    public ResponseEntity downloadFile(Long attachmentId) throws SQLException, IOException {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElse(null);

        InputStream is = attachment.getFileData().getBinaryStream();
        byte[] fileData = attachmentUtils.toByte(is);
        ByteArrayResource resource = new ByteArrayResource(fileData);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + attachment.getFileName())
                .contentType(MediaType.MULTIPART_FORM_DATA).contentLength(fileData.length)
                .body(resource);
    }

    private AttachmentDTO getAttachmentDto(Attachment attachment) {
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setId(attachment.getId());
        attachmentDTO.setFileName(contentUtils.getParam(attachment.getFileName()));
        attachmentDTO.setFileData(attachment.getFileData());
        return attachmentDTO;
    }

    private List<AttachmentDTO> getAttachmentDtoList(List<Attachment> attachmentList) {
        List<AttachmentDTO> attachmentDTOList = new ArrayList<>();
        for (Attachment attachment : attachmentList) {
            AttachmentDTO attachmentDTO = new AttachmentDTO();
            attachmentDTO.setId(attachment.getId());
            attachmentDTO.setFileName(contentUtils.getParam(attachment.getFileName()));
            attachmentDTO.setFileData(attachment.getFileData());
            attachmentDTOList.add(attachmentDTO);
        }
        return attachmentDTOList;
    }
}