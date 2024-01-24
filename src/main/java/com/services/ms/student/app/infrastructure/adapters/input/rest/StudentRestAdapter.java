package com.services.ms.student.app.infrastructure.adapters.input.rest;

import com.services.ms.student.app.application.ports.input.StudentServicePort;
import com.services.ms.student.app.infrastructure.adapters.input.rest.mapper.StudentRestMapper;
import com.services.ms.student.app.infrastructure.adapters.input.rest.model.request.StudentCreateRequest;
import com.services.ms.student.app.infrastructure.adapters.input.rest.model.response.StudentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentRestAdapter {

  private final StudentServicePort servicePort;
  private final StudentRestMapper restMapper;

  @GetMapping("/v1/api")
  public List<StudentResponse> findAll() {
    return restMapper.toStudentResponseList(servicePort.findAll());
  }

  @GetMapping("/v1/api/{id}")
  public StudentResponse findById(@PathVariable Long id) {
    return restMapper.toStudentResponse(servicePort.findById(id));
  }

  @PostMapping("/v1/api")
  public ResponseEntity<StudentResponse> save(@Valid @RequestBody StudentCreateRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(restMapper.toStudentResponse(
            servicePort.save(restMapper.toStudent(request))));
  }

  @PutMapping("/v1/api/{id}")
  public StudentResponse update(@PathVariable Long id, @Valid @RequestBody StudentCreateRequest request) {
    return restMapper.toStudentResponse(
        servicePort.update(id, restMapper.toStudent(request)));
  }

  @DeleteMapping("/v1/api/{id}")
  public void delete(@PathVariable Long id) {
    servicePort.deleteById(id);
  }

}
