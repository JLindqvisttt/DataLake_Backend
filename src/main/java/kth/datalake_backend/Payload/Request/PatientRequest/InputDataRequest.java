package kth.datalake_backend.Payload.Request.PatientRequest;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class InputDataRequest {

    @NotBlank
    private MultipartFile files;

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFiles() {
        return files;
    }

    public void setFiles(MultipartFile files) {
        this.files = files;
    }
}
