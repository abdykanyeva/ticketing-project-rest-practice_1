package com.cydeo.controller;


import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.entity.Project;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjects(){
        List<ProjectDTO> listAllProjects = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved", listAllProjects, HttpStatus.OK));
    }

    @GetMapping("/{code}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") ProjectDTO projectDTO){
        return ResponseEntity.ok(new ResponseWrapper("Projects are retrieved by code", projectService.getByProjectCode(projectDTO.getProjectCode()), HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Manager", "Manager"})
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO){
        projectService.save(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully  created", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO project){
        projectService.update(project);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("Project is successfully updated", HttpStatus.OK));
    }

    @DeleteMapping("{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String projectCode){
        projectService.delete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project successfully deleted", HttpStatus.OK));
    }

    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
       List<ProjectDTO> listAllProjects =  projectService.listAllProjectDetails();

       return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved", listAllProjects, HttpStatus.OK));

    }


    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode){
        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully completed", HttpStatus.OK));
    }

}
